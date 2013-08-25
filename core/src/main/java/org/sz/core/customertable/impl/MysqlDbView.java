 package org.sz.core.customertable.impl;
 
  import java.sql.SQLException;
 import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sz.core.customertable.BaseDbView;
import org.sz.core.customertable.IDbView;
import org.sz.core.util.StringUtil;
 
 public class MysqlDbView extends BaseDbView
   implements IDbView
 {
   private static final String sqlAllView = "SELECT TABLE_NAME FROM information_schema.`TABLES` WHERE TABLE_TYPE LIKE 'VIEW'";
 
   public List<String> getViews(String viewName)
     throws SQLException
   {
     String sql = "SELECT TABLE_NAME FROM information_schema.`TABLES` WHERE TABLE_TYPE LIKE 'VIEW'";
     if (StringUtil.isNotEmpty(viewName)) {
       sql = sql + " AND TABLE_NAME LIKE '" + viewName + "%'";
     }
     return this.jdbcTemplate.queryForList(sql, String.class);
   }
 
   public String getType(String type)
   {
     type = type.toLowerCase();
     if (type.indexOf("number") > -1)
       return "number";
     if (type.indexOf("date") > -1) {
       return "date";
     }
     if (type.indexOf("char") > -1) {
       return "varchar";
     }
     return "varchar";
   }
 }