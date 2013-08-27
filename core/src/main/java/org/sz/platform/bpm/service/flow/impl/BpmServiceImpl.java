package org.sz.platform.bpm.service.flow.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.activity.ActivityRequiredException;
import javax.annotation.Resource;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.sz.core.bpm.model.ProcessExecution;
import org.sz.core.bpm.model.ProcessTask;
import org.sz.core.bpm.model.ProcessTaskHistory;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.DateUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.BpmDao;
import org.sz.platform.bpm.dao.flow.BpmDefinitionDao;
import org.sz.platform.bpm.dao.flow.ExecutionDao;
import org.sz.platform.bpm.dao.flow.ProcessRunDao;
import org.sz.platform.bpm.dao.flow.TaskDao;
import org.sz.platform.bpm.dao.flow.TaskHistoryDao;
import org.sz.platform.bpm.dao.flow.TaskUserDao;
import org.sz.platform.bpm.model.flow.BpmDefinition;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.model.flow.NodeTranUser;
import org.sz.platform.bpm.model.flow.NodeUserMap;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.model.flow.TaskFork;
import org.sz.platform.bpm.model.flow.TaskNodeStatus;
import org.sz.platform.bpm.model.flow.TaskOpinion;
import org.sz.platform.bpm.model.flow.TaskUser;
import org.sz.platform.bpm.service.flow.BpmNodeSetService;
import org.sz.platform.bpm.service.flow.BpmNodeUserService;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.TaskForkService;
import org.sz.platform.bpm.service.flow.TaskOpinionService;
import org.sz.platform.bpm.service.flow.thread.TaskThreadService;
import org.sz.platform.bpm.util.BpmUtil;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.SysUserService;

@Service("bpmService")
public class BpmServiceImpl implements BpmService {
	private Logger logger = LoggerFactory.getLogger(BpmServiceImpl.class);

	@Resource
	private BpmDao bpmDao;

	@Resource
	private TaskDao taskDao;
	
	

	@Resource
	private ProcessRunDao processRunDao;

	@Resource
	private ExecutionDao executionDao;

	@Resource
	private TaskUserDao taskUserDao;

	@Resource
	private TaskOpinionService taskOpinionService;

	@Resource
	private TaskHistoryDao taskHistoryDao;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private TaskService taskService;

	@Resource
	private HistoryService historyService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	ProcessEngineConfiguration processEngineConfiguration;

	@Resource
	BpmDefinitionDao bpmDefinitionDao;

	@Resource
	BpmNodeSetService bpmNodeSetService;

	@Resource
	TaskForkService taskForkService;

	@Resource
	BpmNodeUserService bpmNodeUserService;

	@Resource
	SysUserService sysUserService;
	private static Lock lockTransto = new ReentrantLock();

	private static Lock lockComplete = new ReentrantLock();

	public ProcessInstance startFlowById(String proessDefId, Map<String, Object> variables) {
		ProcessInstance instance = this.runtimeService.startProcessInstanceById(proessDefId, variables);

		return instance;
	}

	public ProcessInstance startFlowById(String porcessDefId, String businessKey, Map<String, Object> variables) {
		ProcessInstance processInstance = this.runtimeService.startProcessInstanceById(porcessDefId, businessKey, variables);

		return processInstance;
	}

	public ProcessInstance startFlowByKey(String processDefKey, String businessKey, Map<String, Object> variables) {
		ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey(processDefKey, businessKey, variables);

		return processInstance;
	}

	public Deployment deploy(String name, String xml) {
		InputStream stream = null;
		try {
			stream = new ByteArrayInputStream(xml.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Deployment deploy = this.repositoryService.createDeployment().name(name).addString("bpmn20.xml", xml).deploy();

		if(stream != null){
			Deployment deploy = this.repositoryService.createDeployment().name(name).addInputStream("bpmn20.xml", stream).deploy();
			return deploy;
		}
		return null;
	}

	public void transTo(String taskId, String toNode) throws ActivityRequiredException {
		transTo(taskId, toNode, null);
	}

	public ProcessDefinitionEntity getProcessDefinitionEntity(String processDefinitionId) {
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) this.repositoryService).getDeployedProcessDefinition(processDefinitionId);
		return processDefinition;
	}

	public void transTo(String taskId, String toNode, Map<String, Object> variables) throws ActivityRequiredException {
		TaskEntity task = getTask(taskId);

		ProcessDefinitionEntity processDefinition = getProcessDefinitionEntity(task.getProcessDefinitionId());

		ActivityImpl curActi = processDefinition.findActivity(task.getTaskDefinitionKey());

		BpmNodeSet bpmNodeSet = null;

		ActivityImpl destAct = null;

		boolean isNeedRemoveTran = false;

		if ("_RULE_INVALID".equals(toNode)) {
			isNeedRemoveTran = true;
		} else {
			if (StringUtils.isEmpty(toNode)) {
				for (PvmTransition tran : curActi.getOutgoingTransitions()) {
					String destActId = tran.getDestination().getId();
					bpmNodeSet = this.bpmNodeSetService.getByActDefIdJoinTaskKey(task.getProcessDefinitionId(), destActId);
					if (bpmNodeSet != null) {
						destAct = (ActivityImpl) tran.getDestination();
						break;
					}
				}
			} else
				destAct = processDefinition.findActivity(toNode);

			if (curActi == destAct) {
				throw new ActivitiException("不能跳转到本节点!");
			}
			if (destAct == null) {
				this.taskService.complete(task.getId(), variables);
				return;
			}

			if (bpmNodeSet == null) {
				bpmNodeSet = this.bpmNodeSetService.getByActDefIdJoinTaskKey(task.getProcessDefinitionId(), destAct.getId());
			}
			if (bpmNodeSet != null) {
				String token = (String) this.taskService.getVariableLocal(task.getId(), TaskFork.TAKEN_VAR_NAME);
				if (token != null) {
					TaskFork taskFork = this.taskForkService.getByInstIdJoinTaskKeyForkToken(task.getProcessInstanceId(), destAct.getId(), token);
					if (taskFork != null) {
						if (taskFork.getFininshCount().intValue() < taskFork.getForkCount().intValue() - 1) {
							taskFork.setFininshCount(Integer.valueOf(taskFork.getFininshCount().intValue() + 1));
							this.taskForkService.update(taskFork);

							String[] tokenSplits = token.split("[_]");
							if (tokenSplits.length == 2) {
								this.taskService.setVariableLocal(task.getId(), TaskFork.TAKEN_VAR_NAME, null);
							}
							isNeedRemoveTran = true;
						} else {
							this.taskForkService.delById(taskFork.getTaskForkId());

							String[] tokenSplits = token.split("[_]");
							if (tokenSplits.length == 2) {
								this.taskService.setVariableLocal(task.getId(), TaskFork.TAKEN_VAR_NAME, null);
							} else if (tokenSplits.length >= 3) {
								String newToken = token.substring(0, token.lastIndexOf("_" + tokenSplits[(tokenSplits.length - 1)]));
								this.taskService.setVariableLocal(task.getId(), TaskFork.TAKEN_VAR_NAME, newToken);
							}
						}
					}
				}
			}

		}

		List backTransList = new ArrayList();
		backTransList.addAll(curActi.getOutgoingTransitions());
		try {
			lockTransto.lock();

			curActi.getOutgoingTransitions().clear();
			if (!isNeedRemoveTran) {
				TransitionImpl transitionImpl = curActi.createOutgoingTransition();
				transitionImpl.setDestination(destAct);
			}

			this.taskService.complete(task.getId(), variables);
		} finally {
			curActi.getOutgoingTransitions().clear();

			curActi.getOutgoingTransitions().addAll(backTransList);

			lockTransto.unlock();
		}
	}

	public void onlyCompleteTask(String taskId, Map variables) {
		TaskEntity task = getTask(taskId);

		ProcessDefinitionEntity processDefinition = getProcessDefinitionEntity(task.getProcessDefinitionId());

		ActivityImpl curActi = processDefinition.findActivity(task.getTaskDefinitionKey());

		lockComplete.lock();

		List backTransList = new ArrayList();
		backTransList.addAll(curActi.getOutgoingTransitions());
		try {
			curActi.getOutgoingTransitions().clear();

			this.taskService.complete(task.getId(), variables);
		} finally {
			curActi.getOutgoingTransitions().addAll(backTransList);
		}

		lockComplete.unlock();
	}

	public ProcessDefinitionEntity getProcessDefinitionByDeployId(String deployId) {
		ProcessDefinition proDefinition = (ProcessDefinition) this.repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
		if (proDefinition == null)
			return null;
		return getProcessDefinitionByDefId(proDefinition.getId());
	}

	public ProcessDefinitionEntity getProcessDefinitionByDefId(String actDefId) {
		ProcessDefinitionEntity ent = (ProcessDefinitionEntity) ((RepositoryServiceImpl) this.repositoryService).getDeployedProcessDefinition(actDefId);

		return ent;
	}

	public ProcessDefinitionEntity getProcessDefinitionByTaskId(String taskId) {
		TaskEntity taskEntity = (TaskEntity) this.taskService.createTaskQuery().taskId(taskId).singleResult();
		return getProcessDefinitionByDefId(taskEntity.getProcessDefinitionId());
	}

	public List<ProcessDefinition> getProcessDefinitionByKey(String key) {
		List list = this.repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).list();
		return list;
	}

	public ProcessDefinitionEntity getProcessDefinitionByProcessInanceId(String processInstanceId) {
		String processDefinitionId = null;
		ProcessInstance processInstance = (ProcessInstance) this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if (processInstance == null) {
			HistoricProcessInstance hisProInstance = (HistoricProcessInstance) this.historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			processDefinitionId = hisProInstance.getProcessDefinitionId();
		} else {
			processDefinitionId = processInstance.getProcessDefinitionId();
		}
		return getProcessDefinitionByDefId(processDefinitionId);
	}

	public List<String> getActiveTasks(String taskId) {
		List acts = new ArrayList();
		TaskEntity taskEntity = (TaskEntity) this.taskService.createTaskQuery().taskId(taskId).singleResult();

		List<Task> tasks = this.taskService.createTaskQuery().processInstanceId(taskEntity.getProcessInstanceId()).list();

		for (Task task : tasks) {
			acts.add(task.getName());
		}

		return acts;
	}

	public Map<String, String> getOutNodesByTaskId(String taskId) {
		Map map = new HashMap();
		Task task = getTask(taskId);
		ProcessDefinitionEntity ent = (ProcessDefinitionEntity) ((RepositoryServiceImpl) this.repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());

		ActivityImpl curActi = ent.findActivity(task.getTaskDefinitionKey());

		List<PvmTransition> outs = curActi.getOutgoingTransitions();
		for (PvmTransition tran : outs) {
			ActivityImpl destNode = (ActivityImpl) tran.getDestination();
			map.put(destNode.getId(), (String) destNode.getProperty("name"));
		}
		return map;
	}

	public List<String> getActiveActIdsByTaskId(String taskId) {
		TaskEntity taskEntity = (TaskEntity) this.taskService.createTaskQuery().taskId(taskId).singleResult();
		return getActiveActIdsByProcessInstanceId(taskEntity.getProcessInstanceId());
	}

	public List<String> getActiveActIdsByProcessInstanceId(String processInstanceId) {
		List acts = new ArrayList();
		List<TaskEntity> taskList = getTasks(processInstanceId);

		for (TaskEntity entity : taskList) {
			acts.add(entity.getTaskDefinitionKey());
		}

		return acts;
	}

	public String getDefXmlByDeployId(String deployId) {
		return this.bpmDao.getDefXmlByDeployId(deployId);
	}

	public String getDefXmlByProcessDefinitionId(String processDefinitionId) {
		ProcessDefinitionEntity entity = getProcessDefinitionByDefId(processDefinitionId);
		String defXml = getDefXmlByDeployId(entity.getDeploymentId());
		return defXml;
	}

	public void wirteDefXml(String deployId, String defXml) {
		this.bpmDao.wirteDefXml(deployId, defXml);
		ProcessDefinitionEntity ent = getProcessDefinitionByDeployId(deployId);

		//TODO
		//((ProcessEngineConfigurationImpl) this.processEngineConfiguration).getDeploymentCache().removeProcessDefinition(ent.getId());
	}

	public List<ActivityImpl> getActivityNodes(String actDefId) {
		ProcessDefinitionEntity ent = (ProcessDefinitionEntity) ((RepositoryServiceImpl) this.repositoryService).getDeployedProcessDefinition(actDefId);
		return ent.getActivities();
	}

	public TaskEntity getTask(String taskId) {
		TaskEntity taskEntity = (TaskEntity) this.taskService.createTaskQuery().taskId(taskId).singleResult();
		return taskEntity;
	}

	public Map<String, Map<String, String>> getJumpNodes(String taskId) {
		List actIds = getActiveActIdsByTaskId(taskId);
		TaskEntity taskEntity = (TaskEntity) this.taskService.createTaskQuery().taskId(taskId).singleResult();

		String defXml = getDefXmlByProcessDefinitionId(taskEntity.getProcessDefinitionId());

		Map map = BpmUtil.getTranstoActivitys(defXml, actIds);

		return map;
	}
	
	/**
	 * 得到创建节点
	 * 
	 * */
	public void getJumpNode1s(String taskId) {
		TaskEntity taskEntity = (TaskEntity) this.taskService.createTaskQuery().taskId(taskId).singleResult();

		List<Task> tasks = this.taskService.createTaskQuery().processInstanceId(taskEntity.getProcessInstanceId()).list();

	}

	public Map<String, String> getTaskNodes(String actDefId, String nodeId) {
		Map nodeMaps = getExecuteNodesMap(actDefId, true);

		if (nodeMaps.containsKey(nodeId)) {
			nodeMaps.remove(nodeId);
		}
		return nodeMaps;
	}

	protected Map<String, String> getExecuteNodes(ActivityImpl actImpl) {
		Map nodeMap = new HashMap();
		List<ActivityImpl> acts = actImpl.getActivities();
		if (acts.size() == 0)
			return nodeMap;
		for (ActivityImpl act : acts) {
			String nodeType = (String) act.getProperties().get("type");
			if (nodeType.indexOf("Task") != -1) {
				String name = (String) act.getProperties().get("name");
				nodeMap.put(act.getId(), name);
			} else if ("subProcess".equals(nodeType)) {
				nodeMap.putAll(getExecuteNodes(act));
			}
		}
		return nodeMap;
	}

	public List<String> getExecuteNodes(String actDefId) {
		List values = new ArrayList();
		Map nodeMap = getExecuteNodesMap(actDefId, true);

		values.addAll(nodeMap.values());

		Iterator valuesIt = nodeMap.values().iterator();
		while (valuesIt.hasNext()) {
			String value = (String) valuesIt.next();
			if (StringUtils.isNotEmpty(value)) {
				values.add(value);
			}
		}

		return values;
	}

	public Map<String, String> getExecuteNodesMap(String actDefId, boolean includeSubProcess) {
		Map nodeMap = new HashMap();

		List<ActivityImpl> acts = getActivityNodes(actDefId);
		for (ActivityImpl actImpl : acts) {
			String nodeType = (String) actImpl.getProperties().get("type");

			if (nodeType.indexOf("Task") != -1) {
				String name = (String) actImpl.getProperties().get("name");
				nodeMap.put(actImpl.getId(), name);
			} else if ((includeSubProcess) && ("subProcess".equals(nodeType))) {
				nodeMap.putAll(getExecuteNodes(actImpl));
			}
		}
		return nodeMap;
	}

	public List<TaskEntity> getTasks(QueryFilter queryFilter) {
		return this.taskDao.getAll(queryFilter);
	}

	public List<TaskEntity> getMyTasks(QueryFilter queryFilter) {
		return this.taskDao.getMyTasks(ContextUtil.getCurrentUserId(), queryFilter);
	}
	
	public List<TaskEntity> getMyCcTasks(QueryFilter queryFilter) {
		return this.taskDao.getCcTasks(ContextUtil.getCurrentUserId(), queryFilter);
	}
	
	public boolean isEndProcess(String processInstanceId) {
		HistoricProcessInstance his = (HistoricProcessInstance) this.historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

		return (his != null) && (his.getEndTime() != null);
	}

	public boolean isSignTask(TaskEntity taskEntity) {
		RepositoryServiceImpl impl = (RepositoryServiceImpl) this.repositoryService;

		ProcessDefinitionEntity ent = (ProcessDefinitionEntity) impl.getDeployedProcessDefinition(taskEntity.getProcessDefinitionId());

		ActivityImpl taskAct = ent.findActivity(taskEntity.getTaskDefinitionKey());

		String multiInstance = (String) taskAct.getProperty("multiInstance");

		return multiInstance != null;
	}

	public List<HistoricTaskInstance> getHistoryTasks(String processInstanceId) {
		List list = this.historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
		return list;
	}

	public HistoricTaskInstanceEntity getHistoricTaskInstanceEntity(String taskId) {
		return (HistoricTaskInstanceEntity) this.historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
	}

	public void assignTask(String taskId, String userId) {
		this.taskService.setOwner(taskId, userId);
		this.taskService.setAssignee(taskId, userId);
	}

	public void setDueDate(String taskId, Date dueDate) {
		this.taskDao.setDueDate(taskId, dueDate);
	}

	public ExecutionEntity getExecution(String executionId) {
		Execution execution = (Execution) this.runtimeService.createExecutionQuery().executionId(executionId).singleResult();
		return (ExecutionEntity) execution;
	}

	public ExecutionEntity getExecutionByTaskId(String taskId) {
		TaskEntity taskEntity = getTask(taskId);
		return getExecution(taskEntity.getExecutionId());
	}

	public Map<String, Object> getVarsByTaskId(String taskId) {
		return this.taskService.getVariables(taskId);
	}

	public void setExecutionVariable(String executionId, String variableName, Object varVal) {
		this.runtimeService.setVariable(executionId, variableName, varVal);
	}

	public void setTaskVariable(String taskId, String variableName, Object varVal) {
		this.taskService.setVariable(taskId, variableName, varVal);
	}

	public ProcessTask newTask(String orgTaskId, String assignee) {
		String newExecutionId = UniqueIdUtil.getNextId();
		String newTaskId = UniqueIdUtil.getNextId();

		TaskEntity taskEntity = getTask(orgTaskId);
		ExecutionEntity executionEntity = null;

		executionEntity = getExecution(taskEntity.getExecutionId());

		ProcessExecution newExecution = new ProcessExecution(executionEntity);

		newExecution.setId(newExecutionId);

		ProcessTask newTask = new ProcessTask();
		BeanUtils.copyProperties(newTask, taskEntity);
		newTask.setId(newTaskId);
		newTask.setExecutionId(newExecutionId);
		newTask.setCreateTime(new Date());

		newTask.setAssignee(assignee);
		newTask.setOwner(assignee);

		ProcessTaskHistory newTaskHistory = new ProcessTaskHistory(taskEntity);
		newTaskHistory.setAssignee(assignee);
		newTaskHistory.setStartTime(new Date());
		newTaskHistory.setId(newTaskId);
		newTaskHistory.setOwner(assignee);

		this.executionDao.add(newExecution);
		this.taskDao.insertTask(newTask);
		this.taskHistoryDao.add(newTaskHistory);

		return newTask;
	}

	public void newForkTasks(TaskEntity taskEntity, Set<String> uIds, String userType) {
		String token = (String) taskEntity.getVariableLocal(TaskFork.TAKEN_VAR_NAME);
		if (token == null)
			token = TaskFork.TAKEN_PRE;
		Iterator uIt = uIds.iterator();
		int i = 0;
		while (uIt.hasNext()) {
			if (i++ == 0) {
				assignTask(taskEntity, (String) uIt.next(), userType);

				taskEntity.setVariableLocal(TaskFork.TAKEN_VAR_NAME, token + "_" + i);

				changeTaskExecution(taskEntity);
				continue;
			}
			ProcessTask processTask = newTask(taskEntity, (String) uIt.next(), userType);

			TaskEntity newTask = getTask(processTask.getId());

			TaskThreadService.addTask(newTask);

			this.taskService.setVariableLocal(processTask.getId(), TaskFork.TAKEN_VAR_NAME, token + "_" + i);
			TaskOpinion taskOpinion = new TaskOpinion(processTask);
			try {
				taskOpinion.setOpinionId(Long.valueOf(UniqueIdUtil.genId()));
				taskOpinion.setTaskToken(token);
				this.taskOpinionService.add(taskOpinion);
			} catch (Exception ex) {
				this.logger.error(ex.getMessage());
			}
		}
	}

	public void newForkTasks(TaskEntity taskEntity, List<String> uIdList) {
		Set uIdSet = new HashSet();
		uIdSet.addAll(uIdList);
		newForkTasks(taskEntity, uIdSet, "user");
	}

	public void assignTask(TaskEntity taskEntity, String uId, String userType) {
		if ("user".equals(userType)) {
			taskEntity.setAssignee(uId.toString());
			taskEntity.setOwner(uId.toString());
		} else {
			taskEntity.addGroupIdentityLink(uId, userType);
		}
	}

	protected void changeTaskExecution(TaskEntity taskEntity) {
		String newExecutionId = UniqueIdUtil.getNextId();
		ProcessExecution newExecution = new ProcessExecution(taskEntity.getExecution());
		newExecution.setId(newExecutionId);
		this.executionDao.add(newExecution);

		taskEntity.setExecutionId(newExecutionId);
	}

	protected ProcessTask newTask(TaskEntity taskEntity, String uId, String userType) {
		String newExecutionId = UniqueIdUtil.getNextId();
		String newTaskId = UniqueIdUtil.getNextId();

		ProcessExecution newExecution = new ProcessExecution(taskEntity.getExecution());
		newExecution.setId(newExecutionId);

		ProcessTask newTask = new ProcessTask();
		BeanUtils.copyProperties(newTask, taskEntity);
		newTask.setId(newTaskId);
		newTask.setExecutionId(newExecutionId);
		newTask.setCreateTime(new Date());
		ProcessTaskHistory newTaskHistory = new ProcessTaskHistory(taskEntity);

		TaskUser taskUser = null;

		if ("user".equals(userType)) {
			newTask.setAssignee(uId.toString());
			newTask.setOwner(uId.toString());
			newTaskHistory.setAssignee(uId.toString());
			newTaskHistory.setOwner(uId.toString());
		} else {
			taskUser = new TaskUser();
			taskUser.setId(UniqueIdUtil.getNextId());
			taskUser.setGroupId(uId);
			taskUser.setType(userType);
			taskUser.setReversion(Integer.valueOf(1));
			taskUser.setTaskId(newTaskId);
		}

		newTaskHistory.setStartTime(new Date());
		newTaskHistory.setId(newTaskId);

		this.executionDao.add(newExecution);
		this.taskDao.insertTask(newTask);
		this.taskHistoryDao.add(newTaskHistory);
		if (taskUser != null) {
			this.taskUserDao.add(taskUser);
		}

		return newTask;
	}

	public List<TaskEntity> getTasks(String processInstanceId) {
		List taskList = this.taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		return taskList;
	}

	public List<Long> getCandidateUsers(Long taskId) {
		return this.taskDao.getCandidateUsers(taskId);
	}

	public void saveCondition(long defId, String forkNode, Map<String, String> map) throws IOException {
		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionDao.getById(Long.valueOf(defId));
		String deployId = bpmDefinition.getActDeployId().toString();
		String defXml = this.bpmDao.getDefXmlByDeployId(deployId);
		String graphXml = bpmDefinition.getDefXml();
		defXml = BpmUtil.setCondition(forkNode, map, defXml);
		graphXml = BpmUtil.setGraphXml(forkNode, map, graphXml);
		bpmDefinition.setDefXml(graphXml);
		this.bpmDao.wirteDefXml(deployId, defXml);

		this.bpmDefinitionDao.update(bpmDefinition);

		//((ProcessEngineConfigurationImpl) this.processEngineConfiguration).getDeploymentCache().removeProcessDefinition(bpmDefinition.getActDefId());
	}

	public void delLoopAssigneeVars(String executionId) {
		this.executionDao.delLoopAssigneeVars(executionId);
	}

	public List<TaskEntity> getAgentTasks(Long userId, String actDefId, QueryFilter queryFilter) {
		return this.taskDao.getAgentTasks(userId, actDefId, queryFilter);
	}

	public List<Long> getAgentIdByTaskId(QueryFilter queryFilter) {
		return this.taskDao.getAgentIdByTaskId(queryFilter);
	}

	public List<TaskEntity> getTaskByUserId(Long agentuserid, QueryFilter filter) {
		return this.taskDao.getMyTasks(agentuserid, filter);
	}

	public List<TaskEntity> getAllAgentTask(Long userId, QueryFilter filter) {
		return this.taskDao.getAllAgentTask(userId, filter);
	}

	public String getMyEvents(Object params) {
		List list = this.taskDao.getMyEvents(params);

		Map map = (Map) params;
		String mode = (String) map.get("mode");
		StringBuffer sb = new StringBuffer();
		sb.append("[");

		for (int idx = 0; idx < list.size(); idx++) {
			Object obj = list.get(idx);
			ProcessTask task = (ProcessTask) obj;

			sb.append("{\"id\":\"").append(task.getId()).append("\",");

			Date startTime = task.getCreateTime();
			if (startTime == null) {
				Calendar curCal = Calendar.getInstance();
				startTime = curCal.getTime();
			}

			Date endTime = task.getDueDate();
			if ((endTime == null) && ("month".equals(mode))) {
				endTime = startTime;
			}

			String sTime = DateUtil.formatEnDate(startTime);
			String eTime = endTime == null ? "" : DateUtil.formatEnDate(endTime);

			String eTime0 = "";
			if (("month".equals(mode)) && (sTime.substring(0, 10).equals(eTime.substring(0, 10)))) {
				String[] dateArr = sTime.substring(0, 10).split("/");
				eTime0 = DateUtil.addOneDay(new StringBuilder().append(dateArr[2]).append("-").append(dateArr[0]).append("-").append(dateArr[1]).toString()) + " 00:00:00 AM";
			}

			if (!"month".equals(mode)) {
				String[] dateArr = sTime.substring(0, 10).split("/");
				eTime0 = DateUtil.addOneHour(dateArr[2] + "-" + dateArr[0] + "-" + dateArr[1] + sTime.substring(10, sTime.length()));
			}

			sb.append("\"taskType\":\"").append("2").append("\",");
			sb.append("\"startTime\":\"");

			if ("month".equals(mode))
				sb.append(sTime.substring(0, 10) + " 00:00:00 AM").append("\",");
			else {
				sb.append(sTime).append("\",");
			}

			if (!eTime0.equals(""))
				sb.append("\"endTime\":\"").append(eTime0).append("\",");
			else {
				sb.append("\"endTime\":\"").append(eTime).append("\",");
			}

			sb.append("\"summary\":\"").append(task.getSubject()).append("\",");
			sb.append("\"description\":\"").append(task.getProcessName()).append("\",");
			sb.append("\"status\":\"").append("0").append("\",");
			sb.append("\"params\":\"").append(task.getName()).append("[,]" + task.getDescription() == null ? task.getName() : task.getDescription()).append("[,]" + sTime);

			if (task.getDueDate() != null)
				sb.append("[,]" + eTime);
			else {
				sb.append("[,]null");
			}
			sb.append("[,]" + task.getOwner()).append("[,]" + task.getAssignee()).append("\"},");
		}

		if (list.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");

		return sb.toString();
	}

	public List<NodeTranUser> getNodeTaskUserMap(String taskId, Long preUserId) {
		TaskEntity task = getTask(taskId);

		ProcessDefinitionEntity ent = (ProcessDefinitionEntity) ((RepositoryServiceImpl) this.repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());

		ActivityImpl curActi = ent.findActivity(task.getTaskDefinitionKey());

		List<PvmTransition> trans = curActi.getOutgoingTransitions();

		Long curUserId = null;

		ProcessRun processRun = this.processRunDao.getByActInstanceId(task.getProcessInstanceId());

		if (processRun != null) {
			curUserId = processRun.getCreatorId();
		}

		return getNodeUserMap(ent, trans, curUserId, preUserId);
	}

	private List<NodeTranUser> getNodeUserMap(ProcessDefinitionEntity proDefEnt, List<PvmTransition> trans, Long curUserId, Long preUserId) {
		List<NodeTranUser> nodeList = new ArrayList<NodeTranUser>();

		for (PvmTransition tran : trans) {
			if ((tran != null) && (tran.getDestination() != null)) {
				ActivityImpl destAct = (ActivityImpl) tran.getDestination();
				String nodeId = destAct.getId();

				String nodeName = (String) destAct.getProperty("name");

				String nodeType = (String) destAct.getProperty("type");

				Set<NodeUserMap> nodeUserMapSet = new LinkedHashSet<NodeUserMap>();
				
				BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(proDefEnt.getId(), nodeId);
				
				Short assignMode = null;
				if (bpmNodeSet != null) {
					assignMode = bpmNodeSet.getAssignMode();
				}
				
				NodeTranUser nodeTranUser = new NodeTranUser(nodeId, nodeName, nodeUserMapSet);

				if ((nodeType != null) && (nodeType.indexOf("Gateway") != -1)) {
					List<PvmTransition> subTrans = getTransByPdActId(proDefEnt, nodeId);
					genUserMap(proDefEnt, subTrans, nodeUserMapSet, curUserId, preUserId);
				} else if ("userTask".equals(nodeType)) {
					Set<SysUser> users = getNodeHandlerUsers(proDefEnt.getId(), nodeId, curUserId, preUserId);
					nodeUserMapSet.add(new NodeUserMap(nodeId, nodeName, users,assignMode));
				}
				nodeList.add(nodeTranUser);
			}
		}
		return nodeList;
	}

	private void genUserMap(ProcessDefinitionEntity pd, List<PvmTransition> trans, Set<NodeUserMap> nodeUserMapSet, Long curUserId, Long preUserId) {
		for (PvmTransition tran : trans)
			if ((tran != null) && (tran.getDestination() != null)) {
				ActivityImpl destAct = (ActivityImpl) tran.getDestination();
				String nodeId = destAct.getId();
				String nodeName = (String) destAct.getProperty("name");
				String nodeType = (String) destAct.getProperty("type");
				
				BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(pd.getId(), nodeId);
				
				Short assignMode = null;
				if (bpmNodeSet != null) {
					assignMode = bpmNodeSet.getAssignMode();
				}
				
				if ((nodeType != null) && (nodeType.indexOf("Gateway") != -1)) {
					List<PvmTransition> subTrans = getTransByPdActId(pd, nodeId);
					genUserMap(pd, subTrans, nodeUserMapSet, curUserId, preUserId);
				} else if ((nodeType != null) && (nodeType.indexOf("subProcess") != -1)) {
					Set<SysUser> users = getNodeHandlerUsers(pd.getId(), nodeId, curUserId, preUserId);
					nodeUserMapSet.add(new NodeUserMap(nodeId, nodeName, users, assignMode));
//					ActivityImpl actImpl = pd.findActivity(nodeId);
//					List<ActivityImpl> list = actImpl.getActivities();
//					for(ActivityImpl activityImpl:list){
//						String id = activityImpl.getId();
//						String name = (String) activityImpl.getProperty("name");
//						String type = (String) activityImpl.getProperty("type");
//						BpmNodeSet bpmNodeSet1 = bpmNodeSetService.getByActDefIdNodeId(pd.getId(), id);
//						
//						Short assignMode1 = null;
//						if (bpmNodeSet1 != null) {
//							assignMode1 = bpmNodeSet1.getAssignMode();
//						}
//						if ("userTask".equals(type)) {
//							Set<SysUser> users = getNodeHandlerUsers(pd.getId(), id, curUserId, preUserId);
//							nodeUserMapSet.add(new NodeUserMap(id, name, users, assignMode1));
//						}
//					}
				} else if ("userTask".equals(nodeType)) {
					Set<SysUser> users = getNodeHandlerUsers(pd.getId(), nodeId, curUserId, preUserId);
					nodeUserMapSet.add(new NodeUserMap(nodeId, nodeName, users, assignMode));
				}
			}
	}

	private List<PvmTransition> getTransByPdActId(ProcessDefinitionEntity pdEntity, String nodeId) {
		ActivityImpl actImpl = pdEntity.findActivity(nodeId);
		return actImpl.getOutgoingTransitions();
	}

	public Set<SysUser> getNodeHandlerUsers(String actDefId, String nodeId) {
		List<String> userIdList = this.bpmNodeUserService.getExeUserIdsByInstance(actDefId, nodeId, "");
		Set uSet = new HashSet();
		for (String uId : userIdList)
			try {
				SysUser sysUser = (SysUser) this.sysUserService.getById(new Long(uId));
				uSet.add(sysUser);
			} catch (Exception ex) {
				this.logger.error(ex.getMessage());
			}
		return uSet;
	}

	public Set<SysUser> getNodeHandlerUsers(String actDefId, String nodeId, Long startUserId, Long preUserId) {
		List<String> userIdList = this.bpmNodeUserService.getExeUserIds(actDefId, null, nodeId, startUserId.toString(), preUserId.toString());
		Set uSet = new HashSet();
		for (String uId : userIdList)
			try {
				SysUser sysUser = (SysUser) this.sysUserService.getById(new Long(uId));
				uSet.add(sysUser);
			} catch (Exception ex) {
				this.logger.error(ex.getMessage());
			}
		return uSet;
	}

	public void deleteTask(String taskId) {
		this.taskService.deleteTask(taskId);
		this.taskOpinionService.delByTaskId(new Long(taskId));
	}

	public void deleteTasks(String[] taskIds) {
		for (String taskId : taskIds)
			deleteTask(taskId);
	}

	public void updateTaskAssignee(String taskId, String userId) {
		this.taskDao.updateTaskAssignee(taskId, userId);
	}

	public void updateTaskAssigneeNull(String taskId) {
		this.taskDao.updateTaskAssigneeNull(taskId);
	}

	public void updateTaskOwner(String taskId, String userId) {
		this.taskDao.updateTaskOwner(taskId, userId);
	}

	public ProcessInstance getProcessInstance(String actInstId) {
		ProcessInstance processInstance = (ProcessInstance) this.runtimeService.createProcessInstanceQuery().processInstanceId(actInstId).singleResult();
		return processInstance;
	}

	public List<TaskNodeStatus> getNodeCheckStatusInfo(String actInstId) {
		List taskNodeStatusList = new ArrayList();
		ProcessRun processRun = this.processRunDao.getByActInstanceId(actInstId);
		String actDefId = processRun.getActDefId();
		Map nodeMaps = getExecuteNodesMap(actDefId, true);
		Set taskNodeSet = nodeMaps.keySet();
		Iterator taskNodeIt = taskNodeSet.iterator();

		while (taskNodeIt.hasNext()) {
			String nodeId = (String) taskNodeIt.next();
			List taskOpinions = this.taskOpinionService.getByActInstIdTaskKey(actInstId, nodeId);

			TaskNodeStatus taskNodeStatus = new TaskNodeStatus();
			taskNodeStatus.setActInstId(actInstId);
			taskNodeStatus.setTaskKey(nodeId);
			if ((taskOpinions != null) && (taskOpinions.size() > 0)) {
				TaskOpinion taskOpinion = (TaskOpinion) taskOpinions.get(0);
				if (taskOpinion.getCheckStatus() == null)
					taskNodeStatus.setLastCheckStatus(TaskOpinion.STATUS_INIT);
				else {
					taskNodeStatus.setLastCheckStatus(taskOpinion.getCheckStatus());
				}
			}
			taskNodeStatus.setTaskOpinionList(taskOpinions);
			taskNodeStatusList.add(taskNodeStatus);
		}

		return taskNodeStatusList;
	}

	
}
