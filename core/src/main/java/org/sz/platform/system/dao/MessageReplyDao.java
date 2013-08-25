package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.MessageReply;

public interface MessageReplyDao extends BaseDao<MessageReply> {

	List<MessageReply> getReplyByMsgId(Long messageId);

}