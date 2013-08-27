 package org.sz.platform.bpm.dao.form.impl;
 
  import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.form.BpmFormTemplateDao;
import org.sz.platform.bpm.model.form.BpmFormTemplate;
 
 @Repository("bpmFormTemplateDao")
 public class BpmFormTemplateDaoImpl extends BaseDaoImpl<BpmFormTemplate> implements BpmFormTemplateDao
 {
   public Class getEntityClass()
   {
     return BpmFormTemplate.class;
   }
 
   public List<BpmFormTemplate> getAll(Map params)
   {
     return getBySqlKey("getAll", params);
   }
 
   public void add(BpmFormTemplate bpmFormTemplate)
   {
     getBySqlKey("add", bpmFormTemplate);
   }
 
   public void delSystem()
   {
     delBySqlKey("delSystem", null);
   }
 
   public BpmFormTemplate getByTemplateAlias(String alias)
   {
     return (BpmFormTemplate)getUnique("getByTemplateAlias", alias);
   }
 
   public Integer getHasData()
   {
     return (Integer)getOne("getHasData", null);
   }
 }

