package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.BpmDefVar;

public interface BpmDefVarDao extends BaseDao<BpmDefVar> {

	boolean isVarNameExist(String varName, String varKey, Long defId);

	List<BpmDefVar> getByDeployAndNode(String deployId, String nodeId);

	List<BpmDefVar> getVarsByFlowDefId(Long defId);

	void delByDefId(Long defId);

	List<BpmDefVar> getByDefId(Long defId);

}