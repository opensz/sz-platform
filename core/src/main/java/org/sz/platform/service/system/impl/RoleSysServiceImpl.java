 package org.sz.platform.service.system.impl;
 
  import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.dao.system.RoleSysDao;
import org.sz.platform.model.system.RoleSys;
import org.sz.platform.service.system.RoleSysService;
 
 @Service("roleSysService")
 public class RoleSysServiceImpl extends BaseServiceImpl<RoleSys> implements RoleSysService
 {
 
   @Resource
   private RoleSysDao dao;
 
   protected IEntityDao<RoleSys, Long> getEntityDao()
   {
     return this.dao;
   }
 }

