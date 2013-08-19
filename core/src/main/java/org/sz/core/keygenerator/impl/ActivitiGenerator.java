 package org.sz.core.keygenerator.impl;
 
 import org.sz.core.keygenerator.IKeyGenerator;
import org.sz.core.util.UniqueIdUtil;
 
 public class ActivitiGenerator
   implements IKeyGenerator
 {
   public Object nextId()
     throws Exception
   {
     return UniqueIdUtil.getNextId();
   }
 
   public void setAlias(String alias)
   {
   }
 }