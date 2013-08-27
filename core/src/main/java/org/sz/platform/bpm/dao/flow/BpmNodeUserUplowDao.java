package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.BpmNodeUserUplow;

public interface BpmNodeUserUplowDao extends BaseDao<BpmNodeUserUplow> {

	int delByNodeUserId(long nodeUserId);

	List<BpmNodeUserUplow> getByNodeUserId(long userId);

}