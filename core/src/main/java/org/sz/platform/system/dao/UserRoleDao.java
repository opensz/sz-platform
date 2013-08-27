package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.UserRole;

public interface UserRoleDao extends BaseDao<UserRole> {

	UserRole getUserRoleModel(Long userId, Long roleId);

	int delUserRoleByIds(Long userId, Long roleId);

	List<Long> getUserIdsByRoleId(Long roleId);

	List<UserRole> getUserRoleByRoleId(Long roleId);

	void delRoleId(Long roleId);

	void delByUserId(Long userId);

	List<UserRole> getByUserId(Long userId);

}