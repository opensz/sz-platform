package org.sz.platform.oa.dao.desk;

import org.sz.core.dao.BaseDao;
import org.sz.platform.oa.model.desk.DesktopLayout;

public interface DesktopLayoutDao extends BaseDao<DesktopLayout> {

	DesktopLayout getDefaultLayout();

	int updateDefault(long id);

	int updNotDefault();

	long getDefaultId();

	String getNameById(long layoutId);

	DesktopLayout getLayoutByUserId(Long userId);

}