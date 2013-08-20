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

//	public void setCurrentSystem(Long systemId, HttpServletRequest request,
//			HttpServletResponse response) {
//		SubSystem subSystem = (SubSystem) this.subSystemDao.getById(systemId);
//		if (subSystem != null) {
//			writeCurrentSystemCookie(String.valueOf(systemId), request,
//					response);
//			request.getSession().setAttribute(SubSystem.CURRENT_SYSTEM,
//					subSystem);
//		}
//	}

//	public void writeCurrentSystemCookie(String systemId,
//			HttpServletRequest request, HttpServletResponse response) {
//		if (CookieUtil.isExistByName(SubSystem.CURRENT_SYSTEM, request)) {
//			CookieUtil.delCookie(SubSystem.CURRENT_SYSTEM, request, response);
//		}
//		int tokenValiditySeconds = 1209600;
//		CookieUtil.addCookie(SubSystem.CURRENT_SYSTEM, systemId,
//				tokenValiditySeconds, request, response);
//	}

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
