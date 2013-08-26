package org.sz.platform.bpm.model.flow;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.bpm.model.ProcessTask;
import org.sz.core.model.BaseModel;
import org.sz.core.util.StringUtil;
import org.sz.core.util.TimeUtil;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class TaskOpinion extends BaseModel {
	public static final Short STATUS_INIT = -2;

	public static final Short STATUS_CHECKING = -1;

	public static final Short STATUS_ABANDON = 0;// 弃权跳过

	public static final Short STATUS_AGREE = 1; // 同意

	public static final Short STATUS_REFUSE = 2; // 反对

	public static final Short STATUS_REJECT = 3; // 驳回

	public static final Short STATUS_RECOVER = 4; // 被追回

	public static final Short STATUS_BACK = 5; // 回退

	protected Long opinionId;
	protected String actInstId;
	protected String taskName;
	protected String taskKey;
	protected String taskToken;
	protected Long taskId;
	protected Date startTime;
	protected Date endTime;
	protected Long durTime;
	protected Long exeUserId;
	protected String exeFullname;
	protected String opinion;
	protected Short checkStatus = STATUS_CHECKING;

	protected Long formDefId = Long.valueOf(0L);
	protected String fieldName;
	protected String actDefId;

	public TaskOpinion() {
	}

	public TaskOpinion(ProcessTask task) {
		this.actDefId = task.getProcessDefinitionId();
		this.actInstId = task.getProcessInstanceId();
		this.taskId = new Long(task.getId());
		this.taskName = task.getName();
		this.taskKey = task.getTaskDefinitionKey();
		this.startTime = new Date();
	}

	public TaskOpinion(DelegateTask task) {
		this.actDefId = task.getProcessDefinitionId();
		this.actInstId = task.getProcessInstanceId();
		this.taskId = new Long(task.getId());
		this.taskKey = task.getTaskDefinitionKey();
		this.taskName = task.getName();
		this.startTime = new Date();
	}

	public void setOpinionId(Long opinionId) {
		this.opinionId = opinionId;
	}

	public Long getOpinionId() {
		return this.opinionId;
	}

	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}

	public String getActInstId() {
		return this.actInstId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getTaskId() {
		return this.taskId;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public String getStartTimeStr() {
		if (this.startTime == null)
			return "";
		return TimeUtil.getDateTimeString(this.startTime);
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public String getEndTimeStr() {
		if (this.endTime == null)
			return "";
		return TimeUtil.getDateTimeString(this.endTime);
	}

	public void setDurTime(Long durTime) {
		this.durTime = durTime;
	}

	public String getDurTimeStr() {
		if (this.durTime == null)
			return "";
		return TimeUtil.getTime(this.durTime);
	}

	public Long getDurTime() {
		return this.durTime;
	}

	public void setExeUserId(Long exeUserId) {
		this.exeUserId = exeUserId;
	}

	public Long getExeUserId() {
		return this.exeUserId;
	}

	public void setExeFullname(String exeFullname) {
		this.exeFullname = exeFullname;
	}

	public String getExeFullname() {
		if (StringUtil.isEmpty(this.exeFullname))
			return "";
		return this.exeFullname;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getOpinion() {
		return this.opinion;
	}

	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getStatus() {
		String status = "";
		switch (this.checkStatus.shortValue()) {
		case -2:
			status = "未开始";
			break;
		case -1:
			status = "正在审批";
			break;
		case 0:
			status = "弃权";
			break;
		case 1:
			status = "同意";
			break;
		case 2:
			status = "反对";
			break;
		case 3:
			status = "驳回";
			break;
		case 4:
			status = "追回";
		}

		return status;
	}

	public Short getCheckStatus() {
		return this.checkStatus;
	}

	public Long getFormDefId() {
		return this.formDefId;
	}

	public void setFormDefId(Long formDefId) {
		this.formDefId = formDefId;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getActDefId() {
		return this.actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getTaskKey() {
		return this.taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskToken() {
		return this.taskToken;
	}

	public void setTaskToken(String taskToken) {
		this.taskToken = taskToken;
	}

	public boolean equals(Object object) {
		if (!(object instanceof TaskOpinion)) {
			return false;
		}
		TaskOpinion rhs = (TaskOpinion) object;
		return new EqualsBuilder().append(this.opinionId, rhs.opinionId)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.opinionId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("opinionId", this.opinionId)
				.append("actInstId", this.actInstId)
				.append("taskName", this.taskName)
				.append("taskId", this.taskId)
				.append("startTime", this.startTime)
				.append("endTime", this.endTime)
				.append("durTime", this.durTime)
				.append("exeUserId", this.exeUserId)
				.append("exeFullname", this.exeFullname)
				.append("opinion", this.opinion)
				.append("checkStatus", this.checkStatus)
				.append("actDefId", this.actDefId).toString();
	}
}
