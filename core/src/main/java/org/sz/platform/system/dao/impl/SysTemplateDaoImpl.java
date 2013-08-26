package org.sz.platform.system.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.SysTemplateDao;
import org.sz.platform.system.model.SysTemplate;

@Repository("sysTemplateDao")
public class SysTemplateDaoImpl extends BaseDaoImpl<SysTemplate> implements
		SysTemplateDao {
	public Class getEntityClass() {
		return SysTemplate.class;
	}
}
