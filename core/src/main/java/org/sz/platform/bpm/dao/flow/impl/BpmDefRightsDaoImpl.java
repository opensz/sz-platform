 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.BpmDefRightsDao;
import org.sz.platform.bpm.model.flow.BpmDefRights;
 
 @Repository("bpmDefRightsDao")
 public class BpmDefRightsDaoImpl extends BaseDaoImpl<BpmDefRights> implements BpmDefRightsDao
 {
   public Class getEntityClass()
   {
     return BpmDefRights.class;
   }
 
   public List<BpmDefRights> getDefRight(Long defId, Short rightType)
   {
     Map params = new HashMap();
     params.put("defId", defId);
     params.put("rightType", rightType);
     params.put("searchType", 0);
     return getBySqlKey("getDefRight", params);
   }
 
   public List<BpmDefRights> getTypeRight(Long typeId, Short rightType)
   {
     Map params = new HashMap();
     params.put("typeId", typeId);
     params.put("rightType", rightType);
     params.put("searchType", 1);
     return getBySqlKey("getTypeRight", params);
   }
 
   public void delByTypeId(Long typeId)
   {
     getBySqlKey("delByTypeId", typeId);
   }
 
   public void delByDefId(Long defId)
   {
     delBySqlKey("delByDefId", defId);
   }
 
   public List<BpmDefRights> getByDefId(Long defId)
   {
     return getBySqlKey("getByDefId", defId);
   }
 
   public List<BpmDefRights> getByTypeId(Long typeId)
   {
     return getBySqlKey("getByTypeId", typeId);
   }
 }

