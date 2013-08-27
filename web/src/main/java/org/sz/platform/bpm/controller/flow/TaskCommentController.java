package org.sz.platform.bpm.controller.flow;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.query.QueryFilter;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.model.flow.TaskComment;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.ProcessRunService;
import org.sz.platform.bpm.service.flow.TaskCommentService;

/**
 * 任务评论
 * 
 */
@Controller
@RequestMapping({ "/platform/bpm/taskComment/" })
public class TaskCommentController extends BaseController {

	@Resource
	private TaskCommentService taskCommentService;

	@Resource
	private BpmService bpmService;

	@Resource
	private ProcessRunService processRunService;

	@RequestMapping({ "list" })
	@Action(description = "查看流程任务评论分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long taskId = Long.valueOf(RequestUtil.getLong(request, "taskId"));
		Long runId = Long.valueOf(RequestUtil.getLong(request, "runId"));
		String actDefId = RequestUtil.getString(request, "actDefId");

		QueryFilter query = new WebQueryFilter(request, "taskCommentItem", true);
		query.addFilter("Q_actDefId_S", actDefId);
		query.addFilter("Q_runId_L", runId);
		query.addFilter("Q_taskId_L", taskId);
		List list = this.taskCommentService.getAll(query);

		ModelAndView mv = getAutoView().addObject("taskCommentList", list);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除流程任务评论")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "commentId");
			this.taskCommentService.delByIds(lAryId);
			message = new ResultMessage(1, "删除流程任务评论成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑流程任务评论")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		String taskId = request.getParameter("taskId");

		TaskEntity taskEntity = this.bpmService.getTask(taskId);
		ProcessRun processRun = this.processRunService
				.getByActInstanceId(taskEntity.getProcessInstanceId());

		TaskComment taskComment = new TaskComment();
		taskComment.setActDefId(processRun.getActDefId());
		taskComment.setRunId(processRun.getRunId());
		taskComment.setTaskId(new Long(taskId));
		taskComment.setSubject(processRun.getSubject());
		taskComment.setNodeName(taskEntity.getName());

		Long runId = Long.valueOf(RequestUtil.getLong(request, "runId"));
		String actDefId = RequestUtil.getString(request, "actDefId");

		QueryFilter query = new WebQueryFilter(request, "taskCommentItem", true);
		query.addFilter("Q_actDefId_S", actDefId);
		query.addFilter("Q_runId_L", runId);
		query.addFilter("Q_taskId_L", taskId);
		List list = this.taskCommentService.getAll(query);

		return getAutoView().addObject("taskComment", taskComment).addObject(
				"taskCommentList", list);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看流程任务评论明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "commentId");
		TaskComment taskComment = (TaskComment) this.taskCommentService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("taskComment", taskComment);
	}

	@RequestMapping({ "save" })
	@Action(description = "添加或更新流程任务评论")
	public void save(HttpServletRequest request, HttpServletResponse response,
			TaskComment taskComment) throws Exception {
		if (taskComment.getCommentId() == null) {
			String taskId = request.getParameter("taskId");
			TaskEntity taskEntity = this.bpmService.getTask(taskId);
			this.taskCommentService.addTaskComment(taskComment, taskEntity);
		}

		PrintWriter writer = response.getWriter();
		String result = "{\"result\":1,\"message\":\"保存常用语成功\",\"actDefId\":\""
				+ taskComment.getActDefId() + "\"" + ",\"runId\":\""
				+ taskComment.getRunId() + "\"" + ",\"taskId\":\""
				+ taskComment.getTaskId() + "\"" + "}";

		writer.print(result);
	}
}
