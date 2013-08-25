package org.sz.platform.bpm.service.flow;

import java.util.List;

import org.sz.core.bpm.model.ProcessCmd;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.model.flow.ProcessRun;

public interface ProcessRunService extends BaseService<ProcessRun>{

	ProcessRun nextProcess(ProcessCmd processCmd) throws Exception;
	
	/**
	 * 暂存Task
	 * @param processCmd
	 * @throws Exception
	 */
	void temporaryTask(ProcessCmd processCmd) throws Exception;

	String getFirstNodetByDefId(String actDefId);

	BpmNodeSet getStartBpmNodeSet(Long defId, String actDefId, String nodeId,
			Short toFirstNode);

	ProcessRun startProcess(ProcessCmd processCmd) throws Exception;

	ProcessRun getByActInstanceId(String processInstanceId);

	List<ProcessRun> getAllHistory(QueryFilter queryFilter);

	List<ProcessRun> getMyAttend(QueryFilter filter);

	int updateProcessNameByDefId(Long defId, String processName);

	void delByIds(Long[] ids);

	List<ProcessRun> getMyProcessRun(Long creatorId, String subject,
			Short status, PageBean pb);

}