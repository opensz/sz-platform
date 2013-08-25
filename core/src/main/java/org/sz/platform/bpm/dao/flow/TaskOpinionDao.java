package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.TaskOpinion;

public interface TaskOpinionDao extends BaseDao<TaskOpinion> {

	TaskOpinion getByTaskId(Long taskId);

	List<TaskOpinion> getByActInstId(String actInstId);

	void delByActDefIdTaskOption(String actDefId);

	void delByTaskId(Long taskId);

	List<TaskOpinion> getByActInstIdTaskKey(String actInstId, String taskKey);

	List<TaskOpinion> getByActInstIdExeUserId(String actInstId, Long exeUserId);

	List<TaskOpinion> getFormOptionsByInstance(String instanceId);

}