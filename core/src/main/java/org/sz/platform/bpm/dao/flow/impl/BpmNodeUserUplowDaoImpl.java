 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.BpmNodeUserUplowDao;
import org.sz.platform.bpm.model.flow.BpmNodeUserUplow;
 
 @Repository("bpmNodeUserUplowDao")
 public class BpmNodeUserUplowDaoImpl extends BaseDaoImpl<BpmNodeUserUplow> implements BpmNodeUserUplowDao
 {
   public Class getEntityClass()
   {
     return BpmNodeUserUplow.class;
   }
   public int delByNodeUserId(long nodeUserId) {
     return getSqlSessionTemplate().delete(getIbatisMapperNamespace() + ".delByNodeUserId", Long.valueOf(nodeUserId));
   }
 
   public List<BpmNodeUserUplow> getByNodeUserId(long userId) {
     return getBySqlKey("getByNodeUserId", Long.valueOf(userId));
   }
 }

