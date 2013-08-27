package org.sz.platform.bpm.dao.flow;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.TaskReminder;

public interface TaskReminderDao extends BaseDao<TaskReminder> {

	TaskReminder getByActDefAndNodeId(String actDefId, String nodeId);

}