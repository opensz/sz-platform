 package org.sz.platform.bpm.dao.flow.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.BpmBusLinkDataDao;
import org.sz.platform.bpm.model.flow.BpmBusLinkData;
 
@Repository("bpmBusLinkDataDao")
 public class BpmBusLinkDataDaoImpl extends BaseDaoImpl<BpmBusLinkData> implements BpmBusLinkDataDao
 {
   public Class getEntityClass()
   {
     return BpmBusLinkData.class;
   }
 
   public void delByActDefIdLinkData(String actDefId)
   {
     delBySqlKey("delByActDefIdLinkData", actDefId);
   }
 }

