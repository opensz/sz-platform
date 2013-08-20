package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.SubSystem;

public interface SubSystemDao extends BaseDao<SubSystem> {

	List<SubSystem> getByUserId(Long userId);

	List<SubSystem> getLocalSystem();

	List<SubSystem> getActiveSystem();

	Integer isAliasExist(String alias);

	Integer isAliasExistForUpd(String alias, Long systemId);

}