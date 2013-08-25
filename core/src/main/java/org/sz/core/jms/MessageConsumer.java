package org.sz.core.jms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.sz.core.engine.MessageEngine;
import org.sz.core.model.InnerMessage;
import org.sz.core.model.MailModel;
import org.sz.core.model.SmsMobile;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.system.model.MessageSend;

public class MessageConsumer {
	private final Log logger = LogFactory.getLog(MessageConsumer.class);

	public void sendMessage(Object model) throws Exception {
		MessageEngine mSender = (MessageEngine) ContextUtil
				.getBean(MessageEngine.class);

		if ((model instanceof MailModel)) {
			MailModel mailModel = (MailModel) model;
			SimpleMailMessage message = new SimpleMailMessage();
			message.setSubject(mailModel.getSubject());
			message.setTo(mailModel.getTo());
			message.setCc(mailModel.getCc());
			message.setBcc(mailModel.getBcc());
			message.setText(mailModel.getContent());
			message.setSentDate(mailModel.getSendDate());
			mSender.sendMail(message);
			this.logger.debug("MailModel");
		} else if ((model instanceof SmsMobile)) {
			SmsMobile smsModel = (SmsMobile) model;
			List mobiles = new ArrayList();
			mobiles.add(smsModel.getPhoneNumber());
			mSender.sendSms(mobiles, smsModel.getSmsContent());
			this.logger.debug("SmsMobile");
		} else if ((model instanceof InnerMessage)) {
			InnerMessage innerModel = (InnerMessage) model;
			MessageSend messageSend = new MessageSend();
			messageSend.setId(Long.valueOf(UniqueIdUtil.genId()));
			messageSend.setUserName(innerModel.getFromName());
			messageSend.setUserId(Long.valueOf(Long.parseLong(innerModel
					.getFrom())));
			messageSend.setSendTime(new Date());
			messageSend.setMessageType("6");
			messageSend.setContent(innerModel.getContent());
			messageSend.setSubject(innerModel.getSubject());
			messageSend.setCreateBy(Long.valueOf(Long.parseLong(innerModel
					.getFrom())));
			messageSend
					.setRid(Long.valueOf(Long.parseLong(innerModel.getTo())));
			messageSend.setReceiverName(innerModel.getToName());
			messageSend.setCanReply(Short.valueOf(innerModel.getCanReply()));
			messageSend.setCreatetime(innerModel.getSendDate());
			mSender.sendInnerMessage(messageSend);

			this.logger.debug("InnerMessage");
		}
	}
}
