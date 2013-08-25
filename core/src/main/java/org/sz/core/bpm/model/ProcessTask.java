package org.sz.core.bpm.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ProcessTask
{
  private String id;
  private String name;
  private String subject;
  private String parentTaskId;
  private String description;
  private String priority;
  private Date createTime;
  private String owner;
  private String assignee;
  private String delegationState;
  private String executionId;
  private String processInstanceId;
  private String processDefinitionId;
  private String taskDefinitionKey;
  private Date dueDate;
  private Integer revision;
  private String processName;
  private String taskUrl;

  public String getId()
  {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getParentTaskId() {
    return this.parentTaskId;
  }

  public void setParentTaskId(String parentTaskId) {
    this.parentTaskId = parentTaskId;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPriority() {
    return this.priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getOwner() {
    return this.owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getAssignee() {
    return this.assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getDelegationState() {
    return this.delegationState;
  }

  public void setDelegationState(String delegationState) {
    this.delegationState = delegationState;
  }

  public String getExecutionId() {
    return this.executionId;
  }

  public void setExecutionId(String executionId) {
    this.executionId = executionId;
  }

  public String getProcessInstanceId() {
    return this.processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getProcessDefinitionId() {
    return this.processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  public String getTaskDefinitionKey() {
    return this.taskDefinitionKey;
  }

  public void setTaskDefinitionKey(String taskDefinitionKey) {
    this.taskDefinitionKey = taskDefinitionKey;
  }

  public Date getDueDate() {
    return this.dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public String getSubject()
  {
    return this.subject;
  }

  public void setSubject(String subject)
  {
    this.subject = subject;
  }

  public Integer getRevision()
  {
    return this.revision;
  }

  public void setRevision(Integer revision)
  {
    this.revision = revision;
  }

  public String getProcessName()
  {
    return this.processName;
  }

  public void setProcessName(String processName)
  {
    this.processName = processName;
  }

  public String getTaskUrl() {
    return this.taskUrl;
  }

  public void setTaskUrl(String taskUrl) {
    this.taskUrl = taskUrl;
  }
}