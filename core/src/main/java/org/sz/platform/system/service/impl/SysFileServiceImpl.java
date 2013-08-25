package org.sz.platform.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.system.dao.SysFileDao;
import org.sz.platform.system.model.SysFile;
import org.sz.platform.system.service.SysFileService;

@Service("sysFileService")
public class SysFileServiceImpl extends BaseServiceImpl<SysFile> implements
		SysFileService {

	@Resource
	private SysFileDao dao;

	protected IEntityDao<SysFile, Long> getEntityDao() {
		return this.dao;
	}

	public List<SysFile> getFileAttch(QueryFilter fileter) {
		return this.dao.getFileAttch(fileter);
	}
}
