 package org.sz.platform.model.system;
 
 import org.sz.core.model.BaseModel;
 
 public class SysDataSource extends BaseModel
 {
   public static final String Oracle = "oracle";
   public static final String MySql = "mysql";
   public static final String Sql2005 = "sql2005";
   public static final String DB2 = "db2";
   private Long id;
   private String name;
   private String alias;
   private String driverName;
   private String url;
   private String userName;
   private String password;
   private String dbType = "";
 
   public String getDbType() {
     return this.dbType;
   }
   public void setDbType(String dbType) {
     this.dbType = dbType;
   }
   public void setId(Long id) {
     this.id = id;
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public String getName()
   {
     return this.name;
   }
 
   public void setAlias(String alias) {
     this.alias = alias;
   }
 
   public String getAlias()
   {
     return this.alias;
   }
 
   public void setDriverName(String driverName) {
     this.driverName = driverName;
   }
 
   public String getDriverName()
   {
     return this.driverName;
   }
 
   public void setUrl(String url) {
     this.url = url;
   }
 
   public String getUrl()
   {
     return this.url;
   }
 
   public void setUserName(String userName) {
     this.userName = userName;
   }
 
   public String getUserName()
   {
     return this.userName;
   }
 
   public void setPassword(String password) {
     this.password = password;
   }
 
   public String getPassword()
   {
     return this.password;
   }
 }

