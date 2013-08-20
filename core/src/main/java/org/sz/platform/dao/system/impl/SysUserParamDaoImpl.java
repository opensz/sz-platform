 package org.sz.platform.dao.system.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.SysUserParamDao;
import org.sz.platform.model.system.SysUserParam;
 
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

