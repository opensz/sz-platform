package org.sz.platform.service.system;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.MessageReply;
import org.sz.platform.model.system.SysUser;

public interface MessageReplyService extends BaseService<MessageReply>{

	void saveReply(MessageReply messageReply, SysUser sysUser) throws Exception;

	List<MessageReply> getReplyByMsgId(Long messageId);

}