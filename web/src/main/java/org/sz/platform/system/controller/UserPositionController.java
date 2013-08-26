package org.sz.platform.system.controller;

import java.io.IOException;
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
import org.sz.platform.system.model.Position;
import org.sz.platform.system.service.PositionService;
import org.sz.platform.system.service.UserPositionService;

@Controller
@RequestMapping({ "/platform/system/userPosition/" })
public class UserPositionController extends BaseController {

	@Resource
	private UserPositionService userPositionService;

	@Resource
	private PositionService positionService;

	@RequestMapping({ "del" })
	@Action(description = "删除用户岗位表")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "userPosId");
			this.userPositionService.delByIds(lAryId);
			message = new ResultMessage(1, "删除用户岗位成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除用户岗位失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑用户岗位表")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView();
		Long posId = Long.valueOf(RequestUtil.getLong(request, "posId"));

		Position position = (Position) this.positionService.getById(posId);
		if (position != null) {
			mv.addObject("position", position);
			List list = this.userPositionService.getAll(new WebQueryFilter(
					request, "userPositionItem", true));
			mv.addObject("userPositionList", list);
		}

		String returnUrl = RequestUtil.getString(request, "returnUrl",
				RequestUtil.getPrePage(request));
		mv.addObject("returnUrl", returnUrl);

		return mv;
	}

	@RequestMapping({ "add" })
	@Action(description = "添加或更新用户岗位表")
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long posId = Long.valueOf(RequestUtil.getLong(request, "posId", 0L));
		Long[] userIds = RequestUtil.getLongAryByStr(request, "userIds");
		this.userPositionService.add(posId, userIds);

		String returnUrl = RequestUtil.getString(request, "returnUrl",
				RequestUtil.getPrePage(request));
		response.sendRedirect(RequestUtil.getPrePage(request));
	}

	@RequestMapping({ "setIsPrimary" })
	public void setIsPrimary(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long userPosId = Long.valueOf(RequestUtil.getLong(request, "userPosId",
				0L));
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			this.userPositionService.setIsPrimary(userPosId);
			message = new ResultMessage(1, "设置岗位成功");
		} catch (Exception ex) {
			message = new ResultMessage(0, "设置岗位失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "编辑用户岗位表")
	public ModelAndView get(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView();
		Long posId = Long.valueOf(RequestUtil.getLong(request, "posId"));

		Position position = (Position) this.positionService.getById(posId);
		if (position != null) {
			mv.addObject("position", position);
			List list = this.userPositionService.getAll(new WebQueryFilter(
					request, "userPositionItem", false));
			mv.addObject("userPositionList", list);
		}

		String returnUrl = RequestUtil.getString(request, "returnUrl",
				RequestUtil.getPrePage(request));
		mv.addObject("returnUrl", returnUrl);
		return mv;
	}
}
