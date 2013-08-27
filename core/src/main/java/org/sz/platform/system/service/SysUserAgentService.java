package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.SysUserAgent;

public interface SysUserAgentService extends BaseService<SysUserAgent> {

	void add(SysUserAgent sysUserAgent, List upList);

	void update(SysUserAgent sysUserAgent, List upList);

	List<SysUserAgent> getByTouserId(Long userId);

}