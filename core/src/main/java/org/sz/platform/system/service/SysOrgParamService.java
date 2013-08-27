package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.SysOrgParam;

public interface SysOrgParamService extends BaseService<SysOrgParam> {

	void add(long orgId, List<SysOrgParam> valueList);

	List<SysOrgParam> getListByOrgId(Long orgId);

}