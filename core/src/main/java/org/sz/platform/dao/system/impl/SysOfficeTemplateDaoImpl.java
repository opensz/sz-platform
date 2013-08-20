 package org.sz.platform.dao.system.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.SysOfficeTemplateDao;
import org.sz.platform.model.system.SysOfficeTemplate;
 
 @Repository("sysOfficeTemplateDao")
 public class SysOfficeTemplateDaoImpl extends BaseDaoImpl<SysOfficeTemplate> implements SysOfficeTemplateDao
 {
   public Class getEntityClass()
   {
     return SysOfficeTemplate.class;
   }
 }

