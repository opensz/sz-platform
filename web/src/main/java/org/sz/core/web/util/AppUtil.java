package org.sz.core.web.util;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sz.core.model.OnlineUser;
import org.sz.core.util.ContextUtil;
import org.sz.platform.system.model.SubSystem;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.SubSystemService;

public class AppUtil extends ContextUtil {

	private static ServletContext servletContext;
	private static Map<Long, OnlineUser> onlineUsers = new LinkedHashMap();

	public static void init(ServletContext _servletContext) {
		servletContext = _servletContext;
	}

	public static ServletContext getServletContext() throws Exception {
		return servletContext;
	}

	public static String getAppAbsolutePath() {
		return servletContext.getRealPath("/");
	}

	public static String getRealPath(String path) {
		return servletContext.getRealPath(path);
	}

	public static Map<Long, OnlineUser> getOnlineUsers() {
		return onlineUsers;
	}

	public static SubSystem getCurrentSystem(HttpServletRequest request) {
		SysUser curUser = ContextUtil.getCurrentUser();

		if (curUser == null)
			return null;

		SubSystem subSystem = (SubSystem) request.getSession().getAttribute(
				SubSystem.CURRENT_SYSTEM);

		if (subSystem != null)
			return subSystem;

		boolean isCookieExists = CookieUtil.isExistByName(
				SubSystem.CURRENT_SYSTEM, request);

		if (!isCookieExists) {
			return null;
		}

		String systemId = CookieUtil.getValueByName(SubSystem.CURRENT_SYSTEM,
				request);
		SubSystemService subSystemService = (SubSystemService) AppUtil
				.getBean(SubSystemService.class);
		subSystem = (SubSystem) subSystemService
				.getById(Long.valueOf(systemId));

		request.getSession().setAttribute(SubSystem.CURRENT_SYSTEM, subSystem);

		return subSystem;
	}

	public static Long getCurrentSystemId(HttpServletRequest request) {
		SubSystem subSystem = getCurrentSystem(request);
		if (subSystem != null)
			return Long.valueOf(subSystem.getSystemId());
		return null;
	}

	public static void setCurrentSystem(Long systemId, HttpServletRequest request,
			HttpServletResponse response) {
		SubSystem subSystem = ((SubSystemService) getBean("subSystemService"))
				.getById(systemId);
		if (subSystem != null) {
			writeCurrentSystemCookie(String.valueOf(systemId), request,
					response);
			request.getSession().setAttribute(SubSystem.CURRENT_SYSTEM,
					subSystem);
		}
	}

	public static void writeCurrentSystemCookie(String systemId,
			HttpServletRequest request, HttpServletResponse response) {
		if (CookieUtil.isExistByName(SubSystem.CURRENT_SYSTEM, request)) {
			CookieUtil.delCookie(SubSystem.CURRENT_SYSTEM, request, response);
		}
		int tokenValiditySeconds = 1209600;
		CookieUtil.addCookie(SubSystem.CURRENT_SYSTEM, systemId,
				tokenValiditySeconds, request, response);
	}
}
