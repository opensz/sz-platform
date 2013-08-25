 package org.sz.platform.bpm.service.flow.impl;
 
  import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.BpmDefRightsDao;
import org.sz.platform.bpm.model.flow.BpmDefRights;
import org.sz.platform.bpm.service.flow.BpmDefRightsService;
 
 @Service("bpmDefRightsService")
 public class BpmDefRightsServiceImpl extends BaseServiceImpl<BpmDefRights> implements BpmDefRightsService
 {
 
   @Resource
   private BpmDefRightsDao dao;
 
   protected IEntityDao<BpmDefRights, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public List<BpmDefRights> getDefRight(Long defId, Short rightType)
   {
     return this.dao.getDefRight(defId, rightType);
   }
 
   public List<BpmDefRights> getTypeRight(Long typeId, Short rightType)
   {
     return this.dao.getTypeRight(typeId, rightType);
   }
 
   public Map<String, Map<String, String>> getRights(Long assignId, int assignType)
   {
     Map rightsMap = new HashMap();
     List<BpmDefRights> list = null;
     if (assignType == 0) {
       list = this.dao.getByDefId(assignId);
     }
     else {
       list = this.dao.getByTypeId(assignId);
     }
 
     List user = new ArrayList();
     List role = new ArrayList();
     List org = new ArrayList();
     for (BpmDefRights rights : list) {
       switch (rights.getRightType().shortValue()) {
       case 1:
         user.add(rights);
         break;
       case 2:
         role.add(rights);
         break;
       default:
         org.add(rights);
       }
     }
 
     Map userMap = coverList2Map(user);
     Map roleMap = coverList2Map(role);
     Map orgMap = coverList2Map(org);
     rightsMap.put("user", userMap);
     rightsMap.put("role", roleMap);
     rightsMap.put("org", orgMap);
     return rightsMap;
   }
 
   public Map<String, String> coverList2Map(List<BpmDefRights> list)
   {
     Map m = new HashMap();
     if (BeanUtils.isEmpty(list)) return m;
 
     String ownerId = "";
     String ownerName = "";
     for (BpmDefRights r : list) {
       ownerId = ownerId + r.getOwnerId() + ",";
       ownerName = ownerName + r.getOwnerName() + ",";
     }
     if (ownerId.length() > 0) ownerId = ownerId.substring(0, ownerId.length() - 1);
     if (ownerName.length() > 0) ownerName = ownerName.substring(0, ownerName.length() - 1);
     m.put("ownerId", ownerId);
     m.put("ownerName", ownerName);
 
     return m;
   }
 
   public void saveRights(Long assignId, int assignType, String[] rightType, String[] ownerId, String[] ownerName) throws Exception {
     if (assignType == 0) {
       this.dao.delByDefId(assignId);
     }
     else {
       this.dao.delByTypeId(assignId);
     }
     List rightList = coverTypeRights(assignId, assignType, rightType, ownerId, ownerName);
     add(rightList);
   }
 
   public void add(List<BpmDefRights> rightList)
   {
     if ((rightList == null) || (rightList.size() == 0)) return;
     for (BpmDefRights r : rightList)
       this.dao.add(r);
   }
 
   private List<BpmDefRights> coverTypeRights(Long assignId, int assignType, String[] rightType, String[] ownerId, String[] ownerName)
     throws Exception
   {
     if ((ownerId == null) || (ownerId.length == 0)) return null;
 
     List list = new ArrayList();
 
     for (int i = 0; i < rightType.length; i++) {
       String right = rightType[i];
       String[] ids = ownerId[i].split(",");
       String[] names = ownerName[i].split(",");
       if (BeanUtils.isEmpty(ids))
         continue;
       for (int j = 0; j < ids.length; j++) {
         String id = ids[j];
         String name = names[j];
         if (StringUtil.isEmpty(id))
           continue;
         BpmDefRights defRight = new BpmDefRights();
         defRight.setId(Long.valueOf(UniqueIdUtil.genId()));
         if (assignType == 0) {
           defRight.setDefId(assignId);
           defRight.setSearchType((short)0);
         }
         else {
           defRight.setFlowTypeId(assignId);
           defRight.setSearchType((short)1);
         }
         defRight.setRightType(new Short(right));
         defRight.setOwnerId(new Long(id));
         defRight.setOwnerName(name);
         list.add(defRight);
       }
     }
 
     return list;
   }
 }

