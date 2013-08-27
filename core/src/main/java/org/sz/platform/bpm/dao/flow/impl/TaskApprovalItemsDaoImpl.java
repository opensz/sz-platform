 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.TaskApprovalItemsDao;
import org.sz.platform.bpm.model.flow.TaskApprovalItems;
 
 @Repository("taskApprovalItemsDao")
 public class TaskApprovalItemsDaoImpl extends BaseDaoImpl<TaskApprovalItems> implements TaskApprovalItemsDao
 {
   public Class getEntityClass()
   {
     return TaskApprovalItems.class;
   }
 
   public TaskApprovalItems getFlowApproval(String actDefId, int isGlobal)
   {
     Map map = new HashMap();
     map.put("actDefId", actDefId);
     map.put("isGlobal", Integer.valueOf(isGlobal));
     return (TaskApprovalItems)getUnique("getFlowApproval", map);
   }
 
   public TaskApprovalItems getTaskApproval(String actDefId, String nodeId, int isGlobal)
   {
     Map map = new HashMap();
     map.put("actDefId", actDefId);
     map.put("nodeId", nodeId);
     map.put("isGlobal", Integer.valueOf(isGlobal));
     return (TaskApprovalItems)getUnique("getTaskApproval", map);
   }
 
   public void delFlowApproval(String actDefId, int isGlobal)
   {
     Map map = new HashMap();
     map.put("actDefId", actDefId);
     map.put("isGlobal", Integer.valueOf(isGlobal));
     delBySqlKey("delFlowApproval", map);
   }
 
   public void delTaskApproval(String actDefId, String nodeId, int isGlobal)
   {
     Map map = new HashMap();
     map.put("actDefId", actDefId);
     map.put("nodeId", nodeId);
     map.put("isGlobal", Integer.valueOf(isGlobal));
     delBySqlKey("delTaskApproval", map);
   }
 
   public List<TaskApprovalItems> getApprovalByActDefId(String actDefId, String nodeId)
   {
     Map map = new HashMap();
     map.put("actDefId", actDefId);
     map.put("nodeId", nodeId);
     map.put("isGlobal", TaskApprovalItems.global);
     map.put("notGlobal", TaskApprovalItems.notGlobal);
     return getBySqlKey("getApprovalByActDefId", map);
   }
 }

