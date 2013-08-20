 package org.sz.platform.dao.system.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.SysTemplateDao;
import org.sz.platform.model.system.SysTemplate;
 
 @Repository("sysTemplateDao")
 public class SysTemplateDaoImpl extends BaseDaoImpl<SysTemplate> implements SysTemplateDao
 {
   public Class getEntityClass()
   {
     return SysTemplate.class;
   }
 }

