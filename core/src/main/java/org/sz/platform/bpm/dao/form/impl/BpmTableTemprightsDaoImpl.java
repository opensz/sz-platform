 package org.sz.platform.bpm.dao.form.impl;
 
  import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.form.BpmTableTemprightsDao;
import org.sz.platform.bpm.model.form.BpmTableTemprights;
 
 @Repository("bpmTableTemprightsDao")
 public class BpmTableTemprightsDaoImpl extends BaseDaoImpl<BpmTableTemprights> implements BpmTableTemprightsDao
 {
   public Class getEntityClass()
   {
     return BpmTableTemprights.class;
   }
 
   public void delByTemplateId(Long templateId) {
     delBySqlKey("delByTemplateId", templateId);
   }
 
   public void delByCategoryId(Long categoryId) {
     delBySqlKey("delByCategoryId", categoryId);
   }
 
   public List<BpmTableTemprights> getByTemplateId(Long templateId) {
     return getBySqlKey("getByTemplateId", templateId);
   }
 
   public List<BpmTableTemprights> getByCategoryId(Long categoryId) {
     return getBySqlKey("getByCategoryId", categoryId);
   }
 }

