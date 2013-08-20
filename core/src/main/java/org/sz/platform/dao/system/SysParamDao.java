package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.SysParam;

public interface SysParamDao extends BaseDao<SysParam> {

	List<SysParam> getUserParam();

	List<SysParam> getOrgParam();

}