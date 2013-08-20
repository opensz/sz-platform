package org.sz.platform.service.system;

import java.util.List;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.model.system.MessageSend;
import org.sz.platform.model.system.SysUser;

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