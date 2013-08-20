 package org.sz.platform.model.system;
 
 import org.sz.platform.model.system.MessageSend;

 import java.util.Date;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class MessageSend extends BaseModel
 {
   public static final String MESSAGETYPE_PERSON = "1";
   public static final String MESSAGETYPE_SCHEDULE = "2";
   public static final String MESSAGETYPE_PLAN = "3";
   public static final String MESSAGETYPE_SYSTEM = "4";
   public static final String MESSAGETYPE_AGENCY = "5";
   public static final String MESSAGETYPE_FLOWTASK = "6";
   public static final String MESSAGETYPE_CONTRACTPAY = "7";
   public static final String MESSAGECTRATEBY ="1";
   
   public static final String SPLIT_FLAG = "[userorg]";
   protected Long id;
   protected String subject;
   protected Long userId;
   protected String userName;
   protected String messageType;
   protected String content;
   protected Date sendTime;
   protected Short canReply;
   protected String receiverName;
   protected String receiverOrgName;
   protected Date receiveTime;
   protected Long rid;
 
   public Long getRid()
   {
     return this.rid;
   }
 
   public void setRid(Long rid) {
     this.rid = rid;
   }
 
   public Date getReceiveTime() {
     return this.receiveTime;
   }
 
   public void setReceiveTime(Date receiveTime) {
     this.receiveTime = receiveTime;
   }
 
   public void setId(Long id) {
     this.id = id;
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setSubject(String subject)
   {
     this.subject = subject;
   }
 
   public String getSubject()
   {
     return this.subject;
   }
 
   public void setUserId(Long userId)
   {
     this.userId = userId;
   }
 
   public Long getUserId()
   {
     return this.userId;
   }
 
   public void setUserName(String userName)
   {
     this.userName = userName;
   }
 
   public String getUserName()
   {
     return this.userName;
   }
 
   public void setMessageType(String messageType)
   {
     this.messageType = messageType;
   }
 
   public String getMessageType()
   {
     return this.messageType;
   }
 
   public void setContent(String content)
   {
     this.content = content;
   }
 
   public String getContent()
   {
     return this.content;
   }
 
   public void setSendTime(Date sendTime)
   {
     this.sendTime = sendTime;
   }
 
   public Date getSendTime()
   {
     return this.sendTime;
   }
 
   public void setCanReply(Short canReply)
   {
     this.canReply = canReply;
   }
 
   public Short getCanReply()
   {
     return this.canReply;
   }
 
   public void setReceiverName(String receiverName)
   {
     this.receiverName = receiverName;
   }
 
   public String getReceiverName()
   {
     return this.receiverName;
   }
 
   public String getReceiverOrgName()
   {
     return this.receiverOrgName;
   }
   public void setReceiverOrgName(String receiverOrgName) {
     this.receiverOrgName = receiverOrgName;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof MessageSend))
     {
       return false;
     }
     MessageSend rhs = (MessageSend)object;
     return new EqualsBuilder().append(this.id, rhs.id).append(this.subject, rhs.subject).append(this.userId, rhs.userId).append(this.userName, rhs.userName).append(this.messageType, rhs.messageType).append(this.content, rhs.content).append(this.sendTime, rhs.sendTime).append(this.canReply, rhs.canReply).append(this.receiverName, rhs.receiverName).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.subject).append(this.userId).append(this.userName).append(this.messageType).append(this.content).append(this.sendTime).append(this.canReply).append(this.receiverName).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("id", this.id).append("subject", this.subject).append("userId", this.userId).append("userName", this.userName).append("messageType", this.messageType).append("content", this.content).append("sendTime", this.sendTime).append("canReply", this.canReply).append("receiverName", this.receiverName).toString();
   }
 }

