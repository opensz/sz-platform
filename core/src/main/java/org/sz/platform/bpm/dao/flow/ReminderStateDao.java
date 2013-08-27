package org.sz.platform.bpm.dao.flow;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.ReminderState;

public interface ReminderStateDao extends BaseDao<ReminderState> {

	Integer getAmountByUserTaskId(long taskId, long userId, int remindType);

	void delExpiredTaskReminderState();

}