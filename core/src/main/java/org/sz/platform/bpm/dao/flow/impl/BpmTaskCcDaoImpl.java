package org.sz.platform.bpm.dao.flow.impl;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.BpmTaskCcDao;
import org.sz.platform.bpm.model.flow.BpmTaskCc;

@Repository("bpmTaskCcDao")
public class BpmTaskCcDaoImpl extends BaseDaoImpl<BpmTaskCc> implements
		BpmTaskCcDao {
	public Class getEntityClass() {
		return BpmTaskCc.class;
	}
}
