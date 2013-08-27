 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.BpmNodeUserDao;
import org.sz.platform.bpm.model.flow.BpmNodeUser;
 
 @Repository("bpmNodeUserDao")
 public class BpmNodeUserDaoImpl extends BaseDaoImpl<BpmNodeUser> implements BpmNodeUserDao
 {
   public Class getEntityClass()
   {
     return BpmNodeUser.class;
   }
 
   public List<BpmNodeUser> getBySetId(Long setId)
   {
     return getBySqlKey("getBySetId", setId);
   }
 
   public void delByActDefId(String actDefId) {
     delBySqlKey("delByActDefId", actDefId);
   }
 }

