package org.sz.platform.dao.system;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.sz.core.dao.BaseDao;
import org.sz.core.query.QueryFilter;
import org.sz.platform.model.system.SysUser;




public interface SysUserDao  extends BaseDao<SysUser> {

	UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException;

	SysUser getByAccount(String account,Long orgId);
	
	SysUser getByAccount(String account);

	List<SysUser> getUserNoOrg(QueryFilter queryFilter);

	List<SysUser> getUserByOrgId(QueryFilter queryFilter);

	List<SysUser> getByOrgId(Long orgId);

	List<SysUser> getByPosId(Long posId);

	List<SysUser> getByRoleId(Long roleId);

	List<SysUser> getUserByPath(String path);

	List<SysUser> getUserByQuery(QueryFilter queryFilter);

	List<Long> getUserIdsByRoleId(Long roleId);

	List<SysUser> getUserByRoleId(QueryFilter queryFilter);

	List<SysUser> getDistinctUserByPosPath(QueryFilter queryFilter);

	List<SysUser> getDistinctUserByOrgPath(QueryFilter queryFilter);

	boolean isAccountExist(String account);

	boolean isAccountExistForUpd(Long userId, String account);

	List<SysUser> getByUserOrParam(Map<String, String> property);

	List<SysUser> getByOrgOrParam(Map<String, String> property);

	List<SysUser> getUpLowPost(Map<String, Object> p);

	List<SysUser> getUpLowOrg(Map<String, Object> p);

	List<SysUser> getByIdSet(Set idSet);

	SysUser getByMail(String address);

	void updPwd(Long userId, String pwd);

	void updStatus(Long userId, Short status, Short isLock);
	
	int updStatus(Long[] userIds, Short status);

	List<SysUser> getDirectLeaderByOrgId(Long orgId);
	
	boolean checkUser(String account,Long orgId);
	
	
	

	/**
	 * 通过username 和orgId 两个字段判断用户是否重复
	 * 
	 * @param username
	 * @param orgId
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Transactional
	UserDetails loadUserByUsername(String username, Long orgId)
			throws UsernameNotFoundException;
	
	List<SysUser> getUsers(Map<String, Object> params);
	
	

}