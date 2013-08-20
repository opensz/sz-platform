 package org.sz.platform.service.system.impl;
 
  import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.dao.system.SysOrgParamDao;
import org.sz.platform.dao.system.SysParamDao;
import org.sz.platform.model.system.SysOrgParam;
import org.sz.platform.model.system.SysParam;
import org.sz.platform.service.system.SysOrgParamService;
 
 @Service("sysOrgParamService")
 public class SysOrgParamServiceImpl extends BaseServiceImpl<SysOrgParam> implements SysOrgParamService
 {
 
   @Resource
   private SysOrgParamDao sysOrgParamDao;
 
   @Resource
   private SysParamDao sysParamDao;
 
   protected IEntityDao<SysOrgParam, Long> getEntityDao()
   {
     return this.sysOrgParamDao;
   }
 
   public void add(long orgId, List<SysOrgParam> valueList)
   {
     this.sysOrgParamDao.delByOrgId(orgId);
     if ((valueList == null) || (valueList.size() == 0)) return;
     for (SysOrgParam p : valueList)
       this.sysOrgParamDao.add(p);
   }
 
   public List<SysOrgParam> getListByOrgId(Long orgId)
   {
     List<SysOrgParam> list = this.sysOrgParamDao.getByOrgId(orgId);
     if (list.size() > 0) {
       for (SysOrgParam param : list) {
         long paramId = param.getParamId().longValue();
         SysParam sysParam = (SysParam)this.sysParamDao.getById(Long.valueOf(paramId));
         param.setParamName(sysParam.getParamName());
       }
     }
     return list;
   }
 }

