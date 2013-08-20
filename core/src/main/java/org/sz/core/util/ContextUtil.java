package org.sz.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.sz.platform.model.system.SysUser;


public class ContextUtil {
	private static Logger logger = LoggerFactory.getLogger(ContextUtil.class);
	
	public static SysUser getCurrentUser() {
		SysUser sysUser = null;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null) {
			Authentication auth = securityContext.getAuthentication();
			if (auth != null) {
				Object principal = auth.getPrincipal();
				if ((principal instanceof SysUser)) {
					sysUser = (SysUser) principal;
				}
			}
		}	
		return sysUser;
	}

	public static Long getCurrentUserId() {
		SysUser curUser = getCurrentUser();
		if (curUser != null)
			return curUser.getUserId();
		return null;
	}
	
	public static Object getBean(Class cls) {
		return SpringContextHolder.getBean(cls);
	}
	
	public static Object getBean(String name) {
		return SpringContextHolder.getBean(name);
	}
}
