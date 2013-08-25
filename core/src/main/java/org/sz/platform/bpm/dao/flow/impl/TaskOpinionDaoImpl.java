 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.TaskOpinionDao;
import org.sz.platform.bpm.model.flow.TaskOpinion;
 
 @Repository("taskOpinionDao")
 public class TaskOpinionDaoImpl extends BaseDaoImpl<TaskOpinion> implements TaskOpinionDao
 {
   public Class getEntityClass()
   {
     return TaskOpinion.class;
   }
 
   public TaskOpinion getByTaskId(Long taskId)
   {
     return (TaskOpinion)getUnique("getByTaskId", taskId);
   }
 
   public List<TaskOpinion> getByActInstId(String actInstId)
   {
     return getBySqlKey("getByActInstId", actInstId);
   }
 
   public void delByActDefIdTaskOption(String actDefId)
   {
     delBySqlKey("delByActDefIdTaskOption", actDefId);
   }
 
   public void delByTaskId(Long taskId)
   {
     Map params = new HashMap();
     params.put("delByTaskId", taskId);
     delBySqlKey("delByTaskId", params);
   }
 
   public List<TaskOpinion> getByActInstIdTaskKey(String actInstId, String taskKey)
   {
     Map params = new HashMap();
     params.put("actInstId", actInstId);
     params.put("taskKey", taskKey);
     return getBySqlKey("getByActInstIdTaskKey", params);
   }
 
   public List<TaskOpinion> getByActInstIdExeUserId(String actInstId, Long exeUserId)
   {
     Map params = new HashMap();
     params.put("actInstId", actInstId);
     params.put("exeUserId", exeUserId);
     return getBySqlKey("getByActInstIdExeUserId", params);
   }
 
   public List<TaskOpinion> getFormOptionsByInstance(String instanceId)
   {
     return getBySqlKey("getFormOptionsByInstance", instanceId);
   }
 }

