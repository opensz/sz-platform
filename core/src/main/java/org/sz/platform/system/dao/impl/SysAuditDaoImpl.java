 package org.sz.platform.system.dao.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.SysAuditDao;
import org.sz.platform.system.model.SysAudit;
 
 @Repository("sysAuditDao")
 public class SysAuditDaoImpl extends BaseDaoImpl<SysAudit> implements SysAuditDao
 {
   public Class getEntityClass()
   {
     return SysAudit.class;
   }
 }

