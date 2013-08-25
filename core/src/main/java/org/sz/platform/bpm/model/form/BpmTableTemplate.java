 package org.sz.platform.bpm.model.form;
 
 import org.sz.platform.bpm.model.form.BpmTableTemplate;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class BpmTableTemplate extends BaseModel
 {
   public static final int AUTHOR_TYPE_ALL = 1;
   public static final int AUTHOR_TYPE_SELF = 2;
   public static final int AUTHOR_TYPE_UNDER = 3;
   public static final int AUTHOR_TYPE_ORG = 4;
   protected Long id;
   protected Long tableId;
   protected Long categoryId;
   protected String htmlList;
   protected String htmlDetail;
   protected String templateName;
   protected String tableName = "";
 
   protected String categoryName = "";
   protected int authorType;
 
   public void setId(Long id)
   {
     this.id = id;
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setTableId(Long tableId) {
     this.tableId = tableId;
   }
 
   public Long getTableId()
   {
     return this.tableId;
   }
 
   public void setCategoryId(Long categoryId) {
     this.categoryId = categoryId;
   }
 
   public Long getCategoryId()
   {
     return this.categoryId;
   }
 
   public void setHtmlList(String htmlList) {
     this.htmlList = htmlList;
   }
 
   public String getHtmlList()
   {
     return this.htmlList;
   }
 
   public void setHtmlDetail(String htmlDetail) {
     this.htmlDetail = htmlDetail;
   }
 
   public String getHtmlDetail()
   {
     return this.htmlDetail;
   }
 
   public void setTemplateName(String templateName) {
     this.templateName = templateName;
   }
 
   public String getTemplateName()
   {
     return this.templateName;
   }
 
   public String getTableName()
   {
     return this.tableName;
   }
   public void setTableName(String tableName) {
     this.tableName = tableName;
   }
   public String getCategoryName() {
     return this.categoryName;
   }
   public void setCategoryName(String categoryName) {
     this.categoryName = categoryName;
   }
 
   public int getAuthorType()
   {
     return this.authorType;
   }
 
   public void setAuthorType(int authorType)
   {
     this.authorType = authorType;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof BpmTableTemplate))
     {
       return false;
     }
     BpmTableTemplate rhs = (BpmTableTemplate)object;
     return new EqualsBuilder().append(this.id, rhs.id).append(this.tableId, rhs.tableId).append(this.categoryId, rhs.categoryId).append(this.htmlList, rhs.htmlList).append(this.htmlDetail, rhs.htmlDetail).append(this.templateName, rhs.templateName).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.tableId).append(this.categoryId).append(this.htmlList).append(this.htmlDetail).append(this.templateName).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("id", this.id).append("tableId", this.tableId).append("categoryId", this.categoryId).append("htmlList", this.htmlList).append("htmlDetail", this.htmlDetail).append("templateName", this.templateName).toString();
   }
 }

