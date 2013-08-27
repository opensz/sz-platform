package org.sz.platform.oa.service.desk;

import org.sz.core.service.BaseService;
import org.sz.platform.oa.model.desk.DesktopLayout;

public interface DesktopLayoutService extends BaseService<DesktopLayout> {

	DesktopLayout getDefaultLayout();

	void setDefault(Long layoutId) throws Exception;

}