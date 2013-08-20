package org.sz.platform.service.system;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.SubSystem;
import org.sz.platform.model.system.SysUser;

public interface SubSystemService extends BaseService<SubSystem>{

	//void setCurrentSystem(Long systemId, HttpServletRequest request, HttpServletResponse response);

	//void writeCurrentSystemCookie(String systemId, HttpServletRequest request, HttpServletResponse response);

	List<SubSystem> getByUser(SysUser user);

	List<SubSystem> getLocalSystem();

	Integer isAliasExist(String alias);

	Integer isAliasExistForUpd(String alias, Long systemId);

	List<SubSystem> getActiveSystem();

}