 package org.sz.platform.bpm.dao.flow.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.TaskCommentDao;
import org.sz.platform.bpm.model.flow.TaskComment;
 
 @Repository("taskCommentDao")
 public class TaskCommentDaoImpl extends BaseDaoImpl<TaskComment> implements TaskCommentDao
 {
   public Class getEntityClass()
   {
     return TaskComment.class;
   }
 }

