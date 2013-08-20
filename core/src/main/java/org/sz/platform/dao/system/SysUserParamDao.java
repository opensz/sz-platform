package org.sz.platform.dao.system;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.SysUserParam;

public interface SysUserParamDao extends BaseDao<SysUserParam> {

	int delByUserId(long userId);

}