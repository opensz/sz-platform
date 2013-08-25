package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.SysUserParam;

public interface SysUserParamService extends BaseService<SysUserParam> {

	void add(long userId, List<SysUserParam> valueList);

	List<SysUserParam> getByUserId(long userId);

}