package org.sz.platform.system.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.sz.core.annotion.Action;
import org.sz.core.util.ContextUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.MessageSend;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.MessageSendService;

@Controller
@RequestMapping({ "/platform/system/messageSend/" })
public class MessageSendFormController extends BaseFormController {

	@Resource
	private MessageSendService messageSendService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新发送消息")
	public void save(HttpServletRequest request, HttpServletResponse response,
			MessageSend messageSend, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("messageSend", messageSend,
				bindResult, request);

		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String receiverId = RequestUtil.getString(request, "receiverId");
		String receiverName = RequestUtil.getString(request, "receiverName");
		String receiverOrgId = RequestUtil.getString(request, "receiverOrgId");
		String receiverOrgName = RequestUtil.getString(request,
				"receiverOrgName");

		SysUser curUser = ContextUtil.getCurrentUser();
		this.messageSendService.addMessageSend(messageSend, curUser,
				receiverId, receiverName, receiverOrgId, receiverOrgName);

		String resultMsg = null;

		if (messageSend.getId() == null) {
			resultMsg = getText("record.added", new Object[] { "发送消息" });
		} else {
			resultMsg = getText("record.updated", new Object[] { "发送消息" });
		}

		writeResultMessage(response.getWriter(), resultMsg, 1);
	}

	@ModelAttribute
	protected MessageSend getFormObject(@RequestParam("id") Long id, Model model)
			throws Exception {
		this.logger.debug("enter MessageSend getFormObject here....");
		MessageSend messageSend = null;
		if (id != null)
			messageSend = (MessageSend) this.messageSendService.getById(id);
		else {
			messageSend = new MessageSend();
		}
		return messageSend;
	}
}
