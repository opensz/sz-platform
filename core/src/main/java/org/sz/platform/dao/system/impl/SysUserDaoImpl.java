package org.sz.platform.dao.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.platform.dao.system.SysRoleDao;
import org.sz.platform.dao.system.SysUserDao;
import org.sz.platform.model.system.SysUser;

@Repository("sysUserDao")
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements
		UserDetailsService, SysUserDao {
	private static ThreadLocal<SysUser> curUser = new ThreadLocal();
	@Resource
	SysRoleDao sysRoleDao;

	public Class getEntityClass() {
		return SysUser.class;
	}

	public UserDetails loadUserByUsername(String username, Long orgId)
			throws UsernameNotFoundException, DataAccessException {
		SysUser sysUser = getByAccount(username, orgId);
		if (sysUser == null)
			throw new UsernameNotFoundException("用户不存在");

		return sysUser;
	}

	public SysUser getByAccount(String account, Long orgId) {
		Map param = new HashMap();
		param.put("account", account);
		param.put("orgId", orgId);

		SysUser sysUser = (SysUser) getUnique("getByAccount", param);

		return sysUser;
	}

	public SysUser getByAccount(String account) {
		return this.getByAccount(account, null);
	}

	public List<SysUser> getUserNoOrg(QueryFilter queryFilter) {
		return getBySqlKey("getUserNoOrg", queryFilter);
	}

	public List<SysUser> getUserByOrgId(QueryFilter queryFilter) {
		return getBySqlKey("getUserByOrgId", queryFilter);
	}

	public List<SysUser> getByOrgId(Long orgId) {
		return getBySqlKey("getByOrgId", orgId);
	}

	public List<SysUser> getByPosId(Long posId) {
		return getBySqlKey("getByPosId", posId);
	}

	public List<SysUser> getByRoleId(Long roleId) {
		return getBySqlKey("getByRoleId", roleId);
	}

	public List<SysUser> getUserByPath(String path) {
		Map param = new HashMap();
		param.put("path", path);
		return getBySqlKey("getUserByOrgId", param);
	}

	public List<SysUser> getUserByQuery(QueryFilter queryFilter) {
		return getBySqlKey("getUserByQuery", queryFilter);
	}

	public List<Long> getUserIdsByRoleId(Long roleId) {
		String statement = getIbatisMapperNamespace() + ".getUserIdsByRoleId";

		List list = getSqlSessionTemplate().selectList(statement, roleId);

		return list;
	}

	public List<SysUser> getUserByRoleId(QueryFilter queryFilter) {
		return getBySqlKey("getUserByRoleId", queryFilter);
	}

	public List<SysUser> getDistinctUserByPosPath(QueryFilter queryFilter) {
		return getBySqlKey("getDistinctUserByPosPath", queryFilter);
	}

	public List<SysUser> getDistinctUserByOrgPath(QueryFilter queryFilter) {
		return getBySqlKey("getDistinctUserByOrgPath", queryFilter);
	}

	public boolean isAccountExist(String account) {
		Integer rtn = (Integer) getOne("isAccountExist", account);
		return rtn.intValue() > 0;
	}

	public boolean isAccountExistForUpd(Long userId, String account) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("account", account);
		Integer rtn = (Integer) getOne("isAccountExistForUpd", map);
		return rtn.intValue() > 0;
	}

	public List<SysUser> getByUserOrParam(Map<String, String> property) {
		List list = getBySqlKey("getByUserOrParam_" + getDbType(), property);
		return list;
	}

	public List<SysUser> getByOrgOrParam(Map<String, String> property) {
		return getBySqlKey("getByOrgOrParam_" + getDbType(), property);
	}

	public List<SysUser> getUpLowPost(Map<String, Object> p) {
		return getBySqlKey("getUpLowPost", p);
	}

	public List<SysUser> getUpLowOrg(Map<String, Object> p) {
		return getBySqlKey("getUpLowOrg", p);
	}

	public List<SysUser> getByIdSet(Set idSet) {
		Map params = new HashMap();
		if ((idSet == null) || (idSet.size() == 0))
			params.put("ids", Integer.valueOf(-1));
		else {
			params.put("ids", StringUtil.getSetAsString(idSet));
		}
		return getBySqlKey("getByIdSet", params);
	}

	public SysUser getByMail(String address) {
		return (SysUser) getUnique("getByMail", address);
	}

	public void updPwd(Long userId, String pwd) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("password", pwd);
		update("updPwd", map);
	}

	public void updStatus(Long userId, Short status, Short isLock) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("status", status);
		map.put("isLock", isLock);
		update("updStatus", map);
	}

	@Override
	public int updStatus(Long[] userIds, Short status) {
		Map map = new HashMap();
		map.put("userIds", userIds);
		map.put("status", status);
		try {
			update("updStatusByIds", map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}

	public List<SysUser> getDirectLeaderByOrgId(Long orgId) {
		List users = getBySqlKey("getDirectLeaderByOrgId", orgId);
		return users;
	}

	@Override
	public boolean checkUser(String account, Long orgId) {
		Map map = new HashMap();
		map.put("account", account);
		map.put("orgId", orgId);
		Integer rtn = (Integer) getOne("checkUser", map);
		if (rtn > 0) {// 存在此用户
			return true;
		}
		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		SysUser sysUser = getByAccount(username);

		if (sysUser == null)
			throw new UsernameNotFoundException("用户不存在");

		return sysUser;

	}

	@Override
	public List<SysUser> getUsers(Map<String, Object> params) {

		// String qlString =
		// "select distinct t from User t left join fetch t.staff inner join t.roles r";

		// qlString += SqlBuilderUtils.mapToSql("t", params);
		// return this.entityManager.createQuery(qlString).getResultList();
		String qlString = "select distinct a.* from sys_user a inner join sys_user_role b on a.USERID=b.USERID";
		return this.getBySqlKey("getUsers", params);

	}

	

	private JdbcTemplate getJdbcTemplate() throws Exception {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ContextUtil.getBean("jdbcTemplate");
		return jdbcTemplate;
	}

}
