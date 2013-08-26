package org.sz.platform.oa.dao.desk.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.oa.dao.desk.DesktopMycolumnDao;
import org.sz.platform.oa.model.desk.DesktopMycolumn;

@Repository("desktopMycolumnDao")
public class DesktopMycolumnDaoImpl extends BaseDaoImpl<DesktopMycolumn>
		implements DesktopMycolumnDao {
	public Class getEntityClass() {
		return DesktopMycolumn.class;
	}

	public List<DesktopMycolumn> getByUserId(Long userId) {
		return getBySqlKey("getByUserId", userId);
	}

	public List<DesktopMycolumn> getMyDeskData(Long userId) {
		return getBySqlKey("getMyDeskData", userId);
	}

	public List<DesktopMycolumn> getDefaultDeskDataById(Long layoutId) {
		return getBySqlKey("getDefaultDeskDataById", layoutId);
	}

	public List<DesktopMycolumn> getDefaultDeskData() {
		return getBySqlKey("getDefaultDeskData");
	}

	public void delByUserId(Long userId) {
		Map params = new HashMap();
		params.put("userId", userId);
		getBySqlKey("delByUserId", params);
	}

	public void delByLayoutId(Long layoutId) {
		Map params = new HashMap();
		params.put("layoutId", layoutId);
		getBySqlKey("delByLayoutId", params);
	}

	public void delByLinkMycolumn(Long COLUMNID) {
		getBySqlKey("delByLinkMycolumn", COLUMNID);
	}
}
