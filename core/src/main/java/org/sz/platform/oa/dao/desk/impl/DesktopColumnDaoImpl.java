package org.sz.platform.oa.dao.desk.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.oa.dao.desk.DesktopColumnDao;
import org.sz.platform.oa.model.desk.DesktopColumn;

@Repository("desktopColumnDao")
public class DesktopColumnDaoImpl extends BaseDaoImpl<DesktopColumn> implements
		DesktopColumnDao {
	public Class getEntityClass() {
		return DesktopColumn.class;
	}

	public void delAll() {
		delBySqlKey("delAll", null);
	}

	public void delBySysColumn() {
		delBySqlKey("delBySysColumn", null);
	}

	public List<DesktopColumn> getSysColumn() {
		return getBySqlKey("getSysColumn", null);
	}

	public DesktopColumn getByDeskName(String name) {
		return (DesktopColumn) getUnique("getByDeskName", name);
	}

	public String getNameById(long columnId) {
		return (String) getOne("getNameById", Long.valueOf(columnId));
	}

	public String getColumnUrlById(long columnId) {
		return (String) getOne("getColumnUrlById", Long.valueOf(columnId));
	}
}
