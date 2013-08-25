 package org.sz.platform.system.model;
 
 import org.sz.platform.system.model.RoleResources;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class RoleResources extends BaseModel
   implements Cloneable
 {
   protected Long roleResId;
   protected Long roleId;
   protected Long resId;
   protected Long systemId;
 
   public void setRoleResId(Long roleResId)
   {
     this.roleResId = roleResId;
   }
 
   public Long getRoleResId()
   {
     return this.roleResId;
   }
 
   public void setRoleId(Long roleId)
   {
     this.roleId = roleId;
   }
 
   public Long getRoleId()
   {
     return this.roleId;
   }
 
   public void setResId(Long resId)
   {
     this.resId = resId;
   }
 
   public Long getResId()
   {
     return this.resId;
   }
 
   public Long getSystemId()
   {
     return this.systemId;
   }
   public void setSystemId(Long systemId) {
     this.systemId = systemId;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof RoleResources))
     {
       return false;
     }
     RoleResources rhs = (RoleResources)object;
     return new EqualsBuilder().append(this.roleResId, rhs.roleResId).append(this.roleId, rhs.roleId).append(this.resId, rhs.resId).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.roleResId).append(this.roleId).append(this.resId).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("roleResId", this.roleResId).append("roleId", this.roleId).append("resId", this.resId).toString();
   }
 
   public Object clone()
   {
     RoleResources obj = null;
     try {
       obj = (RoleResources)super.clone();
     } catch (CloneNotSupportedException e) {
       e.printStackTrace();
     }
     return obj;
   }
 }

