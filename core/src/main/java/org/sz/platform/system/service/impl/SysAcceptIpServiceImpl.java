package org.sz.platform.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.system.dao.SysAcceptIpDao;
import org.sz.platform.system.model.SysAcceptIp;
import org.sz.platform.system.service.SysAcceptIpService;

@Service("sysAcceptIpService")
public class SysAcceptIpServiceImpl extends BaseServiceImpl<SysAcceptIp>
		implements SysAcceptIpService {

	@Resource
	private SysAcceptIpDao sysAcceptIpDao;

	protected IEntityDao<SysAcceptIp, Long> getEntityDao() {
		return this.sysAcceptIpDao;
	}

	public List<SysAcceptIp> getWhiteList() {
		return this.sysAcceptIpDao.getWhiteList();
	}

	public List<SysAcceptIp> getBlackList() {
		return this.sysAcceptIpDao.getBlackList();
	}
}
