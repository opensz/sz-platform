package org.sz.platform.oa.service.desk.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.oa.dao.desk.DesktopLayoutDao;
import org.sz.platform.oa.model.desk.DesktopLayout;
import org.sz.platform.oa.service.desk.DesktopLayoutService;

@Service("desktopLayoutService")
public class DesktopLayoutServiceImpl extends BaseServiceImpl<DesktopLayout>
		implements DesktopLayoutService {

	@Resource
	private DesktopLayoutDao dao;

	protected IEntityDao<DesktopLayout, Long> getEntityDao() {
		return this.dao;
	}

	public DesktopLayout getDefaultLayout() {
		return this.dao.getDefaultLayout();
	}

	public void setDefault(Long layoutId) throws Exception {
		this.dao.updNotDefault();
		this.dao.updateDefault(layoutId.longValue());
	}
}
