package org.sz.platform.bpm.dao.flow;

import org.sz.core.bpm.model.ProcessExecution;
import org.sz.core.dao.BaseDao;

public interface ExecutionDao extends BaseDao<ProcessExecution> {

	String getIbatisMapperNamespace();

	void delLoopAssigneeVars(String executionId);

}