package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.DesktopMycolumn;

public interface DesktopMycolumnDao extends BaseDao<DesktopMycolumn> {

	List<DesktopMycolumn> getByUserId(Long userId);

	List<DesktopMycolumn> getMyDeskData(Long userId);

	List<DesktopMycolumn> getDefaultDeskDataById(Long layoutId);

	List<DesktopMycolumn> getDefaultDeskData();

	void delByUserId(Long userId);

	void delByLayoutId(Long layoutId);

	void delByLinkMycolumn(Long COLUMNID);

}