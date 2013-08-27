package org.sz.platform.bpm.dao.flow.impl;

import org.springframework.stereotype.Repository;
import org.sz.core.bpm.model.ProcessExecution;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.ExecutionDao;

@Repository("executionDao")
public class ExecutionDaoImpl extends BaseDaoImpl<ProcessExecution>
		implements ExecutionDao {
	public Class getEntityClass() {
		return ProcessExecution.class;
	}

	public String getIbatisMapperNamespace() {
		return "org.sz.core.bpm.model.ProcessExecution";
	}

	public void delLoopAssigneeVars(String executionId) {
		delBySqlKey("delAssigneeByExecutionId", executionId);
		delBySqlKey("delLoopCounterByExecutionId", executionId);
	}
}
