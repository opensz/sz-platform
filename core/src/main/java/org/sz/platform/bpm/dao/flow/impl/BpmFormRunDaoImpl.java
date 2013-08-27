 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.BpmFormRunDao;
import org.sz.platform.bpm.model.flow.BpmFormRun;
 
 @Repository("bpmFormRunDao")
 public class BpmFormRunDaoImpl extends BaseDaoImpl<BpmFormRun> implements BpmFormRunDao
 {
   public Class getEntityClass()
   {
     return BpmFormRun.class;
   }
 
   public BpmFormRun getByInstanceAndNode(String actInstanceId, String actNodeId)
   {
     Map params = new HashMap();
     params.put("actInstanceId", actInstanceId);
     params.put("actNodeId", actNodeId);
     return (BpmFormRun)getUnique("getByInstanceAndNode", params);
   }
 
   public BpmFormRun getGlobalForm(String actInstanceId)
   {
     return (BpmFormRun)getUnique("getGlobalForm", actInstanceId);
   }
 
   public void delByInstanceId(String actInstanceId)
   {
     delBySqlKey("delByInstanceId", actInstanceId);
   }
 }

