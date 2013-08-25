 package org.sz.platform.bpm.model.flow;
 
 import org.sz.platform.bpm.model.flow.ReminderState;

 import java.util.Date;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class ReminderState extends BaseModel
 {
   protected Long id;
   protected String taskId;
   protected Date reminderTime;
   protected String actInstanceId = "";
 
   protected Long userId = Long.valueOf(0L);
 
   protected String actDefId = "";
 
   protected int remindType = 1;
 
   public void setId(Long id)
   {
     this.id = id;
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setTaskId(String taskId)
   {
     this.taskId = taskId;
   }
 
   public String getTaskId()
   {
     return this.taskId;
   }
 
   public void setReminderTime(Date reminderTime)
   {
     this.reminderTime = reminderTime;
   }
 
   public Date getReminderTime()
   {
     return this.reminderTime;
   }
 
   public String getActInstanceId() {
     return this.actInstanceId;
   }
   public void setActInstanceId(String actInstanceId) {
     this.actInstanceId = actInstanceId;
   }
   public Long getUserId() {
     return this.userId;
   }
   public void setUserId(Long userId) {
     this.userId = userId;
   }
 
   public String getActDefId() {
     return this.actDefId;
   }
   public void setActDefId(String actDefId) {
     this.actDefId = actDefId;
   }
 
   public int getRemindType() {
     return this.remindType;
   }
   public void setRemindType(int remindType) {
     this.remindType = remindType;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof ReminderState))
     {
       return false;
     }
     ReminderState rhs = (ReminderState)object;
     return new EqualsBuilder().append(this.id, rhs.id).append(this.taskId, rhs.taskId).append(this.reminderTime, rhs.reminderTime).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.taskId).append(this.reminderTime).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("id", this.id).append("taskId", this.taskId).append("reminderTime", this.reminderTime).toString();
   }
 }

