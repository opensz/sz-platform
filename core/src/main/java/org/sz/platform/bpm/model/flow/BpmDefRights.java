package org.sz.platform.bpm.model.flow;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class BpmDefRights extends BaseModel implements Cloneable {
	public static final short RIGHT_TYPE_USER = 1;
	public static final short RIGHT_TYPE_ROLE = 2;
	public static final short RIGHT_TYPE_ORG = 3;
	public static final short SEARCH_TYPE_DEF = 0;
	public static final short SEARCH_TYPE_GLT = 1;
	protected Long id;
	protected Long defId;
	protected Long flowTypeId;
	protected Short rightType;
	protected Long ownerId;
	protected String ownerName;
	protected Short searchType;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public Long getDefId() {
		return this.defId;
	}

	public void setFlowTypeId(Long flowTypeId) {
		this.flowTypeId = flowTypeId;
	}

	public Long getFlowTypeId() {
		return this.flowTypeId;
	}

	public void setRightType(Short rightType) {
		this.rightType = rightType;
	}

	public Short getRightType() {
		return this.rightType;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setSearchType(Short searchType) {
		this.searchType = searchType;
	}

	public Short getSearchType() {
		return this.searchType;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmDefRights)) {
			return false;
		}
		BpmDefRights rhs = (BpmDefRights) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.defId, rhs.defId)
				.append(this.flowTypeId, rhs.flowTypeId)
				.append(this.rightType, rhs.rightType)
				.append(this.ownerId, rhs.ownerId)
				.append(this.ownerName, rhs.ownerName)
				.append(this.searchType, rhs.searchType).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.defId).append(this.flowTypeId)
				.append(this.rightType).append(this.ownerId)
				.append(this.ownerName).append(this.searchType).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("defId", this.defId)
				.append("flowTypeId", this.flowTypeId)
				.append("rightType", this.rightType)
				.append("ownerId", this.ownerId)
				.append("ownerName", this.ownerName)
				.append("searchType", this.searchType).toString();
	}

	public Object clone() {
		BpmDefRights obj = null;
		try {
			obj = (BpmDefRights) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
