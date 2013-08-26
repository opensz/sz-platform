package org.sz.platform.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.system.dao.UserRoleDao;
import org.sz.platform.system.model.UserRole;
import org.sz.platform.system.service.SecurityUtil;
import org.sz.platform.system.service.UserRoleService;

@Service("userRoleService")
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements
		UserRoleService {

	@Resource
	private UserRoleDao userRoleDao;

	protected IEntityDao<UserRole, Long> getEntityDao() {
		return this.userRoleDao;
	}

	public void add(Long roleId, Long[] userIds) throws Exception {
		if ((roleId == null) || (roleId.longValue() == 0L) || (userIds == null)
				|| (userIds.length == 0))
			return;
		for (Long userId : userIds) {
			UserRole userRole = this.userRoleDao.getUserRoleModel(userId,
					roleId);
			if (userRole != null)
				continue;
			long userRoleId = UniqueIdUtil.genId();
			UserRole urro = new UserRole();
			urro.setUserRoleId(Long.valueOf(userRoleId));
			urro.setRoleId(roleId);
			urro.setUserId(userId);
			this.userRoleDao.add(urro);

			SecurityUtil.removeUserRoleCache(userId);
		}
	}

	public UserRole getUserRoleModel(Long userId, Long roleId) {
		return this.userRoleDao.getUserRoleModel(userId, roleId);
	}

	public void delUserRoleByIds(String[] lAryId, Long userId) {
		if (BeanUtils.isEmpty(lAryId))
			return;
		for (String roleId : lAryId)
			if (!StringUtil.isEmpty(roleId))
				this.userRoleDao.delUserRoleByIds(userId,
						Long.valueOf(Long.parseLong(roleId)));
	}

	public void saveUserRole(Long userId, Long[] roleIds) throws Exception {
		this.userRoleDao.delByUserId(userId);

		if (BeanUtils.isEmpty(roleIds))
			return;

		for (int i = 0; i < roleIds.length; i++) {
			Long roleId = roleIds[i];
			UserRole userRole = new UserRole();
			userRole.setRoleId(roleId);
			userRole.setUserId(userId);
			userRole.setUserRoleId(Long.valueOf(UniqueIdUtil.genId()));
			this.userRoleDao.add(userRole);
		}
	}

	public List<Long> getUserIdsByRoleId(Long roleId) {
		return this.userRoleDao.getUserIdsByRoleId(roleId);
	}

	public List<UserRole> getUserRoleByRoleId(Long roleId) {
		return this.userRoleDao.getUserRoleByRoleId(roleId);
	}

	public void delRoleId(Long roleId) {
		this.userRoleDao.delRoleId(roleId);
	}

	public void delByUserRoleId(Long[] aryUserRoleId) {
		for (int i = 0; i < aryUserRoleId.length; i++) {
			Long userRoleId = aryUserRoleId[i];
			UserRole userRole = (UserRole) this.userRoleDao.getById(userRoleId);
			this.userRoleDao.delById(userRoleId);

			SecurityUtil.removeUserRoleCache(userRole.getUserId());
		}
	}

	public List<UserRole> getByUserId(Long userId) {
		return this.userRoleDao.getByUserId(userId);
	}
}
