package org.sz.platform.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.MessageDao;
import org.sz.platform.system.model.Message;

@Repository("messageDao")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao {
	public Class getEntityClass() {
		return Message.class;
	}

	public List<Message> getListByActDefIdNodeId(String actDefId, String nodeId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);
		return getBySqlKey("getListByActDefIdNodeId", params);
	}

	public void delByActdefidAndNodeid(String actdefId, String nodeId) {
		Map params = new HashMap();
		params.put("actdefId", actdefId);
		params.put("nodeId", nodeId);
		delBySqlKey("delByMessageId", params);
	}

	public List<Message> getByActDefId(String actDefId) {
		Map params = new HashMap();
		params.put("actDefId", actDefId);
		return getBySqlKey("getByActDefId", params);
	}
}
