 package org.sz.platform.bpm.model.flow;
 
 import org.sz.platform.bpm.model.flow.TaskApprovalItems;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class TaskApprovalItems extends BaseModel
 {
   public static final Short global = 1;
 
   public static final Short notGlobal = 0;
   protected Long itemId;
   protected Long setId;
   protected String actDefId;
   protected String nodeId;
   protected Short isGlobal;
   protected String expItems;
 
   public Long getItemId()
   {
     return this.itemId;
   }
 
   public void setItemId(Long itemId) {
     this.itemId = itemId;
   }
 
   public Long getSetId() {
     return this.setId;
   }
 
   public void setSetId(Long setId) {
     this.setId = setId;
   }
 
   public String getActDefId() {
     return this.actDefId;
   }
 
   public void setActDefId(String actDefId) {
     this.actDefId = actDefId;
   }
 
   public String getNodeId() {
     return this.nodeId;
   }
 
   public void setNodeId(String nodeId) {
     this.nodeId = nodeId;
   }
 
   public Short getIsGlobal() {
     return this.isGlobal;
   }
 
   public void setIsGlobal(Short isGlobal) {
     this.isGlobal = isGlobal;
   }
 
   public String getExpItems() {
     return this.expItems;
   }
 
   public void setExpItems(String expItems) {
     this.expItems = expItems;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof TaskApprovalItems))
     {
       return false;
     }
     TaskApprovalItems rhs = (TaskApprovalItems)object;
     return new EqualsBuilder().append(this.itemId, rhs.itemId).append(this.setId, rhs.setId).append(this.actDefId, rhs.actDefId).append(this.nodeId, rhs.nodeId).append(this.isGlobal, rhs.isGlobal).append(this.expItems, rhs.expItems).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.itemId).append(this.setId).append(this.actDefId).append(this.nodeId).append(this.isGlobal).append(this.expItems).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("itemId", this.itemId).append("setId", this.setId).append("actDefId", this.actDefId).append("nodeId", this.nodeId).append("isGlobal", this.isGlobal).append("expItems", this.expItems).toString();
   }
 }

