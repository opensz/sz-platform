package org.sz.platform.service.system.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.dao.system.MessageReceiverDao;
import org.sz.platform.dao.system.MessageSendDao;
import org.sz.platform.model.system.MessageReceiver;
import org.sz.platform.model.system.MessageSend;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.service.system.MessageSendService;

@Service("messageSendService")
public class MessageSendServiceImpl extends BaseServiceImpl<MessageSend>
		implements MessageSendService {

	@Resource
	private MessageSendDao dao;

	@Resource
	private MessageReceiverDao messageReceiverDao;

	protected IEntityDao<MessageSend, Long> getEntityDao() {
		return this.dao;
	}

	public List<MessageSend> getReceiverByUser(QueryFilter queryFilter) {
		return this.dao.getReceiverByUser(queryFilter);
	}

	public List<MessageSend> getNotReadMsg(Long receiverId) {
		return this.dao.getNotReadMsg(receiverId);
	}

	public List<MessageSend> getNotReadMsgFirst(Long receiverId) {
		return this.dao.getNotReadMsgFirst(receiverId);
	}

	public void addMessageSend(MessageSend messageSend, SysUser curUser,
			String receiverId, String receiverName, String receiverOrgId,
			String receiverOrgName) throws Exception {
		if ((receiverOrgName.length() > 0) && (receiverName.length() > 0)) {
			messageSend.setReceiverName(receiverName + "," + receiverOrgName);
		}

		Long messageId = null;
		if (messageSend.getId() == null) {
			messageId = Long.valueOf(UniqueIdUtil.genId());
			messageSend.setId(messageId);
			messageSend.setUserId(curUser.getUserId());
			messageSend.setUserName(curUser.getFullname());
			Date now = new Date();
			messageSend.setSendTime(now);
			add(messageSend);
		} else {
			messageId = messageSend.getId();
			update(messageSend);
		}

		String[] idArr = receiverId.split(",");
		String[] nameArr = receiverName.split(",");
		String[] orgIdArr = receiverOrgId.split(",");
		String[] orgNameArr = receiverOrgName.split(",");

		MessageReceiver messageReceiver = null;
		if (receiverId.length() > 0) {
			for (int i = 0; i < idArr.length; i++) {
				messageReceiver = new MessageReceiver();
				messageReceiver.setId(Long.valueOf(UniqueIdUtil.genId()));
				messageReceiver.setMessageId(messageId);
				if (StringUtil.isNotEmpty(idArr[i])) {
					messageReceiver.setReceiverId(Long.valueOf(Long
							.parseLong(idArr[i])));
					if (nameArr.length > i)
						messageReceiver.setReceiver(nameArr[i]);
					messageReceiver.setReceiveType(new Short("0"));
				}
				this.messageReceiverDao.add(messageReceiver);
			}
		}

		if (receiverOrgId.length() > 0)
			for (int i = 0; i < orgIdArr.length; i++) {
				messageReceiver = new MessageReceiver();
				messageReceiver.setId(Long.valueOf(UniqueIdUtil.genId()));
				messageReceiver.setMessageId(messageId);
				if (StringUtil.isNotEmpty(orgIdArr[i])) {
					messageReceiver.setReceiverId(Long.valueOf(Long
							.parseLong(orgIdArr[i])));
					if (orgNameArr.length > i)
						messageReceiver.setReceiver(orgNameArr[i]);
					messageReceiver.setReceiveType(MessageReceiver.TYPE_ORG);
				}
				this.messageReceiverDao.add(messageReceiver);
			}
	}

	public void addMessageSend(MessageSend messageSend) throws Exception {
		this.dao.add(messageSend);
		MessageReceiver receiver = new MessageReceiver();
		receiver.setCreateBy(messageSend.getCreateBy());
		receiver.setCreatetime(new Date());
		receiver.setId(Long.valueOf(UniqueIdUtil.genId()));
		receiver.setMessageId(messageSend.getId());
		receiver.setReceiverId(messageSend.getRid());
		receiver.setReceiveType(MessageReceiver.TYPE_USER);
		receiver.setReceiver(messageSend.getReceiverName());
		this.messageReceiverDao.add(receiver);
	}

	public Integer getNotReadMsgCount(Long receiverId) {
		return this.dao.getNotReadMsgCount(receiverId);
	}
}
