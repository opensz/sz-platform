package org.sz.core.bpm.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

//import org.sz.platform.model.bpm.ProcessRun;
//import org.sz.platform.model.bpm.TaskOpinion;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ProcessCmd {
	private String actDefId;
	private String flowKey;
	private String taskId;
	private String subject;
	private String destTask;
	private String[] lastDestTaskIds;
	private String[] lastDestTaskUids;
	private String businessKey;
	private String businessType; // add
	private String businessUrl;
	private String signUserIds;
	private String assigneeIds;
	private String forkUserUids;
	private String forkUserType = "user";
	private Long stackId;
	private boolean isBack = false;

	private boolean isRecover = false;

	private boolean isSignTask = false;

	private boolean isOnlyCompleteTask = false;
	private Short voteAgree;
	private String voteContent;
	private Map<String, Object> variables = new HashMap<String, Object>();

	private String formData = "";

	private Map formDataMap = null;

	private boolean isAgentTask = false;

	private String currentUserId = "";

	// private ProcessRun processRun = null;

	private String userAccount = null;

	private Long caseId = null;

	private String tabData = "";

	private String ccUserIds; // 抄送ids

	private Long serviceItemId;
	private Long deskRequestId;

	public String getActDefId() {
		return this.actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Map<String, Object> getVariables() {
		return this.variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public void putVariables(Map<String, Object> variables) {
		this.variables.putAll(variables);
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDestTask() {
		return this.destTask;
	}

	public void setDestTask(String destTask) {
		this.destTask = destTask;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getSignUserIds() {
		return this.signUserIds;
	}

	public void setSignUserIds(String signUserIds) {
		this.signUserIds = signUserIds;
	}

	public String getFlowKey() {
		return this.flowKey;
	}

	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}

	public String getBusinessUrl() {
		return this.businessUrl;
	}

	public void setBusinessUrl(String businessUrl) {
		this.businessUrl = businessUrl;
	}

	public boolean isBack() {
		return this.isBack;
	}

	public void setBack(boolean isBack) {
		this.isBack = isBack;

		this.destTask = null;
	}

	public void setBack(boolean isBack, String destTask) {
		this.isBack = isBack;

		this.destTask = destTask;
	}

	public boolean isRecover() {
		return this.isRecover;
	}

	public void setRecover(boolean isRecover) {
		this.isBack = isRecover;
		this.isRecover = isRecover;

		this.destTask = null;
	}

	public Short getVoteAgree() {
		return this.voteAgree;
	}

	public void setVoteAgree(Short voteAgree) {
		// if (TaskOpinion.STATUS_REJECT.equals(voteAgree))
		// setBack(true);
		// else if (TaskOpinion.STATUS_RECOVER.equals(voteAgree)) {
		// setRecover(true);
		// }
		this.voteAgree = voteAgree;
	}

	public String getVoteContent() {
		return this.voteContent;
	}

	public void setVoteContent(String voteContent) {
		this.voteContent = voteContent;
	}

	public String getAssigneeIds() {
		return this.assigneeIds;
	}

	public void setAssigneeIds(String assigneeIds) {
		this.assigneeIds = assigneeIds;
	}

	public Long getStackId() {
		return this.stackId;
	}

	public void setStackId(Long stackId) {
		this.stackId = stackId;
	}

	public boolean isSignTask() {
		return this.isSignTask;
	}

	public void setSignTask(boolean isSignTask) {
		this.isSignTask = isSignTask;
	}

	public String getFormData() {
		return this.formData;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}

	public Map getFormDataMap() {
		return this.formDataMap;
	}

	public void setFormDataMap(Map formDataMap) {
		this.formDataMap = formDataMap;
	}

	public String getCurrentUserId() {
		return this.currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	// public ProcessRun getProcessRun() {
	// return this.processRun;
	// }
	//
	// public void setProcessRun(ProcessRun processRun) {
	// this.processRun = processRun;
	// }

	public String getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getForkUserUids() {
		return this.forkUserUids;
	}

	public void setForkUserUids(String forkUserUids) {
		this.forkUserUids = forkUserUids;
	}

	public String getForkUserType() {
		return this.forkUserType;
	}

	public void setForkUserType(String forkUserType) {
		this.forkUserType = forkUserType;
	}

	public String[] getLastDestTaskIds() {
		return this.lastDestTaskIds;
	}

	public void setLastDestTaskIds(String[] lastDestTaskIds) {
		this.lastDestTaskIds = lastDestTaskIds;
	}

	public String[] getLastDestTaskUids() {
		return this.lastDestTaskUids;
	}

	public void setLastDestTaskUids(String[] lastDestTaskUids) {
		this.lastDestTaskUids = lastDestTaskUids;
	}

	public boolean isOnlyCompleteTask() {
		return this.isOnlyCompleteTask;
	}

	public void setOnlyCompleteTask(boolean isOnlyCompleteTask) {
		this.isOnlyCompleteTask = isOnlyCompleteTask;
	}

	public boolean isAgentTask() {
		return this.isAgentTask;
	}

	public void setAgentTask(boolean isAgentTask) {
		this.isAgentTask = isAgentTask;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String getTabData() {
		return tabData;
	}

	public void setTabData(String tabData) {
		this.tabData = tabData;
	}

	public String getCcUserIds() {
		return ccUserIds;
	}

	public void setCcUserIds(String ccUserIds) {
		this.ccUserIds = ccUserIds;
	}

	public Long getServiceItemId() {
		return serviceItemId;
	}

	public void setServiceItemId(Long serviceItemId) {
		this.serviceItemId = serviceItemId;
	}

	public Long getDeskRequestId() {
		return deskRequestId;
	}

	public void setDeskRequestId(Long deskRequestId) {
		this.deskRequestId = deskRequestId;
	}

}
