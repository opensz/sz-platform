package org.sz.platform.bpm.service.flow;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activity.ActivityRequiredException;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.sz.core.bpm.model.ProcessTask;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.model.flow.NodeTranUser;
import org.sz.platform.bpm.model.flow.TaskNodeStatus;
import org.sz.platform.system.model.SysUser;

public interface BpmService {

	ProcessInstance startFlowById(String proessDefId,
			Map<String, Object> variables);

	ProcessInstance startFlowById(String porcessDefId, String businessKey,
			Map<String, Object> variables);

	ProcessInstance startFlowByKey(String processDefKey, String businessKey,
			Map<String, Object> variables);

	Deployment deploy(String name, String xml);

	void transTo(String taskId, String toNode) throws ActivityRequiredException;

	ProcessDefinitionEntity getProcessDefinitionEntity(
			String processDefinitionId);

	void transTo(String taskId, String toNode, Map<String, Object> variables)
			throws ActivityRequiredException;

	void onlyCompleteTask(String taskId, Map variables);

	ProcessDefinitionEntity getProcessDefinitionByDeployId(String deployId);

	ProcessDefinitionEntity getProcessDefinitionByDefId(String actDefId);

	ProcessDefinitionEntity getProcessDefinitionByTaskId(String taskId);

	List<ProcessDefinition> getProcessDefinitionByKey(String key);

	ProcessDefinitionEntity getProcessDefinitionByProcessInanceId(
			String processInstanceId);

	List<String> getActiveTasks(String taskId);

	Map<String, String> getOutNodesByTaskId(String taskId);

	List<String> getActiveActIdsByTaskId(String taskId);

	List<String> getActiveActIdsByProcessInstanceId(String processInstanceId);

	String getDefXmlByDeployId(String deployId);

	String getDefXmlByProcessDefinitionId(String processDefinitionId);

	void wirteDefXml(String deployId, String defXml);

	List<ActivityImpl> getActivityNodes(String actDefId);

	TaskEntity getTask(String taskId);

	Map<String, Map<String, String>> getJumpNodes(String taskId);

	/**
	 * 得到创建节点
	 * 
	 * */
	void getJumpNode1s(String taskId);

	Map<String, String> getTaskNodes(String actDefId, String nodeId);

	List<String> getExecuteNodes(String actDefId);

	Map<String, String> getExecuteNodesMap(String actDefId,
			boolean includeSubProcess);

	List<TaskEntity> getTasks(QueryFilter queryFilter);

	List<TaskEntity> getMyTasks(QueryFilter queryFilter);
	
	List<TaskEntity> getMyCcTasks(QueryFilter queryFilter);

	boolean isEndProcess(String processInstanceId);

	boolean isSignTask(TaskEntity taskEntity);

	List<HistoricTaskInstance> getHistoryTasks(String processInstanceId);

	HistoricTaskInstanceEntity getHistoricTaskInstanceEntity(String taskId);

	void assignTask(String taskId, String userId);

	void setDueDate(String taskId, Date dueDate);

	ExecutionEntity getExecution(String executionId);

	ExecutionEntity getExecutionByTaskId(String taskId);

	Map<String, Object> getVarsByTaskId(String taskId);

	void setExecutionVariable(String executionId, String variableName,
			Object varVal);

	void setTaskVariable(String taskId, String variableName, Object varVal);

	ProcessTask newTask(String orgTaskId, String assignee);

	void newForkTasks(TaskEntity taskEntity, Set<String> uIds, String userType);

	void newForkTasks(TaskEntity taskEntity, List<String> uIdList);

	void assignTask(TaskEntity taskEntity, String uId, String userType);

	List<TaskEntity> getTasks(String processInstanceId);

	List<Long> getCandidateUsers(Long taskId);

	void saveCondition(long defId, String forkNode, Map<String, String> map)
			throws IOException;

	void delLoopAssigneeVars(String executionId);

	List<TaskEntity> getAgentTasks(Long userId, String actDefId,
			QueryFilter queryFilter);

	List<Long> getAgentIdByTaskId(QueryFilter queryFilter);

	List<TaskEntity> getTaskByUserId(Long agentuserid, QueryFilter filter);

	List<TaskEntity> getAllAgentTask(Long userId, QueryFilter filter);

	String getMyEvents(Object params);

	List<NodeTranUser> getNodeTaskUserMap(String taskId, Long preUserId);

	Set<SysUser> getNodeHandlerUsers(String actDefId, String nodeId);

	Set<SysUser> getNodeHandlerUsers(String actDefId, String nodeId,
			Long startUserId, Long preUserId);

	void deleteTask(String taskId);

	void deleteTasks(String[] taskIds);

	void updateTaskAssignee(String taskId, String userId);

	void updateTaskAssigneeNull(String taskId);

	void updateTaskOwner(String taskId, String userId);

	ProcessInstance getProcessInstance(String actInstId);

	List<TaskNodeStatus> getNodeCheckStatusInfo(String actInstId);
	

}