 package org.sz.platform.model.system;
 
 import org.sz.platform.model.system.SysOrgParam;

 import java.util.Date;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class SysOrgParam extends BaseModel
 {
   protected Long valueId;
   protected Long orgId;
   protected Long paramId;
   protected String paramValue;
   protected String paramName;
   protected String dataType;
   protected Long paramIntValue;
   protected Date paramDateValue;
 
   public Long getParamIntValue()
   {
     return this.paramIntValue;
   }
   public void setParamIntValue(Long paramIntValue) {
     this.paramIntValue = paramIntValue;
   }
   public Date getParamDateValue() {
     return this.paramDateValue;
   }
   public void setParamDateValue(Date paramDateValue) {
     this.paramDateValue = paramDateValue;
   }
   public String getParamName() {
     return this.paramName;
   }
   public void setParamName(String paramName) {
     this.paramName = paramName;
   }
   public String getDataType() {
     return this.dataType;
   }
   public void setDataType(String dataType) {
     this.dataType = dataType;
   }
 
   public void setValueId(Long valueId) {
     this.valueId = valueId;
   }
 
   public Long getValueId()
   {
     return this.valueId;
   }
 
   public void setOrgId(Long orgId)
   {
     this.orgId = orgId;
   }
 
   public Long getOrgId()
   {
     return this.orgId;
   }
 
   public void setParamId(Long paramId)
   {
     this.paramId = paramId;
   }
 
   public Long getParamId()
   {
     return this.paramId;
   }
 
   public void setParamValue(String paramValue)
   {
     this.paramValue = paramValue;
   }
 
   public String getParamValue()
   {
     return this.paramValue;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof SysOrgParam))
     {
       return false;
     }
     SysOrgParam rhs = (SysOrgParam)object;
     return new EqualsBuilder().append(this.valueId, rhs.valueId).append(this.orgId, rhs.orgId).append(this.paramId, rhs.paramId).append(this.paramValue, rhs.paramValue).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.valueId).append(this.orgId).append(this.paramId).append(this.paramValue).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("valueId", this.valueId).append("orgId", this.orgId).append("paramId", this.paramId).append("paramValue", this.paramValue).toString();
   }
 }

