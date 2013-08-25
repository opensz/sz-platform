package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.SysParam;

public interface SysParamDao extends BaseDao<SysParam> {

	List<SysParam> getUserParam();

	List<SysParam> getOrgParam();

}