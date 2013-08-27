package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.model.flow.ProcessRun;

public interface ProcessRunDao extends BaseDao<ProcessRun> {

	ProcessRun getByActInstanceId(String processInstanceId);

	List<ProcessRun> getAllHistory(QueryFilter queryFilter);

	int updateProcessNameByDefId(Long defId, String processName);

	List<ProcessRun> getMyAttend(QueryFilter filter);

	List<ProcessRun> getMyProcessRun(Long creatorId, String subject,
			Short status, PageBean pb);

	List<ProcessRun> getMyAttend(Long assignee, Short status, PageBean pb);

	List<ProcessRun> myStart(Long creatorId, PageBean pb);

}