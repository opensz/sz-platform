package org.sz.platform.bpm.service.flow;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.bpm.model.flow.TaskOpinion;

public interface TaskOpinionService extends BaseService<TaskOpinion> {

	TaskOpinion getByTaskId(Long taskId);

	List<TaskOpinion> getByActInstId(String actInstId);

	void delByActDefIdTaskOption(String actDefId);

	List<TaskOpinion> getByActInstIdTaskKey(String actInstId, String taskKey);

	TaskOpinion getLatestTaskOpinion(String actInstId, String taskKey);

	TaskOpinion getLatestUserOpinion(String actInstId, Long exeUserId);

	void delByTaskId(Long taskId);

}