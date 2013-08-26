package org.sz.platform.bpm.controller.flow;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.TaskSignData;
import org.sz.platform.bpm.service.flow.TaskSignDataService;

@Controller
@RequestMapping({ "/platform/bpm/taskSignData/" })
public class TaskSignDataController extends BaseController {

	@Resource
	private TaskSignDataService taskSignDataService;

	@RequestMapping({ "list" })
	@Action(description = "查看任务会签数据分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.taskSignDataService.getAll(new WebQueryFilter(request,
				"taskSignDataItem"));
		ModelAndView mv = getAutoView().addObject("taskSignDataList", list);
		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除任务会签数据")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "dataId");
		this.taskSignDataService.delByIds(lAryId);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑任务会签数据")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long dataId = Long.valueOf(RequestUtil.getLong(request, "dataId"));
		String returnUrl = RequestUtil.getPrePage(request);
		TaskSignData taskSignData = null;
		if (dataId.longValue() != 0L)
			taskSignData = (TaskSignData) this.taskSignDataService
					.getById(dataId);
		else {
			taskSignData = new TaskSignData();
		}
		return getAutoView().addObject("taskSignData", taskSignData).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看任务会签数据明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "dataId");
		TaskSignData taskSignData = (TaskSignData) this.taskSignDataService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("taskSignData", taskSignData);
	}
}
