 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.stereotype.Service;
import org.sz.core.bpm.engine.BaseProcessService;
import org.sz.platform.bpm.dao.flow.BpmDefVarDao;
import org.sz.platform.bpm.model.flow.BpmDefVar;
import org.sz.platform.bpm.service.flow.IBpmActService;
import org.sz.platform.bpm.service.flow.cmd.EndProcessCmd;
import org.sz.platform.bpm.service.flow.cmd.GetExecutionCmd;
 
 @Service
 public class BpmActService extends BaseProcessService
   implements IBpmActService
 {
 
   @Resource
   private BpmDefVarDao dao;
 
   public List<BpmDefVar> getVarsByFlowDefId(Long defId)
   {
     return this.dao.getVarsByFlowDefId(defId);
   }
 
   public ExecutionEntity getExecution(String executionId)
   {
     return (ExecutionEntity)this.commandExecutor.execute(new GetExecutionCmd(executionId));
   }
 
   public void endProcessByTaskId(String taskId)
   {
     EndProcessCmd cmd = new EndProcessCmd(taskId);
     this.commandExecutor.execute(cmd);
   }
 }

