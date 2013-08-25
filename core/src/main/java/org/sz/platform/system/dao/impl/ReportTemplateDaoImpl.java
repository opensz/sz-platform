 package org.sz.platform.system.dao.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.ReportTemplateDao;
import org.sz.platform.system.model.ReportTemplate;
 
 @Repository("reportTemplateDao")
 public class ReportTemplateDaoImpl extends BaseDaoImpl<ReportTemplate> implements ReportTemplateDao
 {
   public Class getEntityClass()
   {
     return ReportTemplate.class;
   }
 }

