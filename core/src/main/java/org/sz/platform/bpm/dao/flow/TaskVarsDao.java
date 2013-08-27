package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.model.flow.TaskVars;

public interface TaskVarsDao extends BaseDao<TaskVars> {

	List<TaskVars> getTaskVars(QueryFilter queryFilter);

}