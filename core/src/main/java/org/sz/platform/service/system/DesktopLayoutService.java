package org.sz.platform.service.system;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.DesktopLayout;

public interface DesktopLayoutService extends BaseService<DesktopLayout>{

	DesktopLayout getDefaultLayout();

	void setDefault(Long layoutId) throws Exception;

}