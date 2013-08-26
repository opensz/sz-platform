package org.sz.platform.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.system.dao.SysTemplateDao;
import org.sz.platform.system.model.SysTemplate;
import org.sz.platform.system.service.SysTemplateService;

@Service("sysTemplateService")
public class SysTemplateServiceImpl extends BaseServiceImpl<SysTemplate>
		implements SysTemplateService {

	@Resource
	private SysTemplateDao dao;

	protected IEntityDao<SysTemplate, Long> getEntityDao() {
		return this.dao;
	}
}
