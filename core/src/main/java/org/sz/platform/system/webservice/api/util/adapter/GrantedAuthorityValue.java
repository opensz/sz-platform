 package org.sz.platform.system.webservice.api.util.adapter;
 
 public class GrantedAuthorityValue
 {
   public String authority = "";
 
   public GrantedAuthorityValue()
   {
   }
 
   public GrantedAuthorityValue(Object obj)
   {
     this.authority = obj.toString();
   }
 }

