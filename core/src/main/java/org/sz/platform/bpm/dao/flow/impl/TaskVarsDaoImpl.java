 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.core.query.QueryFilter;
import org.sz.platform.bpm.dao.flow.TaskVarsDao;
import org.sz.platform.bpm.model.flow.TaskVars;
 
 @Repository("taskVarsDao")
 public class TaskVarsDaoImpl extends BaseDaoImpl<TaskVars> implements TaskVarsDao
 {
   public Class<TaskVars> getEntityClass()
   {
     return TaskVars.class;
   }
 
   public List<TaskVars> getTaskVars(QueryFilter queryFilter)
   {
     return getBySqlKey("getTaskVars", queryFilter);
   }
 }

