 package org.sz.platform.dao.system.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.SysUserAgentDao;
import org.sz.platform.model.system.SysUserAgent;
 
 @Repository("sysUserAgentDao")
 public class SysUserAgentDaoImpl extends BaseDaoImpl<SysUserAgent> implements SysUserAgentDao
 {
   public Class getEntityClass()
   {
     return SysUserAgent.class;
   }
 
   public List<SysUserAgent> getByTouserId(Long userId) {
     Map params = new HashMap();
     params.put("touserid", userId);
     return getBySqlKey("getByTouserId", params);
   }
 }

