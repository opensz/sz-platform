 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.BpmNodeSetDao;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
 
 @Repository("bpmNodeSetDao")
 public class BpmNodeSetDaoImpl extends BaseDaoImpl<BpmNodeSet> implements BpmNodeSetDao
 {
   public Class getEntityClass()
   {
     return BpmNodeSet.class;
   }
 
   public List<BpmNodeSet> getByDefId(Long defId)
   {
     return this.getByDefId(defId, (short)0);
   }
   
   public List<BpmNodeSet> getByDefId(Long defId, Short setType)
   {
	   Map<String, Object> params = new HashMap<String, Object>();
	   params.put("setType", setType);
	   params.put("defId", defId);
	   return getBySqlKey("getByDefId", params);
   }   
 
   public BpmNodeSet getByDefIdNodeId(Long defId, String nodeId)
   {
     Map params = new HashMap();
     params.put("defId", defId);
     params.put("nodeId", nodeId);
 
     return (BpmNodeSet)getUnique("getByDefIdNodeId", params);
   }
 
   public BpmNodeSet getByActDefIdNodeId(String actDefId, String nodeId)
   {
     Map params = new HashMap();
     params.put("actDefId", actDefId);
     params.put("nodeId", nodeId);
 
     return (BpmNodeSet)getUnique("getByActDefIdNodeId", params);
   }
 
   public BpmNodeSet getByActDefIdJoinTaskKey(String actDefId, String joinTaskKey)
   {
     Map params = new HashMap();
     params.put("actDefId", actDefId);
     params.put("joinTaskKey", joinTaskKey);
     return (BpmNodeSet)getUnique("getByActDefIdJoinTaskKey", params);
   }
 
   public void delByDefId(Long defId)
   {
     delBySqlKey("delByDefId", defId);
   }
 
   public BpmNodeSet getBySetType(Long defId, Short setType)
   {
     Map params = new HashMap();
     params.put("defId", defId);
     params.put("setType", setType);
     return (BpmNodeSet)getUnique("getBySetType", params);
   }
 
   public List<BpmNodeSet> getByStartGlobal(Long defId)
   {
     List list = getBySqlKey("getByStartGlobal", defId);
     return list;
   }
 
   public void delByStartGlobalDefId(Long defId)
   {
     delBySqlKey("delByStartGlobalDefId", defId);
   }
 
   public Map<String, BpmNodeSet> getMapByDefId(Long defId)
   {
     Map map = new HashMap();
     List<BpmNodeSet> list = getByDefId(defId);
     for (BpmNodeSet bpmNodeSet : list) {
       map.put(bpmNodeSet.getNodeId(), bpmNodeSet);
     }
     return map;
   }
 
   public List<BpmNodeSet> getByActDefId(String actDefId)
   {
     return getBySqlKey("getByActDefId", actDefId);
   }
 
   public void updateIsJumpForDef(String nodeId, String actDefId, Short isJumpForDef)
   {
     Map params = new HashMap();
     params.put("isJumpForDef", isJumpForDef);
     params.put("nodeId", nodeId);
     params.put("actDefId", actDefId);
     update("updateIsJumpForDef", params);
   }
 }

