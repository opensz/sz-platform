package org.sz.platform.oa.dao.desk;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.oa.model.desk.DesktopMycolumn;

public interface DesktopMycolumnDao extends BaseDao<DesktopMycolumn> {

	List<DesktopMycolumn> getByUserId(Long userId);

	List<DesktopMycolumn> getMyDeskData(Long userId);

	List<DesktopMycolumn> getDefaultDeskDataById(Long layoutId);

	List<DesktopMycolumn> getDefaultDeskData();

	void delByUserId(Long userId);

	void delByLayoutId(Long layoutId);

	void delByLinkMycolumn(Long COLUMNID);

}