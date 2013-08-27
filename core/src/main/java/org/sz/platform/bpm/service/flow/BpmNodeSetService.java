package org.sz.platform.bpm.service.flow;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.BpmNodeSet;

public interface BpmNodeSetService  extends BaseService<BpmNodeSet>{

	void save(Long defId, List<BpmNodeSet> nodeList) throws Exception;

	List<BpmNodeSet> getByDefId(Long defId);

	BpmNodeSet getByDefIdNodeId(Long defId, String nodeId);

	Map<String, BpmNodeSet> getMapByDefId(Long defId);

	BpmNodeSet getByActDefIdNodeId(String actDefId, String nodeId);

	BpmNodeSet getByActDefIdJoinTaskKey(String actDefId, String joinTaskKey);

	BpmNodeSet getBySetType(Long defId, Short setType);

	List<BpmNodeSet> getByActDefId(String actDefId);

	void updateIsJumpForDef(String nodeId, String actDefId, Short isJumpForDef);

}