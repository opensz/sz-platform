package org.sz.platform.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.system.dao.RoleSysDao;
import org.sz.platform.system.model.RoleSys;
import org.sz.platform.system.service.RoleSysService;

@Service("roleSysService")
public class RoleSysServiceImpl extends BaseServiceImpl<RoleSys> implements
		RoleSysService {

	@Resource
	private RoleSysDao dao;

	protected IEntityDao<RoleSys, Long> getEntityDao() {
		return this.dao;
	}
}
