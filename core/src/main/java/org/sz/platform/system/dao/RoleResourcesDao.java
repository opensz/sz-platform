package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.RoleResources;

public interface RoleResourcesDao extends BaseDao<RoleResources>{

	List<RoleResources> getBySysAndRole(Long systemId, Long roleId);

	void delByRoleAndSys(Long systemId, Long roleId);

	void delByResId(Long resId);

	List<RoleResources> getRoleRes(Long roleId);

	List<RoleResources> getByResId(Long resId);

}