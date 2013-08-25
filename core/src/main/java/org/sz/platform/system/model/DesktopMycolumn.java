 package org.sz.platform.system.model;
 
 import org.sz.platform.system.model.DesktopMycolumn;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class DesktopMycolumn extends BaseModel
 {
   protected Long id;
   protected Long userId;
   protected Long layoutId;
   protected Long columnId;
   protected Short col;
   protected Integer sn;
   protected String columnName;
   protected String layoutName;
   protected String widths;
   protected String columnUrl;
   protected String servicemethod;
   protected String columnHtml;
 
   public void setId(Long id)
   {
     this.id = id;
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setUserId(Long userId)
   {
     this.userId = userId;
   }
 
   public Long getUserId()
   {
     return this.userId;
   }
 
   public void setLayoutId(Long layoutId)
   {
     this.layoutId = layoutId;
   }
 
   public Long getLayoutId()
   {
     return this.layoutId;
   }
 
   public void setColumnId(Long columnId)
   {
     this.columnId = columnId;
   }
 
   public Long getColumnId()
   {
     return this.columnId;
   }
 
   public void setCol(Short col)
   {
     this.col = col;
   }
 
   public Short getCol()
   {
     return this.col;
   }
 
   public void setSn(Integer sn)
   {
     this.sn = sn;
   }
 
   public Integer getSn()
   {
     return this.sn;
   }
 
   public String getColumnName() {
     return this.columnName;
   }
   public void setColumnName(String columnName) {
     this.columnName = columnName;
   }
   public String getLayoutName() {
     return this.layoutName;
   }
   public void setLayoutName(String layoutName) {
     this.layoutName = layoutName;
   }
   public String getWidths() {
     return this.widths;
   }
   public void setWidths(String widths) {
     this.widths = widths;
   }
   public String getColumnUrl() {
     return this.columnUrl;
   }
   public void setColumnUrl(String columnUrl) {
     this.columnUrl = columnUrl;
   }
   public String getServicemethod() {
     return this.servicemethod;
   }
   public void setServicemethod(String servicemethod) {
     this.servicemethod = servicemethod;
   }
   public String getColumnHtml() {
     return this.columnHtml;
   }
   public void setColumnHtml(String columnHtml) {
     this.columnHtml = columnHtml;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof DesktopMycolumn))
     {
       return false;
     }
     DesktopMycolumn rhs = (DesktopMycolumn)object;
     return new EqualsBuilder().append(this.id, rhs.id).append(this.userId, rhs.userId).append(this.layoutId, rhs.layoutId).append(this.columnId, rhs.columnId).append(this.col, rhs.col).append(this.sn, rhs.sn).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.userId).append(this.layoutId).append(this.columnId).append(this.col).append(this.sn).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("id", this.id).append("userId", this.userId).append("layoutId", this.layoutId).append("columnId", this.columnId).append("col", this.col).append("sn", this.sn).toString();
   }
 }

