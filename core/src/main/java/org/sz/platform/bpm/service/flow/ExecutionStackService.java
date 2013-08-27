package org.sz.platform.bpm.service.flow;

import java.util.List;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.sz.core.bpm.model.ProcessCmd;
import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.ExecutionStack;

public interface ExecutionStackService extends BaseService<ExecutionStack> {

	void initStack(String actInstId) throws Exception;

	ExecutionStack backPrepared(ProcessCmd processCmd, TaskEntity taskEntity,
			String taskToken);

	//第一个任务处理
	ExecutionStack firstPrepared(ProcessCmd processCmd, TaskEntity taskEntity);
	
	void pop(ExecutionStack parentStack, boolean isRecover);

	void pushNewTasks(String actInstId, String destNodeId,
			List<TaskEntity> newTasks, String oldTaskToken) throws Exception;

	void addStack(String actInstId, String destNodeId, String oldTaskToken)
			throws Exception;

	List<ExecutionStack> getByParentId(Long parentId);

	List<ExecutionStack> getByParentIdAndEndTimeNotNull(Long parentId);

	List<ExecutionStack> getByActInstIdNodeId(String actInstId, String nodeId);

	ExecutionStack getLastestStack(String actInstId, String nodeId);

	ExecutionStack getLastestStack(String actInstId, String nodeId,
			String taskToken);

	Integer delSubChilds(Long stackId, String nodePath);

	//得到第一个stack
	ExecutionStack getFirstStack(String actInstId);
}