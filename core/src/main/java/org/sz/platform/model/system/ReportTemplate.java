 package org.sz.platform.model.system;
 
 import org.sz.platform.model.system.ReportTemplate;

 import java.util.Date;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class ReportTemplate extends BaseModel
 {
   public static final String targetPath = "reportlets";
   protected Long reportId;
   protected String title;
   protected String descp;
   protected String reportLocation;
   protected Date createTime;
   protected Date updateTime;
   protected String reportKey;
   protected Short isDefaultIn;
   protected Long typeId;
   protected String fileName;
   protected String reportSeverlet = "/ReportServer?reportlet=org.sz.core.report.ReportParamSearch&fileName=";
 
   public void setReportId(Long reportId)
   {
     this.reportId = reportId;
   }
 
   public Long getReportId()
   {
     return this.reportId;
   }
 
   public void setTitle(String title)
   {
     this.title = title;
   }
 
   public String getTitle()
   {
     return this.title;
   }
 
   public void setDescp(String descp)
   {
     this.descp = descp;
   }
 
   public String getDescp()
   {
     return this.descp;
   }
 
   public void setReportLocation(String reportLocation)
   {
     this.reportLocation = reportLocation;
   }
 
   public String getReportLocation()
   {
     return this.reportLocation;
   }
 
   public void setCreateTime(Date createTime)
   {
     this.createTime = createTime;
   }
 
   public Date getCreateTime()
   {
     return this.createTime;
   }
 
   public void setUpdateTime(Date updateTime)
   {
     this.updateTime = updateTime;
   }
 
   public Date getUpdateTime()
   {
     return this.updateTime;
   }
 
   public void setReportKey(String reportKey)
   {
     this.reportKey = reportKey;
   }
 
   public String getReportKey()
   {
     return this.reportKey;
   }
 
   public void setIsDefaultIn(Short isDefaultIn)
   {
     this.isDefaultIn = isDefaultIn;
   }
 
   public Short getIsDefaultIn()
   {
     return this.isDefaultIn;
   }
 
   public Long getTypeId()
   {
     return this.typeId;
   }
   public void setTypeId(Long typeId) {
     this.typeId = typeId;
   }
 
   public String getFileName()
   {
     String[] paths = this.reportLocation.split("\\\\");
     return paths[(paths.length - 1)];
   }
 
   public String getReportSeverlet() {
     return this.reportSeverlet;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof ReportTemplate))
     {
       return false;
     }
     ReportTemplate rhs = (ReportTemplate)object;
     return new EqualsBuilder().append(this.reportId, rhs.reportId).append(this.title, rhs.title).append(this.descp, rhs.descp).append(this.reportLocation, rhs.reportLocation).append(this.createTime, rhs.createTime).append(this.updateTime, rhs.updateTime).append(this.reportKey, rhs.reportKey).append(this.isDefaultIn, rhs.isDefaultIn).append(this.typeId, rhs.typeId).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.reportId).append(this.title).append(this.descp).append(this.reportLocation).append(this.createTime).append(this.updateTime).append(this.reportKey).append(this.isDefaultIn).append(this.typeId).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("reportId", this.reportId).append("title", this.title).append("descp", this.descp).append("reportLocation", this.reportLocation).append("createTime", this.createTime).append("updateTime", this.updateTime).append("reportKey", this.reportKey).append("isDefaultIn", this.isDefaultIn).append("typeId", this.typeId).toString();
   }
 }

