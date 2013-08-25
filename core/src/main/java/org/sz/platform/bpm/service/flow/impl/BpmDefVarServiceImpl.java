 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.bpm.dao.flow.BpmDefVarDao;
import org.sz.platform.bpm.model.flow.BpmDefVar;
import org.sz.platform.bpm.service.flow.BpmDefVarService;
 
 @Service("bpmDefVarService")
 public class BpmDefVarServiceImpl extends BaseServiceImpl<BpmDefVar> implements BpmDefVarService
 {
 
   @Resource
   private BpmDefVarDao dao;
 
   protected IEntityDao<BpmDefVar, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public boolean isVarNameExist(String varName, String varKey, Long defId) {
     return this.dao.isVarNameExist(varName, varKey, defId);
   }
 
   public List<BpmDefVar> getByDeployAndNode(String deployId, String nodeId)
   {
     return this.dao.getByDeployAndNode(deployId, nodeId);
   }
 
   public List<BpmDefVar> getVarsByFlowDefId(long defId)
   {
     return this.dao.getVarsByFlowDefId(Long.valueOf(defId));
   }
 }

