 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.dao.flow.TaskVarsDao;
import org.sz.platform.bpm.model.flow.TaskVars;
import org.sz.platform.bpm.service.flow.TaskVarsService;
 
 @Service("taskVarsService")
 public class TaskVarsServiceImpl extends BaseServiceImpl<TaskVars> implements TaskVarsService
 {
 
   @Resource
   private TaskVarsDao dao;
 
   protected IEntityDao<TaskVars, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public List<TaskVars> getVars(QueryFilter queryFilter)
   {
     return this.dao.getTaskVars(queryFilter);
   }
 }

