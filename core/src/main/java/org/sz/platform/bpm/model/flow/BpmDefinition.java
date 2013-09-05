package org.sz.platform.bpm.model.flow;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class BpmDefinition extends BaseModel implements Cloneable {
	public static final String DefaultSubjectRule = "{流程标题:title}-{发起人:startUser}-{发起时间:startTime}";
	public static final Short MAIN = 1;

	public static final Short NOT_MAIN = 0;

	public static final Short STATUS_DEPLOYED = 1;

	public static final Short STATUS_NOTDEPLOYED = 0;

	public static final Short STATUS_SUSPEND = -1;
	public static final String TABLE_NAME = "bpm_definition";
	protected Long defId;
	protected Long typeId;
	protected String businessType; // 业务类型， CatType=BUSINESS_TYPE
	protected String subject;
	protected String defKey;
	protected String taskNameRule; // task name rule
	protected String descp;
	protected Date createtime;
	protected Short status;
	protected String defXml;
	protected Long actDeployId;
	protected String actDefKey;
	protected String actDefId;
	protected Long createBy;
	protected Long updateBy;
	protected String reason;
	protected Integer versionNo;
	protected Long parentDefId;
	protected Short isMain;
	protected Date updatetime;
	protected String typeName;
	protected Short needStartForm = 1;

	protected Short toFirstNode = 0;

//	protected Short isIso = 0;

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public Long getDefId() {
		return this.defId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getTypeId() {
		return this.typeId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setDefKey(String defKey) {
		this.defKey = defKey;
	}

	public String getDefKey() {
		return this.defKey;
	}

	public void setTaskNameRule(String taskNameRule) {
		this.taskNameRule = taskNameRule;
	}

	public String getTaskNameRule() {
		return this.taskNameRule;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public String getDescp() {
		return this.descp;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setDefXml(String defXml) {
		this.defXml = defXml;
	}

	public String getDefXml() {
		return this.defXml;
	}

	public void setActDeployId(Long actDeployId) {
		this.actDeployId = actDeployId;
	}

	public Long getActDeployId() {
		return this.actDeployId;
	}

	public void setActDefKey(String actDefKey) {
		this.actDefKey = actDefKey;
	}

	public String getActDefKey() {
		return this.actDefKey;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return this.actDefId;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getCreateBy() {
		return this.createBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Long getUpdateBy() {
		return this.updateBy;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return this.reason;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public Integer getVersionNo() {
		return this.versionNo;
	}

	public void setParentDefId(Long parentDefId) {
		this.parentDefId = parentDefId;
	}

	public Long getParentDefId() {
		return this.parentDefId;
	}

	public void setIsMain(Short isMain) {
		this.isMain = isMain;
	}

	public Short getIsMain() {
		return this.isMain;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Short getToFirstNode() {
		return this.toFirstNode;
	}

	public void setToFirstNode(Short toFirstNode) {
		this.toFirstNode = toFirstNode;
	}

	public Short getNeedStartForm() {
		return this.needStartForm;
	}

	public void setNeedStartForm(Short needStartForm) {
		this.needStartForm = needStartForm;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

//	public Short getIsIso() {
//		return isIso;
//	}
//
//	public void setIsIso(Short isIso) {
//		this.isIso = isIso;
//	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmDefinition)) {
			return false;
		}
		BpmDefinition rhs = (BpmDefinition) object;
		return new EqualsBuilder().append(this.defId, rhs.defId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.defId)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("defId", this.defId)
				.append("typeId", this.typeId).append("subject", this.subject)
				.append("defKey", this.defKey)
				.append("taskNameRule", this.taskNameRule)
				.append("descp", this.descp)
				.append("createtime", this.createtime)
				.append("status", this.status).append("defXml", this.defXml)
				.append("actDeployId", this.actDeployId)
				.append("actDefKey", this.actDefKey)
				.append("actDefId", this.actDefId)
				.append("createBy", this.createBy)
				.append("updateBy", this.updateBy)
				.append("reason", this.reason)
				.append("versionNo", this.versionNo)
				.append("parentDefId", this.parentDefId)
				.append("isMain", this.isMain)
				.append("updatetime", this.updatetime).toString();
	}

	public Object clone() {
		BpmDefinition obj = null;
		try {
			obj = (BpmDefinition) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}