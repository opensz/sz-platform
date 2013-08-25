 package org.sz.platform.system.dao.impl;
 
  import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.SysOrgParamDao;
import org.sz.platform.system.model.SysOrgParam;
 
 @Repository("sysOrgParamDao")
 public class SysOrgParamDaoImpl extends BaseDaoImpl<SysOrgParam> implements SysOrgParamDao
 {
   public Class getEntityClass()
   {
     return SysOrgParam.class;
   }
 
   public int delByOrgId(long orgId)
   {
     return delBySqlKey("delByOrgId", Long.valueOf(orgId));
   }
 
   public List<SysOrgParam> getByOrgId(Long orgId) {
     return getBySqlKey("getByOrgId", orgId);
   }
 }

