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
import org.sz.platform.bpm.model.flow.ReminderState;
import org.sz.platform.bpm.service.flow.ReminderStateService;

@Controller
@RequestMapping({ "/platform/bpm/reminderState/" })
public class ReminderStateController extends BaseController {

	@Resource
	private ReminderStateService reminderStateService;

	@RequestMapping({ "list" })
	@Action(description = "查看任务催办执行情况分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.reminderStateService.getAll(new WebQueryFilter(request,
				"reminderStateItem"));
		ModelAndView mv = getAutoView().addObject("reminderStateList", list);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除任务催办执行情况")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.reminderStateService.delByIds(lAryId);
			message = new ResultMessage(1, "删除任务催办执行情况成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑任务催办执行情况")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		String returnUrl = RequestUtil.getPrePage(request);
		ReminderState reminderState = null;
		if (id.longValue() != 0L)
			reminderState = (ReminderState) this.reminderStateService
					.getById(id);
		else {
			reminderState = new ReminderState();
		}
		return getAutoView().addObject("reminderState", reminderState)
				.addObject("returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看任务催办执行情况明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		ReminderState reminderState = (ReminderState) this.reminderStateService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("reminderState", reminderState);
	}
}
