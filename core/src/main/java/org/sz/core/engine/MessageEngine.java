package org.sz.core.engine;

import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.sz.core.sms.IShortMessage;
import org.sz.core.util.ContextUtil;
import org.sz.platform.system.model.MessageSend;
import org.sz.platform.system.service.MessageSendService;

public class MessageEngine {
	private final Log logger = LogFactory.getLog(MessageEngine.class);
	private JavaMailSender mailSender;
	private IShortMessage shortMessage;
	private String fromUser = "";

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setShortMessage(IShortMessage shortMessage) {
		this.shortMessage = shortMessage;
	}

	public void sendMail(SimpleMailMessage msg) {
		try {
			MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
					mimeMessage);
			mimeMessageHelper.setSubject(msg.getSubject());

			mimeMessageHelper.setTo(msg.getTo());
			if (msg.getCc() != null)
				mimeMessageHelper.setCc(msg.getCc());
			if (msg.getBcc() != null)
				mimeMessageHelper.setBcc(msg.getBcc());
			mimeMessageHelper.setFrom(this.fromUser);
			mimeMessageHelper.setSentDate(new Date());
			mimeMessageHelper.setText(msg.getText(), true);
			this.mailSender.send(mimeMessage);
		} catch (Exception ex) {
			this.logger.error(ex.getMessage());
		}
	}

	public void sendSms(List<String> mobiles, String content) {
		boolean result;
		try {
			result = this.shortMessage.sendSms(mobiles, content);
		} catch (Exception ex) {

			this.logger.error(ex.getMessage());
		}
	}

	public void sendInnerMessage(MessageSend messageSend) {
		try {
			MessageSendService messageSendService = (MessageSendService) ContextUtil
					.getBean(MessageSendService.class);
			messageSendService.addMessageSend(messageSend);
		} catch (Exception ex) {
			this.logger.error(ex.getMessage());
		}
	}
}
