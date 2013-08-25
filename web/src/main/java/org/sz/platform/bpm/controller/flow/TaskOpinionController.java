package org.sz.platform.bpm.controller.flow;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.util.StringUtil;
import org.sz.core.web.controller.BaseController;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.ProcessRunService;
import org.sz.platform.bpm.service.flow.TaskOpinionService;

/**
 * 任务审批意见
 *
 */

@Controller
@RequestMapping({ "/platform/bpm/taskOpinion/" })
public class TaskOpinionController extends BaseController {

	@Resource
	private TaskOpinionService taskOpinionService;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private BpmService bpmService;

	@RequestMapping({ "list" })
	@Action(description = "查看流程任务审批意见分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actInstId = request.getParameter("actInstId");
		String taskId = request.getParameter("taskId");
		String runId = request.getParameter("runId");

		ProcessRun processRun = null;
		if (StringUtil.isNotEmpty(taskId)) {
			TaskEntity taskEntity = this.bpmService.getTask(taskId);
			actInstId = taskEntity.getProcessInstanceId();
		} else if (StringUtils.isNotEmpty(runId)) {
			processRun = (ProcessRun) this.processRunService.getById(new Long(
					runId));
			actInstId = processRun.getActInstId();
		}

		List list = this.taskOpinionService.getByActInstId(actInstId);
		if (processRun == null) {
			processRun = this.processRunService.getByActInstanceId(actInstId);
		}
		ModelAndView mv = getAutoView().addObject("taskOpinionList", list)
				.addObject("processRun", processRun);
		return mv;
	}
}
