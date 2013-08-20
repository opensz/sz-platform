package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.SysOrgParam;

public interface SysOrgParamDao extends BaseDao<SysOrgParam> {

	int delByOrgId(long orgId);

	List<SysOrgParam> getByOrgId(Long orgId);

}