package org.sz.core.bpm.model;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

public class ProcessExecution {
	private String id;
	private Integer revision;
	private String processInstanceId;
	private String businessKey;
	private String processDefinitionId;
	private String activityId;
	private Short isActive;
	private Short isConcurrent;
	private Short isScope;
	private String parentId;
	private String superExecutionId;

	public ProcessExecution() {
	}

	public ProcessExecution(ExecutionEntity executionEntity) {
		this.revision = Integer.valueOf(executionEntity.getRevision());
		this.processInstanceId = executionEntity.getProcessInstanceId();

		this.processDefinitionId = executionEntity.getProcessDefinitionId();
		this.activityId = executionEntity.getActivityId();
		this.isActive = Short.valueOf(executionEntity.isActive() ? "1" : "0");
		this.isConcurrent = Short.valueOf(executionEntity.isConcurrent() ? "1"
				: "0");
		this.isScope = Short.valueOf(executionEntity.isScope() ? "1" : "0");
		this.parentId = executionEntity.getParentId();
		this.superExecutionId = executionEntity.getSuperExecutionId();
	}

	public Integer getRevision() {
		return this.revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getProcessDefinitionId() {
		return this.processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getSuperExecutionId() {
		return this.superExecutionId;
	}

	public void setSuperExecutionId(String superExecutionId) {
		this.superExecutionId = superExecutionId;
	}

	public String getActivityId() {
		return this.activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public Short getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Short isActive) {
		this.isActive = isActive;
	}

	public Short getIsConcurrent() {
		return this.isConcurrent;
	}

	public void setIsConcurrent(Short isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public Short getIsScope() {
		return this.isScope;
	}

	public void setIsScope(Short isScope) {
		this.isScope = isScope;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}