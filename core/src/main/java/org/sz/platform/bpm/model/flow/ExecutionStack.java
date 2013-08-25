 package org.sz.platform.bpm.model.flow;
 
 import org.sz.platform.bpm.model.flow.ExecutionStack;

 import java.util.Date;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class ExecutionStack extends BaseModel
 {
   public static final Short MULTI_TASK = 1;
 
   public static final Short COMMON_TASK = 0;
   protected Long stackId;
   protected String nodeId;
   protected String nodeName;
   protected Date startTime;
   protected Date endTime;
   protected String assignees;
   protected Short isMultiTask;
   protected Long parentId;
   protected String actInstId;
   protected String taskIds;
   protected String nodePath;
   protected Integer depth;
   protected String actDefId;
   protected String taskToken;
 
   public void setStackId(Long stackId)
   {
     this.stackId = stackId;
   }
 
   public Long getStackId()
   {
     return this.stackId;
   }
 
   public void setNodeId(String nodeId)
   {
     this.nodeId = nodeId;
   }
 
   public String getNodeId()
   {
     return this.nodeId;
   }
 
   public void setNodeName(String nodeName)
   {
     this.nodeName = nodeName;
   }
 
   public String getNodeName()
   {
     return this.nodeName;
   }
 
   public void setStartTime(Date startTime)
   {
     this.startTime = startTime;
   }
 
   public Date getStartTime()
   {
     return this.startTime;
   }
 
   public void setEndTime(Date endTime)
   {
     this.endTime = endTime;
   }
 
   public Date getEndTime()
   {
     return this.endTime;
   }
 
   public void setAssignees(String assignees)
   {
     this.assignees = assignees;
   }
 
   public String getAssignees()
   {
     return this.assignees;
   }
 
   public void setIsMultiTask(Short isMultiTask)
   {
     this.isMultiTask = isMultiTask;
   }
 
   public Short getIsMultiTask()
   {
     return this.isMultiTask;
   }
 
   public void setParentId(Long parentId)
   {
     this.parentId = parentId;
   }
 
   public Long getParentId()
   {
     return this.parentId;
   }
 
   public void setActInstId(String actInstId)
   {
     this.actInstId = actInstId;
   }
 
   public String getActInstId()
   {
     return this.actInstId;
   }
 
   public void setTaskIds(String taskIds)
   {
     this.taskIds = taskIds;
   }
 
   public String getTaskIds()
   {
     return this.taskIds;
   }
 
   public void setNodePath(String nodePath)
   {
     this.nodePath = nodePath;
   }
 
   public String getNodePath()
   {
     return this.nodePath;
   }
 
   public void setDepth(Integer depth)
   {
     this.depth = depth;
   }
 
   public Integer getDepth()
   {
     return this.depth;
   }
 
   public String getActDefId() {
     return this.actDefId;
   }
 
   public void setActDefId(String actDefId) {
     this.actDefId = actDefId;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof ExecutionStack))
     {
       return false;
     }
     ExecutionStack rhs = (ExecutionStack)object;
     return new EqualsBuilder().append(this.stackId, rhs.stackId).append(this.nodeId, rhs.nodeId).append(this.nodeName, rhs.nodeName).append(this.startTime, rhs.startTime).append(this.endTime, rhs.endTime).append(this.assignees, rhs.assignees).append(this.isMultiTask, rhs.isMultiTask).append(this.parentId, rhs.parentId).append(this.actInstId, rhs.actInstId).append(this.taskIds, rhs.taskIds).append(this.nodePath, rhs.nodePath).append(this.depth, rhs.depth).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.stackId).append(this.nodeId).append(this.nodeName).append(this.startTime).append(this.endTime).append(this.assignees).append(this.isMultiTask).append(this.parentId).append(this.actInstId).append(this.taskIds).append(this.nodePath).append(this.depth).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("stackId", this.stackId).append("nodeId", this.nodeId).append("nodeName", this.nodeName).append("startTime", this.startTime).append("endTime", this.endTime).append("assignees", this.assignees).append("isMultiTask", this.isMultiTask).append("parentId", this.parentId).append("actInstId", this.actInstId).append("taskIds", this.taskIds).append("nodePath", this.nodePath).append("depth", this.depth).toString();
   }
 
   public String getTaskToken()
   {
     return this.taskToken;
   }
   public void setTaskToken(String taskToken) {
     this.taskToken = taskToken;
   }
 }

