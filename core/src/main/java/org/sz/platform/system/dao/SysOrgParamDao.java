package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.SysOrgParam;

public interface SysOrgParamDao extends BaseDao<SysOrgParam> {

	int delByOrgId(long orgId);

	List<SysOrgParam> getByOrgId(Long orgId);

}