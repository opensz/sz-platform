package org.sz.platform.bpm.dao.flow;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.flow.BpmNodeUser;

public interface BpmNodeUserDao extends BaseDao<BpmNodeUser> {

	List<BpmNodeUser> getBySetId(Long setId);

	void delByActDefId(String actDefId);

}