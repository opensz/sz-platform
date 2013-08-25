 package org.sz.platform.bpm.model.flow;
 
 import org.sz.platform.bpm.model.flow.BpmBusLinkData;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class BpmBusLinkData extends BaseModel
 {
   protected Long dataId;
   protected String tableName;
   protected String pkName;
   protected Long runId;
   protected String pkValue;
   protected String actDefId;
   protected Long userId = Long.valueOf(0L);
 
   public void setDataId(Long dataId)
   {
     this.dataId = dataId;
   }
 
   public Long getDataId()
   {
     return this.dataId;
   }
 
   public void setTableName(String tableName)
   {
     this.tableName = tableName;
   }
 
   public String getTableName()
   {
     return this.tableName;
   }
 
   public void setPkName(String pkName)
   {
     this.pkName = pkName;
   }
 
   public String getPkName()
   {
     return this.pkName;
   }
 
   public void setRunId(Long runId)
   {
     this.runId = runId;
   }
 
   public Long getRunId()
   {
     return this.runId;
   }
 
   public void setPkValue(String pkValue)
   {
     this.pkValue = pkValue;
   }
 
   public String getPkValue()
   {
     return this.pkValue;
   }
 
   public void setActDefId(String actDefId)
   {
     this.actDefId = actDefId;
   }
 
   public String getActDefId()
   {
     return this.actDefId;
   }
 
   public Long getUserId()
   {
     return this.userId;
   }
   public void setUserId(Long userId) {
     this.userId = userId;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof BpmBusLinkData))
     {
       return false;
     }
     BpmBusLinkData rhs = (BpmBusLinkData)object;
     return new EqualsBuilder().append(this.dataId, rhs.dataId).append(this.tableName, rhs.tableName).append(this.pkName, rhs.pkName).append(this.runId, rhs.runId).append(this.pkValue, rhs.pkValue).append(this.actDefId, rhs.actDefId).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.dataId).append(this.tableName).append(this.pkName).append(this.runId).append(this.pkValue).append(this.actDefId).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("dataId", this.dataId).append("tableName", this.tableName).append("pkName", this.pkName).append("runId", this.runId).append("pkValue", this.pkValue).append("actDefId", this.actDefId).toString();
   }
 }

