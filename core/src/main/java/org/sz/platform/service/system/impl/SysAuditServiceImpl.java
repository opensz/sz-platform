 package org.sz.platform.service.system.impl;
 
  import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.dao.system.SysAuditDao;
import org.sz.platform.model.system.SysAudit;
import org.sz.platform.service.system.SysAuditService;
 
 @Service("sysAuditService")
 public class SysAuditServiceImpl extends BaseServiceImpl<SysAudit> implements SysAuditService
 {
 
   @Resource
   private SysAuditDao dao;
 
   protected IEntityDao<SysAudit, Long> getEntityDao()
   {
     return this.dao;
   }
 }

