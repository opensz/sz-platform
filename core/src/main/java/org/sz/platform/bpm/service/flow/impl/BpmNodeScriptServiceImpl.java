 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.BpmNodeScriptDao;
import org.sz.platform.bpm.model.flow.BpmNodeScript;
import org.sz.platform.bpm.service.flow.BpmNodeScriptService;
 
 @Service("bpmNodeScriptService")
 public class BpmNodeScriptServiceImpl extends BaseServiceImpl<BpmNodeScript> implements BpmNodeScriptService
 {
 
   @Resource
   private BpmNodeScriptDao dao;
 
   protected IEntityDao<BpmNodeScript, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public List<BpmNodeScript> getByNodeScriptId(String nodeId, String actDefId)
   {
     return this.dao.getByBpmNodeScriptId(nodeId, actDefId);
   }
 
   public Map<String, BpmNodeScript> getMapByNodeScriptId(String nodeId, String actDefId)
   {
     List<BpmNodeScript> list = getByNodeScriptId(nodeId, actDefId);
     Map map = new HashMap();
     for (BpmNodeScript script : list) {
       map.put("type" + script.getScriptType(), script);
     }
 
     return map;
   }
 
   public BpmNodeScript getScriptByType(String nodeId, String actDefId, Integer scriptType)
   {
     return this.dao.getScriptByType(nodeId, actDefId, scriptType);
   }
 
   public void saveScriptDef(String defId, String nodeId, List<BpmNodeScript> list)
     throws Exception
   {
     this.dao.delByDefAndNodeId(defId, nodeId);
 
     for (BpmNodeScript script : list) {
       long id = UniqueIdUtil.genId();
       script.setId(Long.valueOf(id));
       script.setActDefId(defId);
       script.setNodeId(nodeId);
       this.dao.add(script);
     }
   }
 }

