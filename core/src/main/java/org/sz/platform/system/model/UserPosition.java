package org.sz.platform.system.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class UserPosition extends BaseModel {
	public static Short PRIMARY_NO = 0;
	public static Short PRIMARY_YES = 1;
	protected Long userPosId;
	protected Long posId;
	protected Long userId;
	protected Short isPrimary = PRIMARY_NO;
	protected String account;
	protected String fullname;
	protected String posName;

	public String getPosName() {
		return this.posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setUserPosId(Long userPosId) {
		this.userPosId = userPosId;
	}

	public Long getUserPosId() {
		return this.userPosId;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}

	public Long getPosId() {
		return this.posId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setIsPrimary(Short isPrimary) {
		this.isPrimary = isPrimary;
	}

	public Short getIsPrimary() {
		return this.isPrimary;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public boolean equals(Object object) {
		if (!(object instanceof UserPosition)) {
			return false;
		}
		UserPosition rhs = (UserPosition) object;
		return new EqualsBuilder().append(this.userPosId, rhs.userPosId)
				.append(this.posId, rhs.posId).append(this.userId, rhs.userId)
				.append(this.isPrimary, rhs.isPrimary).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.userPosId).append(this.posId).append(this.userId)
				.append(this.isPrimary).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("userPosId", this.userPosId)
				.append("posId", this.posId).append("userId", this.userId)
				.append("isPrimary", this.isPrimary).toString();
	}
}
