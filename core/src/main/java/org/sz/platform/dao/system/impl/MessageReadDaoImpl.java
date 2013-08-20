package org.sz.platform.dao.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.MessageReadDao;
import org.sz.platform.model.system.MessageRead;

@Repository("messageReadDao")
public class MessageReadDaoImpl extends BaseDaoImpl<MessageRead> implements
		MessageReadDao {
	public Class getEntityClass() {
		return MessageRead.class;
	}

	public List<MessageRead> getMessageReadList(Long messageId) {
		Map param = new HashMap();
		param.put("messageId", messageId);
		return getBySqlKey("getAll", param);
	}

	public MessageRead getReadByUser(Long messageId, Long receiverId) {
		Map param = new HashMap();
		param.put("messageId", messageId);
		param.put("receiverId", receiverId);
		return (MessageRead) getUnique("getReadByUser", param);
	}

	public List<MessageRead> getReadByMsgId(Long messageId) {
		return getBySqlKey("getReadByMsgId", messageId);
	}
}
