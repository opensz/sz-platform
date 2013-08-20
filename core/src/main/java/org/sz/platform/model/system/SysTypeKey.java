 package org.sz.platform.model.system;
 
 import org.sz.platform.model.system.SysTypeKey;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class SysTypeKey extends BaseModel
 {
   protected Long typeId = Long.valueOf(0L);
   protected String typeKey;
   protected String typeName;
   protected Integer sn = Integer.valueOf(0);
 
   protected Integer flag = Integer.valueOf(0);
 
   protected Integer type = Integer.valueOf(0);
 
   public Integer getFlag()
   {
     return this.flag;
   }
 
   public void setFlag(Integer flag) {
     this.flag = flag;
   }
 
   public Integer getType() {
     return this.type;
   }
 
   public void setType(Integer type) {
     this.type = type;
   }
 
   public void setTypeId(Long typeId) {
     this.typeId = typeId;
   }
 
   public Long getTypeId()
   {
     return this.typeId;
   }
 
   public void setTypeKey(String typeKey)
   {
     this.typeKey = typeKey;
   }
 
   public String getTypeKey()
   {
     return this.typeKey;
   }
 
   public void setTypeName(String typeName)
   {
     this.typeName = typeName;
   }
 
   public String getTypeName()
   {
     return this.typeName;
   }
 
   public void setSn(Integer sn)
   {
     this.sn = sn;
   }
 
   public Integer getSn()
   {
     return this.sn;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof SysTypeKey))
     {
       return false;
     }
     SysTypeKey rhs = (SysTypeKey)object;
     return new EqualsBuilder().append(this.typeId, rhs.typeId).append(this.typeKey, rhs.typeKey).append(this.typeName, rhs.typeName).append(this.sn, rhs.sn).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.typeId).append(this.typeKey).append(this.typeName).append(this.sn).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("typeId", this.typeId).append("typeKey", this.typeKey).append("typeName", this.typeName).append("sn", this.sn).toString();
   }
 }

