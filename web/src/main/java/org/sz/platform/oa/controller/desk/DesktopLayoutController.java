package org.sz.platform.oa.controller.desk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.sz.platform.oa.model.desk.DesktopLayout;
import org.sz.platform.oa.service.desk.DesktopLayoutService;

@Controller
@RequestMapping({ "/platform/system/desktopLayout/" })
public class DesktopLayoutController extends BaseController {

	@Resource
	private DesktopLayoutService desktopLayoutService;

	@RequestMapping({ "list" })
	@Action(description = "查看桌面布局分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.desktopLayoutService.getAll(new WebQueryFilter(
				request, "desktopLayoutItem"));
		ModelAndView mv = getAutoView().addObject("desktopLayoutList", list);
		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除桌面布局")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.desktopLayoutService.delByIds(lAryId);
			message = new ResultMessage(1, "删除桌面布局成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑桌面布局")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		String returnUrl = RequestUtil.getPrePage(request);
		ModelAndView mv = getAutoView();
		DesktopLayout desktopLayout = null;
		if (id.longValue() != 0L) {
			desktopLayout = (DesktopLayout) this.desktopLayoutService
					.getById(id);
			String[] aryWidth = desktopLayout.getWidth().split(",");
			mv.addObject("aryWidth", aryWidth);
		} else {
			desktopLayout = new DesktopLayout();
		}
		return mv.addObject("desktopLayout", desktopLayout).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看桌面布局明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		DesktopLayout desktopLayout = (DesktopLayout) this.desktopLayoutService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("desktopLayout", desktopLayout);
	}

	@RequestMapping({ "show" })
	@Action(description = "桌面浏览")
	public ModelAndView news(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		DesktopLayout bean = (DesktopLayout) this.desktopLayoutService
				.getById(id);
		Map desktopLayoutmap = new HashMap();
		desktopLayoutmap.put("cols", "" + bean.getCols());
		desktopLayoutmap.put("widths", bean.getWidth());
		desktopLayoutmap.put("id", "" + id);
		return getAutoView().addObject("desktopLayoutmap", desktopLayoutmap);
	}

	@RequestMapping({ "setDefault" })
	@Action(description = "设置默认桌面布局")
	public void setupDefault(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		long id = RequestUtil.getLong(request, "id");
		try {
			this.desktopLayoutService.setDefault(Long.valueOf(id));
			message = new ResultMessage(1, "设置成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "设置失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
}
