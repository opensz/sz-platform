package org.sz.platform.bpm.service.flow;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.TaskComment;

public interface TaskCommentService extends BaseService<TaskComment>{

	void addTaskComment(TaskComment taskComment, TaskEntity taskEntity)
			throws Exception;

}