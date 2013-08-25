package org.sz.core.customertable;

import java.sql.SQLException;
import java.util.List;

import org.sz.platform.system.model.SysDataSource;

public abstract interface IDbView
{
  public abstract void setDataSource(SysDataSource paramSysDataSource)
    throws Exception;

  public abstract List<String> getViews(String paramString)
    throws SQLException;

  public abstract TableModel getModelByViewName(String paramString)
    throws SQLException;
}