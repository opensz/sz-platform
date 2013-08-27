package org.sz.platform.bpm.model.flow;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class BpmNodeUser extends BaseModel implements Cloneable {
	public static final short ASSIGN_TYPE_START_USER = 0;
	public static final short ASSIGN_TYPE_USER = 1;
	public static final short ASSIGN_TYPE_GROUP = 100; // 多组选择
	public static final short ASSIGN_TYPE_ROLE = 2;
	public static final short ASSIGN_TYPE_ORG = 3;
	public static final short ASSIGN_TYPE_ORG_CHARGE = 4;
	public static final short ASSIGN_TYPE_POS = 5;
	public static final short ASSIGN_TYPE_UP_LOW = 6;
	public static final short ASSIGN_TYPE_USER_ATTR = 7;
	public static final short ASSIGN_TYPE_ORG_ATTR = 8;
	public static final short ASSIGN_TYPE_SAME_DEP = 9;
	public static final short ASSIGN_TYPE_SAME_NODE = 10;
	public static final short ASSIGN_TYPE_DIRECT_LED = 11;
	public static final short ASSIGN_TYPE_SCRIPT = 12;
	public static final short ASSIGN_TYPE_PREUSER_ORG_LEADER = 13;
	public static final short ASSIGN_TYPE_STARTUSER_LEADER = 14;
	public static final short ASSIGN_TYPE_PREVUSER_LEADER = 15;
	public static final short COMP_TYPE_OR = 0;
	public static final short COMP_TYPE_AND = 1;
	public static final short COMP_TYPE_EXCLUDE = 2;
	protected Long nodeUserId;
	protected Long setId;
	protected String nodeId;
	protected Short assignType;
	protected String actDefId;
	protected String cmpIds;
	protected String cmpNames;
	protected Short compType;
	protected Integer sn;

	public void setNodeUserId(Long nodeUserId) {
		this.nodeUserId = nodeUserId;
	}

	public Long getNodeUserId() {
		return this.nodeUserId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	public Long getSetId() {
		return this.setId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setAssignType(Short assignType) {
		this.assignType = assignType;
	}

	public Short getAssignType() {
		return this.assignType;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return this.actDefId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeUser)) {
			return false;
		}
		BpmNodeUser rhs = (BpmNodeUser) object;
		return new EqualsBuilder().append(this.nodeUserId, rhs.nodeUserId)
				.append(this.setId, rhs.setId).append(this.nodeId, rhs.nodeId)
				.append(this.assignType, rhs.assignType)
				.append(this.actDefId, rhs.actDefId)
				.append(this.cmpIds, rhs.cmpIds)
				.append(this.cmpNames, rhs.cmpNames).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.nodeUserId).append(this.setId).append(this.nodeId)
				.append(this.assignType).append(this.actDefId)
				.append(this.cmpIds).append(this.cmpNames).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("nodeUserId", this.nodeUserId)
				.append("setId", this.setId).append("nodeId", this.nodeId)
				.append("assignType", this.assignType)
				.append("actDefId", this.actDefId)
				.append("cmpIds", this.cmpIds)
				.append("cmpNames", this.cmpNames).toString();
	}

	public String getCmpIds() {
		return this.cmpIds;
	}

	public void setCmpIds(String cmpIds) {
		this.cmpIds = cmpIds;
	}

	public String getCmpNames() {
		return this.cmpNames;
	}

	public void setCmpNames(String cmpNames) {
		this.cmpNames = cmpNames;
	}

	public Short getCompType() {
		return this.compType;
	}

	public void setCompType(Short compType) {
		this.compType = compType;
	}

	public Integer getSn() {
		return this.sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Object clone() {
		BpmNodeUser obj = null;
		try {
			obj = (BpmNodeUser) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
