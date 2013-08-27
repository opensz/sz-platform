package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.BpmNodeScript;

public interface BpmNodeScriptDao extends BaseDao<BpmNodeScript> {

	List<BpmNodeScript> getByBpmNodeScriptId(String nodeId, String actDefId);

	void delByDefAndNodeId(String actDefId, String nodeId);

	BpmNodeScript getScriptByType(String nodeId, String actDefId,
			Integer scriptType);

	void delByActDefId(String actDefId);

}