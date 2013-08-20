 package org.sz.platform.service.system.impl;
 
  import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.dao.system.SysTemplateDao;
import org.sz.platform.model.system.SysTemplate;
import org.sz.platform.service.system.SysTemplateService;
 
 @Service("sysTemplateService")
 public class SysTemplateServiceImpl extends BaseServiceImpl<SysTemplate> implements SysTemplateService
 {
 
   @Resource
   private SysTemplateDao dao;
 
   protected IEntityDao<SysTemplate, Long> getEntityDao()
   {
     return this.dao;
   }
 }

