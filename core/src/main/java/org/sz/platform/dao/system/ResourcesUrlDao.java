package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.ResourcesUrl;
import org.sz.platform.model.system.ResourcesUrlExt;

public interface ResourcesUrlDao extends BaseDao<ResourcesUrl> {

	List<ResourcesUrl> getByResId(long resId);

	void delByResId(long resId);

	List<ResourcesUrlExt> getUrlAndRoleBySystemId(long systemId);

	List<ResourcesUrlExt> getSubSystemResources(String defaultUrl);

	List<ResourcesUrlExt> getSubSystemResByAlias(String alias);

}