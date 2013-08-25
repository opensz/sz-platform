 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.BpmNodeSignDao;
import org.sz.platform.bpm.model.flow.BpmNodeSign;
 
 @Repository("bpmNodeSignDao")
 public class BpmNodeSignDaoImpl extends BaseDaoImpl<BpmNodeSign> implements BpmNodeSignDao
 {
   public Class getEntityClass()
   {
     return BpmNodeSign.class;
   }
 
   public BpmNodeSign getByDefIdAndNodeId(String actDefId, String nodeId)
   {
     Map map = new HashMap();
     map.put("actDefId", actDefId);
     map.put("nodeId", nodeId);
     BpmNodeSign model = (BpmNodeSign)getUnique("getByDefIdAndNodeId", map);
     return model;
   }
 
   public List<BpmNodeSign> getByActDefId(String actDefId, String nodeId)
   {
     Map map = new HashMap();
     map.put("actDefId", actDefId);
     map.put("nodeId", nodeId);
     return getBySqlKey("getByDefIdAndNodeId", map);
   }
 
   public void delActDefId(String actDefId)
   {
     delBySqlKey("delByActDefId", actDefId);
   }
 }

