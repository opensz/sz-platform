package org.sz.platform.bpm.service.flow;

import org.activiti.engine.delegate.DelegateTask;
import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.TaskFork;

public interface TaskForkService extends BaseService<TaskFork>{

	Integer getMaxSn(String actInstId);

	TaskFork newTaskFork(DelegateTask delegateTask, String joinTaskName,
			String joinTaskKey, Integer forkCount);

	TaskFork getByInstIdJoinTaskKey(String actInstId, String joinTaskKey);

	TaskFork getByInstIdJoinTaskKeyForkToken(String actInstId,
			String joinTaskKey, String forkToken);

}