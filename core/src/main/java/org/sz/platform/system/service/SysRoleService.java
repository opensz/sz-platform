package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.system.model.ResourcesUrlExt;
import org.sz.platform.system.model.SysRole;

public interface SysRoleService extends BaseService<SysRole> {

	boolean isExistRoleAlias(String alias);

	boolean isExistRoleAliasForUpd(String alias, Long roleId);

	List<SysRole> getRoleList(QueryFilter queryFilter);

	List<SysRole> getByUserId(Long userId);

	List<SysRole> getBySystemId(Long systemId);

	String getRoleIdsByUserId(Long userId);

	List<SysRole> getRoleTree(QueryFilter queryFilter);

	List<ResourcesUrlExt> getUrlRightMap(Long systemId);

	List<ResourcesUrlExt> getFunctionRoleList(Long systemId);

	List<ResourcesUrlExt> getSubSystemFunction(String alias);

	List<ResourcesUrlExt> getSubSystemResources(String alias);

	void copyRole(SysRole sysRole, long oldRoleId) throws Exception;

	List<SysRole> loadSecurityRole(String alias, String roleName);

}