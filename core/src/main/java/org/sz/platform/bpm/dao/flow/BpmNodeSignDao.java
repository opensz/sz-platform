package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.BpmNodeSign;

public interface BpmNodeSignDao extends BaseDao<BpmNodeSign> {

	BpmNodeSign getByDefIdAndNodeId(String actDefId, String nodeId);

	List<BpmNodeSign> getByActDefId(String actDefId, String nodeId);

	void delActDefId(String actDefId);

}