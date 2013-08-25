package org.sz.platform.bpm.controller.flow;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.query.QueryFilter;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.TaskVars;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.TaskVarsService;

@Controller
@RequestMapping({ "/platform/bpm/taskVars/" })
public class TaskVarsController extends BaseController {

	@Resource
	private TaskVarsService taskVarsService;

	@Resource
	private BpmService bpmService;

	@RequestMapping({ "list" })
	public ModelAndView taskVars(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		Task task = this.bpmService.getTask(taskId);
		String executionId = task.getExecutionId();
		QueryFilter queryFilter = new WebQueryFilter(request);
		if (executionId != null) {
			queryFilter.getFilters().put("executionId", executionId);
		}
		List list = this.taskVarsService.getVars(queryFilter);
		ModelAndView mv = getAutoView().addObject("taskVarsList", list)
				.addObject("taskId", taskId);
		return mv;
	}

	@RequestMapping({ "updateVars" })
	public void updateTaskVars(HttpServletRequest request,
			HttpServletResponse response, TaskVars po) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));

		String varsValue = RequestUtil.getString(request, "varsValue");

		TaskVars taskVars = (TaskVars) this.taskVarsService.getById(id);

		String type = taskVars.getType();
		try {
			if (type.equals("string")) {
				taskVars.setTextValue(varsValue);
			} else if (type.equals("double")) {
				taskVars.setDoubleValue(Double.valueOf(Double
						.parseDouble(varsValue)));
			} else {
				taskVars.setTextValue(varsValue);
				taskVars.setLongValue(Long.valueOf(Long.parseLong(varsValue)));
			}
			this.taskVarsService.update(taskVars);
			writeResultMessage(response.getWriter(), new ResultMessage(1,
					"更新成功"));
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), new ResultMessage(0,
					"变量类型不匹配"));
		}
	}
}
