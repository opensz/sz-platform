package org.sz.platform.system.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class UserRole extends BaseModel implements Cloneable {
	protected Long userRoleId;
	protected Long roleId;
	protected Long userId;
	protected String fullname;
	protected String account;
	protected String roleName = "";

	protected String systemName = "";

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Long getUserRoleId() {
		return this.userRoleId;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public boolean equals(Object object) {
		if (!(object instanceof UserRole)) {
			return false;
		}
		UserRole rhs = (UserRole) object;
		return new EqualsBuilder().append(this.roleId, rhs.roleId)
				.append(this.userId, rhs.userId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.roleId)
				.append(this.userId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("roleId", this.roleId)
				.append("userId", this.userId).toString();
	}

	public Object clone() {
		UserRole obj = null;
		try {
			obj = (UserRole) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
