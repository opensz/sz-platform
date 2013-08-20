 package org.sz.platform.dao.system.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.ReportParamDao;
import org.sz.platform.model.system.ReportParam;
 
 @Repository("reportParamDao")
 public class ReportParamDaoImpl extends BaseDaoImpl<ReportParam> implements ReportParamDao
 {
   public Class getEntityClass()
   {
     return ReportParam.class;
   }
 }

