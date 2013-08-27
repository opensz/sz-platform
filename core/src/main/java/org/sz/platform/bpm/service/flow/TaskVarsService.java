package org.sz.platform.bpm.service.flow;

import java.util.List;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.bpm.model.flow.TaskVars;

public interface TaskVarsService extends BaseService<TaskVars>{

	List<TaskVars> getVars(QueryFilter queryFilter);

}