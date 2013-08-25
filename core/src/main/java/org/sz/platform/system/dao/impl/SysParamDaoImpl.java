 package org.sz.platform.system.dao.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.SysParamDao;
import org.sz.platform.system.model.SysParam;
 
 @Repository("sysParamDao")
 public class SysParamDaoImpl extends BaseDaoImpl<SysParam> implements SysParamDao
 {
   public Class getEntityClass()
   {
     return SysParam.class;
   }
 
   public List<SysParam> getUserParam()
   {
     Map p = new HashMap();
     p.put("effect", 1);
     return getBySqlKey("getAll", p);
   }
 
   public List<SysParam> getOrgParam()
   {
     Map p = new HashMap();
     p.put("effect", 2);
     return getBySqlKey("getAll", p);
   }
 }

