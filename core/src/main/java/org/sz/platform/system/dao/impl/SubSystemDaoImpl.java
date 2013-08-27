package org.sz.platform.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.SubSystemDao;
import org.sz.platform.system.model.SubSystem;

@Repository("subSystemDao")
public class SubSystemDaoImpl extends BaseDaoImpl<SubSystem> implements
		SubSystemDao {
	public Class getEntityClass() {
		return SubSystem.class;
	}

	public List<SubSystem> getByUserId(Long userId) {
		return getBySqlKey("getByUserId", userId);
	}

	public List<SubSystem> getLocalSystem() {
		return getBySqlKey("getLocalSystem", Short.valueOf(SubSystem.isLocal_Y));
	}

	public List<SubSystem> getActiveSystem() {
		return getBySqlKey("getActiveSystem");
	}

	public Integer isAliasExist(String alias) {
		Integer rtn = (Integer) getOne("isAliasExist", alias);
		return rtn;
	}

	public Integer isAliasExistForUpd(String alias, Long systemId) {
		Map map = new HashMap();
		map.put("alias", alias);
		map.put("systemId", systemId);
		Integer rtn = (Integer) getOne("isAliasExistForUpd", map);
		return rtn;
	}
}
