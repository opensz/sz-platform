package org.sz.platform.system.service;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.SysDataSource;

public interface SysDataSourceService extends BaseService<SysDataSource> {

	void delByIds(Long[] ids);

	List<Map<String, Object>> testConnectById(Long[] ids);

	List<Map<String, Object>> testConnectByForm(SysDataSource sysDataSource);

	SysDataSource getByAlias(String alias);

	boolean isAliasExisted(String alias);

	boolean isAliasExistedByUpdate(SysDataSource sysDataSource);

}