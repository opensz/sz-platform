 package org.sz.platform.system.dao.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.RoleResourcesDao;
import org.sz.platform.system.model.RoleResources;
 
 @Repository("roleResourcesDao")
 public class RoleResourcesDaoImpl extends BaseDaoImpl<RoleResources> implements RoleResourcesDao
 {
   public Class getEntityClass()
   {
     return RoleResources.class;
   }
 
   public List<RoleResources> getBySysAndRole(Long systemId, Long roleId)
   {
     Map params = new HashMap();
     params.put("systemId", systemId);
     params.put("roleId", roleId);
     return getBySqlKey("getBySysAndRes", params);
   }
 
   public void delByRoleAndSys(Long systemId, Long roleId)
   {
     Map params = new HashMap();
     params.put("systemId", systemId);
     params.put("roleId", roleId);
     delBySqlKey("delByRoleAndSys", params);
   }
 
   public void delByResId(Long resId)
   {
     delBySqlKey("delByResId", resId);
   }
 
   public List<RoleResources> getRoleRes(Long roleId)
   {
     return getBySqlKey("getRoleRes", roleId);
   }
 
   public List<RoleResources> getByResId(Long resId)
   {
     return getBySqlKey("getByResId", resId);
   }
 }

