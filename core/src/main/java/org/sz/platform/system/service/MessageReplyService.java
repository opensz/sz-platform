package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.MessageReply;
import org.sz.platform.system.model.SysUser;

public interface MessageReplyService extends BaseService<MessageReply>{

	void saveReply(MessageReply messageReply, SysUser sysUser) throws Exception;

	List<MessageReply> getReplyByMsgId(Long messageId);

}