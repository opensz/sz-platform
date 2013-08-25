 package org.sz.platform.system.model;
 
 import org.sz.platform.system.model.DesktopLayout;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class DesktopLayout extends BaseModel
 {
   protected Long id;
   protected String name;
   protected Short cols;
   protected String width;
   protected String memo;
   protected Integer isDefault;
 
   public void setId(Long id)
   {
     this.id = id;
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setName(String name)
   {
     this.name = name;
   }
 
   public String getName()
   {
     return this.name;
   }
 
   public void setCols(Short cols)
   {
     this.cols = cols;
   }
 
   public Short getCols()
   {
     return this.cols;
   }
 
   public void setWidth(String width)
   {
     this.width = width;
   }
 
   public String getWidth()
   {
     return this.width;
   }
 
   public void setMemo(String memo)
   {
     this.memo = memo;
   }
 
   public String getMemo()
   {
     return this.memo;
   }
 
   public void setIsDefault(Integer isDefault)
   {
     this.isDefault = isDefault;
   }
 
   public Integer getIsDefault()
   {
     return this.isDefault;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof DesktopLayout))
     {
       return false;
     }
     DesktopLayout rhs = (DesktopLayout)object;
     return new EqualsBuilder().append(this.id, rhs.id).append(this.name, rhs.name).append(this.cols, rhs.cols).append(this.width, rhs.width).append(this.memo, rhs.memo).append(this.isDefault, rhs.isDefault).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.name).append(this.cols).append(this.width).append(this.memo).append(this.isDefault).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("id", this.id).append("name", this.name).append("cols", this.cols).append("width", this.width).append("memo", this.memo).append("isDefault", this.isDefault).toString();
   }
 }

