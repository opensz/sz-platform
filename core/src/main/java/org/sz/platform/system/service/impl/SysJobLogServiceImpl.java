package org.sz.platform.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.system.dao.SysJobLogDao;
import org.sz.platform.system.model.SysJobLog;
import org.sz.platform.system.service.SysJobLogService;

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
