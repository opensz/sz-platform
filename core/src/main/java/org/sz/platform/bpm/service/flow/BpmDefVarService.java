package org.sz.platform.bpm.service.flow;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.BpmDefVar;

public interface BpmDefVarService extends BaseService<BpmDefVar>{

	boolean isVarNameExist(String varName, String varKey, Long defId);

	List<BpmDefVar> getByDeployAndNode(String deployId, String nodeId);

	List<BpmDefVar> getVarsByFlowDefId(long defId);

}