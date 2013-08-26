package org.sz.platform.system.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class Demension extends BaseModel {
	public static Demension positionDem = new Demension();
	private Long demId;
	private String demName;
	private String demDesc;
	private String demOrgPath;

	public void setDemId(Long demId) {
		this.demId = demId;
	}

	public Long getDemId() {
		return this.demId;
	}

	public void setDemName(String demName) {
		this.demName = demName;
	}

	public String getDemName() {
		return this.demName;
	}

	public void setDemDesc(String demDesc) {
		this.demDesc = demDesc;
	}

	public String getDemDesc() {
		return this.demDesc;
	}

	public String getDemOrgPath() {
		return this.demOrgPath;
	}

	public void setDemOrgPath(String demOrgPath) {
		this.demOrgPath = demOrgPath;
	}

	public boolean equals(Object object) {
		if (!(object instanceof Demension)) {
			return false;
		}
		Demension rhs = (Demension) object;
		return new EqualsBuilder().append(this.demId, rhs.demId)
				.append(this.demName, rhs.demName)
				.append(this.demDesc, rhs.demDesc).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.demId)
				.append(this.demName).append(this.demDesc).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("demId", this.demId)
				.append("demName", this.demName)
				.append("demDesc", this.demDesc).toString();
	}

	static {
		positionDem.setDemId(Long.valueOf(0L));
		positionDem.setDemName("岗位维度");
	}
}
