package org.sz.platform.service.system;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.SysOrgParam;

public interface SysOrgParamService extends BaseService<SysOrgParam>{

	void add(long orgId, List<SysOrgParam> valueList);

	List<SysOrgParam> getListByOrgId(Long orgId);

}