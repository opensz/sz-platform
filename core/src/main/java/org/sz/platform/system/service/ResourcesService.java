package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.Resources;
import org.sz.platform.system.model.SubSystem;
import org.sz.platform.system.model.SysUser;

public interface ResourcesService extends BaseService<Resources> {

	void addRes(Resources resources, String[] aryName, String[] aryUrl)
			throws Exception;

	void updRes(Resources resources, String[] aryName, String[] aryUrl)
			throws Exception;

	List<Resources> getChildByParentId(long systemId, long parentId, String ctx);

	List<Resources> getBySystemId(long systemId, String ctx);

	Resources getParentResourcesByParentId(long systemId, long parentId,
			String ctx);

	void delByIds(Long[] ids);

	void updateChildrenNodeDisplay(Resources entity);

	List<Resources> getBySysRolResChecked(Long systemId, Long roleId, String ctx);

	List<Resources> getSysMenu(SubSystem sys, SysUser user, String ctx);

	void update(Resources entity);

	Integer isAliasExists(Resources resources);

	Integer isAliasExistsForUpd(Resources resources);

}