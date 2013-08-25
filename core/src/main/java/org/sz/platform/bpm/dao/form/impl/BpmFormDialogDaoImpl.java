 package org.sz.platform.bpm.dao.form.impl;
 
  import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.form.BpmFormDialogDao;
import org.sz.platform.bpm.model.form.BpmFormDialog;
 
 @Repository("bpmFormDialogDao")
 public class BpmFormDialogDaoImpl extends BaseDaoImpl<BpmFormDialog> implements BpmFormDialogDao
 {
   public Class getEntityClass()
   {
     return BpmFormDialog.class;
   }
 
   public BpmFormDialog getByAlias(String alias)
   {
     return (BpmFormDialog)getUnique("getByAlias", alias);
   }
 
   public Integer isExistAlias(String alias)
   {
     return (Integer)getOne("isExistAlias", alias);
   }
 
   public Integer isExistAliasForUpd(Long id, String alias)
   {
     Map map = new HashMap();
     map.put("id", id);
     map.put("alias", alias);
     return (Integer)getOne("isExistAliasForUpd", map);
   }
 }

