package org.sz.platform.service.system;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.SysAcceptIp;

public interface SysAcceptIpService extends BaseService<SysAcceptIp>{

	List<SysAcceptIp> getWhiteList();

	List<SysAcceptIp> getBlackList();

}