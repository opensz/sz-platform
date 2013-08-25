package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.system.model.MessageSend;
import org.sz.platform.system.model.SysUser;

public interface MessageSendService extends BaseService<MessageSend>{

	List<MessageSend> getReceiverByUser(QueryFilter queryFilter);

	List<MessageSend> getNotReadMsg(Long receiverId);
	
	List<MessageSend> getNotReadMsgFirst(Long receiverId);

	void addMessageSend(MessageSend messageSend, SysUser curUser,
			String receiverId, String receiverName, String receiverOrgId,
			String receiverOrgName) throws Exception;

	void addMessageSend(MessageSend messageSend) throws Exception;

	Integer getNotReadMsgCount(Long receiverId);
}