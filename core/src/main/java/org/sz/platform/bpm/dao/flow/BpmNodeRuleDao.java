package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.BpmNodeRule;

public interface BpmNodeRuleDao extends BaseDao<BpmNodeRule> {

	List<BpmNodeRule> getByDefIdNodeId(String actDefId, String nodeId);

	void reSort(Long ruleId, Long priority);

	void delByActDefId(String actDefId);

}