 package org.sz.platform.system.model;
 
 import org.sz.platform.system.model.RoleSys;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class RoleSys extends BaseModel
   implements Cloneable
 {
   protected Long id;
   protected Long roleid;
   protected Long systemid;
 
   public void setId(Long id)
   {
     this.id = id;
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setRoleid(Long roleid) {
     this.roleid = roleid;
   }
 
   public Long getRoleid()
   {
     return this.roleid;
   }
 
   public void setSystemid(Long systemid) {
     this.systemid = systemid;
   }
 
   public Long getSystemid()
   {
     return this.systemid;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof RoleSys))
     {
       return false;
     }
     RoleSys rhs = (RoleSys)object;
     return new EqualsBuilder().append(this.id, rhs.id).append(this.roleid, rhs.roleid).append(this.systemid, rhs.systemid).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.roleid).append(this.systemid).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("id", this.id).append("roleid", this.roleid).append("systemid", this.systemid).toString();
   }
 
   public Object clone()
   {
     RoleSys obj = null;
     try {
       obj = (RoleSys)super.clone();
     } catch (CloneNotSupportedException e) {
       e.printStackTrace();
     }
     return obj;
   }
 }

