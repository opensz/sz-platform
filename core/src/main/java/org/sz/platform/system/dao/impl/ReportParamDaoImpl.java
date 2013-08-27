package org.sz.platform.system.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.ReportParamDao;
import org.sz.platform.system.model.ReportParam;

@Repository("reportParamDao")
public class ReportParamDaoImpl extends BaseDaoImpl<ReportParam> implements
		ReportParamDao {
	public Class getEntityClass() {
		return ReportParam.class;
	}
}
