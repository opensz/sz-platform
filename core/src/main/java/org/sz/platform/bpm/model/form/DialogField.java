 package org.sz.platform.bpm.model.form;
 
 public class DialogField
 {
   private String fieldName = "";
 
   private String comment = "";
 
   private String condition = "";
 
   private String fieldType = "";
 
   public String getFieldName()
   {
     return this.fieldName;
   }
   public void setFieldName(String fieldName) {
     this.fieldName = fieldName;
   }
   public String getComment() {
     return this.comment;
   }
   public void setComment(String comment) {
     this.comment = comment;
   }
   public String getCondition() {
     return this.condition;
   }
   public void setCondition(String condition) {
     this.condition = condition;
   }
   public String getFieldType() {
     return this.fieldType;
   }
   public void setFieldType(String fieldType) {
     this.fieldType = fieldType;
   }
 
   public String getConditionField() {
     String tmp = "Q_";
     if (this.fieldType.equals("varchar")) {
       tmp = tmp + this.fieldName + "_S";
     }
     else if (this.fieldName.equals("number")) {
       tmp = tmp + this.fieldName + "_DB";
     }
     else if (this.fieldName.equals("date")) {
       if ((this.condition.equals("<")) || (this.condition.equals("<=")))
         tmp = tmp + this.fieldName + "_DG";
       else
         tmp = tmp + this.fieldName + "_DL";
     }
     else {
       tmp = tmp + this.fieldName + "_S";
     }
     return tmp;
   }
 }

