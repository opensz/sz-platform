 package org.sz.platform.bpm.service.flow.impl;
 
  import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.dao.flow.ReminderStateDao;
import org.sz.platform.bpm.model.flow.ReminderState;
import org.sz.platform.bpm.service.flow.ReminderStateService;
 
 @Service("reminderStateService")
 public class ReminderStateServiceImpl extends BaseServiceImpl<ReminderState> implements ReminderStateService
 {
 
   @Resource
   private ReminderStateDao dao;
 
   protected IEntityDao<ReminderState, Long> getEntityDao()
   {
     return this.dao;
   }
 }

