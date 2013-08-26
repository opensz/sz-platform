package org.sz.platform.system.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
import org.sz.platform.system.service.MessageReadService;
import org.sz.platform.system.service.MessageReplyService;
import org.sz.platform.system.service.MessageSendService;

@Controller
@RequestMapping({ "/platform/system/messageSend/" })
public class MessageSendController extends BaseController {

	@Resource
	private MessageSendService sendService;

	@Resource
	private MessageReadService readService;

	@Resource
	Properties configproperties;

	@Resource
	private MessageReplyService replyService;

	@RequestMapping({ "form" })
	@Action(description = "发送和接收列表框架")
	public ModelAndView form(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysUser curUser = ContextUtil.getCurrentUser();
		Long userId = curUser.getUserId();

		ModelAndView mv = getAutoView().addObject("userId", userId);
		return mv;
	}

	@RequestMapping({ "list" })
	@Action(description = "查看发送消息分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long userId = ContextUtil.getCurrentUserId();
		Date now = new Date();
		Long longTime = Long.valueOf(now.getTime());
		String spanTime = this.configproperties.getProperty("send.timeout");
		QueryFilter queryFilter = new WebQueryFilter(request, "messageSendItem");
		queryFilter.addFilter("userId", userId);
		List list = this.sendService.getAll(queryFilter);
		ModelAndView mv = getAutoView().addObject("messageSendList", list)
				.addObject("longTime", longTime)
				.addObject("spanTime", spanTime);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除发送消息")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.sendService.delByIds(lAryId);
			message = new ResultMessage(1, "删除发送消息成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑发送消息")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		SysUser curUser = ContextUtil.getCurrentUser();
		Long userId = curUser.getUserId();
		String returnUrl = RequestUtil.getPrePage(request);
		MessageSend messageSend = null;
		if (id.longValue() != 0L)
			messageSend = (MessageSend) this.sendService.getById(id);
		else {
			messageSend = new MessageSend();
		}
		return getAutoView().addObject("messageSend", messageSend)
				.addObject("userId", userId).addObject("returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看发送消息明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		SysUser curUser = ContextUtil.getCurrentUser();
		Long userId = curUser.getUserId();
		MessageSend messageSend = (MessageSend) this.sendService.getById(Long
				.valueOf(id));

		return getAutoView().addObject("messageSend", messageSend).addObject(
				"userId", userId);
	}

	@RequestMapping({ "readMsgDialog" })
	@Action(description = "查看未读信息")
	public ModelAndView readMsgDialog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysUser sysUser = ContextUtil.getCurrentUser();
		List listSize = this.sendService
				.getNotReadMsgFirst(sysUser.getUserId());
		MessageSend messageSend = (MessageSend) listSize.get(0);

		//
		// 查询条数
		Integer count = sendService.getNotReadMsgCount(sysUser.getUserId());

		this.readService.addMessageRead(messageSend.getId(), sysUser);

		MessageReply msgReply = new MessageReply();
		msgReply.setMessageId(messageSend.getId());
		msgReply.setIsPrivate(new Short("1"));

		return getAutoView().addObject("messageSend", messageSend)
				.addObject("flag", Boolean.valueOf(count > 1))
				.addObject("msgReply", msgReply);
	}

	@ResponseBody
	@RequestMapping({ "notReadMsg" })
	@Action(description = "未读信息条数")
	public Integer notReadMsg(HttpServletResponse response) throws IOException {
		// List list =
		// this.sendService.getNotReadMsg(ContextUtil.getCurrentUserId());
		//
		// sendService.getNotReadMsgCount(ContextUtil.getCurrentUserId())
		// response.getWriter().print(list.size());

		return sendService.getNotReadMsgCount(ContextUtil.getCurrentUserId());
	}

	@RequestMapping({ "readDetail" })
	@Action(description = "查看已读明细")
	public ModelAndView readDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		int conReply = RequestUtil.getInt(request, "canReply");

		List readlist = this.readService.getReadByMsgId(Long.valueOf(id));
		return getAutoView().addObject("readlist", readlist).addObject(
				"canReply", Integer.valueOf(conReply));
	}

	@RequestMapping({ "replyDetail" })
	@Action(description = "查看回复明细")
	public ModelAndView replyDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");

		List replylist = this.replyService.getReplyByMsgId(Long.valueOf(id));
		return getAutoView().addObject("replylist", replylist);
	}
}
