package org.sz.platform.bpm.dao.flow.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.util.StringUtil;
import org.sz.platform.bpm.dao.flow.ExecutionStackDao;
import org.sz.platform.bpm.model.flow.ExecutionStack;

@Repository("executionStackDao")
public class ExecutionStackDaoImpl extends BaseDaoImpl<ExecutionStack>
		implements ExecutionStackDao {
	public Class getEntityClass() {
		return ExecutionStack.class;
	}

	public List<ExecutionStack> getByActInstIdNodeId(String actInstId,
			String nodeId) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("nodeId", nodeId);
		return getBySqlKey("getByActInstIdNodeId", params);
	}

	public List<ExecutionStack> getByActInstIdNodeIdToken(String actInstId,
			String nodeId, String taskToken) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("nodeId", nodeId);
		params.put("taskToken", taskToken);
		return getBySqlKey("getByActInstIdNodeIdToken", params);
	}

	public ExecutionStack getLastestStack(String actInstId, String nodeId) {
		List list = getByActInstIdNodeId(actInstId, nodeId);
		if (list.size() > 0) {
			return (ExecutionStack) list.get(0);
		}
		return null;
	}

	public ExecutionStack getLastestStack(String actInstId,
			String parentNodeId, String taskToken) {
		if (StringUtil.isNotEmpty(taskToken)) {
			List list = getByActInstIdNodeId(actInstId, parentNodeId);
			if (list.size() > 0) {
				return (ExecutionStack) list.get(0);
			}
			return null;
		}
		return getLastestStack(actInstId, parentNodeId);
	}

	public List<ExecutionStack> getByActInstIdDepth(String actInstId,
			Integer depth) {
		Map params = new HashMap();
		params.put("actInstId", actInstId);
		params.put("depth", depth);
		return getBySqlKey("getByActInstIdDepth", params);
	}

	public Integer delSubChilds(Long stackId, String nodePath) {
		Map params = new HashMap();
		params.put("stackId", stackId);
		params.put("nodePath", nodePath);
		return Integer.valueOf(delBySqlKey("delSubChilds", params));
	}

	public List<ExecutionStack> getByParentId(Long parentId) {
		return getBySqlKey("getByParentId", parentId);
	}

	public List<ExecutionStack> getByParentIdAndEndTimeNotNull(Long parentId) {
		return getBySqlKey("getByParentIdAndEndTimeNotNull", parentId);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}

	public ExecutionStack getFirstStack(String actInstId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("actInstId", actInstId);
		List<ExecutionStack> list = getBySqlKey("getFirstStackByActInstId", params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
