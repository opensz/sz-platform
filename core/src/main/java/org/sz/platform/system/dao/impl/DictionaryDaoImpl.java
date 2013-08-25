 package org.sz.platform.system.dao.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.system.dao.DictionaryDao;
import org.sz.platform.system.model.Dictionary;
 
 @Repository("dictionaryDao")
 public class DictionaryDaoImpl extends BaseDaoImpl<Dictionary> implements DictionaryDao
 {
   public Class getEntityClass()
   {
     return Dictionary.class;
   }
 
   public List<Dictionary> getByTypeId(long typeId)
   {
     return getBySqlKey("getByTypeId", Long.valueOf(typeId));
   }
 
   public List<Dictionary> getByNodePath(String nodePath)
   {
     Map params = new HashMap();
     params.put("nodePath", nodePath);
     return getBySqlKey("getByNodePath", params);
   }
 
   public void delByTypeId(Long typeId)
   {
     delBySqlKey("getByTypeId", typeId);
   }
 
   public boolean isItemKeyExists(long typeId, String itemKey)
   {
     Map params = new HashMap();
     params.put("typeId", Long.valueOf(typeId));
     params.put("itemKey", itemKey);
     int count = ((Integer)getOne("isItemKeyExists", params)).intValue();
     return count > 0;
   }
 
   public boolean isItemKeyExistsForUpdate(long dicId, long typeId, String itemKey)
   {
     Map params = new HashMap();
     params.put("dicId", Long.valueOf(dicId));
     params.put("typeId", Long.valueOf(typeId));
     params.put("itemKey", itemKey);
     int count = ((Integer)getOne("isItemKeyExistsForUpdate", params)).intValue();
     return count > 0;
   }
 
   public void updSn(Long dicId, Integer sn)
   {
     Map params = new HashMap();
     params.put("dicId", dicId);
     params.put("sn", sn);
     update("updSn", params);
   }
 }

