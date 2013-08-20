package org.sz.platform.dao.system;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.SysDataSource;
public interface SysDataSourceDao extends BaseDao<SysDataSource> {

	boolean isAliasExisted(String alias);

	boolean isAliasExistedByUpdate(SysDataSource sysDataSource);

	SysDataSource getByAlias(String alias);

}