 package org.sz.platform.system.service.impl;
 
 import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.system.dao.RoleResourcesDao;
import org.sz.platform.system.dao.RoleSysDao;
import org.sz.platform.system.model.RoleResources;
import org.sz.platform.system.model.RoleSys;
import org.sz.platform.system.service.RoleResourcesService;
import org.sz.platform.system.service.SecurityUtil;
import org.sz.platform.system.service.SubSystemService;
import org.sz.platform.system.service.SysRoleService;
 
 @Service("roleResourcesService")
 public class RoleResourcesServiceImpl extends BaseServiceImpl<RoleResources> implements RoleResourcesService
 {
 
   @Resource
   private RoleResourcesDao roleResourcesDao;
 
   @Resource
   private SysRoleService sysRoleService;
 
   @Resource
   private SubSystemService subSystemService;
 
   @Resource
   private RoleSysDao roleSysDao;
 
   protected IEntityDao<RoleResources, Long> getEntityDao()
   {
     return this.roleResourcesDao;
   }
 
   public void update(Long systemId, Long roleId, Long[] resIds)
     throws Exception
   {
     this.roleResourcesDao.delByRoleAndSys(systemId, roleId);
 
     this.roleSysDao.delBySysAndRole(systemId, roleId);
 
     RoleSys roleSys = new RoleSys();
     roleSys.setId(Long.valueOf(UniqueIdUtil.genId()));
     roleSys.setSystemid(systemId);
     roleSys.setRoleid(roleId);
     this.roleSysDao.add(roleSys);
 
     if ((systemId.longValue() > 0L) && (roleId.longValue() > 0L) && (resIds != null) && (resIds.length > 0)) {
       Long[] arr$ = resIds; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { long resId = arr$[i$].longValue();
         RoleResources rores = new RoleResources();
         rores.setRoleResId(Long.valueOf(UniqueIdUtil.genId()));
         rores.setResId(Long.valueOf(resId));
         rores.setSystemId(systemId);
         rores.setRoleId(roleId);
         add(rores);
       }
     }
 
     SecurityUtil.loadRes(this.sysRoleService, this.subSystemService, systemId);
   }
 }

