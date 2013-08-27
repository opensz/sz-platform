package org.sz.platform.system.dao;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.SysUserParam;

public interface SysUserParamDao extends BaseDao<SysUserParam> {

	int delByUserId(long userId);

}