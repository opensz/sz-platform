 package org.sz.platform.dao.system.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.RoleSysDao;
import org.sz.platform.model.system.RoleSys;
 
 @Repository("roleSysDao")
 public class RoleSysDaoImpl extends BaseDaoImpl<RoleSys> implements RoleSysDao
 {
   public Class getEntityClass()
   {
     return RoleSys.class;
   }
 
   public void delBySysAndRole(Long systemId, Long roleId)
   {
     Map params = new HashMap();
     params.put("systemid", systemId);
     params.put("roleid", roleId);
     delBySqlKey("delBySysAndRole", params);
   }
 
   public List<RoleSys> getByRole(Long roleId)
   {
     List list = getBySqlKey("getByRole", roleId);
     return list;
   }
 }

