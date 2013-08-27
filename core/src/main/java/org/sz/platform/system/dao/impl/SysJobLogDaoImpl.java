package org.sz.platform.system.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.SysJobLogDao;
import org.sz.platform.system.model.SysJobLog;

@Repository("sysJobLogDao")
public class SysJobLogDaoImpl extends BaseDaoImpl<SysJobLog> implements
		SysJobLogDao {
	public Class getEntityClass() {
		return SysJobLog.class;
	}
}
