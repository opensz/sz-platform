 package org.sz.platform.bpm.model.flow;
 
 import java.util.ArrayList;
import java.util.List;

import org.sz.platform.bpm.model.flow.TaskOpinion;
 
 public class TaskNodeStatus
 {
   private String actInstId;
   private String taskKey;
   private Short lastCheckStatus = TaskOpinion.STATUS_INIT;
 
   private List<TaskOpinion> taskOpinionList = new ArrayList();
 
   public void setLastCheckStatus(Short lastCheckStatus)
   {
     this.lastCheckStatus = lastCheckStatus;
   }
 
   public TaskNodeStatus()
   {
   }
 
   public String getActInstId() {
     return this.actInstId;
   }
 
   public void setActInstId(String actInstId) {
     this.actInstId = actInstId;
   }
 
   public String getTaskKey() {
     return this.taskKey;
   }
 
   public void setTaskKey(String taskKey) {
     this.taskKey = taskKey;
   }
 
   public List<TaskOpinion> getTaskOpinionList() {
     return this.taskOpinionList;
   }
 
   public void setTaskOpinionList(List<TaskOpinion> taskOpinionList) {
     this.taskOpinionList = taskOpinionList;
   }
 
   public TaskNodeStatus(String actInstId, String taskKey, Short lastCheckStatus, List<TaskOpinion> taskOpinionList)
   {
     this.actInstId = actInstId;
     this.taskKey = taskKey;
     this.lastCheckStatus = lastCheckStatus;
     this.taskOpinionList = taskOpinionList;
   }
 
   public Short getLastCheckStatus() {
     return this.lastCheckStatus;
   }
 }

