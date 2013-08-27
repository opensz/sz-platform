package org.sz.platform.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.platform.system.dao.MessageSendDao;
import org.sz.platform.system.model.MessageSend;

@Repository("messageSendDao")
public class MessageSendDaoImpl extends BaseDaoImpl<MessageSend> implements
		MessageSendDao {
	public Class getEntityClass() {
		return MessageSend.class;
	}

	public List<MessageSend> getReceiverByUser(QueryFilter queryFilter) {
		return getBySqlKey("getReceiverByUser", queryFilter);
	}

	public List<MessageSend> getNotReadMsg(Long receiverId) {
		return getBySqlKey("getNotReadMsgByUserId", receiverId);
	}

	public List<MessageSend> getNotReadMsgFirst(Long receiverId) {
		return getBySqlKey("getNotReadMsgFirst", receiverId);
	}

	public Integer getNotReadMsgCount(Long receiverId) {
		Object obj = this.getOne("getNotReadMsgCount", receiverId);
		if (obj != null) {
			return Integer.valueOf(obj.toString());
		}
		return 0;
	}

	public List<MessageSend> getNotReadMsgByUserId(long userId, PageBean pb) {
		Map params = new HashMap();
		params.put("receiverId", Long.valueOf(userId));
		return getBySqlKey("getNotReadMsgByUserId", params, pb);
	}
}
