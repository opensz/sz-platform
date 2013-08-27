package org.sz.platform.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.RoleSysDao;
import org.sz.platform.system.model.RoleSys;

@Repository("roleSysDao")
public class RoleSysDaoImpl extends BaseDaoImpl<RoleSys> implements RoleSysDao {
	public Class getEntityClass() {
		return RoleSys.class;
	}

	public void delBySysAndRole(Long systemId, Long roleId) {
		Map params = new HashMap();
		params.put("systemid", systemId);
		params.put("roleid", roleId);
		delBySqlKey("delBySysAndRole", params);
	}

	public List<RoleSys> getByRole(Long roleId) {
		List list = getBySqlKey("getByRole", roleId);
		return list;
	}
}
