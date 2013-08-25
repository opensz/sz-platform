 package org.sz.platform.system.service.impl;
 
  import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.system.dao.ReportParamDao;
import org.sz.platform.system.model.ReportParam;
import org.sz.platform.system.service.ReportParamService;
 
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

