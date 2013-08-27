package org.sz.platform.bpm.dao.flow;

import java.util.List;
import java.util.Map;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.BpmNodeSet;

public interface BpmNodeSetDao extends BaseDao<BpmNodeSet> {

	List<BpmNodeSet> getByDefId(Long defId);
	
	public List<BpmNodeSet> getByDefId(Long defId, Short setType);

	BpmNodeSet getByDefIdNodeId(Long defId, String nodeId);

	BpmNodeSet getByActDefIdNodeId(String actDefId, String nodeId);

	BpmNodeSet getByActDefIdJoinTaskKey(String actDefId, String joinTaskKey);

	void delByDefId(Long defId);

	BpmNodeSet getBySetType(Long defId, Short setType);

	List<BpmNodeSet> getByStartGlobal(Long defId);

	void delByStartGlobalDefId(Long defId);

	Map<String, BpmNodeSet> getMapByDefId(Long defId);

	List<BpmNodeSet> getByActDefId(String actDefId);

	void updateIsJumpForDef(String nodeId, String actDefId, Short isJumpForDef);

}