package org.sz.platform.bpm.service.flow.listener;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.cxf.common.util.StringUtils;
import org.sz.core.bpm.util.BpmConst;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.model.flow.BpmTaskCc;
import org.sz.platform.bpm.model.flow.ForkUser;
import org.sz.platform.bpm.model.flow.TaskFork;
import org.sz.platform.bpm.model.flow.TaskOpinion;
import org.sz.platform.bpm.model.flow.TaskSignData;
import org.sz.platform.bpm.service.flow.BpmNodeSetService;
import org.sz.platform.bpm.service.flow.BpmNodeUserService;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.BpmTaskCcService;
import org.sz.platform.bpm.service.flow.TaskForkService;
import org.sz.platform.bpm.service.flow.TaskOpinionService;
import org.sz.platform.bpm.service.flow.TaskSignDataService;
import org.sz.platform.bpm.service.flow.TaskUserAssignService;
import org.sz.platform.bpm.service.flow.thread.TaskThreadService;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.SysUserService;

public class TaskCreateListener extends BaseTaskListener {
	private TaskOpinionService taskOpinionService = (TaskOpinionService) ContextUtil
			.getBean(TaskOpinionService.class);

	private TaskUserAssignService taskUserAssignService = (TaskUserAssignService) ContextUtil
			.getBean(TaskUserAssignService.class);

	private TaskForkService taskForkService = (TaskForkService) ContextUtil
			.getBean(TaskForkService.class);

	private BpmNodeSetService bpmNodeSetService = (BpmNodeSetService) ContextUtil
			.getBean(BpmNodeSetService.class);

	private BpmService bpmService = (BpmService) ContextUtil
			.getBean("bpmService");

	private TaskSignDataService taskSignDataService = (TaskSignDataService) ContextUtil
			.getBean(TaskSignDataService.class);

	private SysUserService sysUserService = (SysUserService) ContextUtil
			.getBean(SysUserService.class);

	private BpmTaskCcService bpmTaskCcService = (BpmTaskCcService) ContextUtil
			.getBean(BpmTaskCcService.class);

	protected void execute(DelegateTask delegateTask, String actDefId,
			String nodeId) {
		
		//添加抄送任务
		addCcTask(delegateTask);
		
		String token = TaskThreadService.getToken();
		if (token != null) {
			delegateTask.setVariableLocal(TaskFork.TAKEN_VAR_NAME, token);
		}

		TaskThreadService.addTask((TaskEntity) delegateTask);

		TaskOpinion taskOpinion = new TaskOpinion(delegateTask);
		try {
			taskOpinion.setOpinionId(Long.valueOf(UniqueIdUtil.genId()));
			taskOpinion.setTaskToken(token);
			this.taskOpinionService.add(taskOpinion);
		} catch (Exception ex) {
			this.logger.error(ex.getMessage());
		}

		Map nodeUserMap = this.taskUserAssignService.getNodeUserMap();

		//用于判断含有子流程的并发处理
		Object assignee = delegateTask.getVariable("assignee");
		if (this.taskUserAssignService.getSignUser() != null && assignee != null) {
			String firstNodeId = "";
			Object firstNode = delegateTask.getVariable("firstNode");
			if (firstNode != null) {
				firstNodeId = firstNode.toString();
			}

			if (firstNodeId.equals(delegateTask.getTaskDefinitionKey())) {
				String userId = "";
				if (assignee != null) {
					userId = assignee.toString();
					delegateTask.setOwner(userId);
					delegateTask.setAssignee(userId);
				}
				// this.TaskSignCreateExecute(delegateTask, actDefId, nodeId);
			} else {
				if ((nodeUserMap != null) && (nodeUserMap.get(nodeId) != null)) {
					List userIds = (List) nodeUserMap.get(nodeId);
					assignUser(delegateTask, userIds);
					return;
				}
			}

			return;
		}

		
		BpmNodeSet bpmNodeSet = this.bpmNodeSetService.getByActDefIdNodeId(
				actDefId, nodeId);
		//
		//
		//分发结点时执行
		if ((bpmNodeSet != null)
				&& (BpmNodeSet.NODE_TYPE_FORK.equals(bpmNodeSet.getNodeType()))) {
			if ((nodeUserMap != null) && (nodeUserMap.get(nodeId) != null)) {
				List userIds = (List) nodeUserMap.get(nodeId);

				this.bpmService
						.newForkTasks((TaskEntity) delegateTask, userIds);

				this.taskForkService.newTaskFork(delegateTask,
						bpmNodeSet.getJoinTaskName(),
						bpmNodeSet.getJoinTaskKey(),
						Integer.valueOf(userIds.size()));
			} else {
				ForkUser forkUser = this.taskUserAssignService.getForkUser();
				if (forkUser != null) {
					this.bpmService.newForkTasks((TaskEntity) delegateTask,
							forkUser.getForkUserIdsAsList());

					this.taskForkService.newTaskFork(delegateTask,
							bpmNodeSet.getJoinTaskName(),
							bpmNodeSet.getJoinTaskKey(),
							Integer.valueOf(forkUser.getForkUserIds().size()));
				}
			}
			return;
		}

		//根据nodeUserMap 分配用户
		if ((nodeUserMap != null) && (nodeUserMap.get(nodeId) != null)) {
			List userIds = (List) nodeUserMap.get(nodeId);
			assignUser(delegateTask, userIds);
			return;
		}

		
		//
		//nodeUserMap不存在用户， 前台没有传入分配人是调用
		BpmNodeUserService userService = (BpmNodeUserService) ContextUtil
				.getBean(BpmNodeUserService.class);

		ProcessInstance processInstance = this.bpmService
				.getProcessInstance(delegateTask.getProcessInstanceId());
		List users = null;
		if (processInstance != null) {
			String preTaskUser = TaskThreadService.getPreTaskUser();
			if (StringUtils.isEmpty(preTaskUser)) {
				preTaskUser = (String) delegateTask.getVariable("startUser");
			}

			users = userService.getExeUserIdsByInstance(
					delegateTask.getProcessInstanceId(), nodeId, preTaskUser);
		} else {
			String startUserId = (String) delegateTask.getVariable("startUser");

			users = userService.getExeUserIds(actDefId, null, nodeId,
					startUserId, startUserId);
		}

		assignUser(delegateTask, users);
	}
	
	private void addCcTask(DelegateTask delegateTask){
		String taskId = delegateTask.getId();
		Long[] userIds = this.taskUserAssignService.getCcUserIds();
		if (userIds != null && userIds.length > 0) {
			for (Long userId : userIds) {
				BpmTaskCc entity = new BpmTaskCc();
				entity.setCcTime(new Date());
				try {
					entity.setId(UniqueIdUtil.genId());
				} catch (Exception e) {
					logger.error("主键生成失败!");
				}
				entity.setTaskId(taskId);
				entity.setUserId(userId);
				bpmTaskCcService.add(entity);
			}
		}
	}

	private void assignUser(DelegateTask delegateTask, List<String> users) {	
		if (BeanUtils.isEmpty(users))
			return;
		if (users.size() == 1) {
			delegateTask.setOwner((String) users.get(0));
			delegateTask.setAssignee((String) users.get(0));
		} else {
			delegateTask.addCandidateUsers(users);
		}
	}

	protected int getScriptType() {
		return BpmConst.StartScript.intValue();
	}

	protected void TaskSignCreateExecute(DelegateTask delegateTask,
			String actDefId, String nodeId) {
		TaskOpinion taskOpinion = new TaskOpinion(delegateTask);
		try {
			taskOpinion.setOpinionId(Long.valueOf(UniqueIdUtil.genId()));
			this.taskOpinionService.add(taskOpinion);
		} catch (Exception ex) {
			this.logger.error(ex.getMessage());
		}

		TaskThreadService.addTask((TaskEntity) delegateTask);

		String processInstanceId = delegateTask.getProcessInstanceId();

		this.logger.debug("enter the signuser listener notify method, taskId:"
				+ delegateTask.getId() + " assignee:"
				+ delegateTask.getAssignee());

		Integer instanceOfNumbers = (Integer) delegateTask
				.getVariable("nrOfInstances");
		Integer loopCounter = (Integer) delegateTask.getVariable("loopCounter");

		if (loopCounter == null)
			loopCounter = Integer.valueOf(0);

		this.logger.debug("instance of numbers:" + instanceOfNumbers
				+ " loopCounters:" + loopCounter);

		if (loopCounter.intValue() > 0)
			return;

		Integer maxSignNums = this.taskSignDataService.getMaxSignNums(
				processInstanceId, nodeId, TaskSignData.COMPLETED);
		List signUserList = this.taskUserAssignService.getSignUser();

		for (int i = 0; i < instanceOfNumbers.intValue(); i++) {
			TaskSignData signData = new TaskSignData();
			signData.setActInstId(processInstanceId);

			signData.setNodeName(delegateTask.getName());
			signData.setNodeId(nodeId);
			signData.setSignNums(Integer.valueOf(maxSignNums.intValue() + 1));
			signData.setIsCompleted(TaskSignData.NOT_COMPLETED);

			String signUserId = (String) signUserList.get(i);
			if (signUserId != null) {
				signData.setVoteUserId(new Long(signUserId));
				String fullname = ((SysUser) this.sysUserService
						.getById(new Long(signUserId))).getFullname();
				signData.setVoteUserName(fullname);
			}
			try {
				signData.setDataId(Long.valueOf(UniqueIdUtil.genId()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			this.taskSignDataService.add(signData);
		}

		if (delegateTask.getVariable("signResult_" + nodeId) != null) {
			delegateTask.setVariable(delegateTask.getId(), "signResult_"
					+ nodeId);
		}
	}
}