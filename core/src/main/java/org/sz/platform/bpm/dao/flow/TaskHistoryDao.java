package org.sz.platform.bpm.dao.flow;

import org.sz.core.bpm.model.ProcessTaskHistory;
import org.sz.core.dao.BaseDao;

public interface TaskHistoryDao extends BaseDao<ProcessTaskHistory> {

	String getIbatisMapperNamespace();

}