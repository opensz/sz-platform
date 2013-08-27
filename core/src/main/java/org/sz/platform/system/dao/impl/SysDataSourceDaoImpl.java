package org.sz.platform.system.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.SysDataSourceDao;
import org.sz.platform.system.model.SysDataSource;

@Repository("sysDataSourceDao")
public class SysDataSourceDaoImpl extends BaseDaoImpl<SysDataSource> implements
		SysDataSourceDao {
	public Class getEntityClass() {
		return SysDataSource.class;
	}

	public boolean isAliasExisted(String alias) {
		return ((Integer) getOne("isAliasExisted", alias)).intValue() > 0;
	}

	public boolean isAliasExistedByUpdate(SysDataSource sysDataSource) {
		return ((Integer) getOne("isAliasExistedByUpdate", sysDataSource))
				.intValue() > 0;
	}

	public SysDataSource getByAlias(String alias) {
		return (SysDataSource) getUnique("getByAlias", alias);
	}
}
