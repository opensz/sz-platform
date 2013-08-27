 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.BpmNodeScriptDao;
import org.sz.platform.bpm.model.flow.BpmNodeScript;
 
 @Repository("bpmNodeScriptDao")
 public class BpmNodeScriptDaoImpl extends BaseDaoImpl<BpmNodeScript> implements BpmNodeScriptDao
 {
   public Class getEntityClass()
   {
     return BpmNodeScript.class;
   }
 
   public List<BpmNodeScript> getByBpmNodeScriptId(String nodeId, String actDefId)
   {
     Map params = new HashMap();
     params.put("actDefId", actDefId);
     params.put("nodeId", nodeId);
 
     return getBySqlKey("getBpmNodeScript", params);
   }
 
   public void delByDefAndNodeId(String actDefId, String nodeId)
   {
     Map params = new HashMap();
     params.put("actDefId", actDefId);
     params.put("nodeId", nodeId);
 
     update("delByDefAndNodeId", params);
   }
 
   public BpmNodeScript getScriptByType(String nodeId, String actDefId, Integer scriptType)
   {
     Map params = new HashMap();
     params.put("actDefId", actDefId);
     params.put("nodeId", nodeId);
     params.put("scriptType", scriptType);
     return (BpmNodeScript)getUnique("getScriptByType", params);
   }
 
   public void delByActDefId(String actDefId)
   {
     delBySqlKey("delByActDefId", actDefId);
   }
 }

