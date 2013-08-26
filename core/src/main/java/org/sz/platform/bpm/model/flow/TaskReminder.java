package org.sz.platform.bpm.model.flow;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class TaskReminder extends BaseModel {
	protected Long taskDueId;
	protected String actDefId;
	protected String nodeId;
	protected Integer reminderStart;
	protected Integer reminderEnd;
	protected Integer times;
	protected String mailContent;
	protected String msgContent;
	protected String smsContent;
	protected Integer action;
	protected String script;
	protected Integer completeTime;

	public void setTaskDueId(Long taskDueId) {
		this.taskDueId = taskDueId;
	}

	public Long getTaskDueId() {
		return this.taskDueId;
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

	public void setReminderStart(Integer reminderStart) {
		this.reminderStart = reminderStart;
	}

	public Integer getReminderStart() {
		return this.reminderStart;
	}

	public void setReminderEnd(Integer reminderEnd) {
		this.reminderEnd = reminderEnd;
	}

	public Integer getReminderEnd() {
		return this.reminderEnd;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getTimes() {
		return this.times;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getMailContent() {
		return this.mailContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getMsgContent() {
		return this.msgContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getSmsContent() {
		return this.smsContent;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Integer getAction() {
		return this.action;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getScript() {
		return this.script;
	}

	public void setCompleteTime(Integer completeTime) {
		this.completeTime = completeTime;
	}

	public Integer getCompleteTime() {
		return this.completeTime;
	}

	public boolean equals(Object object) {
		if (!(object instanceof TaskReminder)) {
			return false;
		}
		TaskReminder rhs = (TaskReminder) object;
		return new EqualsBuilder().append(this.taskDueId, rhs.taskDueId)
				.append(this.actDefId, rhs.actDefId)
				.append(this.nodeId, rhs.nodeId)
				.append(this.reminderStart, rhs.reminderStart)
				.append(this.reminderEnd, rhs.reminderEnd)
				.append(this.times, rhs.times)
				.append(this.mailContent, rhs.mailContent)
				.append(this.msgContent, rhs.msgContent)
				.append(this.smsContent, rhs.smsContent)
				.append(this.action, rhs.action)
				.append(this.script, rhs.script)
				.append(this.completeTime, rhs.completeTime).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.taskDueId).append(this.actDefId)
				.append(this.nodeId).append(this.reminderStart)
				.append(this.reminderEnd).append(this.times)
				.append(this.mailContent).append(this.msgContent)
				.append(this.smsContent).append(this.action)
				.append(this.script).append(this.completeTime).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("taskDueId", this.taskDueId)
				.append("actDefId", this.actDefId)
				.append("nodeId", this.nodeId)
				.append("reminderStart", this.reminderStart)
				.append("reminderEnd", this.reminderEnd)
				.append("times", this.times)
				.append("mailContent", this.mailContent)
				.append("msgContent", this.msgContent)
				.append("smsContent", this.smsContent)
				.append("action", this.action).append("script", this.script)
				.append("completeTime", this.completeTime).toString();
	}
}
