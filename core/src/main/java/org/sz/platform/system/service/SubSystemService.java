package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.SubSystem;
import org.sz.platform.system.model.SysUser;

public interface SubSystemService extends BaseService<SubSystem>{

	SubSystem getById(Long systemId);
	
	List<SubSystem> getByUser(SysUser user);

	List<SubSystem> getLocalSystem();

	Integer isAliasExist(String alias);

	Integer isAliasExistForUpd(String alias, Long systemId);

	List<SubSystem> getActiveSystem();

}