package org.sz.platform.bpm.service.flow.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.sz.core.bpm.util.BpmConst;
import org.sz.core.util.ContextUtil;
import org.sz.platform.bpm.model.flow.TaskOpinion;
import org.sz.platform.bpm.service.flow.TaskOpinionService;
import org.sz.platform.bpm.service.flow.TaskReminderService;

public class TaskAssignListener extends BaseTaskListener {
	//private CalendarAssignService calendarAssignService = (CalendarAssignService) ContextUtil.getBean(CalendarAssignService.class);

	private TaskReminderService taskReminderService = (TaskReminderService) ContextUtil.getBean(TaskReminderService.class);

	private TaskOpinionService taskOpinionService = (TaskOpinionService) ContextUtil.getBean(TaskOpinionService.class);

	protected void execute(DelegateTask delegateTask, String actDefId, String nodeId) {
		String userId = delegateTask.getAssignee();

		TaskOpinion taskOpinion = this.taskOpinionService.getByTaskId(new Long(delegateTask.getId()));
		if (taskOpinion != null) {
			this.logger.debug("update taskopinion exe userId" + userId);
			this.taskOpinionService.update(taskOpinion);
		}

		delegateTask.setOwner(userId);
	}

	protected int getScriptType() {
		return BpmConst.AssignScript.intValue();
	}
}
