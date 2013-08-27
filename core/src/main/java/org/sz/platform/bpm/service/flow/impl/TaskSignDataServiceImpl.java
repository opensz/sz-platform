 package org.sz.platform.bpm.service.flow.impl;
 
 import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.springframework.stereotype.Service;
import org.sz.core.bpm.model.ProcessTask;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.TaskSignDataDao;
import org.sz.platform.bpm.model.flow.TaskSignData;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.TaskSignDataService;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.SysUserService;
 
 @Service("taskSignDataService")
 public class TaskSignDataServiceImpl extends BaseServiceImpl<TaskSignData> implements TaskSignDataService
 {
 
   @Resource
   private TaskSignDataDao dao;
 
   @Resource
   private BpmService bpmService;
 
   @Resource
   private SysUserService sysUserService;
 
   @Resource
   private RuntimeService runtimeService;
 
   protected IEntityDao<TaskSignData, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public List<TaskSignData> getByActInstIdNodeIdSignNums(String actInstId, String nodeId, Integer signNums)
   {
     return this.dao.getByActInstIdNodeIdSignNums(actInstId, nodeId, signNums);
   }
 
   public Integer getMaxSignNums(String actInstId, String nodeId, Short isCompleted)
   {
     return this.dao.getMaxSignNums(actInstId, nodeId, isCompleted);
   }
 
   public TaskSignData getByTaskId(String taskId)
   {
     return this.dao.getByTaskId(taskId);
   }
 
   public void signVoteTask(String taskId, String content, Short isAgree)
   {
     TaskEntity taskEntity = this.bpmService.getTask(taskId);
     ExecutionEntity executionEntity = this.bpmService.getExecution(taskEntity.getExecutionId());
     Integer signNums = this.dao.getMaxSignNums(taskEntity.getProcessInstanceId(), executionEntity.getActivityId(), TaskSignData.NOT_COMPLETED);
     TaskSignData taskSignData = this.dao.getUserTaskSign(taskEntity.getProcessInstanceId(), executionEntity.getActivityId(), signNums, ContextUtil.getCurrentUserId());
 
     if (taskSignData != null)
     {
       taskSignData.setIsAgree(new Short(isAgree.shortValue()));
       taskSignData.setContent(content);
       taskSignData.setVoteTime(new Date());
       taskSignData.setTaskId(taskId);
       update(taskSignData);
     }
   }
 
   public Integer getTotalVoteCount(String actInstId, String nodeId)
   {
     return this.dao.getTotalVoteCount(actInstId, nodeId);
   }
 
   public Integer getAgreeVoteCount(String actInstId, String nodeId)
   {
     return this.dao.getAgreeVoteCount(actInstId, nodeId);
   }
 
   public Integer getRefuseVoteCount(String actInstId, String nodeId)
   {
     return this.dao.getRefuseVoteCount(actInstId, nodeId);
   }
 
   public Integer getAbortVoteCount(String actInstId, String nodeId)
   {
     return this.dao.getAbortVoteCount(actInstId, nodeId);
   }
 
   public void batchUpdateCompleted(String actInstId, String nodeId)
   {
     this.dao.batchUpdateCompleted(actInstId, nodeId);
   }
 
   public void addSign(String userIds, String taskId)
   {
     TaskEntity taskEntity = this.bpmService.getTask(taskId);
     ExecutionEntity executionEntity = this.bpmService.getExecution(taskEntity.getExecutionId());
     ProcessDefinitionEntity proDefEntity = this.bpmService.getProcessDefinitionByProcessInanceId(executionEntity.getProcessInstanceId());
 
     ActivityImpl actImpl = proDefEntity.findActivity(executionEntity.getActivityId());
 
     String multiInstance = (String)actImpl.getProperty("multiInstance");
 
     Integer maxSignNums = this.dao.getMaxSignNums(executionEntity.getProcessInstanceId(), executionEntity.getActivityId(), TaskSignData.NOT_COMPLETED);
     this.log.debug("multiInstance:" + multiInstance);
 
     String[] uIds = userIds.split("[,]");
     if (uIds != null)
     {
       Integer signNums = Integer.valueOf(maxSignNums.intValue() == 0 ? 1 : maxSignNums.intValue());
 
       Integer nrOfInstances = (Integer)this.runtimeService.getVariable(executionEntity.getId(), "nrOfInstances");
       if (nrOfInstances != null)
       {
         this.runtimeService.setVariable(executionEntity.getId(), "nrOfInstances", Integer.valueOf(nrOfInstances.intValue() + uIds.length));
       }
 
       List uidlist = new ArrayList();
       if (userIds.length() > 0)
       {
         List<TaskSignData> existTaskSignDatas = this.dao.getExistUserTaskSign(taskEntity.getProcessInstanceId(), taskEntity.getTaskDefinitionKey());
 
         for (TaskSignData taskSignData : existTaskSignDatas) {
           uidlist.add(taskSignData.getVoteUserId());
         }
       }
 
       for (int i = 0; i < uIds.length; i++)
       {
         if (!uidlist.contains(new Long(uIds[i]))) {
           Long dataId = null;
           TaskSignData newSignData = new TaskSignData();
           try {
             dataId = Long.valueOf(UniqueIdUtil.genId());
           } catch (Exception ex) {
             this.log.error(ex.getMessage());
           }
 
           ProcessTask newProcessTask = this.bpmService.newTask(taskId, uIds[i]);
           newSignData.setTaskId(newProcessTask.getId());
 
           newSignData.setDataId(dataId);
           newSignData.setNodeId(executionEntity.getActivityId());
           newSignData.setNodeName(taskEntity.getName());
           newSignData.setActInstId(taskEntity.getProcessInstanceId());
           newSignData.setSignNums(signNums);
           newSignData.setIsCompleted(TaskSignData.NOT_COMPLETED);
           newSignData.setIsAgree(null);
           newSignData.setContent(null);
           newSignData.setVoteTime(null);
           newSignData.setVoteUserId(new Long(uIds[i]));
 
           SysUser sysUser = (SysUser)this.sysUserService.getById(new Long(uIds[i]));
           newSignData.setVoteUserName(sysUser.getFullname());
 
           this.dao.add(newSignData);
         }
       }
     }
   }
 }

