package org.sz.platform.service.system.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.dao.system.ResourcesDao;
import org.sz.platform.dao.system.ResourcesUrlDao;
import org.sz.platform.dao.system.RoleResourcesDao;
import org.sz.platform.dao.system.RoleSysDao;
import org.sz.platform.dao.system.SysRoleDao;
import org.sz.platform.dao.system.UserRoleDao;
import org.sz.platform.model.system.ResourcesUrlExt;
import org.sz.platform.model.system.RoleResources;
import org.sz.platform.model.system.RoleSys;
import org.sz.platform.model.system.SysRole;
import org.sz.platform.model.system.UserRole;
import org.sz.platform.service.system.SysRoleService;

@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements
		SysRoleService {

	@Resource
	private SysRoleDao sysRoleDao;

	@Resource
	private ResourcesDao resourcesDao;

	@Resource
	private ResourcesUrlDao resourcesUrlDao;

	@Resource
	private RoleSysDao roleSysDao;

	@Resource
	UserRoleDao userRoleDao;

	@Resource
	private RoleResourcesDao roleResourcesDao;

	protected IEntityDao<SysRole, Long> getEntityDao() {
		return this.sysRoleDao;
	}

	public boolean isExistRoleAlias(String alias) {
		return this.sysRoleDao.isExistRoleAlias(alias);
	}

	public boolean isExistRoleAliasForUpd(String alias, Long roleId) {
		return this.sysRoleDao.isExistRoleAliasForUpd(alias, roleId);
	}

	public List<SysRole> getRoleList(QueryFilter queryFilter) {
		return this.sysRoleDao.getRole(queryFilter);
	}

	public List<SysRole> getByUserId(Long userId) {
		return this.sysRoleDao.getByUserId(userId);
	}

	public List<SysRole> getBySystemId(Long systemId) {
		return this.sysRoleDao.getBySystemId(systemId);
	}

	public String getRoleIdsByUserId(Long userId) {
		StringBuffer sb = new StringBuffer("");
		List<SysRole> sysRoleList = getByUserId(userId);
		for (SysRole sysRole : sysRoleList) {
			sb.append(sysRole.getRoleId()).append(",");
		}
		if (sysRoleList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public List<SysRole> getRoleTree(QueryFilter queryFilter) {
		return this.sysRoleDao.getRoleTree(queryFilter);
	}

	public List<ResourcesUrlExt> getUrlRightMap(Long systemId) {
		List urlList = this.resourcesUrlDao.getUrlAndRoleBySystemId(systemId
				.longValue());

		List defaultUrlList = this.resourcesDao
				.getDefaultUrlAndRoleBySystemId(systemId.longValue());

		List returnList = new ArrayList();
		if ((urlList != null) && (urlList.size() > 0))
			returnList.addAll(urlList);
		if ((defaultUrlList != null) && (defaultUrlList.size() > 0))
			returnList.addAll(defaultUrlList);
		return returnList;
	}

	public List<ResourcesUrlExt> getFunctionRoleList(Long systemId) {
		List defaultUrlList = this.resourcesDao
				.getFunctionAndRoleBySystemId(systemId.longValue());
		return defaultUrlList;
	}

	public List<ResourcesUrlExt> getSubSystemFunction(String alias) {
		List defaultUrlList = this.resourcesDao.getSubSystemsFuncByAlias(alias);
		return defaultUrlList;
	}

	public List<ResourcesUrlExt> getSubSystemResources(String alias) {
		List urlList = this.resourcesUrlDao.getSubSystemResByAlias(alias);

		List defaultUrlList = this.resourcesDao.getSubSystemResByAlias(alias);

		List returnList = new ArrayList();
		if ((urlList != null) && (urlList.size() > 0))
			returnList.addAll(urlList);
		if ((defaultUrlList != null) && (defaultUrlList.size() > 0))
			returnList.addAll(defaultUrlList);
		return returnList;
	}

	public void copyRole(SysRole sysRole, long oldRoleId) throws Exception {
		List<RoleResources> roleResourcesList = this.roleResourcesDao
				.getRoleRes(Long.valueOf(oldRoleId));

		List<UserRole> userRoleList = this.userRoleDao.getUserRoleByRoleId(Long
				.valueOf(oldRoleId));

		List<RoleSys> roleSysList = this.roleSysDao.getByRole(Long
				.valueOf(oldRoleId));

		Long newRoleId = sysRole.getRoleId();

		this.sysRoleDao.add(sysRole);

		for (UserRole userRole : userRoleList) {
			UserRole ur = (UserRole) userRole.clone();
			ur.setUserRoleId(Long.valueOf(UniqueIdUtil.genId()));
			ur.setRoleId(newRoleId);
			this.userRoleDao.add(ur);
		}

		for (RoleResources rores : roleResourcesList) {
			RoleResources roleres = (RoleResources) rores.clone();
			roleres.setRoleResId(Long.valueOf(UniqueIdUtil.genId()));
			roleres.setRoleId(newRoleId);
			this.roleResourcesDao.add(roleres);
		}

		for (RoleSys roleSys : roleSysList) {
			RoleSys newRoleSys = (RoleSys) roleSys.clone();
			newRoleSys.setId(Long.valueOf(UniqueIdUtil.genId()));
			newRoleSys.setRoleid(newRoleId);
			this.roleSysDao.add(newRoleSys);
		}
	}

	public List<SysRole> loadSecurityRole(String alias, String roleName) {
		return this.sysRoleDao.loadSecurityRoleByAlias(alias, roleName);
	}
}
