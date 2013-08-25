 package org.sz.core.customertable.impl;
 
  import java.sql.SQLException;
 import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sz.core.customertable.BaseDbView;
import org.sz.core.customertable.IDbView;
import org.sz.core.util.StringUtil;
 
 public class OracleDbView extends BaseDbView
   implements IDbView
 {
   private static final String sqlAllView = "select view_name from user_views ";
 
   public List<String> getViews(String viewName)
     throws SQLException
   {
     String sql = "select view_name from user_views ";
     if (StringUtil.isNotEmpty(viewName)) {
       sql = sql + " where lower(view_name) like '" + viewName.toLowerCase() + "%'";
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