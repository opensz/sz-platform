package org.sz.platform.system.dao;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.system.model.SysAcceptIp;

public interface SysAcceptIpDao extends BaseDao<SysAcceptIp> {

	List<SysAcceptIp> getWhiteList();

	List<SysAcceptIp> getBlackList();

}