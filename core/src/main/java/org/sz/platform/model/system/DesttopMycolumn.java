 package org.sz.platform.model.system;
 
 import org.sz.platform.model.system.DesttopMycolumn;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class DesttopMycolumn extends BaseModel
 {
   protected Long ID;
   protected String USERID;
   protected String COLUMNID;
   protected String COLUMNNUM;
 
   public void setID(Long ID)
   {
     this.ID = ID;
   }
 
   public Long getID()
   {
     return this.ID;
   }
 
   public void setUSERID(String USERID)
   {
     this.USERID = USERID;
   }
 
   public String getUSERID()
   {
     return this.USERID;
   }
 
   public void setCOLUMNID(String COLUMNID)
   {
     this.COLUMNID = COLUMNID;
   }
 
   public String getCOLUMNID()
   {
     return this.COLUMNID;
   }
 
   public void setCOLUMNNUM(String COLUMNNUM)
   {
     this.COLUMNNUM = COLUMNNUM;
   }
 
   public String getCOLUMNNUM()
   {
     return this.COLUMNNUM;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof DesttopMycolumn))
     {
       return false;
     }
     DesttopMycolumn rhs = (DesttopMycolumn)object;
     return new EqualsBuilder().append(this.ID, rhs.ID).append(this.USERID, rhs.USERID).append(this.COLUMNID, rhs.COLUMNID).append(this.COLUMNNUM, rhs.COLUMNNUM).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.ID).append(this.USERID).append(this.COLUMNID).append(this.COLUMNNUM).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("ID", this.ID).append("USERID", this.USERID).append("COLUMNID", this.COLUMNID).append("COLUMNNUM", this.COLUMNNUM).toString();
   }
 }

