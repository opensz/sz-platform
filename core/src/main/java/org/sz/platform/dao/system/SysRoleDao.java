package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.platform.model.system.SysRole;

public interface SysRoleDao extends BaseDao<SysRole> {

	List<SysRole> getBySystemId(Long systemId, PageBean pb);

	boolean isExistRoleAlias(String alias);

	boolean isExistRoleAliasForUpd(String alias, Long roleId);

	List<SysRole> getRole(QueryFilter queryFilter);

	List<SysRole> getByUserId(Long userId);

	List<SysRole> getRoleTree(QueryFilter queryFilter);

	List<SysRole> loadSecurityRole(String defaultUrl, String roleName);

	List<SysRole> loadSecurityRoleByAlias(String alias, String roleName);

	List<SysRole> getBySystemId(Long systemId);

}