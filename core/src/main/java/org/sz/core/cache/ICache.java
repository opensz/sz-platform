package org.sz.core.cache;

public interface ICache {
	static final String UserRole = "UserRole";

	void add(String paramString, Object paramObject, long paramLong);

	void delByKey(String paramString);

	void clearAll();

	Object getByKey(String paramString);

	boolean containKey(String paramString);
}
