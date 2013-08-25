 package org.sz.platform.bpm.model.flow;
 
 import org.sz.platform.bpm.model.flow.BpmFormRun;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class BpmFormRun extends BaseModel
 {
   protected Long id;
   protected Long formdefId;
   protected Long formdefKey;
   protected String actInstanceId;
   protected String actDefId;
   protected String actNodeId;
   protected Long runId;
   protected Short setType = 0;
 
   protected Short formType = -1;
   protected String formUrl;
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setId(Long id) {
     this.id = id;
   }
 
   public Long getFormdefId() {
     return this.formdefId;
   }
 
   public void setFormdefId(Long formdefId) {
     this.formdefId = formdefId;
   }
 
   public Long getFormdefKey() {
     return this.formdefKey;
   }
 
   public void setFormdefKey(Long formdefKey) {
     this.formdefKey = formdefKey;
   }
 
   public String getActInstanceId() {
     return this.actInstanceId;
   }
 
   public void setActInstanceId(String actInstanceId) {
     this.actInstanceId = actInstanceId;
   }
 
   public String getActDefId() {
     return this.actDefId;
   }
 
   public void setActDefId(String actDefId) {
     this.actDefId = actDefId;
   }
 
   public String getActNodeId() {
     return this.actNodeId;
   }
 
   public void setActNodeId(String actNodeId) {
     this.actNodeId = actNodeId;
   }
 
   public Long getRunId() {
     return this.runId;
   }
 
   public void setRunId(Long runId) {
     this.runId = runId;
   }
 
   public Short getSetType() {
     return this.setType;
   }
 
   public void setSetType(Short setType) {
     this.setType = setType;
   }
 
   public Short getFormType() {
     return this.formType;
   }
 
   public void setFormType(Short formType) {
     this.formType = formType;
   }
 
   public String getFormUrl() {
     return this.formUrl;
   }
 
   public void setFormUrl(String formUrl) {
     this.formUrl = formUrl;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof BpmFormRun))
     {
       return false;
     }
     BpmFormRun rhs = (BpmFormRun)object;
     return new EqualsBuilder().append(this.id, rhs.id).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.id).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("id", this.id).toString();
   }
 }

