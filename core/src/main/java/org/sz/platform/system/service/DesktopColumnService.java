package org.sz.platform.system.service;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.DesktopColumn;

public interface DesktopColumnService extends BaseService<DesktopColumn>{

	String getTemplatePath();

	void delDesktopColumn(Long[] ids);

	boolean isExistDesktopColumn(String deskName);

	void initAllTemplate(long userId) throws Exception;

	void addDesktopColumn(long userId) throws Exception;

	String getNameById(long columnId);

	String getColumnUrlById(long columnId);

	DesktopColumn getByDeskName(String name);
}