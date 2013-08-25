 package org.sz.platform.system.service.impl;
 
  import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.system.dao.ResourcesDao;
import org.sz.platform.system.dao.ResourcesUrlDao;
import org.sz.platform.system.dao.RoleResourcesDao;
import org.sz.platform.system.dao.SubSystemDao;
import org.sz.platform.system.model.Resources;
import org.sz.platform.system.model.ResourcesUrl;
import org.sz.platform.system.model.RoleResources;
import org.sz.platform.system.model.SubSystem;
import org.sz.platform.system.model.SysRole;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.ResourcesService;
 
 @Service("resourcesService")
 public class ResourcesServiceImpl extends BaseServiceImpl<Resources> implements ResourcesService
 {
 
   @Resource
   private ResourcesDao resourcesDao;
 
   @Resource
   private ResourcesUrlDao resourcesUrlDao;
 
   @Resource
   private SubSystemDao subSystemDao;
 
   @Resource
   private RoleResourcesDao roleResourcesDao;
 
   protected IEntityDao<Resources, Long> getEntityDao()
   {
     return this.resourcesDao;
   }
 
   public void addRes(Resources resources, String[] aryName, String[] aryUrl)
     throws Exception
   {
     Long resId = Long.valueOf(UniqueIdUtil.genId());
     resources.setResId(resId);
     this.resourcesDao.add(resources);
 
     if ((aryName == null) || (aryName.length == 0)) {
       return;
     }
     for (int i = 0; i < aryName.length; i++) {
       String url = aryUrl[i];
       if (StringUtil.isEmpty(url))
         continue;
       ResourcesUrl resouceUrl = new ResourcesUrl();
       resouceUrl.setResId(resId);
       resouceUrl.setResUrlId(Long.valueOf(UniqueIdUtil.genId()));
       resouceUrl.setName(aryName[i]);
       resouceUrl.setUrl(url);
       this.resourcesUrlDao.add(resouceUrl);
     }
   }
 
   public void updRes(Resources resources, String[] aryName, String[] aryUrl)
     throws Exception
   {
     Long resId = resources.getResId();
 
     this.resourcesDao.update(resources);
 
     this.resourcesUrlDao.delByResId(resId.longValue());
 
     if ((aryName == null) || (aryName.length == 0)) {
       return;
     }
     for (int i = 0; i < aryName.length; i++) {
       String url = aryUrl[i];
       if (StringUtil.isEmpty(url))
         continue;
       ResourcesUrl resouceUrl = new ResourcesUrl();
       resouceUrl.setResId(resId);
       resouceUrl.setResUrlId(Long.valueOf(UniqueIdUtil.genId()));
       resouceUrl.setName(aryName[i]);
       resouceUrl.setUrl(url);
       this.resourcesUrlDao.add(resouceUrl);
     }
   }
 
   public List<Resources> getChildByParentId(long systemId, long parentId, String ctx)
   {
     List<Resources> resourcesList = this.resourcesDao.getByParentId(parentId);
     if ((resourcesList == null) || (resourcesList.size() == 0)) return resourcesList;
 
     for (Resources res : resourcesList) {
       res.setIcon(ctx + res.getIcon());
     }
     return resourcesList;
   }
 
   public List<Resources> getBySystemId(long systemId, String ctx)
   {
     List<Resources> resourcesList = this.resourcesDao.getBySystemId(systemId);
     if ((resourcesList == null) || (resourcesList.size() == 0)) return resourcesList;
     for (Resources res : resourcesList) {
       res.setIcon(ctx + res.getIcon());
     }
     return resourcesList;
   }
 
   public Resources getParentResourcesByParentId(long systemId, long parentId, String ctx)
   {
     Resources parent = (Resources)this.resourcesDao.getById(Long.valueOf(parentId));
     if ((parentId == 0L) || (parent == null))
     {
       SubSystem sys = (SubSystem)this.subSystemDao.getById(Long.valueOf(systemId));
 
       parent = new Resources();
       parent.setResId(Long.valueOf(0L));
       parent.setParentId(Long.valueOf(-1L));
       parent.setSn(Integer.valueOf(0));
       parent.setSystemId(Long.valueOf(systemId));
 
       parent.setAlias(sys.getAlias());
 
       parent.setIcon(ctx + sys.getLogo());
       parent.setIsDisplayInMenu(Resources.IS_DISPLAY_IN_MENU_Y);
       parent.setIsFolder(Resources.IS_FOLDER_Y);
       parent.setIsOpen(Resources.IS_OPEN_Y);
       parent.setResName(sys.getSysName());
 
       return parent;
     }
     return parent;
   }
 
   public void delByIds(Long[] ids)
   {
     if ((ids == null) || (ids.length == 0)) return;
 
     Long[] arr$ = ids; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { long resId = arr$[i$].longValue();
 
       Resources res = (Resources)this.resourcesDao.getById(Long.valueOf(resId));
 
       Long parentId = res.getParentId();
       List childrenList = this.resourcesDao.getByParentId(resId);
 
       this.resourcesUrlDao.delByResId(resId);
 
       this.roleResourcesDao.delByResId(Long.valueOf(resId));
 
       updateChildrenNodePath(parentId, childrenList);
 
       this.resourcesDao.delById(Long.valueOf(resId));
     }
   }
 
   private void updateChildrenNodePath(Long parentId, List<Resources> childrenList)
   {
     if ((childrenList == null) || (childrenList.size() == 0))
       return;
     for (Resources res : childrenList) {
       res.setParentId(parentId);
       this.resourcesDao.update(res);
     }
   }
 
   public void updateChildrenNodeDisplay(Resources entity)
   {
     if (entity == null) return;
 
     if (entity.getIsDisplayInMenu().shortValue() == Resources.IS_DISPLAY_IN_MENU_N.shortValue()) {
       List<Resources> childrenList = this.resourcesDao.getByParentId(entity.getResId().longValue());
       if ((childrenList != null) && (childrenList.size() > 0)) {
         for (Resources res : childrenList) {
           res.setIsDisplayInMenu(entity.getIsDisplayInMenu());
           super.update(res);
           updateChildrenNodeDisplay(res);
         }
       }
     }
     if (entity.getIsDisplayInMenu().shortValue() == Resources.IS_DISPLAY_IN_MENU_Y.shortValue()) {
       Resources parent = getParentResourcesByParentId(entity.getSystemId().longValue(), entity.getParentId().longValue(), "");
       if ((parent != null) && (parent.getParentId().longValue() != 0L) && (parent.getParentId().longValue() != -1L)) {
         parent.setIsDisplayInMenu(entity.getIsDisplayInMenu());
         super.update(parent);
         updateChildrenNodeDisplay(parent);
       }
     }
   }
 
   public List<Resources> getBySysRolResChecked(Long systemId, Long roleId, String ctx)
   {
     List<Resources> resourcesList = this.resourcesDao.getBySystemId(systemId.longValue());
     List<RoleResources> roleResourcesList = this.roleResourcesDao.getBySysAndRole(systemId, roleId);
 
     Set set = new HashSet();
     if ((roleResourcesList != null) && (roleResourcesList.size() > 0)) {
       for (RoleResources rores : roleResourcesList)
       {
         set.add(Long.valueOf(rores.getResId().longValue()));
       }
     }
 
     if ((resourcesList != null) && (resourcesList.size() > 0)) {
       for (Resources res : resourcesList) {
         if (set.contains(Long.valueOf(res.getResId().longValue())))
           res.setChecked("true");
         else {
           res.setChecked("false");
         }
         res.setIcon(ctx + res.getIcon());
       }
     }
 
     return resourcesList;
   }
 
   public List<Resources> getSysMenu(SubSystem sys, SysUser user, String ctx)
   {
     Collection auths = ContextUtil.getCurrentUser().getAuthorities();
     List<Resources> resourcesList = new ArrayList();
 
     if ((auths != null) && (auths.size() > 0) && (auths.contains(SysRole.ROLE_GRANT_SUPER))) {
       resourcesList = this.resourcesDao.getSuperMenu(Long.valueOf(sys.getSystemId()));
     } else {
       long userId = ContextUtil.getCurrentUser().getUserId().longValue();
       resourcesList = this.resourcesDao.getNormMenu(Long.valueOf(sys.getSystemId()), Long.valueOf(userId));
     }
 
     for (Resources res : resourcesList) {
       res.setIcon(ctx + res.getIcon());
//       res.setIsFolder(null);
     }
     short isLocal = sys.getIsLocal() == null ? 1 : sys.getIsLocal().shortValue();
 
     if (isLocal == SubSystem.isLocal_N)
     {
       for (Resources res : resourcesList) {
         res.setDefaultUrl(sys.getDefaultUrl() + res.getDefaultUrl());
       }
     }
 
     return resourcesList;
   }
 
   public void update(Resources entity)
   {
     super.update(entity);
 
     updateChildrenNodeDisplay(entity);
   }
 
   public Integer isAliasExists(Resources resources)
   {
     Long systemId = resources.getSystemId();
     String alias = resources.getAlias();
     return this.resourcesDao.isAliasExists(systemId, alias);
   }
 
   public Integer isAliasExistsForUpd(Resources resources)
   {
     Long systemId = resources.getSystemId();
     String alias = resources.getAlias();
     Long resId = resources.getResId();
     return this.resourcesDao.isAliasExistsForUpd(systemId, resId, alias);
   }
 }

