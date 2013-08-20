 package org.sz.platform.dao.system.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.SysAuditDao;
import org.sz.platform.model.system.SysAudit;
 
 @Repository("sysAuditDao")
 public class SysAuditDaoImpl extends BaseDaoImpl<SysAudit> implements SysAuditDao
 {
   public Class getEntityClass()
   {
     return SysAudit.class;
   }
 }

