 package org.sz.platform.dao.system.impl;
 
  import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.ScriptDao;
import org.sz.platform.model.system.Script;
 
 @Repository("scriptDao")
 public class ScriptDaoImpl extends BaseDaoImpl<Script> implements ScriptDao
 {
   public Class getEntityClass()
   {
     return Script.class;
   }
 
   public List<String> getDistinctCategory()
   {
     List list = getBySqlKey("getDistinctCategory");
     return list;
   }
 
   public Integer isExistWithName(String name)
   {
     return (Integer)getOne("isExistWithName", name);
   }
 }

