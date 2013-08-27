 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.dao.flow.BpmAgentDao;
import org.sz.platform.bpm.model.flow.BpmAgent;
import org.sz.platform.bpm.service.flow.BpmAgentService;
 
 @Service("bpmAgentService")
 public class BpmAgentServiceImpl extends BaseServiceImpl<BpmAgent> implements BpmAgentService
 {
 
   @Resource
   private BpmAgentDao dao;
 
   protected IEntityDao<BpmAgent, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public List<BpmAgent> getByAgentId(Long agentid) {
     return this.dao.getByAgentId(agentid);
   }
 
   public List<String> getNotInByAgentId(Long agentid) {
     return this.dao.getNotInByAgentId(agentid);
   }
 }

