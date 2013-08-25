 package org.sz.platform.bpm.service.flow.listener;
 
 import org.sz.platform.bpm.service.flow.listener.BaseNodeEventListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.sz.core.bpm.util.BpmConst;
 
 public class StartEventListener extends BaseNodeEventListener
 {
   protected void execute(DelegateExecution execution, String actDefId, String nodeId)
   {
   }
 
   protected Integer getScriptType()
   {
     return BpmConst.StartScript;
   }
 }
