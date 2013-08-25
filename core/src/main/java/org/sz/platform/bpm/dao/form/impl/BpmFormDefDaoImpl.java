 package org.sz.platform.bpm.dao.form.impl;
 
  import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.dao.form.BpmFormDefDao;
import org.sz.platform.bpm.model.form.BpmFormDef;
 
 @Repository("bpmFormDefDao")
 public class BpmFormDefDaoImpl extends BaseDaoImpl<BpmFormDef> implements BpmFormDefDao
 {
   public Class getEntityClass()
   {
     return BpmFormDef.class;
   }
 
   public Integer getCountByFormKey(Long formKey)
   {
     return (Integer)getOne("getCountByFormKey", formKey);
   }
 
   public BpmFormDef getDefaultVersionByFormKey(Long formKey)
   {
     return (BpmFormDef)getOne("getDefaultVersionByFormKey", formKey);
   }
 
   public boolean isTableHasFormDef(Long tableId)
   {
     Integer result = (Integer)getOne("isTableHasFormDef", tableId);
     return result.intValue() > 0;
   }
 
   public List<BpmFormDef> getByFormKey(Long formKey)
   {
     return getBySqlKey("getByFormKey", formKey);
   }
 
   public List<BpmFormDef> getPublished(QueryFilter queryFilter)
   {
     return getBySqlKey("getPublished", queryFilter);
   }
 
   public int getFlowUsed(Long formKey)
   {
     Integer rtn = (Integer)getOne("getFlowUsed", formKey);
     return rtn.intValue();
   }
 
   public void delByFormKey(Long formKey)
   {
     delBySqlKey("delByFormKey", formKey);
   }
 
   public void setDefaultVersion(Long formKey, Long formDefId)
   {
     update("updNotDefaultByFormKey", formKey);
     update("updDefaultByFormId", formDefId);
   }
 }

