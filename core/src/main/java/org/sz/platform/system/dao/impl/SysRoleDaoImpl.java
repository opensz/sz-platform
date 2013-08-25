package org.sz.platform.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.platform.system.dao.SysRoleDao;
import org.sz.platform.system.model.SysRole;

@Repository("sysRoleDao")
public class SysRoleDaoImpl extends BaseDaoImpl<SysRole> implements SysRoleDao {
	public Class getEntityClass() {
		return SysRole.class;
	}

	public List<SysRole> getBySystemId(Long systemId, PageBean pb) {
		return getBySqlKey("getBySystemId", systemId, pb);
	}

	public boolean isExistRoleAlias(String alias) {
		Integer count = (Integer) getOne("isExistRoleAlias", alias);
		return count.intValue() > 0;
	}

	public boolean isExistRoleAliasForUpd(String alias, Long roleId) {
		Map map = new HashMap();
		map.put("alias", alias);
		map.put("roleId", roleId);
		Integer count = (Integer) getOne("isExistRoleAliasForUpd", map);
		return count.intValue() > 0;
	}

	public List<SysRole> getRole(QueryFilter queryFilter) {
		return getBySqlKey("getRole", queryFilter);
	}

	public List<SysRole> getByUserId(Long userId) {
		return getBySqlKey("getByUserId", userId);
	}

	public List<SysRole> getRoleTree(QueryFilter queryFilter) {
		String sqlKey = "getRoleTree_" + getDbType();
		return getBySqlKey(sqlKey, queryFilter);
	}

	public List<SysRole> loadSecurityRole(String defaultUrl, String roleName) {
		Map map = new HashMap();
		map.put("defaultUrl", defaultUrl);
		map.put("roleName", roleName);
		return getBySqlKey("loadSecurityRole", map);
	}

	public List<SysRole> loadSecurityRoleByAlias(String alias, String roleName) {
		Map map = new HashMap();
		map.put("defaultUrl", alias);
		map.put("roleName", roleName);
		return getBySqlKey("loadSecurityRoleByAlias", map);
	}

	public List<SysRole> getBySystemId(Long systemId) {
		List list = getBySqlKey("getBySystemId", systemId);
		return list;
	}
}
