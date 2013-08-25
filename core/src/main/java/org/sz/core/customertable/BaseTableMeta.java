 package org.sz.core.customertable;
 
 import java.util.Map;

import org.sz.core.dao.JdbcHelper;
import org.sz.platform.system.model.SysDataSource;
 
 public abstract class BaseTableMeta
 {
   protected JdbcHelper jdbcHelper;
   protected String currentDb;
 
   public abstract TableModel getTableByName(String paramString);
 
   public abstract Map<String, String> getTablesByName(String paramString);
 
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
   }
 }