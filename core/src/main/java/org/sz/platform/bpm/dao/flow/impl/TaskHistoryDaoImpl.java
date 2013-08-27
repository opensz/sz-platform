package org.sz.platform.bpm.dao.flow.impl;

import org.springframework.stereotype.Repository;
import org.sz.core.bpm.model.ProcessTaskHistory;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.TaskHistoryDao;

@Repository("taskHistoryDao")
public class TaskHistoryDaoImpl extends
BaseDaoImpl<ProcessTaskHistory> implements TaskHistoryDao {
	public Class getEntityClass() {
		return ProcessTaskHistory.class;
	}

	public String getIbatisMapperNamespace() {
		return "org.sz.core.bpm.model.ProcessTaskHistory";
	}
}
