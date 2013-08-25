 package org.sz.core.customertable;
 
 import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.sz.core.dao.JdbcHelper;
import org.sz.platform.system.model.SysDataSource;
 
 public abstract class BaseDbView
 {
   protected JdbcHelper jdbcHelper;
   protected String currentDb;
   protected JdbcTemplate jdbcTemplate;
 
   public abstract String getType(String paramString);
 
   public void setDataSource(SysDataSource sysDataSource)
     throws Exception
   {
     String dsName = sysDataSource.getAlias();
     String className = sysDataSource.getDriverName();
     String url = sysDataSource.getUrl();
     String userName = sysDataSource.getUserName();
     String pwd = sysDataSource.getPassword();
     this.jdbcHelper = JdbcHelper.getInstance();
     this.jdbcHelper.init(dsName, className, url, userName, pwd);
     this.currentDb = dsName;
     this.jdbcTemplate = this.jdbcHelper.getJdbcTemplate();
   }
 
   public TableModel getModelByViewName(String viewName)
     throws SQLException
   {
     Connection conn = this.jdbcTemplate.getDataSource().getConnection();
 
     Statement stmt = null;
     ResultSet rs = null;
 
     TableModel tableModel = new TableModel();
     tableModel.setName(viewName);
     tableModel.setComment(viewName);
     try
     {
       stmt = conn.createStatement();
       rs = stmt.executeQuery("select * from " + viewName);
       ResultSetMetaData metadata = rs.getMetaData();
 
       int count = metadata.getColumnCount();
       for (int i = 1; i <= count; i++) {
         ColumnModel columnModel = new ColumnModel();
         String columnName = metadata.getColumnName(i);
         String typeName = metadata.getColumnTypeName(i);
         String dataType = getType(typeName);
         columnModel.setName(columnName);
         columnModel.setColumnType(dataType);
         columnModel.setComment(columnName);
         tableModel.addColumnModel(columnModel);
       }
     }
     catch (SQLException e) {
       e.printStackTrace();
     } finally {
       try {
         if (rs != null) {
           rs.close();
         }
         if (stmt != null) {
           stmt.close();
         }
         if (conn != null)
           conn.close();
       }
       catch (SQLException e) {
         e.printStackTrace();
       }
     }
     return tableModel;
   }
 }