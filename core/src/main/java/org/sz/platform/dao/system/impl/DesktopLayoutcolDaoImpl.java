 package org.sz.platform.dao.system.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.DesktopLayoutcolDao;
import org.sz.platform.model.system.DesktopLayoutcol;
 
 @Repository("desktopLayoutcolDao")
 public class DesktopLayoutcolDaoImpl extends BaseDaoImpl<DesktopLayoutcol> implements DesktopLayoutcolDao
 {
   public Class getEntityClass()
   {
     return DesktopLayoutcol.class;
   }
 
   public void delByLayoutId(Long layoutId)
   {
     Map params = new HashMap();
     params.put("layoutId", layoutId);
     getBySqlKey("delByLayoutId", params);
   }
 
   public void delByNoLayoutId(Long layoutId)
   {
     Map params = new HashMap();
     params.put("layoutId", layoutId);
     getBySqlKey("delByNoLayoutId", params);
   }
 
   public List<DesktopLayoutcol> getByLayoutId(Long layoutId)
   {
     Map params = new HashMap();
     params.put("layoutId", layoutId);
     return getBySqlKey("getByLayoutId", params);
   }
 
   public void delByLinkLayout(Long COLUMNID) {
     getBySqlKey("delByLinkLayout", COLUMNID);
   }
 }

