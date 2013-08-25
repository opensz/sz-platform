 package org.sz.platform.bpm.service.flow.impl;
 
 import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.sz.core.engine.GroovyScriptEngine;
import org.sz.core.util.BeanUtils;
import org.sz.platform.bpm.model.flow.BpmNodeRule;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.service.flow.BpmNodeRuleService;
import org.sz.platform.bpm.service.flow.JumpRule;
 
 @Service("jumpRule")
 public class JumpRuleImpl implements JumpRule
 {
   private Logger logger = LoggerFactory.getLogger(JumpRuleImpl.class);
 
   @Resource
   private BpmNodeRuleService bpmNodeRuleService;
 
   @Resource
   GroovyScriptEngine scriptEngine;
 
   @Resource
   RuntimeService runtimeService;
 
   public String evaluate(ExecutionEntity execution, Short isJumpForDef) { this.logger.debug("enter the rule decision");
 
     String activityId = execution.getActivityId();
     String actDefId = execution.getProcessDefinitionId();
 
     List<BpmNodeRule> ruleList = this.bpmNodeRuleService.getByDefIdNodeId(actDefId, activityId);
 
     Map vars = new HashMap();
     vars.putAll(execution.getVariables());
 
     if (BeanUtils.isEmpty(ruleList)) {
       return "";
     }
 
     for (BpmNodeRule nodeRule : ruleList) {
       try {
         Boolean rtn = Boolean.valueOf(this.scriptEngine.executeBoolean(nodeRule.getConditionCode(), vars));
 
         if (this.scriptEngine.hasErrors()) {
           this.logger.debug("hasErrors: " + this.scriptEngine.getErrorMessage());
           continue;
         }
 
         if (rtn != null)
         {
           if (rtn.booleanValue()) {
             this.logger.debug("the last rule decision is " + nodeRule.getTargetNode());
             return nodeRule.getTargetNode();
           }
         }
         else
         {
           this.logger.debug("条件表达式返回为空，请使用return 语句返回!");
         }
 
       }
       catch (Exception ex)
       {
         this.logger.debug("error message: " + ex.getMessage());
       }
     }
     if ((ruleList.size() > 0) && (BpmNodeSet.RULE_INVALID_NO_NORMAL.equals(isJumpForDef))) return "_RULE_INVALID";
     return "";
   }
 }

