package org.sz.platform.bpm.model.form;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

import org.sz.platform.bpm.model.form.BpmFormRights;

public class BpmFormRights extends BaseModel implements Cloneable {
	public static final short FieldRights = 1;
	public static final short TableRights = 2;
	public static final short OpinionRights = 3;
	protected Long id;
	protected Long formDefId;
	protected String name;
	protected String permission = "";

	protected short type = 1;

	protected String actDefId = "";

	protected String nodeId = "";

	public BpmFormRights() {
	}

	public BpmFormRights(Long id, Long formDefId, String name,
			String permission, short type) {
		this.id = id;
		this.formDefId = formDefId;
		this.name = name;
		this.permission = permission;
		this.type = type;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setFormDefId(Long formDefId) {
		this.formDefId = formDefId;
	}

	public Long getFormDefId() {
		return this.formDefId;
	}

	public void setName(String fieldName) {
		this.name = fieldName;
	}

	public String getName() {
		return this.name;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return this.permission;
	}

	public short getType() {
		return this.type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public String getActDefId() {
		return this.actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmFormRights)) {
			return false;
		}
		BpmFormRights rhs = (BpmFormRights) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.formDefId, rhs.formDefId)
				.append(this.name, rhs.name)
				.append(this.permission, rhs.permission).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.formDefId).append(this.name)
				.append(this.permission).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("formDefId", this.formDefId)
				.append("fieldName", this.name)
				.append("permission", this.permission).toString();
	}

	public Object clone() {
		BpmFormRights obj = null;
		try {
			obj = (BpmFormRights) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
