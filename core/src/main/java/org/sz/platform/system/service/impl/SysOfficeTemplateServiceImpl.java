package org.sz.platform.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.system.dao.SysOfficeTemplateDao;
import org.sz.platform.system.model.SysOfficeTemplate;
import org.sz.platform.system.service.SysOfficeTemplateService;

@Service("sysOfficeTemplateService")
public class SysOfficeTemplateServiceImpl extends
		BaseServiceImpl<SysOfficeTemplate> implements SysOfficeTemplateService {

	@Resource
	private SysOfficeTemplateDao dao;

	protected IEntityDao<SysOfficeTemplate, Long> getEntityDao() {
		return this.dao;
	}
}
