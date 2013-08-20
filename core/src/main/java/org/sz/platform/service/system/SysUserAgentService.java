package org.sz.platform.service.system;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.SysUserAgent;

public interface SysUserAgentService extends BaseService<SysUserAgent> {

	void add(SysUserAgent sysUserAgent, List upList);

	void update(SysUserAgent sysUserAgent, List upList);

	List<SysUserAgent> getByTouserId(Long userId);

}