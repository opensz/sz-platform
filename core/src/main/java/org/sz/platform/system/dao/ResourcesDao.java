package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.Resources;
import org.sz.platform.system.model.ResourcesUrlExt;

public interface ResourcesDao extends BaseDao<Resources>  {

	List<Resources> getByParentId(long parentId);

	List<Resources> getBySystemId(long systemId);

	List<Resources> getNormMenu(Long systemId, Long userId);

	List<Resources> getSuperMenu(Long systemId);

	List<ResourcesUrlExt> getDefaultUrlAndRoleBySystemId(long systemId);

	List<ResourcesUrlExt> getFunctionAndRoleBySystemId(long systemId);

	List<ResourcesUrlExt> getSubSystemFunction(String defaultUrl);

	List<ResourcesUrlExt> getSubSystemsFuncByAlias(String alias);

	List<ResourcesUrlExt> getSubSystemResources(String defaultUrl);

	List<ResourcesUrlExt> getSubSystemResByAlias(String alias);

	Integer isAliasExists(Long systemId, String alias);

	Integer isAliasExistsForUpd(Long systemId, Long resId, String alias);

}