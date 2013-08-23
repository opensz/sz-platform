package org.sz.core.security.web;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.sz.core.web.util.AppUtil;
import org.sz.platform.model.system.SubSystem;
import org.sz.platform.model.system.SysRole;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.service.system.SecurityUtil;

public class SzDecisionManager implements AccessDecisionManager {
	public Logger logger = LoggerFactory.getLogger(SzDecisionManager.class);
	

	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes.contains(SysRole.ROLE_CONFIG_ANONYMOUS)) {
			return;
		}

		if (authentication == null) {
			throw new AccessDeniedException("没有登录系统");
		}

		Object principal = authentication.getPrincipal();
		if (principal == null) {
			throw new AccessDeniedException("登录对象为空");
		}

		if (!(principal instanceof SysUser)) {
			throw new AccessDeniedException("登录对象必须为SysUser");
		}

		SysUser user = (SysUser) principal;

		Collection<GrantedAuthority> roles = user.getAuthorities();

		String mes = "主系统 >> >  >\nURL:" + object + "\n当前用户拥有角色:" + roles
				+ "\n 当前URL被分配给以下角色:" + configAttributes;

		this.logger.debug(mes);

		if (roles.contains(SysRole.ROLE_GRANT_SUPER)) {
			return;
		}

		if (configAttributes.contains(SysRole.ROLE_CONFIG_PUBLIC)) {
			return;
		}

		SubSystem currentSys = AppUtil.getCurrentSystem(((FilterInvocation) object).getHttpRequest());
		Map systemRoleMap;
		if (currentSys != null) {
			Long systemId = Long.valueOf(currentSys.getSystemId());

			systemRoleMap = SecurityUtil.getSystemRoleMap();

			Set roleSet = (Set) systemRoleMap.get(systemId);
			boolean canAccessSystem = false;

			for (GrantedAuthority hadRole : roles) {
				if (roleSet.contains(hadRole.getAuthority())) {
					canAccessSystem = true;
					break;
				}
			}
			if (!canAccessSystem) {
				throw new AccessDeniedException("没有访问该系统的权限!");
			}

		}

		for (GrantedAuthority hadRole : roles) {
			if (configAttributes.contains(new SecurityConfig(hadRole
					.getAuthority()))) {
				return;
			}
		}

		throw new AccessDeniedException("对不起,你没有访问该页面的权限!");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
}
