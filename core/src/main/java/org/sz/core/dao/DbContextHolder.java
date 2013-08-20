package org.sz.core.dao;

public class DbContextHolder {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal();

	public static void setDbType(String dbType) {
		contextHolder.set(dbType);
	}

	public static String getDbType() {
		String str = (String) contextHolder.get();
		if ((null == str) || ("".equals(str)))
			str = "1";
		return str;
	}

	public static void clearDbType() {
		contextHolder.remove();
	}
}
