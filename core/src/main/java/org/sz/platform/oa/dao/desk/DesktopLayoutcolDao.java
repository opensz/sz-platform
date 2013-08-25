package org.sz.platform.oa.dao.desk;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.oa.model.desk.DesktopLayoutcol;

public interface DesktopLayoutcolDao  extends BaseDao<DesktopLayoutcol>{

	void delByLayoutId(Long layoutId);

	void delByNoLayoutId(Long layoutId);

	List<DesktopLayoutcol> getByLayoutId(Long layoutId);

	void delByLinkLayout(Long COLUMNID);

}