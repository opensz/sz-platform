 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.TaskForkDao;
import org.sz.platform.bpm.model.flow.TaskFork;
 
 @Repository("taskForkDao")
 public class TaskForkDaoImpl extends BaseDaoImpl<TaskFork> implements TaskForkDao
 {
   public Class getEntityClass()
   {
     return TaskFork.class;
   }
 
   public Integer getMaxSn(String actInstId)
   {
     Map params = new HashMap();
     params.put("actInstId", actInstId);
     Integer sn = (Integer)getOne("getMaxSn", params);
     return sn;
   }
 
   public TaskFork getByInstIdJoinTaskKey(String actInstId, String joinTaskKey)
   {
     Map params = new HashMap();
     params.put("actInstId", actInstId);
     params.put("joinTaskKey", joinTaskKey);
     TaskFork taskFork = (TaskFork)getUnique("getByInstIdJoinTaskKey", params);
     return taskFork;
   }
 
   public TaskFork getByInstIdJoinTaskKeyForkToken(String actInstId, String joinTaskKey, String forkToken)
   {
     Map params = new HashMap();
     params.put("actInstId", actInstId);
     params.put("joinTaskKey", joinTaskKey);
     params.put("forkToken", forkToken);
     return (TaskFork)getUnique("getByInstIdJoinTaskKeyForkToken", params);
   }
 }

