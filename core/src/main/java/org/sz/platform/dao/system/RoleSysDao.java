package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.RoleSys;

public interface RoleSysDao extends BaseDao<RoleSys> {

	void delBySysAndRole(Long systemId, Long roleId);

	List<RoleSys> getByRole(Long roleId);

}