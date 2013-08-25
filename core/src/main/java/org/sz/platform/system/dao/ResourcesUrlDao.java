package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.ResourcesUrl;
import org.sz.platform.system.model.ResourcesUrlExt;

public interface ResourcesUrlDao extends BaseDao<ResourcesUrl> {

	List<ResourcesUrl> getByResId(long resId);

	void delByResId(long resId);

	List<ResourcesUrlExt> getUrlAndRoleBySystemId(long systemId);

	List<ResourcesUrlExt> getSubSystemResources(String defaultUrl);

	List<ResourcesUrlExt> getSubSystemResByAlias(String alias);

}