package org.sz.platform.bpm.service.flow;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.model.flow.BpmFormRun;
import org.sz.platform.bpm.model.flow.BpmNodeSet;

public interface BpmFormRunService extends BaseService<BpmFormRun>{

	void addFormRun(String actDefId, Long runId, String actInstanceId)
			throws Exception;

	Map<String, BpmNodeSet> getTaskForm(List<BpmNodeSet> list);

	BpmNodeSet getStartBpmNodeSet(String actDefId, Short toFirstNode);

	boolean getCanDirectStart(Long defId);

	BpmFormRun getByInstanceAndNode(String actInstanceId, String actNodeId);

}