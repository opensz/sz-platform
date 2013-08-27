 package org.sz.platform.bpm.service.flow.impl;
 
  import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.dao.flow.TaskReminderDao;
import org.sz.platform.bpm.model.flow.TaskReminder;
import org.sz.platform.bpm.service.flow.TaskReminderService;
 
 @Service("taskReminderService")
 public class TaskReminderServiceImpl extends BaseServiceImpl<TaskReminder> implements TaskReminderService
 {
 
   @Resource
   private TaskReminderDao dao;
 
   protected IEntityDao<TaskReminder, Long> getEntityDao()
   {
     return this.dao;
   }
   public TaskReminder getByActDefAndNodeId(String actDefId, String nodeId) {
     return this.dao.getByActDefAndNodeId(actDefId, nodeId);
   }
 }

