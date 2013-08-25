package org.sz.platform.system.dao;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.DesktopLayout;

public interface DesktopLayoutDao extends BaseDao<DesktopLayout>{

	DesktopLayout getDefaultLayout();

	int updateDefault(long id);

	int updNotDefault();

	long getDefaultId();

	String getNameById(long layoutId);

	DesktopLayout getLayoutByUserId(Long userId);

}