package org.sz.platform.system.dao;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.SysDataSource;
public interface SysDataSourceDao extends BaseDao<SysDataSource> {

	boolean isAliasExisted(String alias);

	boolean isAliasExistedByUpdate(SysDataSource sysDataSource);

	SysDataSource getByAlias(String alias);

}