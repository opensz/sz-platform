package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.SysAcceptIp;

public interface SysAcceptIpService extends BaseService<SysAcceptIp> {

	List<SysAcceptIp> getWhiteList();

	List<SysAcceptIp> getBlackList();

}