 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.StringUtil;
import org.sz.platform.bpm.dao.flow.BpmNodeRuleDao;
import org.sz.platform.bpm.model.flow.BpmNodeRule;
import org.sz.platform.bpm.service.flow.BpmNodeRuleService;
 
 @Service("bpmNodeRuleService")
 public class BpmNodeRuleServiceImpl extends BaseServiceImpl<BpmNodeRule> implements BpmNodeRuleService
 {
 
   @Resource
   private BpmNodeRuleDao dao;
 
   protected IEntityDao<BpmNodeRule, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public List<BpmNodeRule> getByDefIdNodeId(String actDefId, String nodeId)
   {
     return this.dao.getByDefIdNodeId(actDefId, nodeId);
   }
 
   public void reSort(String ruleIds)
   {
     if (StringUtil.isEmpty(ruleIds)) return;
     String[] aryRuleIds = ruleIds.split(",");
     for (int i = 0; i < aryRuleIds.length; i++) {
       Long ruleId = Long.valueOf(Long.parseLong(aryRuleIds[i]));
       this.dao.reSort(ruleId, Long.valueOf(i));
     }
   }
 }

