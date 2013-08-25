 package org.sz.platform.model.mail;
 
 import org.sz.platform.model.mail.OutMailLinkman;

 import java.util.Date;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class OutMailLinkman extends BaseModel
 {
   protected Long linkId;
   protected Long mailId;
   protected Long userId;
   protected Date sendTime;
   protected String linkName;
   protected String linkAddress;
 
   public void setLinkId(Long linkId)
   {
     this.linkId = linkId;
   }
 
   public Long getLinkId()
   {
     return this.linkId;
   }
 
   public void setMailId(Long mailId)
   {
     this.mailId = mailId;
   }
 
   public Long getMailId()
   {
     return this.mailId;
   }
 
   public Long getUserId()
   {
     return this.userId;
   }
 
   public void setUserId(Long userId)
   {
     this.userId = userId;
   }
 
   public void setSendTime(Date sendTime)
   {
     this.sendTime = sendTime;
   }
 
   public Date getSendTime()
   {
     return this.sendTime;
   }
 
   public void setLinkName(String linkName)
   {
     this.linkName = linkName;
   }
 
   public String getLinkName()
   {
     return this.linkName;
   }
 
   public void setLinkAddress(String linkAddress)
   {
     this.linkAddress = linkAddress;
   }
 
   public String getLinkAddress()
   {
     return this.linkAddress;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof OutMailLinkman))
     {
       return false;
     }
     OutMailLinkman rhs = (OutMailLinkman)object;
     return new EqualsBuilder().append(this.linkId, rhs.linkId).append(this.mailId, rhs.mailId).append(this.sendTime, rhs.sendTime).append(this.linkName, rhs.linkName).append(this.linkAddress, rhs.linkAddress).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.linkId).append(this.mailId).append(this.sendTime).append(this.linkName).append(this.linkAddress).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("linkId", this.linkId).append("mailId", this.mailId).append("sendTime", this.sendTime).append("linkName", this.linkName).append("linkAddress", this.linkAddress).toString();
   }
 }

