package org.sz.platform.bpm.model.flow;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ProcessRun extends BaseModel {
	public static final Short STATUS_SUSPEND = 0;
	public static final Short STATUS_RUNNING = 1;
	public static final Short STATUS_FINISH = 2;

	protected Long runId;
	protected Long defId;
	protected String subject;
	protected Long creatorId;
	protected String creator;
	protected Date createtime;
	protected String busDescp;
	protected Short status;
	protected String actInstId;
	protected String actDefId;
	protected String businessType; // add by bobo, 20130527
	protected String businessKey;
	protected String businessUrl;
	protected Date endTime;
	protected Long duration;
	protected String processName;
	protected String pkName = "";

	protected String tableName = "";

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Long getRunId() {
		return this.runId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public Long getDefId() {
		return this.defId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getCreatorId() {
		return this.creatorId;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setBusDescp(String busDescp) {
		this.busDescp = busDescp;
	}

	public String getBusDescp() {
		return this.busDescp;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}

	public String getActInstId() {
		return this.actInstId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return this.actDefId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBusinessUrl() {
		return this.businessUrl;
	}

	public void setBusinessUrl(String businessUrl) {
		this.businessUrl = businessUrl;
	}

	public String getPkName() {
		return this.pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean equals(Object object) {
		if (!(object instanceof ProcessRun)) {
			return false;
		}
		ProcessRun rhs = (ProcessRun) object;
		return new EqualsBuilder().append(this.runId, rhs.runId)
				.append(this.defId, rhs.defId)
				.append(this.subject, rhs.subject)
				.append(this.creatorId, rhs.creatorId)
				.append(this.creator, rhs.creator)
				.append(this.createtime, rhs.createtime)
				.append(this.busDescp, rhs.busDescp)
				.append(this.status, rhs.status)
				.append(this.actInstId, rhs.actInstId)
				.append(this.actDefId, rhs.actDefId)
				.append(this.businessKey, rhs.businessKey)
				.append(this.businessUrl, rhs.businessUrl).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.runId)
				.append(this.defId).append(this.subject).append(this.creatorId)
				.append(this.creator).append(this.createtime)
				.append(this.busDescp).append(this.status)
				.append(this.actInstId).append(this.actDefId)
				.append(this.businessKey).append(this.businessUrl).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("runId", this.runId)
				.append("defId", this.defId).append("subject", this.subject)
				.append("creatorId", this.creatorId)
				.append("creator", this.creator)
				.append("createtime", this.createtime)
				.append("busDescp", this.busDescp)
				.append("status", this.status)
				.append("actInstId", this.actInstId)
				.append("actDefId", this.actDefId)
				.append("businessKey", this.businessKey)
				.append("businessUrl", this.businessUrl).toString();
	}

	public String getProcessName() {
		return this.processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Long getDuration() {
		return this.duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
}
