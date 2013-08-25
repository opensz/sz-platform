package org.sz.platform.bpm.service.flow.listener;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.core.bpm.util.BpmConst;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.model.flow.TaskOpinion;
import org.sz.platform.bpm.model.flow.TaskSignData;
import org.sz.platform.bpm.service.flow.TaskOpinionService;
import org.sz.platform.bpm.service.flow.TaskSignDataService;
import org.sz.platform.bpm.service.flow.TaskUserAssignService;
import org.sz.platform.bpm.service.flow.thread.TaskThreadService;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.SysUserService;

public class TaskSignCreateListener extends BaseTaskListener {
	private Logger logger = LoggerFactory.getLogger(TaskSignCreateListener.class);

	private TaskSignDataService taskSignDataService = (TaskSignDataService) ContextUtil.getBean(TaskSignDataService.class);

	private SysUserService sysUserService = (SysUserService) ContextUtil.getBean(SysUserService.class);

	private TaskOpinionService taskOpinionService = (TaskOpinionService) ContextUtil.getBean(TaskOpinionService.class);

	private TaskUserAssignService TaskUserAssignService = (TaskUserAssignService) ContextUtil.getBean(TaskUserAssignService.class);

	protected void execute(DelegateTask delegateTask, String actDefId, String nodeId) {
		TaskOpinion taskOpinion = new TaskOpinion(delegateTask);
		try {
			taskOpinion.setOpinionId(Long.valueOf(UniqueIdUtil.genId()));
			this.taskOpinionService.add(taskOpinion);
		} catch (Exception ex) {
			this.logger.error(ex.getMessage());
		}

		TaskThreadService.addTask((TaskEntity) delegateTask);

		String processInstanceId = delegateTask.getProcessInstanceId();

		this.logger.debug("enter the signuser listener notify method, taskId:" + delegateTask.getId() + " assignee:" + delegateTask.getAssignee());

		Integer instanceOfNumbers = (Integer) delegateTask.getVariable("nrOfInstances");
		Integer loopCounter = (Integer) delegateTask.getVariable("loopCounter");

		if (loopCounter == null)
			loopCounter = Integer.valueOf(0);

		this.logger.debug("instance of numbers:" + instanceOfNumbers + " loopCounters:" + loopCounter);

		if (loopCounter.intValue() > 0)
			return;

		Integer maxSignNums = this.taskSignDataService.getMaxSignNums(processInstanceId, nodeId, TaskSignData.COMPLETED);
		List signUserList = this.TaskUserAssignService.getSignUser();

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
				String fullname = ((SysUser) this.sysUserService.getById(new Long(signUserId))).getFullname();
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
			delegateTask.setVariable(delegateTask.getId(), "signResult_" + nodeId);
		}
	}

	protected int getScriptType() {
		return BpmConst.StartScript.intValue();
	}
}
