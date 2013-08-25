 package org.sz.platform.bpm.model.form;
 
 public class PkValue
 {
   private String name = "";
   private Object value;
   private boolean isAdd = false;
 
   public PkValue()
   {
   }
 
   public PkValue(String pkField, Object value)
   {
     this.name = pkField;
     this.value = value;
   }
 
   public boolean getIsAdd()
   {
     return this.isAdd;
   }
 
   public void setIsAdd(boolean isAdd) {
     this.isAdd = isAdd;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public Object getValue() {
     return this.value;
   }
 
   public void setValue(Object value) {
     this.value = value;
   }
 }

