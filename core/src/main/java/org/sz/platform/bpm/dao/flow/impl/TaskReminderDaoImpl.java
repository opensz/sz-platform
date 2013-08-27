 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.TaskReminderDao;
import org.sz.platform.bpm.model.flow.TaskReminder;
 
 @Repository("taskReminderDao")
 public class TaskReminderDaoImpl extends BaseDaoImpl<TaskReminder> implements TaskReminderDao
 {
   public Class getEntityClass()
   {
     return TaskReminder.class;
   }
 
   public TaskReminder getByActDefAndNodeId(String actDefId, String nodeId)
   {
     Map map = new HashMap();
     map.put("actDefId", actDefId);
     map.put("nodeId", nodeId);
     TaskReminder obj = (TaskReminder)getOne("getByActDefAndNodeId", map);
     return obj;
   }
 }

