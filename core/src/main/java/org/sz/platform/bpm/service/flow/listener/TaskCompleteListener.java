package org.sz.platform.bpm.service.flow.listener;

import java.util.Date;

import org.activiti.engine.delegate.DelegateTask;
import org.sz.core.bpm.util.BpmConst;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.platform.bpm.model.flow.TaskFork;
import org.sz.platform.bpm.model.flow.TaskOpinion;
import org.sz.platform.bpm.service.flow.TaskOpinionService;
import org.sz.platform.bpm.service.flow.thread.TaskThreadService;
import org.sz.platform.system.model.SysUser;

public class TaskCompleteListener extends BaseTaskListener {
	TaskOpinionService taskOpinionService = (TaskOpinionService) ContextUtil.getBean(TaskOpinionService.class);

	protected void execute(DelegateTask delegateTask, String actDefId, String nodeId) {
		String taskAssignee = delegateTask.getAssignee();

		setPreUser(taskAssignee);

		String token = (String) delegateTask.getVariableLocal(TaskFork.TAKEN_VAR_NAME);
		if (token != null) {
			TaskThreadService.setToken(token);
		}
		TaskOpinion taskOpinion = this.taskOpinionService.getByTaskId(new Long(delegateTask.getId()));

		if (taskOpinion != null) {
			Short approvalStatus = (Short) delegateTask.getVariable("approvalStatus_" + delegateTask.getTaskDefinitionKey());
			String approvalContent = (String) delegateTask.getVariable("approvalContent_" + delegateTask.getTaskDefinitionKey());

			SysUser sysUser = ContextUtil.getCurrentUser();
			taskOpinion.setExeUserId(sysUser.getUserId());
			taskOpinion.setExeFullname(sysUser.getFullname());
			if (approvalStatus == null)
				taskOpinion.setCheckStatus(TaskOpinion.STATUS_AGREE);
			else {
				taskOpinion.setCheckStatus(approvalStatus);
			}
			taskOpinion.setOpinion(approvalContent);
			taskOpinion.setEndTime(new Date());
			taskOpinion.setDurTime(Long.valueOf(taskOpinion.getEndTime().getTime() - taskOpinion.getStartTime().getTime()));

			this.taskOpinionService.update(taskOpinion);
		}
	}

	private void setPreUser(String assignee) {
		TaskThreadService.cleanTaskUser();
		if (StringUtil.isNotEmpty(assignee))
			TaskThreadService.setPreTaskUser(assignee);
	}

	protected int getScriptType() {
		return BpmConst.EndScript.intValue();
	}
}
