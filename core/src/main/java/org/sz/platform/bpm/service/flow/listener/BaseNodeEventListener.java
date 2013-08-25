package org.sz.platform.bpm.service.flow.listener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sz.core.engine.GroovyScriptEngine;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.platform.bpm.model.flow.BpmNodeScript;
import org.sz.platform.bpm.service.flow.BpmNodeScriptService;

public abstract class BaseNodeEventListener
  implements ExecutionListener
{
  private Log logger = LogFactory.getLog(GroovyScriptEngine.class);

  public void notify(DelegateExecution execution) throws Exception {
    this.logger.debug("enter the node event listener.." + execution.getId());

    ExecutionEntity ent = (ExecutionEntity)execution;

    if (ent.getActivityId() == null) return;

    String actDefId = ent.getProcessDefinitionId();
    String nodeId = ent.getActivityId();

    execute(execution, actDefId, nodeId);

    Integer scriptType = getScriptType();

    exeEventScript(execution, scriptType.intValue(), actDefId, nodeId);
  }

  protected abstract void execute(DelegateExecution paramDelegateExecution, String paramString1, String paramString2);

  protected abstract Integer getScriptType();

  private void exeEventScript(DelegateExecution execution, int scriptType, String actDefId, String nodeId)
  {
    BpmNodeScriptService bpmNodeScriptService = (BpmNodeScriptService)ContextUtil.getBean("bpmNodeScriptService");
    BpmNodeScript model = bpmNodeScriptService.getScriptByType(nodeId, actDefId, Integer.valueOf(scriptType));
    if (model == null) return;

    String script = model.getScript();
    if (StringUtil.isEmpty(script)) return;

    GroovyScriptEngine scriptEngine = (GroovyScriptEngine)ContextUtil.getBean("scriptEngine");
    Map vars = execution.getVariables();
    vars.put("execution", execution);
    scriptEngine.execute(script, vars);

    this.logger.debug("execution script :" + script);
  }
}
