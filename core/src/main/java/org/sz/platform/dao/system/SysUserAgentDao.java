package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.SysUserAgent;

public interface SysUserAgentDao extends BaseDao<SysUserAgent> {

	List<SysUserAgent> getByTouserId(Long userId);

}