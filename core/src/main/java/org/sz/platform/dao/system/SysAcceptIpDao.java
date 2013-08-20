package org.sz.platform.dao.system;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.model.system.SysAcceptIp;

public interface SysAcceptIpDao extends BaseDao<SysAcceptIp> {

	List<SysAcceptIp> getWhiteList();

	List<SysAcceptIp> getBlackList();

}