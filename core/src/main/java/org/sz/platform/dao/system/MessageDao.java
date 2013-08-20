package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.Message;

public interface MessageDao extends BaseDao<Message> {

	List<Message> getListByActDefIdNodeId(String actDefId, String nodeId);

	void delByActdefidAndNodeid(String actdefId, String nodeId);

	List<Message> getByActDefId(String actDefId);

}