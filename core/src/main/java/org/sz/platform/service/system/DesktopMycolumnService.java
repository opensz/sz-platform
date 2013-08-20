package org.sz.platform.service.system;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.DesktopLayout;
import org.sz.platform.model.system.DesktopLayoutcol;
import org.sz.platform.model.system.DesktopMycolumn;

public interface DesktopMycolumnService extends BaseService<DesktopMycolumn>{

	List<DesktopMycolumn> getByUserId(Long userId);

	void delByLayoutId(Long layoutId);

	void delByUserId(Long userId);

	Map getMyDeskData(Long userId, String ctxPath) throws Exception;

	Map getDefaultDeskDataById(Long layoutId, String ctxPath) throws Exception;

	List<DesktopMycolumn> getMyListData(long userId) throws Exception;

	void saveMycolumn(List<DesktopMycolumn> list, Long layoutId, Long userId)
			throws Exception;

	DesktopLayoutcol newsData(Long id, Map<String, String> desktopLayoutAllmap,
			Map<String, String> desktopColumnmap,
			Map<String, String> desktopLayoutmap);

	DesktopLayout getShowData(long userId);

	Map<String, String> desktopSync(long userId) throws Exception;

}