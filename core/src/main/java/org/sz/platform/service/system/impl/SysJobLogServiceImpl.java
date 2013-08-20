package org.sz.platform.service.system.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.dao.system.SysJobLogDao;
import org.sz.platform.model.system.SysJobLog;
import org.sz.platform.service.system.SysJobLogService;

@Service("sysJobLogService")
public class SysJobLogServiceImpl extends BaseServiceImpl<SysJobLog> implements
		SysJobLogService {

	@Resource
	private SysJobLogDao dao;

	protected IEntityDao<SysJobLog, Long> getEntityDao() {
		return this.dao;
	}

	// public void add(ProcessCmd cmd)
	// {
	// }
}
