 package org.sz.core.customertable.impl;
 
  import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sz.core.customertable.BaseDbView;
import org.sz.core.customertable.IDbView;
import org.sz.core.util.StringUtil;
 
 public class SqlserverDbView extends BaseDbView
   implements IDbView
 {
   private final String sqlAllView = "select name from sysobjects where xtype='V'";
 
   public List<String> getViews(String viewName)
   {
     String sql = "select name from sysobjects where xtype='V'";
     if (StringUtil.isNotEmpty(viewName)) {
       sql = sql + " and name like '" + viewName + "%'";
     }
     return this.jdbcTemplate.queryForList(sql, String.class);
   }
 
   public String getType(String type)
   {
     if ((type.indexOf("int") > -1) || (type.equals("real")) || (type.equals("numeric")) || (type.indexOf("money") > -1))
       return "number";
     if (type.indexOf("date") > -1) {
       return "date";
     }
     return "varchar";
   }
 }