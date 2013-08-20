package org.sz.platform.service.system;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.SysUserParam;

public interface SysUserParamService extends BaseService<SysUserParam> {

	void add(long userId, List<SysUserParam> valueList);

	List<SysUserParam> getByUserId(long userId);

}