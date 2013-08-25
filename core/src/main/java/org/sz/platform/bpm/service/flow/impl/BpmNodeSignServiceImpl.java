 package org.sz.platform.bpm.service.flow.impl;
 
  import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.dao.flow.BpmNodeSignDao;
import org.sz.platform.bpm.model.flow.BpmNodeSign;
import org.sz.platform.bpm.service.flow.BpmNodeSignService;
 
 @Service("bpmNodeSignService")
 public class BpmNodeSignServiceImpl extends BaseServiceImpl<BpmNodeSign> implements BpmNodeSignService
 {
 
   @Resource
   private BpmNodeSignDao dao;
 
   protected IEntityDao<BpmNodeSign, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public BpmNodeSign getByDefIdAndNodeId(String actDefId, String nodeId)
   {
     return this.dao.getByDefIdAndNodeId(actDefId, nodeId);
   }
 }

