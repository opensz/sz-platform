package org.sz.platform.system.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.ContextUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.UserUnder;
import org.sz.platform.system.service.UserUnderService;

@Controller
@RequestMapping({ "/platform/system/userUnder/" })
public class UserUnderController extends BaseController {

	@Resource
	private UserUnderService userUnderService;

	@RequestMapping({ "list" })
	@Action(description = "查看下属管理分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long userId = ContextUtil.getCurrentUser().getUserId();
		QueryFilter queryFilter = new WebQueryFilter(request, "userUnderItem");
		queryFilter.addFilter("userid", userId);
		List list = this.userUnderService.getAll(queryFilter);
		ModelAndView mv = getAutoView().addObject("userUnderList", list);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除下属管理")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.userUnderService.delByIds(lAryId);
			message = new ResultMessage(1, "删除下属管理成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑下属管理")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		String returnUrl = RequestUtil.getPrePage(request);
		UserUnder userUnder = null;
		if (id.longValue() != 0L)
			userUnder = (UserUnder) this.userUnderService.getById(id);
		else {
			userUnder = new UserUnder();
		}
		return getAutoView().addObject("userUnder", userUnder).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看下属管理明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		UserUnder userUnder = (UserUnder) this.userUnderService.getById(Long
				.valueOf(id));
		return getAutoView().addObject("userUnder", userUnder);
	}

	@RequestMapping({ "addUnderUser" })
	@Action(description = "添加下属")
	public void addUnderUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userIds = RequestUtil.getSecureString(request, "userIds");
		String userNames = RequestUtil.getSecureString(request, "userNames");
		long userId = ContextUtil.getCurrentUserId().longValue();
		this.userUnderService.addMyUnderUser(Long.valueOf(userId), userIds,
				userNames);
	}
}
