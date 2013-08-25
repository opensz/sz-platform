 package org.sz.platform.bpm.model.flow;
 
 import org.sz.platform.bpm.model.flow.BpmDefVar;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class BpmDefVar extends BaseModel
   implements Cloneable
 {
   public static final String TABLE_NAME = "BPM_DEF_VARS";
   protected Long varId;
   protected Long defId;
   protected String varName;
   protected String varKey;
   protected String varDataType;
   protected String defValue;
   protected String nodeName;
   protected Long versionId;
   protected Long actDeployId;
   protected String nodeId;
   protected String varScope;
 
   public void setVarId(Long varId)
   {
     this.varId = varId;
   }
 
   public Long getVarId()
   {
     return this.varId;
   }
 
   public void setDefId(Long defId)
   {
     this.defId = defId;
   }
 
   public Long getDefId()
   {
     return this.defId;
   }
 
   public void setVarName(String varName)
   {
     this.varName = varName;
   }
 
   public String getVarName()
   {
     return this.varName;
   }
 
   public void setVarKey(String varKey)
   {
     this.varKey = varKey;
   }
 
   public String getVarKey()
   {
     return this.varKey;
   }
 
   public void setVarDataType(String varDataType)
   {
     this.varDataType = varDataType;
   }
 
   public String getVarDataType()
   {
     return this.varDataType;
   }
 
   public void setDefValue(String defValue)
   {
     this.defValue = defValue;
   }
 
   public String getDefValue()
   {
     return this.defValue;
   }
 
   public void setNodeName(String nodeName)
   {
     this.nodeName = nodeName;
   }
 
   public String getNodeName()
   {
     return this.nodeName;
   }
 
   public void setVersionId(Long versionId)
   {
     this.versionId = versionId;
   }
 
   public Long getVersionId()
   {
     return this.versionId;
   }
 
   public void setActDeployId(Long actDeployId)
   {
     this.actDeployId = actDeployId;
   }
 
   public Long getActDeployId()
   {
     return this.actDeployId;
   }
 
   public void setNodeId(String nodeId)
   {
     this.nodeId = nodeId;
   }
 
   public String getNodeId()
   {
     return this.nodeId;
   }
 
   public void setVarScope(String varScope)
   {
     this.varScope = varScope;
   }
 
   public String getVarScope()
   {
     return this.varScope;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof BpmDefVar))
     {
       return false;
     }
     BpmDefVar rhs = (BpmDefVar)object;
     return new EqualsBuilder().append(this.varId, rhs.varId).append(this.defId, rhs.defId).append(this.varName, rhs.varName).append(this.varKey, rhs.varKey).append(this.varDataType, rhs.varDataType).append(this.defValue, rhs.defValue).append(this.nodeName, rhs.nodeName).append(this.versionId, rhs.versionId).append(this.actDeployId, rhs.actDeployId).append(this.nodeId, rhs.nodeId).append(this.varScope, rhs.varScope).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.varId).append(this.defId).append(this.varName).append(this.varKey).append(this.varDataType).append(this.defValue).append(this.nodeName).append(this.versionId).append(this.actDeployId).append(this.nodeId).append(this.varScope).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("varId", this.varId).append("defId", this.defId).append("varName", this.varName).append("varKey", this.varKey).append("varDataType", this.varDataType).append("defValue", this.defValue).append("nodeName", this.nodeName).append("versionId", this.versionId).append("actDeployId", this.actDeployId).append("nodeId", this.nodeId).append("varScope", this.varScope).toString();
   }
 
   public Object clone()
   {
     BpmDefVar obj = null;
     try {
       obj = (BpmDefVar)super.clone();
     } catch (CloneNotSupportedException e) {
       e.printStackTrace();
     }
     return obj;
   }
 }

