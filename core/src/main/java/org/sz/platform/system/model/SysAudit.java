 package org.sz.platform.system.model;
 
 import org.sz.platform.system.model.SysAudit;

 import java.util.Date;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class SysAudit extends BaseModel
 {
   protected Long auditId;
   protected String opName;
   protected Date exeTime;
   protected Long executorId;
   protected String executor;
   protected String fromIp;
   protected String exeMethod;
   protected String requestURI;
   protected String reqParams;
 
   public void setAuditId(Long auditId)
   {
     this.auditId = auditId;
   }
 
   public Long getAuditId()
   {
     return this.auditId;
   }
 
   public void setOpName(String opName) {
     this.opName = opName;
   }
 
   public String getOpName()
   {
     return this.opName;
   }
 
   public void setExeTime(Date exeTime) {
     this.exeTime = exeTime;
   }
 
   public Date getExeTime()
   {
     return this.exeTime;
   }
 
   public void setExecutorId(Long executorId) {
     this.executorId = executorId;
   }
 
   public Long getExecutorId()
   {
     return this.executorId;
   }
 
   public void setExecutor(String executor) {
     this.executor = executor;
   }
 
   public String getExecutor()
   {
     return this.executor;
   }
 
   public void setFromIp(String fromIp) {
     this.fromIp = fromIp;
   }
 
   public String getFromIp()
   {
     return this.fromIp;
   }
 
   public void setExeMethod(String exeMethod) {
     this.exeMethod = exeMethod;
   }
 
   public String getExeMethod()
   {
     return this.exeMethod;
   }
 
   public void setRequestURI(String requestURI) {
     this.requestURI = requestURI;
   }
 
   public String getRequestURI()
   {
     return this.requestURI;
   }
 
   public void setReqParams(String reqParams) {
     this.reqParams = reqParams;
   }
 
   public String getReqParams()
   {
     return this.reqParams;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof SysAudit)) {
       return false;
     }
     SysAudit rhs = (SysAudit)object;
     return new EqualsBuilder().append(this.auditId, rhs.auditId).append(this.opName, rhs.opName).append(this.exeTime, rhs.exeTime).append(this.executorId, rhs.executorId).append(this.executor, rhs.executor).append(this.fromIp, rhs.fromIp).append(this.exeMethod, rhs.exeMethod).append(this.requestURI, rhs.requestURI).append(this.reqParams, rhs.reqParams).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.auditId).append(this.opName).append(this.exeTime).append(this.executorId).append(this.executor).append(this.fromIp).append(this.exeMethod).append(this.requestURI).append(this.reqParams).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("auditId", this.auditId).append("opName", this.opName).append("exeTime", this.exeTime).append("executorId", this.executorId).append("executor", this.executor).append("fromIp", this.fromIp).append("exeMethod", this.exeMethod).append("requestURI", this.requestURI).append("reqParams", this.reqParams).toString();
   }
 }

