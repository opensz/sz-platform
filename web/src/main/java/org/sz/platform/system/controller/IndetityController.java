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
import org.sz.platform.system.model.Identity;
import org.sz.platform.system.service.IdentityService;

@Controller
@RequestMapping({ "/platform/system/indetity/" })
public class IndetityController extends BaseController {

	@Resource
	private IdentityService indetityService;

	@RequestMapping({ "list" })
	@Action(description = "查看流水号生成分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.indetityService.getAll(new WebQueryFilter(request,
				"indetityItem"));
		ModelAndView mv = getAutoView().addObject("indetityList", list);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除流水号生成")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.indetityService.delByIds(lAryId);
			message = new ResultMessage(1, "删除流水号生成成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑流水号生成")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		String returnUrl = RequestUtil.getPrePage(request);
		Identity identity = null;
		if (id.longValue() != 0L)
			identity = (Identity) this.indetityService.getById(id);
		else {
			identity = new Identity();
		}
		return getAutoView().addObject("indetity", identity).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看流水号生成明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		Identity identity = (Identity) this.indetityService.getById(Long
				.valueOf(id));
		return getAutoView().addObject("indetity", identity);
	}
}
