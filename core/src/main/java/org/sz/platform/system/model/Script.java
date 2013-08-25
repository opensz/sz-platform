 package org.sz.platform.system.model;
 
 import org.sz.platform.system.model.Script;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class Script extends BaseModel
 {
   protected Long id;
   protected String name;
   protected String script;
   protected String category;
   protected String memo;
 
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
 
   public void setScript(String script)
   {
     this.script = script;
   }
 
   public String getScript()
   {
     return this.script;
   }
 
   public void setCategory(String category)
   {
     this.category = category;
   }
 
   public String getCategory()
   {
     return this.category;
   }
 
   public void setMemo(String memo)
   {
     this.memo = memo;
   }
 
   public String getMemo()
   {
     return this.memo;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof Script))
     {
       return false;
     }
     Script rhs = (Script)object;
     return new EqualsBuilder().append(this.id, rhs.id).append(this.name, rhs.name).append(this.script, rhs.script).append(this.category, rhs.category).append(this.memo, rhs.memo).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.name).append(this.script).append(this.category).append(this.memo).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("id", this.id).append("name", this.name).append("script", this.script).append("category", this.category).append("memo", this.memo).toString();
   }
 }

