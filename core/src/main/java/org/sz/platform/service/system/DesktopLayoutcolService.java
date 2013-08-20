package org.sz.platform.service.system;

import java.util.List;
import java.util.Map;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.DesktopLayoutcol;

public interface DesktopLayoutcolService extends BaseService<DesktopLayoutcol>  {

	void delByLayoutId(Long layoutId);

	void delByNoLayoutId(Long layoutId);

	List<DesktopLayoutcol> getByLayoutId(Long layoutId);

	void setListData(List<DesktopLayoutcol> list);

	DesktopLayoutcol editData(Long id, Map<String, String> desktopColumnmap,
			Map<String, String> desktopLayoutmap);

	void saveCol(List<DesktopLayoutcol> list, Long layoutId);

	DesktopLayoutcol showData(Long id, Map<String, String> desktopColumnmap,
			Map<String, String> desktopLayoutmap);

	List<DesktopLayoutcol> layoutcolData(Long layoutId);

}