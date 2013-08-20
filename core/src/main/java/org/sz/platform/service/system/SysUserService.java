package org.sz.platform.service.system;

import java.util.List;
import java.util.Set;

import org.sz.core.model.LabelValue;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.BaseService;
import org.sz.platform.model.system.SysUser;

public interface SysUserService extends BaseService<SysUser>{

	SysUser getByAccount(String account,Long orgId);
	
	SysUser getByAccount(String account);

	List<SysUser> getUserByOrgId(QueryFilter queryFilter);

	List<SysUser> getUserByQuery(QueryFilter queryFilter);

	List<Long> getUserIdsByRoleId(Long roleId);

	List<SysUser> getUserByRoleId(QueryFilter queryFilter);

	List<SysUser> getUserNoOrg(QueryFilter queryFilter);

	List<SysUser> getDistinctUserByPosPath(QueryFilter queryFilter);

	List<SysUser> getDistinctUserByOrgPath(QueryFilter queryFilter);

	boolean isAccountExist(String account);

	boolean isAccountExistForUpd(Long userId, String account);

	List<SysUser> getByUserParam(String userParam) throws Exception;

	List<SysUser> getByOrgParam(String userParam) throws Exception;

	List<SysUser> getByParam(long nodeUserId) throws Exception;

	List<SysUser> getByUserIdAndUplow(long userId, long nodeUserId);

	List<SysUser> getByUserIdAndUplow(long userId, List uplowList);

	List<SysUser> getOnlineUser(List<SysUser> list);

	List<SysUser> getByIdSet(Set uIds);

	SysUser getByMail(String address);
	
	  /**
	   * 根据角色ID返回要发送人员的 account,用”,“分隔
	   * @param roleId
	   * @return
	   */
	String getReceiversByRoleId(Long roleId);
	
	String getReceiversByMyLeader(Long userId);

	void updPwd(Long userId, String pwd);

	void updStatus(Long userId, Short status, Short isLock);
	
	void updStatus(Long[] userIds, Long subSystemId);

	Long saveUser(Integer bySelf, SysUser sysUser, Long[] aryOrgIds,
			Long[] orgIdCharge, Long[] arrIsDept, Long orgIdPrimary, Long deptIdPrimary, Long[] posIds,
			Long posIdPrimary, Long[] roleIds) throws Exception;
	
	Long saveUser(String fullName,String userName,String password,Long orgId) throws Exception;
	
}