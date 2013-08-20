 package org.sz.platform.model.system;
 
 import org.sz.platform.model.system.DesktopColumn;
import org.sz.platform.model.system.DesktopLayoutcol;

 import java.util.List;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class DesktopLayoutcol extends BaseModel
 {
   protected Long id;
   protected Long layoutId;
   protected Long columnId;
   protected Integer col;
   protected Integer sn;
   protected String columnName;
   protected String layoutName;
   protected String widths;
   protected String columnUrl;
   protected List<DesktopColumn> desktopColumnList;
 
   public void setId(Long id)
   {
     this.id = id;
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setLayoutId(Long layoutId)
   {
     this.layoutId = layoutId;
   }
 
   public Long getLayoutId()
   {
     return this.layoutId;
   }
 
   public String getColumnUrl() {
     return this.columnUrl;
   }
   public void setColumnUrl(String columnUrl) {
     this.columnUrl = columnUrl;
   }
 
   public void setColumnId(Long columnId) {
     this.columnId = columnId;
   }
 
   public Long getColumnId()
   {
     return this.columnId;
   }
 
   public void setCol(Integer col)
   {
     this.col = col;
   }
 
   public Integer getCol()
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
 
   public String getLayoutName()
   {
     return this.layoutName;
   }
   public void setLayoutName(String layoutName) {
     this.layoutName = layoutName;
   }
   public String getColumnName() {
     return this.columnName;
   }
   public void setColumnName(String columnName) {
     this.columnName = columnName;
   }
   public String getWidths() {
     return this.widths;
   }
   public void setWidths(String widths) {
     this.widths = widths;
   }
 
   public List<DesktopColumn> getDesktopColumnList()
   {
     return this.desktopColumnList;
   }
   public void setDesktopColumnList(List<DesktopColumn> desktopColumnList) {
     this.desktopColumnList = desktopColumnList;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof DesktopLayoutcol))
     {
       return false;
     }
     DesktopLayoutcol rhs = (DesktopLayoutcol)object;
     return new EqualsBuilder().append(this.id, rhs.id).append(this.layoutId, rhs.layoutId).append(this.columnId, rhs.columnId).append(this.col, rhs.col).append(this.sn, rhs.sn).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.layoutId).append(this.columnId).append(this.col).append(this.sn).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("id", this.id).append("layoutId", this.layoutId).append("columnId", this.columnId).append("col", this.col).append("sn", this.sn).toString();
   }
 }

