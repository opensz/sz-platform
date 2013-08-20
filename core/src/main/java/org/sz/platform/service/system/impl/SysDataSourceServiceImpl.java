 package org.sz.platform.service.system.impl;
 
  import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.dao.JdbcHelper;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.platform.dao.system.SysDataSourceDao;
import org.sz.platform.model.system.SysDataSource;
import org.sz.platform.service.system.SysDataSourceService;
 
 @Service("sysDataSourceService")
 public class SysDataSourceServiceImpl extends BaseServiceImpl<SysDataSource> implements SysDataSourceService
 {
 
   @Resource
   private SysDataSourceDao dao;
 
   protected IEntityDao<SysDataSource, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public void delByIds(Long[] ids)
   {
     if (BeanUtils.isEmpty(ids)) return;
     for (Long p : ids) {
       SysDataSource sysDataSource = (SysDataSource)this.dao.getById(p);
 
       JdbcHelper.getInstance().removeAlias(sysDataSource.getAlias());
       delById(p);
     }
   }
 
   public List<Map<String, Object>> testConnectById(Long[] ids)
   {
     List result = new ArrayList();
     Long[] arr$ = ids; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { long id = arr$[i$].longValue();
       SysDataSource sysDataSource = (SysDataSource)this.dao.getById(Long.valueOf(id));
       result.addAll(testConnectByForm(sysDataSource));
     }
 
     return result;
   }
 
   public List<Map<String, Object>> testConnectByForm(SysDataSource sysDataSource)
   {
     List result = new ArrayList();
 
     Map connectResult = new HashMap();
     connectResult.put("name", sysDataSource.getName());
     try {
       Class.forName(sysDataSource.getDriverName());
 
       DriverManager.getConnection(sysDataSource.getUrl(), sysDataSource.getUserName(), sysDataSource.getPassword()).close();
 
       connectResult.put("success", Boolean.valueOf(true));
     }
     catch (ClassNotFoundException e) {
       connectResult.put("msg", "ClassNotFoundException: " + sysDataSource.getDriverName());
       connectResult.put("success", Boolean.valueOf(false));
     }
     catch (SQLException e) {
       connectResult.put("msg", e.getMessage());
       connectResult.put("success", Boolean.valueOf(false));
     }
     result.add(connectResult);
 
     return result;
   }
 
   public SysDataSource getByAlias(String alias)
   {
     return this.dao.getByAlias(alias);
   }
 
   public boolean isAliasExisted(String alias)
   {
     return this.dao.isAliasExisted(alias);
   }
 
   public boolean isAliasExistedByUpdate(SysDataSource sysDataSource)
   {
     return this.dao.isAliasExistedByUpdate(sysDataSource);
   }
 }

