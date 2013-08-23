package org.sz.platform.webservice.impl;

import java.util.Collection;
import javax.annotation.Resource;
import javax.jws.WebService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.sz.platform.dao.system.SysUserDao;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.webservice.api.UserDetailsService;

@WebService
public class UserDetailsServiceImpl implements UserDetailsService {

	@Resource
	SysUserDao sysUserDao;

	public SysUser loadUserByUsername(String account)
			throws UsernameNotFoundException, DataAccessException {
		UserDetails user = this.sysUserDao.loadUserByUsername(account);
		if ((user instanceof SysUser)) {
			SysUser sysUser = (SysUser) user;
			return sysUser;
		}
		return null;
	}

	public Collection<String> loadRoleByUsername(String userName) {
		SysUser user = this.sysUserDao.getByAccount(userName);
		if (user != null) {
			return user.getRoles();
		}
		return null;
	}
}
