package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.DesktopLayoutcol;

public interface DesktopLayoutcolDao  extends BaseDao<DesktopLayoutcol>{

	void delByLayoutId(Long layoutId);

	void delByNoLayoutId(Long layoutId);

	List<DesktopLayoutcol> getByLayoutId(Long layoutId);

	void delByLinkLayout(Long COLUMNID);

}