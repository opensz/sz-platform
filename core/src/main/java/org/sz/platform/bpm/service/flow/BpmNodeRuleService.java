package org.sz.platform.bpm.service.flow;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.BpmNodeRule;

public interface BpmNodeRuleService extends BaseService<BpmNodeRule>{

	List<BpmNodeRule> getByDefIdNodeId(String actDefId, String nodeId);

	void reSort(String ruleIds);

}