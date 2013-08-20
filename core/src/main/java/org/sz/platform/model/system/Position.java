 package org.sz.platform.model.system;
 
 import org.sz.platform.model.system.Position;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class Position extends BaseModel
 {
   public static final long ROOT_PID = -1L;
   public static final long ROOT_ID = 0L;
   public static final int ROOT_DEPTH = 0;
   public static final String IS_PARENT_N = "false";
   public static final String IS_PARENT_Y = "true";
   public static final int IS_LEAF_N = 0;
   public static final int IS_LEAF_Y = 1;
   protected Long posId;
   protected String posName;
   protected String posDesc;
   protected Long parentId;
   protected String nodePath;
   protected Integer depth;
   protected Short sn;
   protected Short isPrimary;
   protected String open = "true";
   protected String isParent;
   protected Integer isLeaf;
 
   public Integer getIsLeaf()
   {
     return this.isLeaf;
   }
   public void setIsLeaf(Integer isLeaf) {
     this.isLeaf = isLeaf;
     if ((isLeaf != null) && (isLeaf.equals(Integer.valueOf(1))))
       this.isParent = "false";
     else if ((isLeaf != null) && (isLeaf.equals(Integer.valueOf(0))))
       this.isParent = "true";
     else
       this.isParent = null;
   }
 
   public String getIsParent()
   {
     if (this.isLeaf == null) return "true";
 
     return this.isLeaf.intValue() == 1 ? "false" : "true";
   }
   public void setIsParent(String isParent) {
     this.isParent = isParent;
     if ((isParent != null) && (isParent.equals("false")))
       this.isLeaf = Integer.valueOf(1);
     else if ((isParent != null) && (isParent.equals("true")))
       this.isLeaf = Integer.valueOf(0);
     else
       this.isLeaf = null;
   }
 
   public String getOpen()
   {
     return this.open;
   }
   public void setOpen(String open) {
     this.open = open;
   }
 
   public void setPosId(Long posId)
   {
     this.posId = posId;
   }
 
   public Long getPosId()
   {
     return this.posId;
   }
 
   public void setPosName(String posName)
   {
     this.posName = posName;
   }
 
   public String getPosName()
   {
     return this.posName;
   }
 
   public void setPosDesc(String posDesc)
   {
     this.posDesc = posDesc;
   }
 
   public String getPosDesc()
   {
     return this.posDesc;
   }
 
   public void setParentId(Long parentId)
   {
     this.parentId = parentId;
   }
 
   public Long getParentId()
   {
     return this.parentId;
   }
 
   public void setNodePath(String nodePath)
   {
     this.nodePath = nodePath;
   }
 
   public String getNodePath()
   {
     return this.nodePath;
   }
 
   public void setDepth(Integer depth)
   {
     this.depth = depth;
   }
 
   public Integer getDepth()
   {
     return this.depth;
   }
 
   public void setSn(Short sn)
   {
     this.sn = sn;
   }
 
   public Short getSn()
   {
     return this.sn;
   }
 
   public Short getIsPrimary()
   {
     return this.isPrimary;
   }
 
   public void setIsPrimary(Short isPrimary) {
     this.isPrimary = isPrimary;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof Position))
     {
       return false;
     }
     Position rhs = (Position)object;
     return new EqualsBuilder().append(this.posId, rhs.posId).append(this.posName, rhs.posName).append(this.posDesc, rhs.posDesc).append(this.parentId, rhs.parentId).append(this.nodePath, rhs.nodePath).append(this.depth, rhs.depth).append(this.sn, rhs.sn).append(this.isPrimary, rhs.isPrimary).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.posId).append(this.posName).append(this.posDesc).append(this.parentId).append(this.nodePath).append(this.depth).append(this.sn).append(this.isPrimary).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("posId", this.posId).append("posName", this.posName).append("posDesc", this.posDesc).append("parentId", this.parentId).append("nodePath", this.nodePath).append("depth", this.depth).append("sn", this.sn).append("isPrimary", this.isPrimary).toString();
   }
 }

