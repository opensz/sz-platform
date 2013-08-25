 package org.sz.platform.bpm.model.flow;
 
 import org.sz.platform.bpm.model.flow.BpmTaskComment;

 import java.util.Date;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class BpmTaskComment extends BaseModel
 {
   protected Long commentId;
   protected Long runId;
   protected Long authorId;
   protected String author;
   protected Date commentTime;
   protected String content;
   protected String nodeName;
   protected Long taskId;
   protected String actDefId;
 
   public void setCommentId(Long commentId)
   {
     this.commentId = commentId;
   }
 
   public Long getCommentId()
   {
     return this.commentId;
   }
 
   public void setRunId(Long runId)
   {
     this.runId = runId;
   }
 
   public Long getRunId()
   {
     return this.runId;
   }
 
   public void setAuthorId(Long authorId)
   {
     this.authorId = authorId;
   }
 
   public Long getAuthorId()
   {
     return this.authorId;
   }
 
   public void setAuthor(String author)
   {
     this.author = author;
   }
 
   public String getAuthor()
   {
     return this.author;
   }
 
   public void setCommentTime(Date commentTime)
   {
     this.commentTime = commentTime;
   }
 
   public Date getCommentTime()
   {
     return this.commentTime;
   }
 
   public void setContent(String content)
   {
     this.content = content;
   }
 
   public String getContent()
   {
     return this.content;
   }
 
   public void setNodeName(String nodeName)
   {
     this.nodeName = nodeName;
   }
 
   public String getNodeName()
   {
     return this.nodeName;
   }
 
   public void setTaskId(Long taskId)
   {
     this.taskId = taskId;
   }
 
   public Long getTaskId()
   {
     return this.taskId;
   }
 
   public void setActDefId(String actDefId)
   {
     this.actDefId = actDefId;
   }
 
   public String getActDefId()
   {
     return this.actDefId;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof BpmTaskComment))
     {
       return false;
     }
     BpmTaskComment rhs = (BpmTaskComment)object;
     return new EqualsBuilder().append(this.commentId, rhs.commentId).append(this.runId, rhs.runId).append(this.authorId, rhs.authorId).append(this.author, rhs.author).append(this.commentTime, rhs.commentTime).append(this.content, rhs.content).append(this.nodeName, rhs.nodeName).append(this.taskId, rhs.taskId).append(this.actDefId, rhs.actDefId).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.commentId).append(this.runId).append(this.authorId).append(this.author).append(this.commentTime).append(this.content).append(this.nodeName).append(this.taskId).append(this.actDefId).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("commentId", this.commentId).append("runId", this.runId).append("authorId", this.authorId).append("author", this.author).append("commentTime", this.commentTime).append("content", this.content).append("nodeName", this.nodeName).append("taskId", this.taskId).append("actDefId", this.actDefId).toString();
   }
 }

