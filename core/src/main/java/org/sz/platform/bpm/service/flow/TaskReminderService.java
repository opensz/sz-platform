package org.sz.platform.bpm.service.flow;

import org.sz.core.service.BaseService;
import org.sz.platform.bpm.model.flow.TaskReminder;

public interface TaskReminderService  extends BaseService<TaskReminder>{

	TaskReminder getByActDefAndNodeId(String actDefId, String nodeId);

}