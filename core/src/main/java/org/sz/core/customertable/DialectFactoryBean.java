package org.sz.core.customertable;

import org.springframework.beans.factory.FactoryBean;
import org.sz.core.mybatis.Dialect;
import org.sz.core.mybatis.dialect.DB2Dialect;
import org.sz.core.mybatis.dialect.MySQLDialect;
import org.sz.core.mybatis.dialect.OracleDialect;
import org.sz.core.mybatis.dialect.SQLServer2005Dialect;

public class DialectFactoryBean
  implements FactoryBean<Dialect>
{
  public static final String ORACLE = "oracle";
  public static final String MYSQL = "mysql";
  public static final String SQLSERVER = "sqlserver";
  public static final String DB2 = "db2";
  private Dialect dialect;
  private String dbType = "mysql";

  public void setDbType(String dbType)
  {
    this.dbType = dbType;
  }

  public Dialect getObject()
    throws Exception
  {
    if (this.dbType.equals("oracle")) {
      this.dialect = new OracleDialect();
    }
    else if (this.dbType.equals("sqlserver")) {
      this.dialect = new SQLServer2005Dialect();
    }
    else if (this.dbType.equals("db2")) {
      this.dialect = new DB2Dialect();
    }
    else if (this.dbType.equals("mysql")) {
      this.dialect = new MySQLDialect();
    }
    else {
      throw new Exception("没有设置合适的数据库类型");
    }
    return this.dialect;
  }

  public Class<?> getObjectType()
  {
    return Dialect.class;
  }

  public boolean isSingleton()
  {
    return true;
  }
}