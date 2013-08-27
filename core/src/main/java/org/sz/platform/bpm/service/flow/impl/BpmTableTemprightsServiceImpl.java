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
import org.sz.platform.bpm.dao.form.BpmTableTemprightsDao;
import org.sz.platform.bpm.model.form.BpmTableTemprights;
import org.sz.platform.bpm.service.flow.BpmTableTemprightsService;
 
 @Service("bpmTableTemprightsService")
 public class BpmTableTemprightsServiceImpl extends BaseServiceImpl<BpmTableTemprights> implements BpmTableTemprightsService
 {
 
   @Resource
   private BpmTableTemprightsDao dao;
 
   public Map<String, Map<String, String>> getRights(Long id, int assignType)
   {
     Map rightsMap = new HashMap();
     List<BpmTableTemprights> list = null;
     if (assignType == BpmTableTemprights.SEARCH_TYPE_DEF.shortValue())
       list = this.dao.getByTemplateId(id);
     else {
       list = this.dao.getByCategoryId(id);
     }
     List user = new ArrayList();
     List role = new ArrayList();
     List org = new ArrayList();
     for (BpmTableTemprights rights : list) {
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
 
   private Map<String, String> coverList2Map(List<BpmTableTemprights> list) {
     Map m = new HashMap();
     if (BeanUtils.isEmpty(list)) {
       return m;
     }
     String ownerId = "";
     String ownerName = "";
     for (BpmTableTemprights r : list) {
       ownerId = ownerId + r.getOwnerId() + ",";
       ownerName = ownerName + r.getOwnerName() + ",";
     }
     if (ownerId.length() > 0)
       ownerId = ownerId.substring(0, ownerId.length() - 1);
     if (ownerName.length() > 0)
       ownerName = ownerName.substring(0, ownerName.length() - 1);
     m.put("ownerId", ownerId);
     m.put("ownerName", ownerName);
 
     return m;
   }
 
   public void saveRights(Long id, int assignType, String[] rightType, String[] ownerId, String[] ownerName)
     throws Exception
   {
     if (assignType == 0) {
       this.dao.delByTemplateId(id);
     }
     else {
       this.dao.delByCategoryId(id);
     }
     List rightList = coverTypeRights(id, assignType, rightType, ownerId, ownerName);
     add(rightList);
   }
 
   public void add(List<BpmTableTemprights> rightList)
   {
     if ((rightList == null) || (rightList.size() == 0))
       return;
     for (BpmTableTemprights r : rightList)
       this.dao.add(r);
   }
 
   private List<BpmTableTemprights> coverTypeRights(Long assignId, int assignType, String[] rightType, String[] ownerId, String[] ownerName)
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
         BpmTableTemprights bpmTableTemprights = new BpmTableTemprights();
         bpmTableTemprights.setId(Long.valueOf(UniqueIdUtil.genId()));
         if (assignType == BpmTableTemprights.SEARCH_TYPE_DEF.shortValue()) {
           bpmTableTemprights.setTemplateId(assignId);
           bpmTableTemprights.setSearchType(BpmTableTemprights.SEARCH_TYPE_DEF);
         }
         else {
           bpmTableTemprights.setCategoryId(assignId);
           bpmTableTemprights.setSearchType(BpmTableTemprights.SEARCH_TYPE_GLT);
         }
         bpmTableTemprights.setRightType(new Short(right));
         bpmTableTemprights.setOwnerId(new Long(id));
         bpmTableTemprights.setOwnerName(name);
         list.add(bpmTableTemprights);
       }
     }
     return list;
   }
 
   protected IEntityDao<BpmTableTemprights, Long> getEntityDao()
   {
     return this.dao;
   }
 }

