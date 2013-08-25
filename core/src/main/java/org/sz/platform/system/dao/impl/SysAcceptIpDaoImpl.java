 package org.sz.platform.system.dao.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.SysAcceptIpDao;
import org.sz.platform.system.model.SysAcceptIp;
 
 @Repository("sysAcceptIpDao")
 public class SysAcceptIpDaoImpl extends BaseDaoImpl<SysAcceptIp> implements SysAcceptIpDao
 {
   public Class getEntityClass()
   {
     return SysAcceptIp.class;
   }
 
   public List<SysAcceptIp> getWhiteList() {
     Map p = new HashMap();
     p.put("ipType", 0);
     return getBySqlKey("getAll", p);
   }
 
   public List<SysAcceptIp> getBlackList() {
     Map p = new HashMap();
     p.put("ipType", 1);
     return getBySqlKey("getAll", p);
   }
 }

