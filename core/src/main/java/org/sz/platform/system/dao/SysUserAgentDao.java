package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.SysUserAgent;

public interface SysUserAgentDao extends BaseDao<SysUserAgent> {

	List<SysUserAgent> getByTouserId(Long userId);

}