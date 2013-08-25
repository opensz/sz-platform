package org.sz.platform.system.controller;

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
import org.sz.platform.system.model.Demension;
import org.sz.platform.system.service.DemensionService;

@Controller
@RequestMapping({ "/platform/system/demension/" })
public class DemensionController extends BaseController {

	@Resource
	private DemensionService demensionService;

	@RequestMapping({ "list" })
	@Action(description = "查看SYS_DEMENSION分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.demensionService.getDemenByQuery(new WebQueryFilter(
				request, "demensionItem"));
		ModelAndView mv = getAutoView().addObject("demensionList", list);

		mv.addObject("checkAll", "<input type='checkbox' id='chkall333' />");
		//request.setAttribute("checkAll", "<input type='checkbox' id='chkall22' />");
		
		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除SYS_DEMENSION")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "demId");
			this.demensionService.delByIds(lAryId);
			message = new ResultMessage(1, "删除维度成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除维度失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑SYS_DEMENSION")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long demId = Long.valueOf(RequestUtil.getLong(request, "demId"));
		Demension demension = null;
		if (demId.longValue() != 0L)
			demension = (Demension) this.demensionService.getById(demId);
		else {
			demension = new Demension();
		}
		return getAutoView().addObject("demension", demension).addObject(
				"demId", demId);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看SYS_DEMENSION明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "demId");
		Demension demension = (Demension) this.demensionService.getById(Long
				.valueOf(id));
		return getAutoView().addObject("demension", demension);
	}
}
