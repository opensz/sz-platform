 package org.sz.platform.model.system;
 
 import org.sz.platform.model.system.ResourcesUrl;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class ResourcesUrl extends BaseModel
 {
   protected Long resUrlId;
   protected Long resId;
   protected String name;
   protected String url;
 
   public void setResUrlId(Long resUrlId)
   {
     this.resUrlId = resUrlId;
   }
 
   public Long getResUrlId()
   {
     return this.resUrlId;
   }
 
   public void setResId(Long resId)
   {
     this.resId = resId;
   }
 
   public Long getResId()
   {
     return this.resId;
   }
 
   public void setName(String name)
   {
     this.name = name;
   }
 
   public String getName()
   {
     return this.name;
   }
 
   public void setUrl(String url)
   {
     this.url = url;
   }
 
   public String getUrl()
   {
     return this.url;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof ResourcesUrl))
     {
       return false;
     }
     ResourcesUrl rhs = (ResourcesUrl)object;
     return new EqualsBuilder().append(this.resUrlId, rhs.resUrlId).append(this.resId, rhs.resId).append(this.name, rhs.name).append(this.url, rhs.url).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.resUrlId).append(this.resId).append(this.name).append(this.url).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("resUrlId", this.resUrlId).append("resId", this.resId).append("name", this.name).append("url", this.url).toString();
   }
 }

