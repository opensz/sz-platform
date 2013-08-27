 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.BpmNodeMessageDao;
import org.sz.platform.bpm.model.flow.BpmNodeMessage;
 
 @Repository("bpmNodeMessageDao")
 public class BpmNodeMessageDaoImpl extends BaseDaoImpl<BpmNodeMessage> implements BpmNodeMessageDao
 {
   public Class getEntityClass()
   {
     return BpmNodeMessage.class;
   }
 
   public List<BpmNodeMessage> getMessageByActDefIdNodeId(String actDefId, String nodeId)
   {
     Map params = new HashMap();
     params.put("actDefId", actDefId);
     params.put("nodeId", nodeId);
     return getBySqlKey("getMessageByActDefIdNodeId", params);
   }
 
   public void delByActDefId(String actDefId)
   {
     delBySqlKey("delByActDefId", actDefId);
   }
   public List<BpmNodeMessage> getByActDefId(String actDefId) {
     return getBySqlKey("getByActDefId", actDefId);
   }
 }

