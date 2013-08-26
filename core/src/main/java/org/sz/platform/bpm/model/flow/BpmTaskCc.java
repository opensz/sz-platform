package org.sz.platform.bpm.model.flow;

import java.util.Date;

public class BpmTaskCc {
	protected Long id;
	protected Long userId;
	protected String taskId;
	protected Date ccTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Date getCcTime() {
		return ccTime;
	}

	public void setCcTime(Date ccTime) {
		this.ccTime = ccTime;
	}
}
