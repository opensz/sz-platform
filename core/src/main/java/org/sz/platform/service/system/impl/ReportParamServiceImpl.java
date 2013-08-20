 package org.sz.platform.service.system.impl;
 
  import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.dao.system.ReportParamDao;
import org.sz.platform.model.system.ReportParam;
import org.sz.platform.service.system.ReportParamService;
 
 @Service("reportParamService")
 public class ReportParamServiceImpl extends BaseServiceImpl<ReportParam> implements ReportParamService
 {
 
   @Resource
   private ReportParamDao dao;
 
   protected IEntityDao<ReportParam, Long> getEntityDao()
   {
     return this.dao;
   }
 }

