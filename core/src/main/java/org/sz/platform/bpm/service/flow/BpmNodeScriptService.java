package org.sz.platform.bpm.service.flow;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.BpmNodeScript;

public interface BpmNodeScriptService extends BaseService<BpmNodeScript> {

	List<BpmNodeScript> getByNodeScriptId(String nodeId, String actDefId);

	Map<String, BpmNodeScript> getMapByNodeScriptId(String nodeId,
			String actDefId);

	BpmNodeScript getScriptByType(String nodeId, String actDefId,
			Integer scriptType);

	void saveScriptDef(String defId, String nodeId, List<BpmNodeScript> list)
			throws Exception;

}