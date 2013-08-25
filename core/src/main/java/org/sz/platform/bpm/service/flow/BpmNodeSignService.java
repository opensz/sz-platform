package org.sz.platform.bpm.service.flow;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.BpmNodeSign;

public interface BpmNodeSignService extends BaseService<BpmNodeSign>  {

	BpmNodeSign getByDefIdAndNodeId(String actDefId, String nodeId);

}