package org.sz.platform.dao.system;

import java.util.List;
import java.util.Map;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.MessageReceiver;

public interface MessageReceiverDao extends BaseDao<MessageReceiver> {

	List<MessageReceiver> getMessageReceiverList(Long messageId);

	List<Map> getReadReplyByUser(Long messageId);

	List<Map> getReadReplyByPath(Long messageId, String path);

}