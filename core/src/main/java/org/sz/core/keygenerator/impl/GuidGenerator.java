 package org.sz.core.keygenerator.impl;
 
 import org.sz.core.keygenerator.IKeyGenerator;
import org.sz.core.util.UniqueIdUtil;
 
 public class GuidGenerator
   implements IKeyGenerator
 {
   public Object nextId()
     throws Exception
   {
     return UniqueIdUtil.getGuid();
   }
 
   public void setAlias(String alias)
   {
   }
 }