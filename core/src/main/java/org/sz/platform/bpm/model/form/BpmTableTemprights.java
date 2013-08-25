 package org.sz.platform.bpm.model.form;
 
 import org.sz.platform.bpm.model.form.BpmTableTemprights;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class BpmTableTemprights extends BaseModel
 {
   public static final int RIGHT_TYPE_USER = 1;
   public static final int RIGHT_TYPE_ROLE = 2;
   public static final int RIGHT_TYPE_ORG = 3;
   public static final Short SEARCH_TYPE_DEF = 0;
   public static final Short SEARCH_TYPE_GLT = 1;
   protected Long id;
   protected Long templateId;
   protected Short rightType;
   protected Long ownerId;
   protected String ownerName;
   protected Short searchType;
   protected Long categoryId;
 
   public void setId(Long id)
   {
     this.id = id;
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setTemplateId(Long templateId) {
     this.templateId = templateId;
   }
 
   public Long getTemplateId()
   {
     return this.templateId;
   }
 
   public void setRightType(Short rightType) {
     this.rightType = rightType;
   }
 
   public Short getRightType()
   {
     return this.rightType;
   }
 
   public void setOwnerId(Long ownerId) {
     this.ownerId = ownerId;
   }
 
   public Long getOwnerId()
   {
     return this.ownerId;
   }
 
   public void setOwnerName(String ownerName) {
     this.ownerName = ownerName;
   }
 
   public String getOwnerName()
   {
     return this.ownerName;
   }
 
   public void setSearchType(Short searchType) {
     this.searchType = searchType;
   }
 
   public Short getSearchType()
   {
     return this.searchType;
   }
 
   public Long getCategoryId() {
     return this.categoryId;
   }
 
   public void setCategoryId(Long categoryId) {
     this.categoryId = categoryId;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof BpmTableTemprights)) {
       return false;
     }
     BpmTableTemprights rhs = (BpmTableTemprights)object;
     return new EqualsBuilder().append(this.id, rhs.id).append(this.templateId, rhs.templateId).append(this.rightType, rhs.rightType).append(this.ownerId, rhs.ownerId).append(this.ownerName, rhs.ownerName).append(this.searchType, rhs.searchType).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.templateId).append(this.rightType).append(this.ownerId).append(this.ownerName).append(this.searchType).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("id", this.id).append("templateId", this.templateId).append("rightType", this.rightType).append("ownerId", this.ownerId).append("ownerName", this.ownerName).append("searchType", this.searchType).toString();
   }
 }

