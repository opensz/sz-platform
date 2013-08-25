package org.sz.core.customertable;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sz.core.customertable.impl.Db2TableOperator;
import org.sz.core.customertable.impl.MysqlTableOperator;
import org.sz.core.customertable.impl.OracleTableOperator;
import org.sz.core.customertable.impl.SqlserverTableOperator;

public class TableOperatorFactoryBean
  implements FactoryBean<ITableOperator>
{
  private static final String ORACLE = "oracle";
  private static final String MYSQL = "mysql";
  private static final String SQLSERVER = "sqlserver";
  private static final String DB2 = "db2";
  private ITableOperator tableOperator;
  private String dbType = "mysql";
  private JdbcTemplate jdbcTemplate;

  public ITableOperator getObject()
    throws Exception
  {
    if (this.dbType.equals("oracle")) {
      this.tableOperator = new OracleTableOperator();
    }
    else if (this.dbType.equals("sqlserver")) {
      this.tableOperator = new SqlserverTableOperator();
    }
    else if (this.dbType.equals("db2")) {
      this.tableOperator = new Db2TableOperator();
    }
    else if (this.dbType.equals("mysql")) {
      this.tableOperator = new MysqlTableOperator();
    }
    else {
      throw new Exception("没有设置合适的数据库类型");
    }
    this.tableOperator.setJdbcTemplate(this.jdbcTemplate);
    return this.tableOperator;
  }

  public void setDbType(String dbType)
  {
    this.dbType = dbType;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
  {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Class<?> getObjectType()
  {
    return ITableOperator.class;
  }

  public boolean isSingleton()
  {
    return true;
  }
}
