package org.sz.platform.bpm.model.flow;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class TaskFork extends BaseModel {
	public static String TAKEN_PRE = "T";

	public static String TAKEN_VAR_NAME = "_token_";
	protected Long taskForkId;
	protected String actInstId;
	protected String forkTaskName;
	protected String forkTaskKey;
	protected Integer forkSn;
	protected Integer forkCount;
	protected Integer fininshCount;
	protected Date forkTime;
	protected String joinTaskKey;
	protected String joinTaskName;
	protected String forkTokens;
	protected String forkTokenPre;

	public void setTaskForkId(Long taskForkId) {
		this.taskForkId = taskForkId;
	}

	public Long getTaskForkId() {
		return this.taskForkId;
	}

	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}

	public String getActInstId() {
		return this.actInstId;
	}

	public void setForkTaskName(String forkTaskName) {
		this.forkTaskName = forkTaskName;
	}

	public String getForkTaskName() {
		return this.forkTaskName;
	}

	public void setForkTaskKey(String forkTaskKey) {
		this.forkTaskKey = forkTaskKey;
	}

	public String getForkTaskKey() {
		return this.forkTaskKey;
	}

	public void setForkSn(Integer forkSn) {
		this.forkSn = forkSn;
	}

	public Integer getForkSn() {
		return this.forkSn;
	}

	public void setForkCount(Integer forkCount) {
		this.forkCount = forkCount;
	}

	public Integer getForkCount() {
		return this.forkCount;
	}

	public void setFininshCount(Integer fininshCount) {
		this.fininshCount = fininshCount;
	}

	public Integer getFininshCount() {
		return this.fininshCount;
	}

	public void setForkTime(Date forkTime) {
		this.forkTime = forkTime;
	}

	public Date getForkTime() {
		return this.forkTime;
	}

	public void setJoinTaskKey(String joinTaskKey) {
		this.joinTaskKey = joinTaskKey;
	}

	public String getJoinTaskKey() {
		return this.joinTaskKey;
	}

	public void setJoinTaskName(String joinTaskName) {
		this.joinTaskName = joinTaskName;
	}

	public String getJoinTaskName() {
		return this.joinTaskName;
	}

	public String getForkTokens() {
		return this.forkTokens;
	}

	public void setForkTokens(String forkTokens) {
		this.forkTokens = forkTokens;
	}

	public String getForkTokenPre() {
		return this.forkTokenPre;
	}

	public void setForkTokenPre(String forkTokenPre) {
		this.forkTokenPre = forkTokenPre;
	}

	public boolean equals(Object object) {
		if (!(object instanceof TaskFork)) {
			return false;
		}
		TaskFork rhs = (TaskFork) object;
		return new EqualsBuilder().append(this.taskForkId, rhs.taskForkId)
				.append(this.actInstId, rhs.actInstId)
				.append(this.forkTaskName, rhs.forkTaskName)
				.append(this.forkTaskKey, rhs.forkTaskKey)
				.append(this.forkSn, rhs.forkSn)
				.append(this.forkCount, rhs.forkCount)
				.append(this.fininshCount, rhs.fininshCount)
				.append(this.forkTime, rhs.forkTime)
				.append(this.joinTaskKey, rhs.joinTaskKey)
				.append(this.joinTaskName, rhs.joinTaskName).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.taskForkId).append(this.actInstId)
				.append(this.forkTaskName).append(this.forkTaskKey)
				.append(this.forkSn).append(this.forkCount)
				.append(this.fininshCount).append(this.forkTime)
				.append(this.joinTaskKey).append(this.joinTaskName)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("taskForkId", this.taskForkId)
				.append("actInstId", this.actInstId)
				.append("forkTaskName", this.forkTaskName)
				.append("forkTaskKey", this.forkTaskKey)
				.append("forkSn", this.forkSn)
				.append("forkCount", this.forkCount)
				.append("fininshCount", this.fininshCount)
				.append("forkTime", this.forkTime)
				.append("joinTaskKey", this.joinTaskKey)
				.append("joinTaskName", this.joinTaskName).toString();
	}
}
