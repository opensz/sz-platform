 package org.sz.platform.model.system;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.sz.platform.model.system.ResourcesUrl;
 
 @XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
 @XmlRootElement(namespace="bpm")
 public class ResourcesUrlExt extends ResourcesUrl
 {
   private String function = "";
   private String role = "";
 
   public String getFunction() {
     return this.function;
   }
   public void setFunction(String function) {
     this.function = function;
   }
   public String getRole() {
     return this.role;
   }
   public void setRole(String role) {
     this.role = role;
   }
 }

