 package org.sz.platform.service.system.impl;
 
  import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.dao.system.SysOfficeTemplateDao;
import org.sz.platform.model.system.SysOfficeTemplate;
import org.sz.platform.service.system.SysOfficeTemplateService;
 
 @Service("sysOfficeTemplateService")
 public class SysOfficeTemplateServiceImpl extends BaseServiceImpl<SysOfficeTemplate> implements SysOfficeTemplateService
 {
 
   @Resource
   private SysOfficeTemplateDao dao;
 
   protected IEntityDao<SysOfficeTemplate, Long> getEntityDao()
   {
     return this.dao;
   }
 }

