package org.sz.platform.system.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class Dictionary extends BaseModel {

	@Deprecated
	public static final String ScriptType = "scriptType";
	protected Long dicId = Long.valueOf(0L);
	protected Long typeId;
	protected String itemKey;
	protected String itemName;
	protected String itemValue;
	protected String descp;
	protected Long sn;
	protected String nodePath;
	protected Long parentId;
	protected Integer type = Integer.valueOf(0);

	protected String open = "true";

	public String getOpen() {
		return this.open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}

	public Long getDicId() {
		return this.dicId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getTypeId() {
		return this.typeId;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}

	public String getItemKey() {
		return this.itemKey;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getItemValue() {
		return this.itemValue;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public String getDescp() {
		return this.descp;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public Long getSn() {
		return this.sn;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodePath() {
		return this.nodePath;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public boolean equals(Object object) {
		if (!(object instanceof Dictionary)) {
			return false;
		}
		Dictionary rhs = (Dictionary) object;
		return new EqualsBuilder().append(this.dicId, rhs.dicId)
				.append(this.typeId, rhs.typeId)
				.append(this.itemKey, rhs.itemKey)
				.append(this.itemName, rhs.itemName)
				.append(this.itemValue, rhs.itemValue)
				.append(this.descp, rhs.descp).append(this.sn, rhs.sn)
				.append(this.nodePath, rhs.nodePath)
				.append(this.parentId, rhs.parentId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.dicId)
				.append(this.typeId).append(this.itemKey).append(this.itemName)
				.append(this.itemValue).append(this.descp).append(this.sn)
				.append(this.nodePath).append(this.parentId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("dicId", this.dicId)
				.append("typeId", this.typeId).append("itemKey", this.itemKey)
				.append("itemName", this.itemName)
				.append("itemValue", this.itemValue)
				.append("descp", this.descp).append("sn", this.sn)
				.append("nodePath", this.nodePath)
				.append("parentId", this.parentId).toString();
	}
}
