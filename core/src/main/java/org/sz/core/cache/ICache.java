package org.sz.core.cache;

public abstract interface ICache
{
  public static final String UserRole = "UserRole";

  public abstract void add(String paramString, Object paramObject, long paramLong);

  public abstract void delByKey(String paramString);

  public abstract void clearAll();

  public abstract Object getByKey(String paramString);

  public abstract boolean containKey(String paramString);
}

