 package org.sz.platform.oa.dao.desk.impl;
 
 import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.oa.dao.desk.DesktopLayoutDao;
import org.sz.platform.oa.model.desk.DesktopLayout;
 
 @Repository("DesktopLayoutDao")
 public class DesktopLayoutDaoImpl extends BaseDaoImpl<DesktopLayout> implements DesktopLayoutDao
 {
   public Class getEntityClass()
   {
     return DesktopLayout.class;
   }
 
   public DesktopLayout getDefaultLayout() {
     return (DesktopLayout)getUnique("getDefaultLayout", null);
   }
 
   public int updateDefault(long id)
   {
     return update("updateDefault", Long.valueOf(id));
   }
 
   public int updNotDefault() {
     return update("updNotDefault", null);
   }
 
   public long getDefaultId() {
     return ((Long)getOne("getDefaultId", null)).longValue();
   }
 
   public String getNameById(long layoutId) {
     return (String)getOne("getNameById", Long.valueOf(layoutId));
   }
 
   public DesktopLayout getLayoutByUserId(Long userId) {
     return (DesktopLayout)getUnique("getLayoutByUserId", userId);
   }
 }

