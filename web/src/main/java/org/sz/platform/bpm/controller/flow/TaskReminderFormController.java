package org.sz.platform.bpm.controller.flow;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.sz.core.annotion.Action;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.TaskReminder;
import org.sz.platform.bpm.service.flow.TaskReminderService;

@Controller
@RequestMapping({ "/platform/bpm/taskReminder/" })
public class TaskReminderFormController extends BaseFormController {

	@Resource
	private TaskReminderService taskReminderService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新任务节点催办时间设置")
	public void save(HttpServletRequest request, HttpServletResponse response,
			TaskReminder taskReminder, BindingResult bindResult)
			throws Exception {
		ResultMessage resultMessage = validForm("taskReminder", taskReminder,
				bindResult, request);

		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		int reminderStartDay = RequestUtil.getInt(request, "reminderStartDay");
		int reminderStartHour = RequestUtil
				.getInt(request, "reminderStartHour");
		int reminderStartMinute = RequestUtil.getInt(request,
				"reminderStartMinute");

		int reminderStart = (reminderStartDay * 24 + reminderStartHour) * 60
				+ reminderStartMinute;

		int reminderEndDay = RequestUtil.getInt(request, "reminderEndDay");
		int reminderEndHour = RequestUtil.getInt(request, "reminderEndHour");
		int reminderEndMinute = RequestUtil
				.getInt(request, "reminderEndMinute");

		int reminderEnd = (reminderEndDay * 24 + reminderEndHour) * 60
				+ reminderEndMinute;

		int completeTimeDay = RequestUtil.getInt(request, "completeTimeDay");
		int completeTimeHour = RequestUtil.getInt(request, "completeTimeHour");
		int completeTimeMinute = RequestUtil.getInt(request,
				"completeTimeMinute");

		int completeTime = (completeTimeDay * 24 + completeTimeHour) * 60
				+ completeTimeMinute;
		if (taskReminder.getTaskDueId() == null) {
			taskReminder.setTaskDueId(Long.valueOf(UniqueIdUtil.genId()));
			taskReminder.setReminderStart(Integer.valueOf(reminderStart));
			taskReminder.setReminderEnd(Integer.valueOf(reminderEnd));
			taskReminder.setCompleteTime(Integer.valueOf(completeTime));
			this.taskReminderService.add(taskReminder);
			resultMsg = getText("record.added", new Object[] { "任务节点催办设置" });
		} else {
			taskReminder.setReminderStart(Integer.valueOf(reminderStart));
			taskReminder.setReminderEnd(Integer.valueOf(reminderEnd));
			taskReminder.setCompleteTime(Integer.valueOf(completeTime));
			this.taskReminderService.update(taskReminder);
			resultMsg = getText("record.updated", new Object[] { "任务节点催办设置" });
		}
		writeResultMessage(response.getWriter(), resultMsg, 1);
	}

	@ModelAttribute
	protected TaskReminder getFormObject(
			@RequestParam("taskDueId") Long taskDueId, Model model)
			throws Exception {
		this.logger.debug("enter TaskReminder getFormObject here....");
		TaskReminder taskReminder = null;
		if (taskDueId != null)
			taskReminder = (TaskReminder) this.taskReminderService
					.getById(taskDueId);
		else {
			taskReminder = new TaskReminder();
		}
		return taskReminder;
	}
}
