 package org.sz.platform.bpm.model.form;
 
 import org.sz.platform.bpm.model.form.BpmFormRule;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class BpmFormRule extends BaseModel
 {
   protected Long id;
   protected String name;
   protected String rule;
   protected String memo;
   protected String tipInfo;
 
   public void setId(Long id)
   {
     this.id = id;
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setName(String name)
   {
     this.name = name;
   }
 
   public String getName()
   {
     return this.name;
   }
 
   public void setRule(String rule)
   {
     this.rule = rule;
   }
 
   public String getRule()
   {
     return this.rule;
   }
 
   public void setMemo(String memo)
   {
     this.memo = memo;
   }
 
   public String getMemo()
   {
     return this.memo;
   }
 
   public void setTipInfo(String tipInfo)
   {
     this.tipInfo = tipInfo;
   }
 
   public String getTipInfo()
   {
     return this.tipInfo;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof BpmFormRule))
     {
       return false;
     }
     BpmFormRule rhs = (BpmFormRule)object;
     return new EqualsBuilder().append(this.id, rhs.id).append(this.name, rhs.name).append(this.rule, rhs.rule).append(this.memo, rhs.memo).append(this.tipInfo, rhs.tipInfo).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.name).append(this.rule).append(this.memo).append(this.tipInfo).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("id", this.id).append("name", this.name).append("rule", this.rule).append("memo", this.memo).append("tipInfo", this.tipInfo).toString();
   }
 }

