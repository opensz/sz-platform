package org.sz.platform.system.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class GlobalType extends BaseModel {
	public static final String CAT_FLOW = "FLOW_TYPE";
	public static final String CAT_FORM = "FORM_TYPE";
	public static final String CAT_FILE = "FILE_TYPE";
	public static final String CAT_ATTACH = "ATTACH_TYPE";
	public static final String CAT_DIC = "DIC";
	public static final String CAT_FILE_FORMAT = "FILEFORMAT";
	public static final String CAT_REPORT = "REPORT_TYPE";
	public static final String CAT_BUSINESS = "BUSINESS_TYPE"; // add by bobo,
																// 2013

	public static final String NODE_KEY_DIC = "DIC";
	public static final String TYPE_NAME_BPM = "流程分类";
	public static final String TYPE_NAME_DIC = "数据字典";

	public static final Integer DATA_TYPE_TREE = Integer.valueOf(1);
	public static final Integer DATA_TYPE_FLAT = Integer.valueOf(0);
	public static final long ROOT_PID = -1L;
	public static final long ROOT_ID = 0L;
	public static final long ROOT_DEPTH = 0L;
	public static final String IS_PARENT_N = "false";
	public static final String IS_PARENT_Y = "true";
	public static final int IS_LEAF_N = 0;
	public static final int IS_LEAF_Y = 1;

	protected Long typeId = Long.valueOf(0L);
	protected String typeName;
	protected String nodePath;
	protected Long depth;
	protected Long parentId;
	protected String nodeKey;
	protected String catKey;
	protected Long sn;
	protected Long userId = Long.valueOf(0L);

	protected Long depId = Long.valueOf(0L);

	protected Integer type = Integer.valueOf(0);

	protected String open = "true";
	protected String isParent;
	protected Integer isLeaf;
	protected int childNodes = 0;

	public Integer getIsLeaf() {
		return this.isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getIsParent() {
		return this.childNodes > 0 ? "true" : "false";
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getOpen() {
		return this.open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getTypeId() {
		return this.typeId;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodePath() {
		return this.nodePath;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	public Long getDepth() {
		int i = this.nodePath.split("\\.").length - 1;
		return Long.valueOf(i);
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	public String getNodeKey() {
		return this.nodeKey;
	}

	public void setCatKey(String catKey) {
		this.catKey = catKey;
	}

	public String getCatKey() {
		return this.catKey;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public Long getSn() {
		return this.sn;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public Long getDepId() {
		return this.depId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	public int getChildNodes() {
		return this.childNodes;
	}

	public void setChildNodes(int childNodes) {
		this.childNodes = childNodes;
	}

	public boolean equals(Object object) {
		if (!(object instanceof GlobalType)) {
			return false;
		}
		GlobalType rhs = (GlobalType) object;
		return new EqualsBuilder().append(this.typeId, rhs.typeId)
				.append(this.typeName, rhs.typeName)
				.append(this.nodePath, rhs.nodePath)
				.append(this.depth, rhs.depth)
				.append(this.parentId, rhs.parentId)
				.append(this.nodeKey, rhs.nodeKey)
				.append(this.catKey, rhs.catKey).append(this.sn, rhs.sn)
				.append(this.userId, rhs.userId).append(this.depId, rhs.depId)
				.append(this.type, rhs.type).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.typeId)
				.append(this.typeName).append(this.nodePath).append(this.depth)
				.append(this.parentId).append(this.nodeKey).append(this.catKey)
				.append(this.sn).append(this.userId).append(this.depId)
				.append(this.type).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("typeId", this.typeId)
				.append("typeName", this.typeName)
				.append("nodePath", this.nodePath).append("depth", this.depth)
				.append("parentId", this.parentId)
				.append("nodeKey", this.nodeKey).append("catKey", this.catKey)
				.append("sn", this.sn).append("userId", this.userId)
				.append("depId", this.depId).append("type", this.type)
				.toString();
	}
}
