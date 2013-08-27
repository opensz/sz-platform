package org.sz.platform.bpm.model.flow;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class BpmNodeUserUplow extends BaseModel {
	public static Map<String, Short> UPLOWTYPE_MAP = new HashMap();
	protected Long ID;
	protected Long nodeUserId;
	protected Long demensionId;
	protected String demensionName;
	protected Integer upLowLevel;
	protected Short upLowType;

	public void setID(Long ID) {
		this.ID = ID;
	}

	public Long getID() {
		return this.ID;
	}

	public void setNodeUserId(Long nodeUserId) {
		this.nodeUserId = nodeUserId;
	}

	public Long getNodeUserId() {
		return this.nodeUserId;
	}

	public void setDemensionId(Long demensionId) {
		this.demensionId = demensionId;
	}

	public Long getDemensionId() {
		return this.demensionId;
	}

	public void setDemensionName(String demensionName) {
		this.demensionName = demensionName;
	}

	public String getDemensionName() {
		return this.demensionName;
	}

	public void setUpLowLevel(Integer upLowLevel) {
		this.upLowLevel = upLowLevel;
	}

	public Integer getUpLowLevel() {
		return this.upLowLevel;
	}

	public void setUpLowType(Short upLowType) {
		this.upLowType = upLowType;
	}

	public Short getUpLowType() {
		return this.upLowType;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeUserUplow)) {
			return false;
		}
		BpmNodeUserUplow rhs = (BpmNodeUserUplow) object;
		return new EqualsBuilder().append(this.ID, rhs.ID)
				.append(this.nodeUserId, rhs.nodeUserId)
				.append(this.demensionId, rhs.demensionId)
				.append(this.demensionName, rhs.demensionName)
				.append(this.upLowLevel, rhs.upLowLevel)
				.append(this.upLowType, rhs.upLowType).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.ID)
				.append(this.nodeUserId).append(this.demensionId)
				.append(this.demensionName).append(this.upLowLevel)
				.append(this.upLowType).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("ID", this.ID)
				.append("nodeUserId", this.nodeUserId)
				.append("demensionId", this.demensionId)
				.append("demensionName", this.demensionName)
				.append("upLowLevel", this.upLowLevel)
				.append("upLowType", this.upLowType).toString();
	}

	static {
		UPLOWTYPE_MAP.put("上级", (short) 1);
		UPLOWTYPE_MAP.put("下级", (short) -1);
		UPLOWTYPE_MAP.put("同级", (short) 0);
	}
}
