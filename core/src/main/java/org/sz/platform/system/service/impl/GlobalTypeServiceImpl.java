 package org.sz.platform.system.service.impl;
 
  import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.system.dao.DictionaryDao;
import org.sz.platform.system.dao.GlobalTypeDao;
import org.sz.platform.system.dao.SysTypeKeyDao;
import org.sz.platform.system.model.GlobalType;
import org.sz.platform.system.model.SysTypeKey;
import org.sz.platform.system.service.GlobalTypeService;
 
 @Service("globalTypeService")
 public class GlobalTypeServiceImpl extends BaseServiceImpl<GlobalType> implements GlobalTypeService
 {
 
   @Resource
   private GlobalTypeDao globalTypeDao;
 
   @Resource
   private DictionaryDao dictionaryDao;
 
   @Resource
   private SysTypeKeyDao sysTypeKeyDao;
 
   protected IEntityDao<GlobalType, Long> getEntityDao()
   {
     return this.globalTypeDao;
   }
 
   public GlobalType getInitGlobalType(int isRoot, long parentId)
     throws Exception
   {
     GlobalType globalType = new GlobalType();
     Long typeId = Long.valueOf(UniqueIdUtil.genId());
 
     if (isRoot == 1) {
       SysTypeKey sysTypeKey = (SysTypeKey)this.sysTypeKeyDao.getById(Long.valueOf(parentId));
       globalType.setCatKey(sysTypeKey.getTypeKey());
       globalType.setTypeName(sysTypeKey.getTypeName());
       globalType.setParentId(Long.valueOf(parentId));
       globalType.setNodePath(parentId + "." + typeId + ".");
       globalType.setType(sysTypeKey.getType());
     }
     else
     {
       globalType = (GlobalType)this.globalTypeDao.getById(Long.valueOf(parentId));
       String nodePath = globalType.getNodePath();
       globalType.setNodePath(nodePath + typeId + ".");
     }
     globalType.setTypeId(typeId);
     return globalType;
   }
 
   public List<GlobalType> getByParentId(Long parentId)
   {
     return this.globalTypeDao.getByParentId(parentId.longValue());
   }
 
   public void delByTypeId(Long typeId)
   {
     if (BeanUtils.isEmpty(typeId)) return;
 
     GlobalType gt = (GlobalType)this.globalTypeDao.getById(typeId);
 
     String oldNodePath = gt.getNodePath();
 
     List<GlobalType> childrenList = this.globalTypeDao.getByNodePath(oldNodePath);
 
     for (GlobalType globalType : childrenList) {
       long Id = globalType.getTypeId().longValue();
       this.globalTypeDao.delById(Long.valueOf(Id));
 
       this.dictionaryDao.delByTypeId(Long.valueOf(Id));
     }
   }
 
   public List<GlobalType> getByNodePath(String nodePath)
   {
     return this.globalTypeDao.getByNodePath(nodePath);
   }
 
   public List<GlobalType> getByParentId(long parentId)
   {
     return this.globalTypeDao.getByParentId(parentId);
   }
 
   public boolean isNodeKeyExists(String catKey, String nodeKey)
   {
     return this.globalTypeDao.isNodeKeyExists(catKey, nodeKey);
   }
 
   public boolean isNodeKeyExistsForUpdate(Long typeId, String catKey, String nodeKey) {
     return this.globalTypeDao.isNodeKeyExistsForUpdate(typeId, catKey, nodeKey);
   }
 
   public void updSn(Long typeId, Long sn)
   {
     this.globalTypeDao.updSn(typeId, sn);
   }
 
   public void move(Long targetId, Long dragId, String moveType)
   {
     GlobalType target = (GlobalType)this.globalTypeDao.getById(targetId);
     GlobalType dragged = (GlobalType)this.globalTypeDao.getById(dragId);
     String nodePath;
     if (("prev".equals(moveType)) || ("next".equals(moveType))) {
       String targetNodePath = target.getNodePath();
       int idx = targetNodePath.lastIndexOf(target.getTypeId() + ".");
       String basePath = targetNodePath.substring(0, idx);
       String dragedNodePath = basePath + dragId + ".";
       dragged.setNodePath(dragedNodePath);
       dragged.setParentId(target.getParentId());
       if ("prev".equals(moveType)) {
         dragged.setSn(Long.valueOf(target.getSn().longValue() - 1L));
       }
       else {
         dragged.setSn(Long.valueOf(target.getSn().longValue() + 1L));
       }
       this.globalTypeDao.update(dragged);
     }
     else
     {
       nodePath = dragged.getNodePath();
       List<GlobalType> list = getByNodePath(nodePath);
 
       for (GlobalType globalType : list)
       {
         if (globalType.getTypeId().equals(dragId))
         {
           globalType.setParentId(targetId);
 
           globalType.setNodePath(target.getNodePath() + globalType.getTypeId() + ".");
         }
         else
         {
           String path = globalType.getNodePath();
 
           String tmpPath = path.replaceAll(nodePath, "");
 
           String targetPath = target.getNodePath();
 
           String tmp = targetPath + dragged.getTypeId() + "." + tmpPath;
 
           globalType.setNodePath(tmp);
         }
         this.globalTypeDao.update(globalType);
       }
     }
   }
 
   public List<GlobalType> getByCatKey(String catKey, boolean hasRoot)
   {
     List list = this.globalTypeDao.getByCatKey(catKey);
 
     if (hasRoot) {
       SysTypeKey sysTypeKey = this.sysTypeKeyDao.getByKey(catKey);
       GlobalType globalType = new GlobalType();
       globalType.setTypeName(sysTypeKey.getTypeName());
       globalType.setCatKey(sysTypeKey.getTypeKey());
       globalType.setParentId(Long.valueOf(0L));
       globalType.setIsParent("true");
       globalType.setTypeId(sysTypeKey.getTypeId());
       globalType.setType(sysTypeKey.getType());
       globalType.setNodePath(sysTypeKey.getTypeId() + ".");
       list.add(0, globalType);
     }
     return list;
   }
 
   public Set<GlobalType> getByBpmRightCat(Long userId, String roleIds, String orgIds, boolean hasRoot)
   {
     Set globalTypeSet = new LinkedHashSet();
 
     List<GlobalType> globalTypeList = this.globalTypeDao.getByBpmRights("FLOW_TYPE", userId, roleIds, orgIds);
     globalTypeSet.addAll(globalTypeList);
 
     for (GlobalType globalType : globalTypeList) {
       if (StringUtils.isNotEmpty(globalType.getNodePath())) {
         String parentNodePath = globalType.getNodePath();
         int index = parentNodePath.indexOf(globalType.getTypeId().toString());
         if (index != -1) {
           parentNodePath = parentNodePath.substring(0, index);
         }
         String[] nodePaths = parentNodePath.split("[.]");
 
         if (nodePaths.length >= 2) {
           for (int i = 1; i < nodePaths.length; i++) {
             GlobalType parentType = (GlobalType)this.globalTypeDao.getById(new Long(nodePaths[i]));
             globalTypeSet.add(parentType);
           }
         }
       }
     }
 
     if (hasRoot) {
       SysTypeKey sysTypeKey = this.sysTypeKeyDao.getByKey("FLOW_TYPE");
       GlobalType globalType = new GlobalType();
       globalType.setTypeName(sysTypeKey.getTypeName());
       globalType.setCatKey(sysTypeKey.getTypeKey());
       globalType.setParentId(Long.valueOf(0L));
       globalType.setIsParent("true");
       globalType.setTypeId(sysTypeKey.getTypeId());
       globalType.setType(sysTypeKey.getType());
       globalType.setNodePath(sysTypeKey.getTypeId() + ".");
       globalTypeSet.add(globalType);
     }
     return globalTypeSet;
   }
 
   public Set<GlobalType> getByFormRightCat(Long userId, String roleIds, String orgIds, boolean hasRoot)
   {
     Set globalTypeSet = new LinkedHashSet();
 
     List<GlobalType> globalTypeList = this.globalTypeDao.getByFormRights("FORM_TYPE", userId, roleIds, orgIds);
     globalTypeSet.addAll(globalTypeList);
 
     for (GlobalType globalType : globalTypeList) {
       if (StringUtils.isNotEmpty(globalType.getNodePath())) {
         String parentNodePath = globalType.getNodePath();
         int index = parentNodePath.indexOf(globalType.getTypeId().toString());
         if (index != -1) {
           parentNodePath = parentNodePath.substring(0, index);
         }
         String[] nodePaths = parentNodePath.split("[.]");
 
         if (nodePaths.length >= 2) {
           for (int i = 1; i < nodePaths.length; i++) {
             GlobalType parentType = (GlobalType)this.globalTypeDao.getById(new Long(nodePaths[i]));
             globalTypeSet.add(parentType);
           }
         }
       }
     }
 
     if (hasRoot) {
       SysTypeKey sysTypeKey = this.sysTypeKeyDao.getByKey("FORM_TYPE");
       GlobalType globalType = new GlobalType();
       globalType.setTypeName(sysTypeKey.getTypeName());
       globalType.setCatKey(sysTypeKey.getTypeKey());
       globalType.setParentId(Long.valueOf(0L));
       globalType.setIsParent("true");
       globalType.setTypeId(sysTypeKey.getTypeId());
       globalType.setType(sysTypeKey.getType());
       globalType.setNodePath(sysTypeKey.getTypeId() + ".");
       globalTypeSet.add(globalType);
     }
     return globalTypeSet;
   }
 
   public List<GlobalType> getPersonType(String catKey, Long userId, boolean hasRoot)
   {
     List list = this.globalTypeDao.getPersonType(catKey, userId);
 
     if (hasRoot) {
       SysTypeKey sysTypeKey = this.sysTypeKeyDao.getByKey(catKey);
       GlobalType globalType = new GlobalType();
       globalType.setTypeName(sysTypeKey.getTypeName());
       globalType.setCatKey(sysTypeKey.getTypeKey());
       globalType.setParentId(Long.valueOf(0L));
       globalType.setIsParent("true");
       globalType.setTypeId(sysTypeKey.getTypeId());
       globalType.setType(sysTypeKey.getType());
       globalType.setNodePath(sysTypeKey.getTypeId() + ".");
       list.add(0, globalType);
     }
     return list;
   }
 
   public String getXmlByCatkey(String catKEY)
   {
     List<GlobalType> list = this.globalTypeDao.getByCatKey(catKEY);
     SysTypeKey sysTypeKey = this.sysTypeKeyDao.getByKey(catKEY);
     Long typeId = sysTypeKey.getTypeId();
 
     StringBuffer sb = new StringBuffer("<folder id='0' label='全部'>");
     if (BeanUtils.isNotEmpty(list)) {
       for (GlobalType gt : list)
         if (typeId.equals(gt.getParentId())) {
           sb.append("<folder id='" + gt.getTypeId() + "' label='" + gt.getTypeName() + "'>");
           sb.append(getBmpChildList(list, gt.getTypeId()));
           sb.append("</folder>");
         }
     }
     sb.append("</folder>");
     return sb.toString();
   }
 
   private String getBmpChildList(List<GlobalType> list, Long parentId)
   {
     StringBuffer sb = new StringBuffer("");
     if (BeanUtils.isNotEmpty(list)) {
       for (GlobalType gt : list) {
         if (gt.getParentId().equals(parentId)) {
           sb.append("<folder id='" + gt.getTypeId() + "' label='" + gt.getTypeName() + "'>");
           sb.append(getBmpChildList(list, gt.getTypeId()));
           sb.append("</folder>");
         }
       }
     }
     return sb.toString();
   }
 
   public GlobalType getByDictNodeKey(String nodeKey)
   {
     return this.globalTypeDao.getByDictNodeKey(nodeKey);
   }
 
   public List<GlobalType> getByBpmRights(String catKey, Long userId, String roleIds, String orgIds)
   {
     return this.globalTypeDao.getByBpmRights(catKey, userId, roleIds, orgIds);
   }
 }

