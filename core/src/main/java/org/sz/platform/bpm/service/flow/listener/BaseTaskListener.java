 package org.sz.platform.bpm.service.flow.listener;
 
 import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.core.engine.GroovyScriptEngine;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.platform.bpm.model.flow.BpmNodeScript;
import org.sz.platform.bpm.service.flow.BpmNodeScriptService;
 
 public abstract class BaseTaskListener
   implements TaskListener
 {
   protected Logger logger = LoggerFactory.getLogger(BaseTaskListener.class);
 
   public void notify(DelegateTask delegateTask)
   {
     this.logger.debug("enter the baseTaskListener notify method...");
 
     TaskEntity taskEnt = (TaskEntity)delegateTask;
     String nodeId = taskEnt.getExecution().getActivityId();
     String actDefId = taskEnt.getProcessDefinitionId();
 
     execute(delegateTask, actDefId, nodeId);
 
     int scriptType = getScriptType();
 
     exeEventScript(delegateTask, scriptType, actDefId, nodeId);
   }
 
   protected abstract void execute(DelegateTask paramDelegateTask, String paramString1, String paramString2);
 
   protected abstract int getScriptType();
 
   private void exeEventScript(DelegateTask delegateTask, int scriptType, String actDefId, String nodeId)
   {
     this.logger.debug("enter the baseTaskListener exeEventScript method...");
     BpmNodeScriptService bpmNodeScriptService = (BpmNodeScriptService)ContextUtil.getBean("bpmNodeScriptService");
     BpmNodeScript model = bpmNodeScriptService.getScriptByType(nodeId, actDefId, Integer.valueOf(scriptType));
     if (model == null) return;
 
     String script = model.getScript();
     if (StringUtil.isEmpty(script)) return;
 
     GroovyScriptEngine scriptEngine = (GroovyScriptEngine)ContextUtil.getBean("scriptEngine");
     Map vars = delegateTask.getVariables();
     vars.put("task", delegateTask);
 
     scriptEngine.execute(script, vars);
   }
 }