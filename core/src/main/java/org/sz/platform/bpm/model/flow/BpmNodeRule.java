 package org.sz.platform.bpm.model.flow;
 
 import org.sz.platform.bpm.model.flow.BpmNodeRule;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class BpmNodeRule extends BaseModel
   implements Cloneable
 {
   private static final long serialVersionUID = 1L;
   public static final String TABLE_NAME = "BPM_NODE_RULE";
   protected Long ruleId = Long.valueOf(0L);
 
   protected String ruleName = "";
 
   protected String conditionCode = "";
 
   protected String actDefId = "";
 
   protected String nodeId = "";
 
   protected Long priority = Long.valueOf(0L);
 
   protected String targetNode = "";
 
   protected String targetNodeName = "";
 
   protected String memo = " ";
 
   public void setRuleId(Long ruleId)
   {
     this.ruleId = ruleId;
   }
 
   public Long getRuleId()
   {
     return this.ruleId;
   }
 
   public void setRuleName(String ruleName)
   {
     this.ruleName = ruleName;
   }
 
   public String getRuleName()
   {
     return this.ruleName;
   }
 
   public void setConditionCode(String conditionCode)
   {
     this.conditionCode = conditionCode;
   }
 
   public String getConditionCode()
   {
     return this.conditionCode;
   }
 
   public void setActDefId(String actDefId)
   {
     this.actDefId = actDefId;
   }
 
   public String getActDefId()
   {
     return this.actDefId;
   }
 
   public void setPriority(Long priority)
   {
     this.priority = priority;
   }
 
   public Long getPriority()
   {
     return this.priority;
   }
 
   public void setTargetNode(String targetNode)
   {
     this.targetNode = targetNode;
   }
 
   public String getTargetNode()
   {
     return this.targetNode;
   }
 
   public void setTargetNodeName(String targetNodeName)
   {
     this.targetNodeName = targetNodeName;
   }
 
   public String getTargetNodeName()
   {
     return this.targetNodeName;
   }
 
   public void setMemo(String memo)
   {
     this.memo = memo;
   }
 
   public String getMemo()
   {
     return this.memo;
   }
 
   public String getNodeId()
   {
     return this.nodeId;
   }
 
   public void setNodeId(String nodeId) {
     this.nodeId = nodeId;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof BpmNodeRule))
     {
       return false;
     }
     BpmNodeRule rhs = (BpmNodeRule)object;
     return new EqualsBuilder().append(this.actDefId, rhs.actDefId).append(this.nodeId, rhs.nodeId).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.actDefId).append(this.nodeId).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("ruleId", this.ruleId).append("ruleName", this.ruleName).append("conditionCode", this.conditionCode).append("actDefId", this.actDefId).append("nodeId", this.nodeId).append("priority", this.priority).append("targetNode", this.targetNode).append("targetNodeName", this.targetNodeName).append("memo", this.memo).toString();
   }
 
   public Object clone()
   {
     BpmNodeRule obj = null;
     try {
       obj = (BpmNodeRule)super.clone();
     } catch (CloneNotSupportedException e) {
       e.printStackTrace();
     }
     return obj;
   }
 }

