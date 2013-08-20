package org.sz.core.web.util;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.sz.core.model.OnlineUser;
import org.sz.core.util.ContextUtil;

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
}
