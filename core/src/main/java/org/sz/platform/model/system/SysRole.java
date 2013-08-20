 package org.sz.platform.model.system;
 
 import org.sz.platform.model.system.SubSystem;
import org.sz.platform.model.system.SysRole;

 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
 import org.apache.commons.lang.builder.ToStringBuilder;
 import org.springframework.security.access.ConfigAttribute;
 import org.springframework.security.access.SecurityConfig;
 import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.sz.core.model.BaseModel;
 
 public class SysRole extends BaseModel
   implements GrantedAuthority, Cloneable
 {
   private static final String ROLE_SUPER = "ROLE_SUPER";
   private static final String ROLE_PUBLIC = "ROLE_PUBLIC";
   private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
   public static final GrantedAuthority ROLE_GRANT_SUPER = new GrantedAuthorityImpl("ROLE_SUPER");
   public static final ConfigAttribute ROLE_CONFIG_PUBLIC = new SecurityConfig("ROLE_PUBLIC");
   public static final ConfigAttribute ROLE_CONFIG_ANONYMOUS = new SecurityConfig("ROLE_ANONYMOUS");
   protected Long roleId;
   protected Long systemId;
   protected String alias;
   protected String roleName;
   protected String memo;
   protected Short allowDel;
   protected Short allowEdit;
   protected Short enabled;
   protected SubSystem subSystem;
 
   public void setRoleId(Long roleId)
   {
     this.roleId = roleId;
   }
 
   public Long getRoleId()
   {
     return this.roleId;
   }
 
   public void setSystemId(Long systemId)
   {
     this.systemId = systemId;
   }
 
   public Long getSystemId()
   {
     return this.systemId;
   }
 
   public void setAlias(String alias)
   {
     this.alias = alias;
   }
 
   public String getAlias()
   {
     return this.alias;
   }
 
   public void setRoleName(String roleName)
   {
     this.roleName = roleName;
   }
 
   public String getRoleName()
   {
     return this.roleName;
   }
 
   public void setMemo(String memo)
   {
     this.memo = memo;
   }
 
   public String getMemo()
   {
     return this.memo;
   }
 
   public void setAllowDel(Short allowDel)
   {
     this.allowDel = allowDel;
   }
 
   public Short getAllowDel()
   {
     return this.allowDel;
   }
 
   public void setAllowEdit(Short allowEdit)
   {
     this.allowEdit = allowEdit;
   }
 
   public Short getAllowEdit()
   {
     return this.allowEdit;
   }
 
   public void setEnabled(Short enabled)
   {
     this.enabled = enabled;
   }
 
   public SubSystem getSubSystem() {
     return this.subSystem;
   }
 
   public void setSubSystem(SubSystem subSystem) {
     this.subSystem = subSystem;
   }
 
   public Short getEnabled()
   {
     return this.enabled;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof SysRole))
     {
       return false;
     }
     SysRole rhs = (SysRole)object;
     return new EqualsBuilder().append(this.roleId, rhs.roleId).append(this.systemId, rhs.systemId).append(this.alias, rhs.alias).append(this.roleName, rhs.roleName).append(this.memo, rhs.memo).append(this.allowDel, rhs.allowDel).append(this.allowEdit, rhs.allowEdit).append(this.enabled, rhs.enabled).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.roleId).append(this.systemId).append(this.alias).append(this.roleName).append(this.memo).append(this.allowDel).append(this.allowEdit).append(this.enabled).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("roleId", this.roleId).append("systemId", this.systemId).append("alias", this.alias).append("roleName", this.roleName).append("memo", this.memo).append("allowDel", this.allowDel).append("allowEdit", this.allowEdit).append("enabled", this.enabled).toString();
   }
 
   public Object clone()
   {
     SysRole obj = null;
     try {
       obj = (SysRole)super.clone();
     } catch (CloneNotSupportedException e) {
       e.printStackTrace();
     }
     return obj;
   }
 
   public String getAuthority()
   {
     return this.alias;
   }
 }

