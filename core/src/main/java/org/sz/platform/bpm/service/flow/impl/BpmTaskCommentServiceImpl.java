 package org.sz.platform.bpm.service.flow.impl;
 
  import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.dao.flow.BpmTaskCommentDao;
import org.sz.platform.bpm.model.flow.BpmTaskComment;
import org.sz.platform.bpm.service.flow.BpmTaskCommentService;
 
 @Service("bpmTaskCommentService")
 public class BpmTaskCommentServiceImpl extends BaseServiceImpl<BpmTaskComment> implements BpmTaskCommentService
 {
 
   @Resource
   private BpmTaskCommentDao dao;
 
   protected IEntityDao<BpmTaskComment, Long> getEntityDao()
   {
     return this.dao;
   }
 }

