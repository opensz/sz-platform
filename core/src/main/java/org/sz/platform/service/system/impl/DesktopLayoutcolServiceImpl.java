 package org.sz.platform.service.system.impl;
 
  import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.dao.system.DesktopColumnDao;
import org.sz.platform.dao.system.DesktopLayoutDao;
import org.sz.platform.dao.system.DesktopLayoutcolDao;
import org.sz.platform.model.system.DesktopColumn;
import org.sz.platform.model.system.DesktopLayout;
import org.sz.platform.model.system.DesktopLayoutcol;
import org.sz.platform.service.system.DesktopLayoutcolService;
 
 @Service("desktopLayoutcolService")
 public class DesktopLayoutcolServiceImpl extends BaseServiceImpl<DesktopLayoutcol> implements DesktopLayoutcolService
 {
 
   @Resource
   private DesktopLayoutcolDao dao;
 
   @Resource
   private DesktopColumnDao desktopColumnDao;
 
   @Resource
   private DesktopLayoutDao desktopLayoutDao;
 
   protected IEntityDao<DesktopLayoutcol, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public void delByLayoutId(Long layoutId)
   {
     this.dao.delByLayoutId(layoutId);
   }
 
   public void delByNoLayoutId(Long layoutId)
   {
     this.dao.delByNoLayoutId(layoutId);
   }
 
   public List<DesktopLayoutcol> getByLayoutId(Long layoutId)
   {
     return this.dao.getByLayoutId(layoutId);
   }
 
   public void setListData(List<DesktopLayoutcol> list)
   {
     for (DesktopLayoutcol dlc : list) {
       if (dlc.getColumnId().longValue() != 0L) {
         dlc.setColumnName(this.desktopColumnDao.getNameById(dlc.getColumnId().longValue()));
       }
       if (dlc.getLayoutId().longValue() != 0L)
         dlc.setLayoutName(this.desktopLayoutDao.getNameById(dlc.getLayoutId().longValue()));
     }
   }
 
   public DesktopLayoutcol editData(Long id, Map<String, String> desktopColumnmap, Map<String, String> desktopLayoutmap)
   {
     List<DesktopColumn> desktopColumnList = this.desktopColumnDao.getAll();
     for (DesktopColumn dc : desktopColumnList) {
       desktopColumnmap.put("" + dc.getId(), dc.getName());
     }
     List<DesktopLayout> desktopLayoutList = this.desktopLayoutDao.getAll();
 
     for (DesktopLayout dc : desktopLayoutList)
       desktopLayoutmap.put("" + dc.getId(), dc.getName());
     DesktopLayoutcol desktopLayoutcol;
     if (id.longValue() != 0L)
       desktopLayoutcol = (DesktopLayoutcol)this.dao.getById(id);
     else {
       desktopLayoutcol = new DesktopLayoutcol();
     }
     return desktopLayoutcol;
   }
 
   public void saveCol(List<DesktopLayoutcol> list, Long layoutId)
   {
     this.dao.delByLayoutId(layoutId);
     for (DesktopLayoutcol desktopLayoutcol : list)
       this.dao.add(desktopLayoutcol);
   }
 
   public DesktopLayoutcol showData(Long id, Map<String, String> desktopColumnmap, Map<String, String> desktopLayoutmap)
   {
     List<DesktopColumn> desktopColumnList = this.desktopColumnDao.getAll();
     for (DesktopColumn dc : desktopColumnList) {
       desktopColumnmap.put("" + dc.getId(), dc.getName());
     }
     DesktopLayout desktopLayout = (DesktopLayout)this.desktopLayoutDao.getById(id);
     desktopLayoutmap.put("cols", "" + desktopLayout.getCols());
     desktopLayoutmap.put("id", "" + desktopLayout.getId());
     desktopLayoutmap.put("widths", desktopLayout.getWidth());
     DesktopLayoutcol desktopLayoutcol = (DesktopLayoutcol)this.dao.getById(id);
     return desktopLayoutcol;
   }
 
   public List<DesktopLayoutcol> layoutcolData(Long layoutId)
   {
     List<DesktopLayoutcol> list = this.dao.getByLayoutId(layoutId);
     for (DesktopLayoutcol dlc : list) {
       if (dlc.getColumnId().longValue() != 0L) {
         DesktopColumn desktopColumn = (DesktopColumn)this.desktopColumnDao.getById(dlc.getColumnId());
         dlc.setColumnName(desktopColumn.getName());
         dlc.setColumnUrl(desktopColumn.getColumnUrl());
       }
     }
     return list;
   }
 }

