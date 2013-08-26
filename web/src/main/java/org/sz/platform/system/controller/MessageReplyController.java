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
import org.sz.platform.system.model.MessageReply;
import org.sz.platform.system.model.MessageSend;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.MessageReplyService;
import org.sz.platform.system.service.MessageSendService;
import org.sz.platform.system.service.SysUserService;

@Controller
@RequestMapping({ "/platform/system/messageReply/" })
public class MessageReplyController extends BaseController {

	@Resource
	private MessageReplyService replyService;

	@Resource
	private MessageSendService sendService;

	@Resource
	private SysUserService userService;

	@RequestMapping({ "list" })
	@Action(description = "查看消息回复分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String returnUrl = RequestUtil.getPrePage(request);
		SysUser curUser = ContextUtil.getCurrentUser();
		Long userId = curUser.getUserId();
		Long messageId = Long
				.valueOf(RequestUtil.getLong(request, "messageId"));
		MessageSend messageSend = (MessageSend) this.sendService
				.getById(messageId);
		QueryFilter queryFilter = new WebQueryFilter(request,
				"messageReplyItem", false);
		queryFilter.addFilter("userId", userId);
		List list = this.replyService.getAll(queryFilter);
		ModelAndView mv = getAutoView().addObject("replyList", list)
				.addObject("userId", userId)
				.addObject("messageSend", messageSend)
				.addObject("returnUrl", returnUrl);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除消息回复")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.replyService.delByIds(lAryId);
			message = new ResultMessage(1, "删除消息回复成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑消息回复")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		Long messageId = Long
				.valueOf(RequestUtil.getLong(request, "messageId"));
		SysUser curUser = ContextUtil.getCurrentUser();
		Long userId = curUser.getUserId();
		String returnUrl = RequestUtil.getPrePage(request);
		MessageReply messageReply = null;
		MessageSend messageSend = null;
		SysUser sysUser = null;
		messageSend = (MessageSend) this.sendService.getById(messageId);
		sysUser = (SysUser) this.userService.getById(userId);
		if (id.longValue() != 0L) {
			messageReply = (MessageReply) this.replyService.getById(id);
		} else {
			messageReply = new MessageReply();
			messageReply.setMessageId(messageId);
			messageReply.setReply(sysUser.getFullname());
			messageReply.setReplyId(userId);
		}
		return getAutoView().addObject("messageReply", messageReply)
				.addObject("subject", messageSend.getSubject())
				.addObject("content", messageSend.getContent())
				.addObject("returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看消息回复明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		MessageReply messageReply = (MessageReply) this.replyService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("messageReply", messageReply);
	}

	@RequestMapping({ "reply" })
	@Action(description = "读取消息并回复")
	public void reply(HttpServletRequest request, HttpServletResponse response,
			MessageReply messageReply) throws Exception {
		try {
			SysUser sysUser = ContextUtil.getCurrentUser();
			this.replyService.saveReply(messageReply, sysUser);
			writeResultMessage(response.getWriter(), "回复消息成功!", 1);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), "回复消息失败!", 0);
		}
	}
}
