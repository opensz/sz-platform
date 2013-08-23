package org.sz.platform.service.system.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.dao.system.SubSystemDao;
import org.sz.platform.model.system.SubSystem;
import org.sz.platform.model.system.SysRole;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.service.system.SubSystemService;


@Service("subSystemService")
public class SubSystemServiceImpl extends BaseServiceImpl<SubSystem> implements SubSystemService {

	@Resource
	private SubSystemDao subSystemDao;

	protected IEntityDao<SubSystem, Long> getEntityDao() {
		return this.subSystemDao;
	}

	public SubSystem getById(Long systemId){
		return (SubSystem) this.subSystemDao.getById(systemId);
	}
	public List<SubSystem> getByUser(SysUser user) {
		if (user.getAuthorities().contains(SysRole.ROLE_GRANT_SUPER)) {
			return getAll();
		}
		return this.subSystemDao.getByUserId(user.getUserId());
	}

	public List<SubSystem> getLocalSystem() {
		return this.subSystemDao.getLocalSystem();
	}

	public Integer isAliasExist(String alias) {
		return this.subSystemDao.isAliasExist(alias);
	}

	public Integer isAliasExistForUpd(String alias, Long systemId) {
		return this.subSystemDao.isAliasExistForUpd(alias, systemId);
	}

	public List<SubSystem> getActiveSystem() {
		return this.subSystemDao.getActiveSystem();
	}
}
