 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.BpmNodeRuleDao;
import org.sz.platform.bpm.model.flow.BpmNodeRule;
 
 @Repository("bpmNodeRuleDao")
 public class BpmNodeRuleDaoImpl extends BaseDaoImpl<BpmNodeRule> implements BpmNodeRuleDao
 {
   public Class getEntityClass()
   {
     return BpmNodeRule.class;
   }
 
   public List<BpmNodeRule> getByDefIdNodeId(String actDefId, String nodeId)
   {
     Map params = new HashMap();
     params.put("actDefId", actDefId);
     params.put("nodeId", nodeId);
 
     return getBySqlKey("getByDefIdNodeId", params);
   }
 
   public void reSort(Long ruleId, Long priority)
   {
     Map map = new HashMap();
     map.put("ruleId", ruleId);
     map.put("priority", priority);
     update("reSort", map);
   }
 
   public void delByActDefId(String actDefId)
   {
     delBySqlKey("delByActDefId", actDefId);
   }
 }

