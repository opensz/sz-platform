 package org.sz.platform.bpm.model.flow;
 
 import org.sz.platform.bpm.model.flow.TaskSignData;

 import java.util.Date;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class TaskSignData extends BaseModel
 {
   public static Short AGREE = 1;
 
   public static Short REFUSE = 2;
 
   public static Short ABORT = 0;
 
   public static Short BACK = 3;
 
   public static Short COMPLETED = 1;
 
   public static Short NOT_COMPLETED = 0;
   protected Long dataId;
   protected String actInstId;
   protected String nodeName;
   protected String nodeId;
   protected String taskId;
   protected Long voteUserId;
   protected String voteUserName;
   protected Date voteTime;
   protected Short isAgree;
   protected String content;
   protected Integer signNums;
   protected Short isCompleted;
   protected String actDefId;
 
   public String getActDefId()
   {
     return this.actDefId;
   }
   public void setActDefId(String actDefId) {
     this.actDefId = actDefId;
   }
 
   public void setDataId(Long dataId) {
     this.dataId = dataId;
   }
 
   public Long getDataId()
   {
     return this.dataId;
   }
 
   public void setActInstId(String actInstId)
   {
     this.actInstId = actInstId;
   }
 
   public String getActInstId()
   {
     return this.actInstId;
   }
 
   public void setNodeName(String nodeName)
   {
     this.nodeName = nodeName;
   }
 
   public String getNodeName()
   {
     return this.nodeName;
   }
 
   public void setNodeId(String nodeId)
   {
     this.nodeId = nodeId;
   }
 
   public String getNodeId()
   {
     return this.nodeId;
   }
 
   public void setTaskId(String taskId)
   {
     this.taskId = taskId;
   }
 
   public String getTaskId()
   {
     return this.taskId;
   }
 
   public void setVoteUserId(Long voteUserId)
   {
     this.voteUserId = voteUserId;
   }
 
   public Long getVoteUserId()
   {
     return this.voteUserId;
   }
 
   public void setVoteUserName(String voteUserName)
   {
     this.voteUserName = voteUserName;
   }
 
   public String getVoteUserName()
   {
     return this.voteUserName;
   }
 
   public void setVoteTime(Date voteTime)
   {
     this.voteTime = voteTime;
   }
 
   public Date getVoteTime()
   {
     return this.voteTime;
   }
 
   public void setIsAgree(Short isAgree)
   {
     this.isAgree = isAgree;
   }
 
   public Short getIsAgree()
   {
     return this.isAgree;
   }
 
   public void setContent(String content)
   {
     this.content = content;
   }
 
   public String getContent()
   {
     return this.content;
   }
 
   public void setSignNums(Integer signNums)
   {
     this.signNums = signNums;
   }
 
   public Integer getSignNums()
   {
     return this.signNums;
   }
 
   public void setIsCompleted(Short isCompleted)
   {
     this.isCompleted = isCompleted;
   }
 
   public Short getIsCompleted()
   {
     return this.isCompleted;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof TaskSignData))
     {
       return false;
     }
     TaskSignData rhs = (TaskSignData)object;
     return new EqualsBuilder().append(this.dataId, rhs.dataId).append(this.actInstId, rhs.actInstId).append(this.nodeName, rhs.nodeName).append(this.nodeId, rhs.nodeId).append(this.taskId, rhs.taskId).append(this.voteUserId, rhs.voteUserId).append(this.voteUserName, rhs.voteUserName).append(this.voteTime, rhs.voteTime).append(this.isAgree, rhs.isAgree).append(this.content, rhs.content).append(this.signNums, rhs.signNums).append(this.isCompleted, rhs.isCompleted).append(this.actDefId, rhs.actDefId).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.dataId).append(this.actInstId).append(this.nodeName).append(this.nodeId).append(this.taskId).append(this.voteUserId).append(this.voteUserName).append(this.voteTime).append(this.isAgree).append(this.content).append(this.signNums).append(this.isCompleted).append(this.actDefId).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("dataId", this.dataId).append("actInstId", this.actInstId).append("nodeName", this.nodeName).append("nodeId", this.nodeId).append("taskId", this.taskId).append("voteUserId", this.voteUserId).append("voteUserName", this.voteUserName).append("voteTime", this.voteTime).append("isAgree", this.isAgree).append("content", this.content).append("signNums", this.signNums).append("isCompleted", this.isCompleted).append("actDefId", this.actDefId).toString();
   }
 }

