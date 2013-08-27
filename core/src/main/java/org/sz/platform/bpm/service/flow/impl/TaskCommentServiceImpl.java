 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.Date;

import javax.annotation.Resource;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.TaskCommentDao;
import org.sz.platform.bpm.model.flow.TaskComment;
import org.sz.platform.bpm.service.flow.TaskCommentService;
 
 @Service("taskCommentService")
 public class TaskCommentServiceImpl extends BaseServiceImpl<TaskComment> implements TaskCommentService
 {
 
   @Resource
   private TaskCommentDao dao;
 
   protected IEntityDao<TaskComment, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public void addTaskComment(TaskComment taskComment, TaskEntity taskEntity)
     throws Exception
   {
     taskComment.setCommentId(Long.valueOf(UniqueIdUtil.genId()));
     taskComment.setAuthorId(ContextUtil.getCurrentUserId());
     taskComment.setAuthor(ContextUtil.getCurrentUser().getFullname());
     taskComment.setNodeName(taskEntity.getName());
     taskComment.setCommentTime(new Date());
     this.dao.add(taskComment);
   }
 }

