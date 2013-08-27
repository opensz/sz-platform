 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.TaskUserDao;
import org.sz.platform.bpm.model.flow.TaskUser;
 
 @Repository("taskUserDao")
 public class TaskUserDaoImpl extends BaseDaoImpl<TaskUser> implements TaskUserDao
 {
   public Class getEntityClass()
   {
     return TaskUser.class;
   }
 
   public List<TaskUser> getByTaskId(String taskId)
   {
     Map params = new HashMap();
     params.put("taskId", taskId);
     return getBySqlKey("getByTaskId", params);
   }
 }

