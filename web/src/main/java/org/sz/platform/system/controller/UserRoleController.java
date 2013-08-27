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
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.service.SysRoleService;
import org.sz.platform.system.service.UserRoleService;

@Controller
@RequestMapping({ "/platform/system/userRole/" })
public class UserRoleController extends BaseController {

	@Resource
	private UserRoleService userRoleService;

	@Resource
	private SysRoleService sysRoleService;

	@RequestMapping({ "del" })
	@Action(description = "删除用户角色映射表")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "userRoleId");
			this.userRoleService.delByUserRoleId(lAryId);
			message = new ResultMessage(1, "删除用户成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除用户失败：" + e.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑用户角色映射表")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView();
		Long roleId = Long.valueOf(RequestUtil.getLong(request, "roleId"));
		QueryFilter queryFilter = new WebQueryFilter(request, "userRoleItem",
				true);
		List userRoleList = this.userRoleService.getAll(queryFilter);
		mv.addObject("userRoleList", userRoleList).addObject("roleId", roleId);

		return mv;
	}

	@RequestMapping({ "add" })
	@Action(description = "添加或更新用户角色")
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long roleId = Long.valueOf(RequestUtil.getLong(request, "roleId", 0L));
		Long[] userIds = RequestUtil.getLongAryByStr(request, "userIds");
		this.userRoleService.add(roleId, userIds);

		String returnUrl = RequestUtil.getString(request, "returnUrl",
				RequestUtil.getPrePage(request));
		response.sendRedirect(RequestUtil.getPrePage(request));
	}
}
