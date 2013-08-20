 package org.sz.platform.model.system;
 
 import org.sz.platform.model.system.Message;
import org.sz.platform.model.system.SysTemplate;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class Message extends BaseModel
 {
   public static final String TABLE_NAME = "SYS_MESSAGE";
   public static final Integer MAIL_TYPE = Integer.valueOf(1);
   public static final Integer MOBILE_TYPE = Integer.valueOf(2);
   public static final Integer INNER_TYPE = Integer.valueOf(3);
   protected Long messageId;
   protected String subject;
   protected String receiver;
   protected String copyTo;
   protected String bcc;
   protected String fromUser;
   protected Long templateId;
   protected Integer messageType;
   protected String jumpUrl;
   protected String remainTime;
   protected SysTemplate sysTemplate;
   protected String actDefId;
   protected String nodeId;
 
   public String getRemainTime()
   {
     if (this.remainTime == null)
       return "";
     return this.remainTime;
   }
 
   public void setRemainTime(String remainTime) {
     this.remainTime = remainTime;
   }
 
   public String getJumpUrl() {
     if (this.jumpUrl == null)
       return "";
     return this.jumpUrl;
   }
 
   public void setJumpUrl(String jumpUrl) {
     this.jumpUrl = jumpUrl;
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
 
   public SysTemplate getSysTemplate() {
     return this.sysTemplate;
   }
 
   public void setSysTemplate(SysTemplate sysTemplate) {
     this.sysTemplate = sysTemplate;
   }
 
   public void setMessageId(Long messageId) {
     this.messageId = messageId;
   }
 
   public Long getMessageId()
   {
     return this.messageId;
   }
 
   public void setSubject(String subject) {
     this.subject = subject;
   }
 
   public String getSubject()
   {
     return this.subject;
   }
 
   public void setReceiver(String receiver) {
     this.receiver = receiver;
   }
 
   public String getReceiver()
   {
     return this.receiver;
   }
 
   public void setCopyTo(String copyTo) {
     this.copyTo = copyTo;
   }
 
   public String getCopyTo()
   {
     return this.copyTo;
   }
 
   public void setBcc(String bcc) {
     this.bcc = bcc;
   }
 
   public String getBcc()
   {
     return this.bcc;
   }
 
   public void setFromUser(String fromUser) {
     this.fromUser = fromUser;
   }
 
   public String getFromUser()
   {
     return this.fromUser;
   }
 
   public void setTemplateId(Long templateId) {
     this.templateId = templateId;
   }
 
   public Long getTemplateId()
   {
     return this.templateId;
   }
 
   public void setMessageType(Integer messageType) {
     this.messageType = messageType;
   }
 
   public Integer getMessageType()
   {
     return this.messageType;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof Message)) {
       return false;
     }
     Message rhs = (Message)object;
     return new EqualsBuilder().append(this.messageId, rhs.messageId).append(this.subject, rhs.subject).append(this.receiver, rhs.receiver).append(this.copyTo, rhs.copyTo).append(this.bcc, rhs.bcc).append(this.fromUser, rhs.fromUser).append(this.templateId, rhs.templateId).append(this.messageType, rhs.messageType).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.messageId).append(this.subject).append(this.receiver).append(this.copyTo).append(this.bcc).append(this.fromUser).append(this.templateId).append(this.messageType).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("messageId", this.messageId).append("subject", this.subject).append("receiver", this.receiver).append("copyTo", this.copyTo).append("bcc", this.bcc).append("fromUser", this.fromUser).append("templateId", this.templateId).append("messageType", this.messageType).toString();
   }
 }

