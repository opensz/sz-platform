package org.sz.platform.system.service;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.DesktopLayout;

public interface DesktopLayoutService extends BaseService<DesktopLayout>{

	DesktopLayout getDefaultLayout();

	void setDefault(Long layoutId) throws Exception;

}