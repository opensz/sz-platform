package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.platform.model.system.MessageSend;

public interface MessageSendDao extends BaseDao<MessageSend> {

	List<MessageSend> getReceiverByUser(QueryFilter queryFilter);

	List<MessageSend> getNotReadMsg(Long receiverId);

	List<MessageSend> getNotReadMsgFirst(Long receiverId);

	List<MessageSend> getNotReadMsgByUserId(long userId, PageBean pb);

	Integer getNotReadMsgCount(Long receiverId);
}