package org.sz.platform.bpm.controller.flow;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.TaskReminder;
import org.sz.platform.bpm.service.flow.TaskReminderService;

/**
 * 任务提醒设置
 *
 */
@Controller
@RequestMapping({ "/platform/bpm/taskReminder/" })
public class TaskReminderController extends BaseController {

	@Resource
	private TaskReminderService taskReminderService;

	@RequestMapping({ "list" })
	@Action(description = "查看任务节点催办时间设置分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.taskReminderService.getAll(new WebQueryFilter(request,
				"taskReminderItem"));
		ModelAndView mv = getAutoView().addObject("taskReminderList", list);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除任务节点催办时间设置")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "taskDueId");
			this.taskReminderService.delByIds(lAryId);
			message = new ResultMessage(1, "删除任务节点催办时间设置成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑任务节点催办时间设置")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		String actDefId = RequestUtil.getString(request, "actDefId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		String returnUrl = RequestUtil.getPrePage(request);
		int reminderStartDay = 0;
		int reminderStartHour = 0;
		int reminderStartMinute = 0;
		int reminderEndDay = 0;
		int reminderEndHour = 0;
		int reminderEndMinute = 0;
		int completeTimeDay = 0;
		int completeTimeHour = 0;
		int complateTimeMinute = 0;
		TaskReminder taskReminder = this.taskReminderService
				.getByActDefAndNodeId(actDefId, nodeId);
		if (taskReminder == null) {
			taskReminder = new TaskReminder();
		} else {
			int reminderStart = taskReminder.getReminderStart().intValue();
			reminderStartDay = reminderStart / 1440;
			reminderStartHour = (reminderStart - reminderStartDay * 1440) / 60;
			reminderStartMinute = reminderStart - reminderStartDay * 1440
					- reminderStartHour * 60;

			int reminderEnd = taskReminder.getReminderEnd().intValue();
			reminderEndDay = reminderEnd / 1440;
			reminderEndHour = (reminderEnd - reminderEndDay * 1440) / 60;
			reminderEndMinute = reminderEnd - reminderEndDay * 1440
					- reminderEndHour * 60;

			int complateTime = taskReminder.getCompleteTime().intValue();
			completeTimeDay = complateTime / 1440;
			completeTimeHour = (complateTime - completeTimeDay * 1440) / 60;
			complateTimeMinute = complateTime - completeTimeDay * 1440
					- completeTimeHour * 60;
		}
		return getAutoView()
				.addObject("taskReminder", taskReminder)
				.addObject("returnUrl", returnUrl)
				.addObject("actDefId", actDefId)
				.addObject("nodeId", nodeId)
				.addObject("reminderStartDay",
						Integer.valueOf(reminderStartDay))
				.addObject("reminderStartHour",
						Integer.valueOf(reminderStartHour))
				.addObject("reminderStartMinute",
						Integer.valueOf(reminderStartMinute))
				.addObject("reminderEndDay", Integer.valueOf(reminderEndDay))
				.addObject("reminderEndHour", Integer.valueOf(reminderEndHour))
				.addObject("reminderEndMinute",
						Integer.valueOf(reminderEndMinute))
				.addObject("completeTimeDay", Integer.valueOf(completeTimeDay))
				.addObject("completeTimeHour",
						Integer.valueOf(completeTimeHour))
				.addObject("complateTimeMinute",
						Integer.valueOf(complateTimeMinute));
	}
}
