package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.DesktopColumn;

public interface DesktopColumnDao  extends BaseDao<DesktopColumn>{

	void delAll();

	void delBySysColumn();

	List<DesktopColumn> getSysColumn();

	DesktopColumn getByDeskName(String name);

	String getNameById(long columnId);

	String getColumnUrlById(long columnId);

}