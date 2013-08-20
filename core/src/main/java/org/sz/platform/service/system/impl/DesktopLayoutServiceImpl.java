 package org.sz.platform.service.system.impl;
 
  import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.dao.system.DesktopLayoutDao;
import org.sz.platform.model.system.DesktopLayout;
import org.sz.platform.service.system.DesktopLayoutService;
 
 @Service("desktopLayoutService")
 public class DesktopLayoutServiceImpl extends BaseServiceImpl<DesktopLayout> implements DesktopLayoutService
 {
 
   @Resource
   private DesktopLayoutDao dao;
 
   protected IEntityDao<DesktopLayout, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public DesktopLayout getDefaultLayout()
   {
     return this.dao.getDefaultLayout();
   }
 
   public void setDefault(Long layoutId)
     throws Exception
   {
     this.dao.updNotDefault();
     this.dao.updateDefault(layoutId.longValue());
   }
 }

