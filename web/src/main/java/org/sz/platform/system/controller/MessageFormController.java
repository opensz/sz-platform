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
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.system.model.Message;
import org.sz.platform.system.service.MessageService;

@Controller
@RequestMapping({ "/platform/system/message/" })
public class MessageFormController extends BaseFormController {

	@Resource
	private MessageService messageService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新消息设置")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Message message, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("message", message, bindResult,
				request);

		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		if (message.getMessageId() == null) {
			message.setMessageId(Long.valueOf(UniqueIdUtil.genId()));
			this.messageService.add(message);
			resultMsg = getText("record.added", new Object[] { "消息设置" });
		} else {
			this.messageService.update(message);
			resultMsg = getText("record.updated", new Object[] { "消息设置" });
		}
		writeResultMessage(response.getWriter(), resultMsg, 1);
	}

	@ModelAttribute
	protected Message getFormObject(@RequestParam("messageId") Long messageId,
			Model model) throws Exception {
		this.logger.debug("enter Message getFormObject here....");
		Message message = null;
		if (messageId != null)
			message = (Message) this.messageService.getById(messageId);
		else {
			message = new Message();
		}
		return message;
	}
}
