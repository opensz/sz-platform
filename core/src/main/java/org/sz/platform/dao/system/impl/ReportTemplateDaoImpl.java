 package org.sz.platform.dao.system.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.ReportTemplateDao;
import org.sz.platform.model.system.ReportTemplate;
 
 @Repository("reportTemplateDao")
 public class ReportTemplateDaoImpl extends BaseDaoImpl<ReportTemplate> implements ReportTemplateDao
 {
   public Class getEntityClass()
   {
     return ReportTemplate.class;
   }
 }

