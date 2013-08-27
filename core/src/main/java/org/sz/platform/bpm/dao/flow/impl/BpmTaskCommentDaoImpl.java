 package org.sz.platform.bpm.dao.flow.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.BpmTaskCommentDao;
import org.sz.platform.bpm.model.flow.BpmTaskComment;
 
 @Repository("bpmTaskCommentDao")
 public class BpmTaskCommentDaoImpl extends BaseDaoImpl<BpmTaskComment> implements BpmTaskCommentDao
 {
   public Class getEntityClass()
   {
     return BpmTaskComment.class;
   }
 
   public void delByactDefId(String actDefId)
   {
     delBySqlKey("delByactDefId", actDefId);
   }
 }

