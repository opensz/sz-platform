 package org.sz.core.cache.impl;
 
 import java.util.HashMap;

import org.sz.core.cache.ICache;
 
 public class MemoryCache
   implements ICache
 {
   private HashMap<String, Object> cache = new HashMap();
 
   public void add(String key, Object obj, long timeout)
   {
     this.cache.put(key, obj);
   }
 
   public void delByKey(String key)
   {
     this.cache.remove(key);
   }
 
   public void clearAll()
   {
     this.cache.clear();
   }
 
   public Object getByKey(String key)
   {
     return this.cache.get(key);
   }
 
   public boolean containKey(String key)
   {
     return this.cache.containsKey(key);
   }
 }

