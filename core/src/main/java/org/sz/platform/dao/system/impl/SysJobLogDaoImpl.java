 package org.sz.platform.dao.system.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.SysJobLogDao;
import org.sz.platform.model.system.SysJobLog;
 
 @Repository("sysJobLogDao")
 public class SysJobLogDaoImpl extends BaseDaoImpl<SysJobLog> implements SysJobLogDao
 {
   public Class getEntityClass()
   {
     return SysJobLog.class;
   }
 }

