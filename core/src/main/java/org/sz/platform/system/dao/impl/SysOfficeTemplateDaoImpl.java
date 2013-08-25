 package org.sz.platform.system.dao.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.SysOfficeTemplateDao;
import org.sz.platform.system.model.SysOfficeTemplate;
 
 @Repository("sysOfficeTemplateDao")
 public class SysOfficeTemplateDaoImpl extends BaseDaoImpl<SysOfficeTemplate> implements SysOfficeTemplateDao
 {
   public Class getEntityClass()
   {
     return SysOfficeTemplate.class;
   }
 }

