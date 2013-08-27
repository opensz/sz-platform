package org.sz.platform.system.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class SysUserOrg extends BaseModel {
	public static final short PRIMARY_YES = 1;
	public static final short PRIMARY_NO = 0;
	public static final Short CHARRGE_YES = 1;
	public static final Short CHARRGE_NO = 0;
	protected Long userOrgId;
	protected Long orgId;
	protected Long userId;
	protected Short isPrimary = 0;
	// protected Short isDept = 0; //是否为部门

	protected Short isCharge = CHARRGE_NO;
	protected String userName;
	protected String account;
	protected String orgName;
	protected String chargeName = "";
	private Long orgSupId; // 父节点id,显示字段

	public void setUserOrgId(Long userOrgId) {
		this.userOrgId = userOrgId;
	}

	public Long getUserOrgId() {
		return this.userOrgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getOrgId() {
		return this.orgId;
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

	public void setIsCharge(Short isCharge) {
		this.isCharge = isCharge;
	}

	public Short getIsCharge() {
		return this.isCharge;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getChargeName() {
		return this.chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getOrgSupId() {
		return orgSupId;
	}

	public void setOrgSupId(Long orgSupId) {
		this.orgSupId = orgSupId;
	}

	//
	// public Short getIsDept() {
	// return isDept;
	// }
	//
	// public void setIsDept(Short isDept) {
	// this.isDept = isDept;
	// }

	public boolean equals(Object object) {
		if (!(object instanceof SysUserOrg)) {
			return false;
		}
		SysUserOrg rhs = (SysUserOrg) object;
		return new EqualsBuilder().append(this.userOrgId, rhs.userOrgId)
				.append(this.orgId, rhs.orgId).append(this.userId, rhs.userId)
				.append(this.isPrimary, rhs.isPrimary)
				.append(this.isCharge, rhs.isCharge).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.userOrgId).append(this.orgId).append(this.userId)
				.append(this.isPrimary).append(this.isCharge).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("userOrgId", this.userOrgId)
				.append("orgId", this.orgId).append("userId", this.userId)
				.append("isPrimary", this.isPrimary)
				.append("isCharge", this.isCharge).toString();
	}
}
