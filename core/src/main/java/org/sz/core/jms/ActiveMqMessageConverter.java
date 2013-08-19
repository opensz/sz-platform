package org.sz.core.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.springframework.jms.support.converter.MessageConverter;
import org.sz.core.model.InnerMessage;
import org.sz.core.model.MailModel;
import org.sz.core.model.SmsMobile;

public class ActiveMqMessageConverter implements MessageConverter {
	public Message toMessage(Object obj, Session session) throws JMSException {
		if ((obj instanceof MailModel)) {
			ObjectMessage objMsg = session.createObjectMessage();
			objMsg.setObject((MailModel) obj);
			return objMsg;
		}
		if ((obj instanceof SmsMobile)) {
			ObjectMessage objMsg = session.createObjectMessage();
			objMsg.setObject((SmsMobile) obj);
			return objMsg;
		}
		if ((obj instanceof InnerMessage)) {
			ObjectMessage objMsg = session.createObjectMessage();
			objMsg.setObject((InnerMessage) obj);
			return objMsg;
		}
		throw new JMSException("Object:[" + obj + "] is not legal message");
	}

	public Object fromMessage(Message msg) throws JMSException {
		if ((msg instanceof ObjectMessage)) {
			ObjectMessage objMsg = (ObjectMessage) msg;
			Object obj = objMsg.getObject();
			if ((obj instanceof MailModel))
				return (MailModel) objMsg.getObject();
			if ((obj instanceof SmsMobile)) {
				return (SmsMobile) objMsg.getObject();
			}
			if ((obj instanceof InnerMessage)) {
				return (InnerMessage) objMsg.getObject();
			}
			throw new JMSException("Msg:[" + msg + "] is not legal message");
		}

		throw new JMSException("Msg:[" + msg + "] is not ObjectMessage");
	}
}
