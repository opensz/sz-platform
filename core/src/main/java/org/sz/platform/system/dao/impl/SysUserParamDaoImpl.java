 package org.sz.platform.system.dao.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.SysUserParamDao;
import org.sz.platform.system.model.SysUserParam;
 
 @Repository("sysUserParamDao")
 public class SysUserParamDaoImpl extends BaseDaoImpl<SysUserParam> implements SysUserParamDao
 {
   public Class getEntityClass()
   {
     return SysUserParam.class;
   }
   public int delByUserId(long userId) {
     return getSqlSessionTemplate().delete(getIbatisMapperNamespace() + ".delByUserId", Long.valueOf(userId));
   }
 }

