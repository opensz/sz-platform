 package org.sz.platform.bpm.model.flow;
 
 import org.sz.platform.bpm.model.flow.BpmNodeSign;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class BpmNodeSign extends BaseModel
   implements Cloneable
 {
   public static final String TABLE_NAME = "BPM_NODE_SIGN";
   public static final Short VOTE_TYPE_PERCENT = 1;
 
   public static final Short VOTE_TYPE_ABSOLUTE = 2;
 
   public static final Short ADD_ALLOWED = 1;
 
   public static final Short ADD_DENY = 0;
 
   public static final Short DECIDE_TYPE_PASS = 1;
 
   public static final Short DECIDE_TYPE_REFUSE = 2;
 
   protected Long signId = Long.valueOf(0L);
 
   protected String nodeId = " ";
 
   protected Long voteAmount = Long.valueOf(0L);
 
   protected Short decideType = 0;
 
   protected Short voteType = 0;
 
   protected String actDefId = " ";
 
   protected Short isAllowAdd = ADD_DENY;
 
   public void setSignId(Long signId)
   {
     this.signId = signId;
   }
 
   public Long getSignId()
   {
     return this.signId;
   }
 
   public void setNodeId(String nodeId)
   {
     this.nodeId = nodeId;
   }
 
   public String getNodeId()
   {
     return this.nodeId;
   }
 
   public void setVoteAmount(Long voteAmount)
   {
     this.voteAmount = voteAmount;
   }
 
   public Long getVoteAmount()
   {
     return this.voteAmount;
   }
 
   public void setDecideType(Short decideType)
   {
     this.decideType = decideType;
   }
 
   public Short getDecideType()
   {
     return this.decideType;
   }
 
   public void setVoteType(Short voteType)
   {
     this.voteType = voteType;
   }
 
   public Short getVoteType()
   {
     return this.voteType;
   }
 
   public void setActDefId(String actDefId)
   {
     this.actDefId = actDefId;
   }
 
   public String getActDefId()
   {
     return this.actDefId;
   }
 
   public Short getIsAllowAdd() {
     return this.isAllowAdd;
   }
 
   public void setIsAllowAdd(Short isAllowAdd) {
     this.isAllowAdd = isAllowAdd;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof BpmNodeSign))
     {
       return false;
     }
     BpmNodeSign rhs = (BpmNodeSign)object;
     return new EqualsBuilder().append(this.signId, rhs.signId).append(this.nodeId, rhs.nodeId).append(this.voteAmount, rhs.voteAmount).append(this.decideType, rhs.decideType).append(this.voteType, rhs.voteType).append(this.actDefId, rhs.actDefId).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.signId).append(this.nodeId).append(this.voteAmount).append(this.decideType).append(this.voteType).append(this.actDefId).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("signId", this.signId).append("nodeId", this.nodeId).append("voteAmount", this.voteAmount).append("decideType", this.decideType).append("voteType", this.voteType).append("actDeployId", this.actDefId).toString();
   }
 
   public Object clone()
   {
     BpmNodeSign obj = null;
     try {
       obj = (BpmNodeSign)super.clone();
     } catch (CloneNotSupportedException e) {
       e.printStackTrace();
     }
     return obj;
   }
 }

