package org.sz.platform.system.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class SysAcceptIp extends BaseModel {
	public static final short IPTYPE_WHITE = 0;
	public static final short IPTYPE_BLACK = 1;
	protected Long acceptId;
	protected String title;
	protected String startIp;
	protected String endIp;
	protected Short ipType;
	protected String remark;

	public void setAcceptId(Long acceptId) {
		this.acceptId = acceptId;
	}

	public Long getAcceptId() {
		return this.acceptId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}

	public String getStartIp() {
		return this.startIp;
	}

	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}

	public String getEndIp() {
		return this.endIp;
	}

	public void setIpType(Short ipType) {
		this.ipType = ipType;
	}

	public Short getIpType() {
		return this.ipType;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public boolean equals(Object object) {
		if (!(object instanceof SysAcceptIp)) {
			return false;
		}
		SysAcceptIp rhs = (SysAcceptIp) object;
		return new EqualsBuilder().append(this.acceptId, rhs.acceptId)
				.append(this.title, rhs.title)
				.append(this.startIp, rhs.startIp)
				.append(this.endIp, rhs.endIp).append(this.ipType, rhs.ipType)
				.append(this.remark, rhs.remark).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.acceptId)
				.append(this.title).append(this.startIp).append(this.endIp)
				.append(this.ipType).append(this.remark).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("acceptId", this.acceptId)
				.append("title", this.title).append("startIp", this.startIp)
				.append("endIp", this.endIp).append("ipType", this.ipType)
				.append("remark", this.remark).toString();
	}
}
