package org.sz.platform.bpm.dao.flow;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.BpmFormRun;

public interface BpmFormRunDao extends BaseDao<BpmFormRun> {

	BpmFormRun getByInstanceAndNode(String actInstanceId, String actNodeId);

	BpmFormRun getGlobalForm(String actInstanceId);

	void delByInstanceId(String actInstanceId);

}