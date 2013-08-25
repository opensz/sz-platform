 package org.sz.platform.bpm.model.flow;
 
 import org.sz.platform.bpm.model.flow.BpmNodeScript;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class BpmNodeScript extends BaseModel
   implements Cloneable
 {
   public static final String TABLE_NAME = "BPM_NODE_SCRIPT";
   protected Long id;
   protected String memo;
   protected String nodeId;
   protected String actDefId;
   protected String script;
   protected Integer scriptType;
 
   public void setId(Long id)
   {
     this.id = id;
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setMemo(String memo)
   {
     this.memo = memo;
   }
 
   public String getMemo()
   {
     return this.memo;
   }
 
   public void setNodeId(String nodeId)
   {
     this.nodeId = nodeId;
   }
 
   public String getNodeId()
   {
     return this.nodeId;
   }
 
   public void setActDefId(String actDefId)
   {
     this.actDefId = actDefId;
   }
 
   public String getActDefId()
   {
     return this.actDefId;
   }
 
   public void setScript(String script)
   {
     this.script = script;
   }
 
   public String getScript()
   {
     return this.script;
   }
 
   public void setScriptType(Integer scriptType)
   {
     this.scriptType = scriptType;
   }
 
   public Integer getScriptType()
   {
     return this.scriptType;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof BpmNodeScript))
     {
       return false;
     }
     BpmNodeScript rhs = (BpmNodeScript)object;
     return new EqualsBuilder().append(this.id, rhs.id).append(this.memo, rhs.memo).append(this.nodeId, rhs.nodeId).append(this.actDefId, rhs.actDefId).append(this.script, rhs.script).append(this.scriptType, rhs.scriptType).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.memo).append(this.nodeId).append(this.actDefId).append(this.script).append(this.scriptType).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("id", this.id).append("memo", this.memo).append("nodeId", this.nodeId).append("actDefId", this.actDefId).append("script", this.script).append("scriptType", this.scriptType).toString();
   }
 
   public Object clone()
   {
     BpmNodeScript obj = null;
     try {
       obj = (BpmNodeScript)super.clone();
     } catch (CloneNotSupportedException e) {
       e.printStackTrace();
     }
     return obj;
   }
 }

