package org.sz.core.keygenerator;

public abstract interface IKeyGenerator
{
  public abstract Object nextId()
    throws Exception;

  public abstract void setAlias(String paramString);
}