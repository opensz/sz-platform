 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.BpmNodeUserUplowDao;
import org.sz.platform.bpm.model.flow.BpmNodeUserUplow;
import org.sz.platform.bpm.service.flow.BpmNodeUserUplowService;
 
 @Service("bpmNodeUserUplowService")
 public class BpmNodeUserUplowServiceImpl extends BaseServiceImpl<BpmNodeUserUplow> implements BpmNodeUserUplowService
 {
 
   @Resource
   private BpmNodeUserUplowDao dao;
 
   protected IEntityDao<BpmNodeUserUplow, Long> getEntityDao()
   {
     return this.dao;
   }
   public void upd(long nodeUserId, List<BpmNodeUserUplow> uplowList) throws Exception {
     this.dao.delByNodeUserId(nodeUserId);
     if ((uplowList != null) && (uplowList.size() > 0))
       for (BpmNodeUserUplow e : uplowList) {
         e.setID(Long.valueOf(UniqueIdUtil.genId()));
         e.setNodeUserId(Long.valueOf(nodeUserId));
         this.dao.add(e);
       }
   }
 
   public List<BpmNodeUserUplow> getByNodeUserId(long userId)
   {
     return this.dao.getByNodeUserId(userId);
   }
 }

