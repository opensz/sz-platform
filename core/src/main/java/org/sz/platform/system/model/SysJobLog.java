 package org.sz.platform.system.model;
 
 import org.sz.platform.system.model.SysJobLog;

 import java.util.Date;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class SysJobLog extends BaseModel
 {
   protected Long logId;
   protected String jobName;
   protected String trigName;
   protected Date startTime;
   protected Date endTime;
   protected String content;
   protected int state;
   protected Long runTime;
 
   public void setLogId(Long logId)
   {
     this.logId = logId;
   }
 
   public Long getLogId()
   {
     return this.logId;
   }
 
   public void setJobName(String jobName)
   {
     this.jobName = jobName;
   }
 
   public String getJobName()
   {
     return this.jobName;
   }
 
   public void setTrigName(String trigName)
   {
     this.trigName = trigName;
   }
 
   public String getTrigName()
   {
     return this.trigName;
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
 
   public void setContent(String content)
   {
     this.content = content;
   }
 
   public String getContent()
   {
     return this.content;
   }
 
   public void setState(int state)
   {
     this.state = state;
   }
 
   public int getState()
   {
     return this.state;
   }
 
   public void setRunTime(Long runTime)
   {
     this.runTime = runTime;
   }
 
   public Long getRunTime()
   {
     return this.runTime;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof SysJobLog))
     {
       return false;
     }
     SysJobLog rhs = (SysJobLog)object;
     return new EqualsBuilder().append(this.logId, rhs.logId).append(this.jobName, rhs.jobName).append(this.trigName, rhs.trigName).append(this.startTime, rhs.startTime).append(this.endTime, rhs.endTime).append(this.content, rhs.content).append(this.state, rhs.state).append(this.runTime, rhs.runTime).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.logId).append(this.jobName).append(this.trigName).append(this.startTime).append(this.endTime).append(this.content).append(this.state).append(this.runTime).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("logId", this.logId).append("jobName", this.jobName).append("trigName", this.trigName).append("startTime", this.startTime).append("endTime", this.endTime).append("content", this.content).append("state", this.state).append("runTime", this.runTime).toString();
   }
 }

