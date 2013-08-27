package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.ExecutionStack;

public interface ExecutionStackDao extends BaseDao<ExecutionStack> {

	List<ExecutionStack> getByActInstIdNodeId(String actInstId, String nodeId);

	List<ExecutionStack> getByActInstIdNodeIdToken(String actInstId,
			String nodeId, String taskToken);

	//得到最新的stack
	ExecutionStack getLastestStack(String actInstId, String nodeId);
	
	//得到第一个stack
	ExecutionStack getFirstStack(String actInstId);

	ExecutionStack getLastestStack(String actInstId, String parentNodeId,
			String taskToken);

	List<ExecutionStack> getByActInstIdDepth(String actInstId, Integer depth);

	Integer delSubChilds(Long stackId, String nodePath);

	List<ExecutionStack> getByParentId(Long parentId);

	List<ExecutionStack> getByParentIdAndEndTimeNotNull(Long parentId);

	void delByActDefId(String actDefId);

}