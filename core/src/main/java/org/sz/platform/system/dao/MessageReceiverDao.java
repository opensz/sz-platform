package org.sz.platform.system.dao;

import java.util.List;
import java.util.Map;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.MessageReceiver;

public interface MessageReceiverDao extends BaseDao<MessageReceiver> {

	List<MessageReceiver> getMessageReceiverList(Long messageId);

	List<Map> getReadReplyByUser(Long messageId);

	List<Map> getReadReplyByPath(Long messageId, String path);

}