 package org.sz.platform.service.system.impl;
 
  import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.dao.system.SysUserParamDao;
import org.sz.platform.model.system.SysUserParam;
import org.sz.platform.service.system.SysUserParamService;
 
 @Service("sysUserParamService")
 public class SysUserParamServiceImpl extends BaseServiceImpl<SysUserParam> implements SysUserParamService
 {
 
   @Resource
   private SysUserParamDao sysUserParamDao;
 
   protected IEntityDao<SysUserParam, Long> getEntityDao()
   {
     return this.sysUserParamDao;
   }
 
   public void add(long userId, List<SysUserParam> valueList)
   {
     this.sysUserParamDao.delByUserId(userId);
     if ((valueList == null) || (valueList.size() == 0)) return;
     for (SysUserParam p : valueList)
       this.sysUserParamDao.add(p);
   }
 
   public List<SysUserParam> getByUserId(long userId)
   {
     return this.sysUserParamDao.getBySqlKey("getByUserId", Long.valueOf(userId));
   }
 }

