package org.sz.platform.bpm.dao.flow;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.TaskFork;

public interface TaskForkDao extends BaseDao<TaskFork> {

	Integer getMaxSn(String actInstId);

	TaskFork getByInstIdJoinTaskKey(String actInstId, String joinTaskKey);

	TaskFork getByInstIdJoinTaskKeyForkToken(String actInstId,
			String joinTaskKey, String forkToken);

}