package org.sz.platform.bpm.model.flow;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class BpmNodeSet extends BaseModel {
	public static final Short FORM_TYPE_ONLINE = 0;

	public static final Short FORM_TYPE_URL = 1;

	public static final Short NODE_TYPE_NORMAL = 0;

	public static final Short NODE_TYPE_FORK = 1;

	public static final Short NODE_TYPE_JOIN = 2;

	public static final Short BACK_ALLOW = 1;

	public static final Short BACK_DENY = 0;

	public static final Short SetType_TaskNode = 0;

	public static final Short SetType_StartForm = 1;

	public static final Short SetType_GloabalForm = 2;

	public static final Short RULE_INVALID_NORMAL = 1;

	public static final Short RULE_INVALID_NO_NORMAL = 0;

	public static final Short AUDIT = 1;
	public static final Short NOT_AUDIT = 0;

	public static final Short FORKJOIN = 1;
	public static final Short NOT_FORKJOIN = 0;

	protected Long setId;
	protected Long defId;
	protected String nodeName;
	protected String actDefId;
	protected String nodeId;
	protected Short formType = -1;
	protected String formUrl;
	protected Long formKey = 0L;
	protected String formDefName;
	protected Long formDefId = 0L;

	protected Short isJumpForDef = RULE_INVALID_NORMAL;
	protected Short nodeType; // 0=普通任务节点 1=分发任务节点
	protected String joinTaskKey;
	protected String joinTaskName;
	protected String beforeHandler;
	protected String afterHandler;
	protected String jumpType;
	protected Short isAllowBack;
	protected Short setType = 0;

	protected Short assignMode; // 流转模式 0=上一步指定 1=自由选取 2=自动分配
	protected Short isAudit; // 是否是审批
	protected Short isForkJoin; // 是否是分发

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	public Long getSetId() {
		return this.setId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public Long getDefId() {
		return this.defId;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return this.actDefId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setFormType(Short formType) {
		this.formType = formType;
	}

	public Short getFormType() {
		return this.formType;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public String getFormUrl() {
		return this.formUrl;
	}

	public Long getFormKey() {
		return this.formKey;
	}

	public void setFormKey(Long formKey) {
		this.formKey = formKey;
	}

	public String getFormDefName() {
		return this.formDefName;
	}

	public void setFormDefName(String formDefName) {
		this.formDefName = formDefName;
	}

	public Short getNodeType() {
		return this.nodeType;
	}

	public void setNodeType(Short nodeType) {
		this.nodeType = nodeType;
	}

	public String getJoinTaskKey() {
		return this.joinTaskKey;
	}

	public void setJoinTaskKey(String joinTaskKey) {
		this.joinTaskKey = joinTaskKey;
	}

	public String getJoinTaskName() {
		return this.joinTaskName;
	}

	public void setJoinTaskName(String joinTaskName) {
		this.joinTaskName = joinTaskName;
	}

	public String getBeforeHandler() {
		return this.beforeHandler;
	}

	public void setBeforeHandler(String beforeHandler) {
		this.beforeHandler = beforeHandler;
	}

	public String getAfterHandler() {
		return this.afterHandler;
	}

	public void setAfterHandler(String afterHandler) {
		this.afterHandler = afterHandler;
	}

	public Short getSetType() {
		return this.setType;
	}

	public void setSetType(Short setType) {
		this.setType = setType;
	}

	public Long getFormDefId() {
		return this.formDefId;
	}

	public void setFormDefId(Long formDefId) {
		this.formDefId = formDefId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeSet)) {
			return false;
		}
		BpmNodeSet rhs = (BpmNodeSet) object;
		return new EqualsBuilder().append(this.setId, rhs.setId)
				.append(this.defId, rhs.defId)
				.append(this.nodeName, rhs.nodeName)
				.append(this.actDefId, rhs.actDefId)
				.append(this.nodeId, rhs.nodeId)
				.append(this.formType, rhs.formType)
				.append(this.formUrl, rhs.formUrl)
				.append(this.formKey, rhs.formKey)
				.append(this.nodeType, rhs.nodeType).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.setId)
				.append(this.defId).append(this.nodeName).append(this.actDefId)
				.append(this.nodeId).append(this.formType).append(this.formUrl)
				.append(this.formKey).append(this.nodeType).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("setId", this.setId)
				.append("defId", this.defId).append("nodeName", this.nodeName)
				.append("actDefId", this.actDefId)
				.append("nodeId", this.nodeId)
				.append("formType", this.formType)
				.append("formUrl", this.formUrl)
				.append("formKey", this.formKey)
				.append("nodeType", this.nodeType).toString();
	}

	public String getJumpType() {
		return this.jumpType;
	}

	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}

	public Short getIsAllowBack() {
		return this.isAllowBack;
	}

	public void setIsAllowBack(Short isAllowBack) {
		this.isAllowBack = isAllowBack;
	}

	public Short getIsJumpForDef() {
		return this.isJumpForDef;
	}

	public void setIsJumpForDef(Short isJumpForDef) {
		this.isJumpForDef = isJumpForDef;
	}

	public Short getAssignMode() {
		return assignMode;
	}

	public void setAssignMode(Short assignMode) {
		this.assignMode = assignMode;
	}

	public Short getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Short isAudit) {
		this.isAudit = isAudit;
	}

	public Short getIsForkJoin() {
		return isForkJoin;
	}

	public void setIsForkJoin(Short isForkJoin) {
		this.isForkJoin = isForkJoin;
	}
}
