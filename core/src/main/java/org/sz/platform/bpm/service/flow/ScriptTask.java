package org.sz.platform.bpm.service.flow;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.sz.core.bpm.util.BpmConst;
import org.sz.core.engine.GroovyScriptEngine;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.platform.bpm.model.flow.BpmNodeScript;

@Service
public class ScriptTask implements JavaDelegate {
	private Log logger = LogFactory.getLog(GroovyScriptEngine.class);

	public void execute(DelegateExecution execution) throws Exception {
		ExecutionEntity ent = (ExecutionEntity) execution;
		String nodeId = ent.getActivityId();
		String actDefId = ent.getProcessDefinitionId();

		BpmNodeScriptService bpmNodeScriptService = (BpmNodeScriptService) ContextUtil
				.getBean("bpmNodeScriptService");

		BpmNodeScript model = bpmNodeScriptService.getScriptByType(nodeId,
				actDefId, BpmConst.ScriptNodeScript);
		if (model == null)
			return;

		String script = model.getScript();
		if (StringUtil.isEmpty(script))
			return;

		GroovyScriptEngine scriptEngine = (GroovyScriptEngine) ContextUtil
				.getBean("scriptEngine");
		Map vars = execution.getVariables();
		vars.put("execution", execution);
		scriptEngine.execute(script, vars);

		this.logger.debug("execution script :" + script);
	}
}
