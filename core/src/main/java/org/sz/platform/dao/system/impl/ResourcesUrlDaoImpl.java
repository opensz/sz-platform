 package org.sz.platform.dao.system.impl;
 
  import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.ResourcesUrlDao;
import org.sz.platform.model.system.ResourcesUrl;
import org.sz.platform.model.system.ResourcesUrlExt;
 
 @Repository("resourcesUrlDao")
 public class ResourcesUrlDaoImpl extends BaseDaoImpl<ResourcesUrl> implements ResourcesUrlDao
 {
   public Class getEntityClass()
   {
     return ResourcesUrl.class;
   }
 
   public List<ResourcesUrl> getByResId(long resId)
   {
     return getBySqlKey("getByResId", Long.valueOf(resId));
   }
 
   public void delByResId(long resId)
   {
     delBySqlKey("delByResId", Long.valueOf(resId));
   }
 
   public List<ResourcesUrlExt> getUrlAndRoleBySystemId(long systemId)
   {
     String statment = getIbatisMapperNamespace() + ".getUrlAndRoleBySystemId";
     return getSqlSessionTemplate().selectList(statment, Long.valueOf(systemId));
   }
 
   public List<ResourcesUrlExt> getSubSystemResources(String defaultUrl)
   {
     String statment = getIbatisMapperNamespace() + ".getSubSystemResources";
     return getSqlSessionTemplate().selectList(statment, defaultUrl);
   }
 
   public List<ResourcesUrlExt> getSubSystemResByAlias(String alias)
   {
     String statment = getIbatisMapperNamespace() + ".getSubSystemResByAlias";
     return getSqlSessionTemplate().selectList(statment, alias);
   }
 }

