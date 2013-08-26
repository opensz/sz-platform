package org.sz.platform.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.UserRoleDao;
import org.sz.platform.system.model.UserRole;

@Repository("userRoleDao")
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements
		UserRoleDao {
	public Class getEntityClass() {
		return UserRole.class;
	}

	public UserRole getUserRoleModel(Long userId, Long roleId) {
		Map param = new HashMap();
		param.put("userId", userId);
		param.put("roleId", roleId);
		UserRole userRole = (UserRole) getUnique("getUserRoleModel", param);
		return userRole;
	}

	public int delUserRoleByIds(Long userId, Long roleId) {
		Map params = new HashMap();
		params.put("userId", userId);
		params.put("roleId", roleId);

		int affectCount = delBySqlKey("delUserRoleByIds", params);
		return affectCount;
	}

	public List<Long> getUserIdsByRoleId(Long roleId) {
		List list = getBySqlKey("getUserIdsByRoleId", roleId);
		return list;
	}

	public List<UserRole> getUserRoleByRoleId(Long roleId) {
		return getBySqlKey("getUserRoleByRoleId", roleId);
	}

	public void delRoleId(Long roleId) {
		delBySqlKey("delByRoleId", roleId);
	}

	public void delByUserId(Long userId) {
		delBySqlKey("delByUserId", userId);
	}

	public List<UserRole> getByUserId(Long userId) {
		return getBySqlKey("getByUserId", userId);
	}
}
