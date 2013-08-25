package org.sz.platform.bpm.dao.flow;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.BpmTaskComment;

public interface BpmTaskCommentDao extends BaseDao<BpmTaskComment> {

	void delByactDefId(String actDefId);

}