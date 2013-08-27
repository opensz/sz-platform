package org.sz.platform.bpm.dao.flow.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.dao.flow.ProcessRunDao;
import org.sz.platform.bpm.model.flow.ProcessRun;

@Repository("processRunDao")
public class ProcessRunDaoImpl extends BaseDaoImpl<ProcessRun> implements
		ProcessRunDao {
	public Class getEntityClass() {
		return ProcessRun.class;
	}

	public ProcessRun getByActInstanceId(String processInstanceId) {
		return (ProcessRun) getUnique("getByActInstanceId", processInstanceId);
	}

	public List<ProcessRun> getAllHistory(QueryFilter queryFilter) {
		return getBySqlKey("getAllHistory", queryFilter);
	}

	public int updateProcessNameByDefId(Long defId, String processName) {
		Map params = new HashMap();
		params.put("defId", defId);
		params.put("processName", processName);
		return update("updateProcessNameByDefId", params);
	}

	public List<ProcessRun> getMyAttend(QueryFilter filter) {
		return getBySqlKey("getMyAttend", filter);
	}

	public List<ProcessRun> getMyProcessRun(Long creatorId, String subject,
			Short status, PageBean pb) {
		Map params = new HashMap();
		params.put("creatorId", creatorId);
		params.put("subject", subject);
		params.put("status", status);
		return getBySqlKey("getMyProcessRun", params, pb);
	}

	public List<ProcessRun> getMyAttend(Long assignee, Short status, PageBean pb) {
		Map params = new HashMap();
		params.put("assignee", assignee);
		params.put("status", status);
		return getBySqlKey("getMyAttend", params, pb);
	}

	public List<ProcessRun> myStart(Long creatorId, PageBean pb) {
		Map params = new HashMap();
		params.put("creatorId", creatorId);
		return getBySqlKey("getAll", params, pb);
	}
}
