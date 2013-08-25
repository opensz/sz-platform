 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.ReminderStateDao;
import org.sz.platform.bpm.model.flow.ReminderState;
 
 @Repository("reminderStateDao")
 public class ReminderStateDaoImpl extends BaseDaoImpl<ReminderState> implements ReminderStateDao
 {
   public Class getEntityClass()
   {
     return ReminderState.class;
   }
 
   public Integer getAmountByUserTaskId(long taskId, long userId, int remindType)
   {
     Map params = new HashMap();
     params.put("taskId", Long.valueOf(taskId));
     params.put("userId", Long.valueOf(userId));
     params.put("remindType", Integer.valueOf(remindType));
 
     Integer rtn = (Integer)getOne("getAmountByUserTaskId", params);
     return rtn;
   }
 
   public void delExpiredTaskReminderState()
   {
     delBySqlKey("delExpiredTaskReminderState", null);
   }
 }

