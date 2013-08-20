 package org.sz.platform.dao.system.impl;
 
  import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.dao.system.SysTypeKeyDao;
import org.sz.platform.model.system.SysTypeKey;
 
 @Repository("sysTypeKeyDao")
 public class SysTypeKeyDaoImpl extends BaseDaoImpl<SysTypeKey> implements SysTypeKeyDao
 {
   public Class getEntityClass()
   {
     return SysTypeKey.class;
   }
 
   public SysTypeKey getByKey(String key)
   {
     key = key.toLowerCase();
     return (SysTypeKey)getUnique("getByKey", key);
   }
 
   public boolean isExistKey(String typeKey)
   {
     Integer sn = (Integer)getOne("isExistKey", typeKey);
     return sn.intValue() > 0;
   }
 
   public boolean isKeyExistForUpdate(String typeKey, Long typeId)
   {
     Map params = new HashMap();
     params.put("typeKey", typeKey);
     params.put("typeId", typeId);
     Integer sn = (Integer)getOne("isKeyExistForUpdate", params);
     return sn.intValue() > 0;
   }
 
   public void updateSequence(Long typeId, int sn)
   {
     SysTypeKey sysTypeKey = new SysTypeKey();
     sysTypeKey.setTypeId(typeId);
     sysTypeKey.setSn(Integer.valueOf(sn));
     update("updateSequence", sysTypeKey);
   }
 }

