 package org.sz.platform.system.model;
 
 import org.sz.platform.system.model.SysOfficeTemplate;

 import java.util.Date;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class SysOfficeTemplate extends BaseModel
 {
   protected Long id;
   protected String subject;
   protected Integer templatetype = Integer.valueOf(1);
   protected String memo;
   protected Long creatorid;
   protected String creator;
   protected Date createtime;
   protected String path;
 
   public void setId(Long id)
   {
     this.id = id;
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setSubject(String subject) {
     this.subject = subject;
   }
 
   public String getSubject()
   {
     return this.subject;
   }
 
   public void setTemplatetype(Integer templatetype) {
     this.templatetype = templatetype;
   }
 
   public Integer getTemplatetype()
   {
     return this.templatetype;
   }
 
   public void setMemo(String memo) {
     this.memo = memo;
   }
 
   public String getMemo()
   {
     return this.memo;
   }
 
   public void setCreatorid(Long creatorid) {
     this.creatorid = creatorid;
   }
 
   public Long getCreatorid()
   {
     return this.creatorid;
   }
 
   public void setCreator(String creator) {
     this.creator = creator;
   }
 
   public String getCreator()
   {
     return this.creator;
   }
 
   public void setCreatetime(Date createtime) {
     this.createtime = createtime;
   }
 
   public Date getCreatetime()
   {
     return this.createtime;
   }
 
   public void setPath(String path) {
     this.path = path;
   }
 
   public String getPath()
   {
     return this.path;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof SysOfficeTemplate))
     {
       return false;
     }
     SysOfficeTemplate rhs = (SysOfficeTemplate)object;
     return new EqualsBuilder().append(this.id, rhs.id).append(this.subject, rhs.subject).append(this.templatetype, rhs.templatetype).append(this.memo, rhs.memo).append(this.creatorid, rhs.creatorid).append(this.creator, rhs.creator).append(this.createtime, rhs.createtime).append(this.path, rhs.path).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.subject).append(this.templatetype).append(this.memo).append(this.creatorid).append(this.creator).append(this.createtime).append(this.path).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("id", this.id).append("subject", this.subject).append("templatetype", this.templatetype).append("memo", this.memo).append("creatorid", this.creatorid).append("creator", this.creator).append("createtime", this.createtime).append("path", this.path).toString();
   }
 }

