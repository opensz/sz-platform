package org.sz.core.dao.helper;

import javax.sql.DataSource;

public abstract interface JdbcCommand
{
  public abstract void execute(DataSource paramDataSource)
    throws Exception;
}

