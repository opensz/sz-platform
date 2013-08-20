package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.MessageReply;

public interface MessageReplyDao extends BaseDao<MessageReply> {

	List<MessageReply> getReplyByMsgId(Long messageId);

}