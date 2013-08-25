 package org.sz.platform.system.service.impl;
 
  import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.system.dao.SysAuditDao;
import org.sz.platform.system.model.SysAudit;
import org.sz.platform.system.service.SysAuditService;
 
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

