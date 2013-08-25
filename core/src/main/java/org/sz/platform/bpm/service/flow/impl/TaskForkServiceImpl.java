 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.Date;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.TaskForkDao;
import org.sz.platform.bpm.model.flow.TaskFork;
import org.sz.platform.bpm.service.flow.TaskForkService;
 
 @Service("taskForkService")
 public class TaskForkServiceImpl extends BaseServiceImpl<TaskFork> implements TaskForkService
 {
 
   @Resource
   private TaskForkDao dao;
 
   protected IEntityDao<TaskFork, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public Integer getMaxSn(String actInstId)
   {
     return this.dao.getMaxSn(actInstId);
   }
 
   public TaskFork newTaskFork(DelegateTask delegateTask, String joinTaskName, String joinTaskKey, Integer forkCount)
   {
     String token = (String)delegateTask.getVariableLocal(TaskFork.TAKEN_VAR_NAME);
 
     TaskFork taskFork = new TaskFork();
     try {
       taskFork.setTaskForkId(Long.valueOf(UniqueIdUtil.genId()));
     } catch (Exception ex) {
       this.log.error(ex.getMessage());
     }
     taskFork.setActInstId(delegateTask.getProcessInstanceId());
     taskFork.setForkTime(new Date());
     taskFork.setFininshCount(Integer.valueOf(0));
     taskFork.setForkCount(forkCount);
     taskFork.setForkTaskKey(delegateTask.getTaskDefinitionKey());
     taskFork.setForkTaskName(delegateTask.getName());
     taskFork.setJoinTaskKey(joinTaskKey);
     taskFork.setJoinTaskName(joinTaskName);
 
     Integer sn = Integer.valueOf(1);
     if (token == null) {
       taskFork.setForkTokenPre(TaskFork.TAKEN_PRE + "_");
     } else {
       String[] lines = token.split("[_]");
       if ((lines != null) && (lines.length > 1)) {
         sn = Integer.valueOf(lines.length - 1);
         String forkTokenPre = token.substring(0, token.lastIndexOf(lines[(lines.length - 1)]));
         taskFork.setForkTokenPre(forkTokenPre);
       }
     }
 
     String forkTokens = "";
     for (int i = 1; i <= forkCount.intValue(); i++) {
       forkTokens = forkTokens + taskFork.getForkTokenPre() + i + ",";
     }
     taskFork.setForkTokens(forkTokens);
 
     taskFork.setForkSn(sn);
     add(taskFork);
     return taskFork;
   }
 
   public TaskFork getByInstIdJoinTaskKey(String actInstId, String joinTaskKey)
   {
     return this.dao.getByInstIdJoinTaskKey(actInstId, joinTaskKey);
   }
 
   public TaskFork getByInstIdJoinTaskKeyForkToken(String actInstId, String joinTaskKey, String forkToken)
   {
     return this.dao.getByInstIdJoinTaskKeyForkToken(actInstId, joinTaskKey, forkToken);
   }
 }

