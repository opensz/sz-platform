package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.UserRole;

public interface UserRoleService extends BaseService<UserRole> {

	void add(Long roleId, Long[] userIds) throws Exception;

	UserRole getUserRoleModel(Long userId, Long roleId);

	void delUserRoleByIds(String[] lAryId, Long userId);

	void saveUserRole(Long userId, Long[] roleIds) throws Exception;

	List<Long> getUserIdsByRoleId(Long roleId);

	List<UserRole> getUserRoleByRoleId(Long roleId);

	void delRoleId(Long roleId);

	void delByUserRoleId(Long[] aryUserRoleId);

	List<UserRole> getByUserId(Long userId);

}