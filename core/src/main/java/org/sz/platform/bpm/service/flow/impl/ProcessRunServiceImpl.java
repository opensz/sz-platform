package org.sz.platform.bpm.service.flow.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.sz.core.bpm.model.ProcessCmd;
import org.sz.core.dao.IEntityDao;
import org.sz.core.engine.FreemarkEngine;
import org.sz.core.jms.MessageProducer;
import org.sz.core.model.InnerMessage;
import org.sz.core.model.MailModel;
import org.sz.core.model.SmsMobile;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.util.TimeUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.bpm.dao.flow.BpmDefinitionDao;
import org.sz.platform.bpm.dao.flow.BpmNodeSetDao;
import org.sz.platform.bpm.dao.flow.ProcessRunDao;
import org.sz.platform.bpm.dao.flow.TaskOpinionDao;
import org.sz.platform.bpm.dao.form.BpmFormHandlerDao;
import org.sz.platform.bpm.model.flow.BpmDefinition;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.model.flow.ExecutionStack;
import org.sz.platform.bpm.model.flow.ForkUser;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.model.flow.TaskFork;
import org.sz.platform.bpm.model.flow.TaskOpinion;
import org.sz.platform.bpm.model.form.BpmFormData;
import org.sz.platform.bpm.model.form.BpmFormDef;
import org.sz.platform.bpm.model.form.BpmFormTable;
import org.sz.platform.bpm.model.form.PkValue;
import org.sz.platform.bpm.service.flow.BpmFormRunService;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.ExecutionStackService;
import org.sz.platform.bpm.service.flow.JumpRule;
import org.sz.platform.bpm.service.flow.ProcessRunService;
import org.sz.platform.bpm.service.flow.TaskSignDataService;
import org.sz.platform.bpm.service.flow.TaskUserAssignService;
import org.sz.platform.bpm.service.flow.thread.TaskThreadService;
import org.sz.platform.bpm.service.form.BpmFormDefService;
import org.sz.platform.bpm.service.form.BpmFormTableService;
import org.sz.platform.bpm.util.BpmUtil;
import org.sz.platform.bpm.util.FormDataUtil;
import org.sz.platform.system.dao.SysUserDao;
import org.sz.platform.system.model.SysUser;

import freemarker.template.TemplateException;

@Service("processRunService")
public class ProcessRunServiceImpl extends BaseServiceImpl<ProcessRun> implements ProcessRunService {

	@Resource
	private ProcessRunDao dao;

	@Resource
	private BpmDefinitionDao bpmDefinitionDao;

	@Resource
	private BpmService bpmService;

	@Resource
	private TaskSignDataService taskSignDataService;

	@Resource
	private BpmFormHandlerDao bpmFormHandlerDao;

	@Resource
	private BpmNodeSetDao bpmNodeSetDao;

	@Resource
	private TaskService taskService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private TaskUserAssignService taskUserAssignService;

	@Resource
	private TaskOpinionDao taskOpinionDao;

	@Resource
	private FreemarkEngine freemarkEngine;

	@Resource
	private SysUserDao sysUserDao;

	@Resource
	private MessageProducer messageProducer;

	@Resource
	private BpmFormRunService bpmFormRunService;

	@Resource
	JumpRule jumpRule;

	@Resource
	BpmActService bpmActService;

	@Resource
	private ExecutionStackService executionStackService;
	

	@Resource
	Properties configproperties;

	@Resource
	private BpmFormTableService bpmFormTableService;
	
	@Resource
	private BpmFormDefService bpmFormDefService;
	

	

	
	protected IEntityDao<ProcessRun, Long> getEntityDao() {
		return this.dao;
	}
	
	private void setCcUser(ProcessCmd processCmd){
		if(StringUtils.isNotBlank(processCmd.getCcUserIds())){
			this.taskUserAssignService.addCcUserIds(processCmd.getCcUserIds());
		}
	}

	private void setNextTaskUser(ProcessCmd processCmd) {
		if ((processCmd.getLastDestTaskIds() != null) && (processCmd.getLastDestTaskUids() != null)) {
			this.taskUserAssignService.addNodeUserMap(processCmd.getLastDestTaskIds(), processCmd.getLastDestTaskUids());
		}

		if (StringUtils.isNotEmpty(processCmd.getForkUserUids())) {
			ForkUser forkTask = new ForkUser(processCmd.getForkUserUids(), processCmd.getForkUserType());
			this.taskUserAssignService.addForkUser(forkTask);
		}
	}

	private void setAgentUser(TaskEntity taskEntity, ProcessCmd processCmd) {
		SysUser sysUser = (SysUser) this.sysUserDao.getById(new Long(taskEntity.getAssignee()));
		//TODO refact
		//ContextUtil.setCurrentUser(sysUser);
	}

	private Map<String, String> handlerFormData(ProcessCmd processCmd, ProcessRun processRun) throws Exception {
		
		String json = processCmd.getFormData();
		Map optionsMap = null;
		if (StringUtils.isNotEmpty(json)) {
			PkValue pkValue = new PkValue(processRun.getPkName(), processRun.getBusinessKey());
			BpmFormData bpmFormData = FormDataUtil.parseJson(json, pkValue);
			processCmd.setVariables(bpmFormData.getVariables());

			Object changeReason = processCmd.getFormDataMap().get("changeReason");
			if(changeReason != null && StringUtils.isNotBlank(changeReason.toString())){
				bpmFormData.getMainFields().put("changeReason_", changeReason.toString());
			}
			this.bpmFormHandlerDao.handFormData(bpmFormData);
			optionsMap = bpmFormData.getOptions();
		}
		return optionsMap;
	}
	
	public ProcessRun nextProcess(ProcessCmd processCmd) throws Exception {
		String businessType = processCmd.getBusinessType();
		String taskId = processCmd.getTaskId();
		TaskEntity taskEntity = this.bpmService.getTask(taskId);

		if ((processCmd.isAgentTask()) && (StringUtils.isNotEmpty(taskEntity.getAssignee()))) {
			setAgentUser(taskEntity, processCmd);
		}

		TaskThreadService.clearNewTasks();

		String taskToken = (String) this.taskService.getVariableLocal(taskId, TaskFork.TAKEN_VAR_NAME);
		BpmNodeSet bpmNodeSet = this.bpmNodeSetDao.getByActDefIdNodeId(taskEntity.getProcessDefinitionId(), taskEntity.getTaskDefinitionKey());
		ProcessRun processRun = this.dao.getByActInstanceId(taskEntity.getProcessInstanceId());

		setNextTaskUser(processCmd);
		
		//设置抄送人员
		this.setCcUser(processCmd);

		Map optionsMap = handlerFormData(processCmd, processRun);
	

		processCmd.setBusinessKey(processRun.getBusinessKey());
		//TODO refact
		//processCmd.setProcessRun(processRun);
		
		invokeHandler(processCmd, bpmNodeSet, true);

		String parentNodeId = taskEntity.getTaskDefinitionKey();

		ExecutionStack parentStack = null;
		if (processCmd.isBack()) {
			
			//回退第一个节点
			if("first".equals(processCmd.getDestTask())){
				executionStackService.firstPrepared(processCmd, taskEntity);
			}else{
				parentStack = this.executionStackService.backPrepared(processCmd, taskEntity, taskToken);
				if (parentStack != null) {
					parentNodeId = parentStack.getNodeId();
				}
			}
		}
		signUsersOrSignVoted(processCmd, taskId, taskEntity.getTaskDefinitionKey());
		if (processCmd.isOnlyCompleteTask()) {
			this.bpmService.onlyCompleteTask(taskId, processCmd.getVariables());
		} else if (StringUtils.isNotEmpty(processCmd.getDestTask())) {
			this.bpmService.transTo(taskId, processCmd.getDestTask(), processCmd.getVariables());
		} else {
			ExecutionEntity execution = this.bpmActService.getExecution(taskEntity.getExecutionId());

			String jumpTo = this.jumpRule.evaluate(execution, bpmNodeSet.getIsJumpForDef());

			this.bpmService.transTo(taskId, jumpTo, processCmd.getVariables());
		}

		invokeHandler(processCmd, bpmNodeSet, false);
		if ((processCmd.isBack()) && (parentStack != null)) {
			this.executionStackService.pop(parentStack, processCmd.isRecover());
		} else {
			this.executionStackService.addStack(taskEntity.getProcessInstanceId(), parentNodeId, taskToken);
		}

//		if ("ITSM3".equals(businessType)) {
//
//		} else {
			updOption(processCmd, taskId);
//		}

		notify(taskEntity.getProcessInstanceId(), processCmd);
		return processRun;
	}
	
	
	/**
	 * 暂存Task
	 */
	public void temporaryTask(ProcessCmd processCmd) throws Exception{
		String taskId = processCmd.getTaskId();
		TaskEntity taskEntity = this.bpmService.getTask(taskId);
		ProcessRun processRun = this.dao.getByActInstanceId(taskEntity.getProcessInstanceId());
		handlerFormData(processCmd, processRun);
	}

	private void pushUser(Map<SysUser, List<TaskEntity>> users, SysUser user, TaskEntity task) {
		if (users.containsKey(user)) {
			((List) users.get(user)).add(task);
		} else {
			List list = new ArrayList();
			list.add(task);
			users.put(user, list);
		}
	}

	private void notify(String instanceId, ProcessCmd processCmd) throws Exception {
		Map users = new HashMap();

		List<TaskEntity> taskList = this.bpmService.getTasks(instanceId);

		for (TaskEntity task : taskList) {
			if (task.getAssignee() != null) {
				SysUser user = (SysUser) this.sysUserDao.getById(Long.valueOf(Long.parseLong(task.getAssignee())));
				pushUser(users, user, task);
			} else {
				List<Long> cUIds = this.bpmService.getCandidateUsers(new Long(task.getId()));
				for (Long uId : cUIds) {
					SysUser user = (SysUser) this.sysUserDao.getById(uId);
					pushUser(users, user, task);
				}
			}
		}

		String informTypes = "";
		if (processCmd.getFormDataMap() == null) {
			return;
		}
		if (processCmd.getFormDataMap().containsKey("informType")) {
			informTypes = processCmd.getFormDataMap().get("informType").toString();
		}
		for (Iterator iterator = users.keySet().iterator(); iterator.hasNext();) {
			SysUser user = (SysUser) iterator.next();
			List<TaskEntity> tasks = (List) users.get(user);
			for (TaskEntity task : tasks) {
				ProcessRun processRun = this.dao.getByActInstanceId(task.getProcessInstanceId());

				
				sendInnerMessage(user, processRun, task.getId());
				
				if (informTypes.contains("1")) {
					sendShortMessage(user, processRun);
				}

				if (informTypes.contains("2")) {
					sendMail(user, processRun, task.getId());
				}
				
				// add send rtx message
				if (informTypes.contains("11")) {
					sendRtxMessage(user, processRun, task.getId());
				}
				
				
			}
		}
	}
	
	/**
	 * 发送RTX信息
	 * @param receiverUser
	 * @param processRun
	 * @param taskId
	 */
	private void sendRtxMessage(SysUser receiverUser, ProcessRun processRun, String taskId) throws IOException, TemplateException{
		
		Map messageModel = new HashMap();
		messageModel.put("processName", processRun.getSubject());
		messageModel.put("userName", receiverUser.getFullname());
		String url = this.configproperties.get("serverUrl") + "/platform/bpm/task/toStart.xht?taskId=" + taskId;
		messageModel.put("linkStr", url);
		String message = this.freemarkEngine.mergeTemplateIntoString("message/message.ftl", messageModel);
		InnerMessage innerMessage = new InnerMessage();
		innerMessage.setSubject(processRun.getSubject());
		innerMessage.setFrom("0");
		innerMessage.setFromName("系统消息");
		innerMessage.setContent(message);
		innerMessage.setTo(receiverUser.getAccount());
		innerMessage.setToName(receiverUser.getFullname());
		innerMessage.setSendDate(new Date(System.currentTimeMillis()));
		this.messageProducer.send(innerMessage);
	}

	private void sendInnerMessage(SysUser receiverUser, ProcessRun processRun, String taskId) throws IOException, TemplateException {
		Map messageModel = new HashMap();
		messageModel.put("processName", processRun.getSubject());
		messageModel.put("userName", receiverUser.getFullname());
		String url = this.configproperties.get("serverUrl") + "/platform/bpm/task/toStart.xht?taskId=" + taskId;
		messageModel.put("linkStr", url);
		String message = this.freemarkEngine.mergeTemplateIntoString("message/message.ftl", messageModel);
		InnerMessage innerMessage = new InnerMessage();
		innerMessage.setSubject(processRun.getSubject());
		innerMessage.setFrom("0");
		innerMessage.setFromName("系统消息");
		innerMessage.setCanReply(new Short("0").shortValue());
		innerMessage.setContent(message);
		innerMessage.setTo(receiverUser.getUserId().toString());
		innerMessage.setToName(receiverUser.getFullname());
		innerMessage.setSendDate(new Date(System.currentTimeMillis()));
		this.messageProducer.send(innerMessage);
	}

	private void sendShortMessage(SysUser receiverUser, ProcessRun processRun) throws IOException, TemplateException {
		if (receiverUser.getMobile().isEmpty())
			return;
		Map messageModel = new HashMap();
		messageModel.put("processName", processRun.getSubject());
		messageModel.put("userName", receiverUser.getFullname());
		String content = this.freemarkEngine.mergeTemplateIntoString("message/shortMessage.ftl", messageModel);
		SmsMobile smsMobile = new SmsMobile();
		String mobile = receiverUser.getMobile();
		if(mobile.indexOf("/") != -1){
			mobile = mobile.substring(0, mobile.indexOf("/"));
		}
		smsMobile.setPhoneNumber(mobile);
		smsMobile.setSmsContent(content);
		this.messageProducer.send(smsMobile);
	}

	private void sendMail(SysUser receiverUser, ProcessRun processRun, String taskId) throws IOException, TemplateException {
		if (receiverUser.getEmail().isEmpty())
			return;
		Map mailMap = new HashMap();
		mailMap.put("processName", processRun.getSubject());
		mailMap.put("userName", receiverUser.getFullname());
		String url = this.configproperties.get("serverUrl") + "/platform/bpm/task/toStart.xht?taskId=" + taskId;
		mailMap.put("linkStr", url);
		String content = this.freemarkEngine.mergeTemplateIntoString("message/mailMessage.ftl", mailMap);
		MailModel mailModel = new MailModel();
		mailModel.setSubject(processRun.getSubject());
		String[] sendTos = { receiverUser.getEmail() };
		mailModel.setTo(sendTos);
		mailModel.setContent(content);
		mailModel.setSendDate(new Date());
		this.messageProducer.send(mailModel);
	}

	private void updOption(ProcessCmd processCmd, String taskId) {
//		if (BeanUtils.isEmpty(optionsMap)) {
//			return;
//		}
		Long lTaskId = Long.valueOf(taskId);
		TaskOpinion taskOpinion = this.taskOpinionDao.getByTaskId(lTaskId);

//		if (taskOpinion == null) {
//			return;
//		}
//		Set set = optionsMap.keySet();
//		String key = (String) set.iterator().next();
//		String value = (String) optionsMap.get(key);

//		taskOpinion.setFieldName(key);
		taskOpinion.setOpinion(processCmd.getVoteContent());
		
		if(processCmd.isBack()){
			//如果是回退更新
			taskOpinion.setCheckStatus(TaskOpinion.STATUS_BACK);
		}

		this.taskOpinionDao.update(taskOpinion);
	}

	private void signUsersOrSignVoted(ProcessCmd processCmd, String taskId, String taskDefKey) {
		if (StringUtils.isNotEmpty(processCmd.getSignUserIds())) {
			String[] userIds = processCmd.getSignUserIds().split("[,]");
			if (userIds != null) {
				List uIds = Arrays.asList(userIds);
				this.taskUserAssignService.setSignUser(uIds);
			}

		} else if (processCmd.getVoteAgree() != null) {
			if (processCmd.isSignTask()) {
				this.taskSignDataService.signVoteTask(taskId, processCmd.getVoteContent(), processCmd.getVoteAgree());
			}
			processCmd.getVariables().put("approvalStatus_" + taskDefKey, processCmd.getVoteAgree());
			processCmd.getVariables().put("approvalContent_" + taskDefKey, processCmd.getVoteContent());
		}
	}

	private void setSignUsers(ProcessCmd processCmd) {
		this.taskUserAssignService.clearSignUser();
		String users = processCmd.getSignUserIds();
		if (StringUtil.isNotEmpty(users)) {
			String[] userIds = users.split("[,]");
			List uIds = Arrays.asList(userIds);
			this.taskUserAssignService.setSignUser(uIds);
		}
	}

	protected BpmDefinition getBpmDefinitionProcessCmd(ProcessCmd processCmd) {
		BpmDefinition bpmDefinition = null;
		if (processCmd.getActDefId() != null)
			bpmDefinition = this.bpmDefinitionDao.getByActDefId(processCmd.getActDefId());
		else {
			bpmDefinition = this.bpmDefinitionDao.getMainDefByActDefKey(processCmd.getFlowKey());
		}
		return bpmDefinition;
	}

	private ProcessInstance startWorkFlow(ProcessCmd processCmd, String businessKey, String userId) {
		ProcessInstance processInstance = null;
		Authentication.setAuthenticatedUserId(userId);
		if (processCmd.getActDefId() != null)
			processInstance = this.bpmService.startFlowById(processCmd.getActDefId(), businessKey, processCmd.getVariables());
		else {
			processInstance = this.bpmService.startFlowByKey(processCmd.getFlowKey(), businessKey, processCmd.getVariables());
		}
		Authentication.setAuthenticatedUserId(null);
		return processInstance;
	}

	public String getFirstNodetByDefId(String actDefId) {
		String bpmnXml = this.bpmService.getDefXmlByProcessDefinitionId(actDefId);

		String firstTaskNode = BpmUtil.getFirstTaskNode(bpmnXml);
		return firstTaskNode;
	}

	private void invokeHandler(ProcessCmd processCmd, BpmNodeSet bpmNodeSet, boolean isBefore) throws Exception {
		if (bpmNodeSet == null)
			return;
		String handler = "";
		if (isBefore)
			handler = bpmNodeSet.getBeforeHandler();
		else {
			handler = bpmNodeSet.getAfterHandler();
		}
		if (StringUtil.isEmpty(handler)) {
			return;
		}
		String[] aryHandler = handler.split("[.]");
		if (aryHandler != null) {
			String beanId = aryHandler[0];
			String method = aryHandler[1];

			Object serviceBean = ContextUtil.getBean(beanId);
			if (serviceBean != null) {
				Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method, new Class[] { ProcessCmd.class });
				invokeMethod.invoke(serviceBean, new Object[] { processCmd });
			}
		}
	}

	public BpmNodeSet getStartBpmNodeSet(Long defId, String actDefId, String nodeId, Short toFirstNode) {
		BpmNodeSet bpmNodeSetStart = this.bpmNodeSetDao.getBySetType(defId, BpmNodeSet.SetType_StartForm);
		BpmNodeSet bpmNodeSetGlobal = this.bpmNodeSetDao.getBySetType(defId, BpmNodeSet.SetType_GloabalForm);
		if (bpmNodeSetStart == null) {
			if (toFirstNode.shortValue() == 1) {
				BpmNodeSet firstBpmNodeSet = this.bpmNodeSetDao.getByDefIdNodeId(defId, nodeId);

				if (firstBpmNodeSet.getFormType() != null) {
					return firstBpmNodeSet;
				}

				if ((bpmNodeSetGlobal != null) && (bpmNodeSetGlobal.getFormType() != null)) {
					return bpmNodeSetGlobal;
				}

			} else if ((bpmNodeSetGlobal != null) && (bpmNodeSetGlobal.getFormType() != null)) {
				return bpmNodeSetGlobal;
			}
		} else {
			return bpmNodeSetStart;
		}
		return null;
	}

	public ProcessRun startProcess(ProcessCmd processCmd) throws Exception {
		BpmDefinition bpmDefinition = getBpmDefinitionProcessCmd(processCmd);
		
		String businessType = bpmDefinition.getBusinessType();
		processCmd.setBusinessType(businessType);

		long defId = bpmDefinition.getDefId().longValue();

		Short toFirstNode = bpmDefinition.getToFirstNode();

		String actDefId = bpmDefinition.getActDefId();

		String nodeId = getFirstNodetByDefId(actDefId);

		BpmNodeSet bpmNodeSet = getStartBpmNodeSet(Long.valueOf(defId), actDefId, nodeId, toFirstNode);

		String userId = processCmd.getCurrentUserId();

		if (toFirstNode.shortValue() == 1) {
			this.taskUserAssignService.addNodeUserMap(new String[] { nodeId }, new String[] { userId.toString() });
		}

		setNextTaskUser(processCmd);

		ProcessRun processRun = initProcessRun();
		processRun.setActDefId(bpmDefinition.getActDefId());
		processRun.setDefId(bpmDefinition.getDefId());
		processRun.setProcessName(bpmDefinition.getSubject());
		
		String businessKey = handerFormData(processRun, processCmd);
		if (StringUtil.isEmpty(businessKey)) {
			businessKey = processCmd.getBusinessKey();
		}


		invokeHandler(processCmd, bpmNodeSet, true);

		if (StringUtil.isEmpty(businessKey)) {
			businessKey = processCmd.getBusinessKey();
		}

		setSignUsers(processCmd);

		ProcessInstance processInstance = startWorkFlow(processCmd, businessKey, userId.toString());

		String processInstanceId = processInstance.getProcessInstanceId();
		

		
		processRun.setBusinessType(businessType);
		processRun.setBusinessKey(businessKey);
	
		processRun.setBusinessUrl(processCmd.getBusinessUrl());
		processRun.setActInstId(processInstanceId);

		String subject = getSubject(bpmDefinition, processCmd);
		processRun.setSubject(subject);
		this.dao.add(processRun);

		this.executionStackService.initStack(processInstanceId);
		//TODO refact
		//processCmd.setProcessRun(processRun);

		invokeHandler(processCmd, bpmNodeSet, false);

		if (toFirstNode.shortValue() == 1) {
			handJumpOverFirstNode(processInstanceId, processCmd);
		}

		this.bpmFormRunService.addFormRun(actDefId, processRun.getRunId(), processInstanceId);

		notify(processRun.getActInstId(), processCmd);
		return processRun;
	}

	private void handJumpOverFirstNode(String processInstanceId, ProcessCmd processCmd) throws Exception {
		TaskThreadService.clearNewTasks();
		List taskList = this.bpmService.getTasks(processInstanceId);
		TaskEntity taskEntity = (TaskEntity) taskList.get(0);
		String taskId = taskEntity.getId();
		String parentNodeId = taskEntity.getTaskDefinitionKey();

		processCmd.getVariables().put("approvalStatus_" + parentNodeId, TaskOpinion.STATUS_AGREE);
		processCmd.getVariables().put("approvalContent_" + parentNodeId, "填写表单");

		this.bpmService.transTo(taskId, "", processCmd.getVariables());
		this.executionStackService.addStack(taskEntity.getProcessInstanceId(), parentNodeId, "");
	}

	private String handerFormData(ProcessRun processRun, ProcessCmd processCmd) throws Exception {
		String businessKey = null;	
		String json = processCmd.getFormData();
		String userId = processCmd.getCurrentUserId();
		BpmFormData bpmFormData = null; 
		if (StringUtils.isNotEmpty(json)) {
			bpmFormData = FormDataUtil.parseJson(json);
		}else{
			bpmFormData = new BpmFormData();
			BpmNodeSet globalNodeSet = bpmNodeSetDao.getBySetType(processRun.getDefId(), BpmNodeSet.SetType_GloabalForm);
			if(globalNodeSet == null){
				throw new RuntimeException("没有设置全局表单!");
			}
			
			
			BpmFormDef bpmFormDef = bpmFormDefService.getById(globalNodeSet.getFormKey());
			
			BpmFormTable bpmFormTable = bpmFormTableService.getById(bpmFormDef.getTableId());
			PkValue pkValue = FormDataUtil.getPk(bpmFormTable);
			bpmFormData.setPkValue(pkValue);
		    bpmFormData.setTableId(bpmFormTable.getTableId());
		    bpmFormData.setTableName("W_" + bpmFormTable.getTableName());
		    bpmFormData.addMainFields(pkValue.getName(), pkValue.getValue());
		}
		if(bpmFormData.getVariables() != null && bpmFormData.getVariables().size() > 0){
			processCmd.putVariables(bpmFormData.getVariables());
		}
		
		//TODO refact
		//新增功能，默认给 状态
		//bpmFormData.addMainFields("status_", org.sz.core.Constants.TASK_STATUS_RUNNING);
		
		bpmFormData.addMainFields("curentUserId_", userId);
		
		bpmFormData.addMainFields("flowRunId_", processRun.getRunId());
		
		this.bpmFormHandlerDao.handFormData(bpmFormData);
		
		PkValue pkValue = bpmFormData.getPkValue();
		businessKey = pkValue.getValue().toString();
		
		processRun.setPkName(pkValue.getName());
		processRun.setTableName(bpmFormData.getTableName());
		
		processRun.setBusinessType(processCmd.getBusinessType()); //add
		processRun.setBusinessKey(businessKey);
		
		return businessKey;
	}

	private String getSubject(BpmDefinition bpmDefinition, ProcessCmd processCmd) {
		if (StringUtils.isNotEmpty(processCmd.getSubject())) {
			return processCmd.getSubject();
		}

		String rule = bpmDefinition.getTaskNameRule();
		Map map = new HashMap();
		map.put("title", bpmDefinition.getSubject());
		SysUser user = ContextUtil.getCurrentUser();
		map.put("startUser", user.getFullname()); //显示用户中文名称
		map.put("startDate", TimeUtil.getCurrentDate());
		map.put("startTime", TimeUtil.getCurrentTime());
		map.put("businessKey", processCmd.getBusinessKey());
		map.putAll(processCmd.getVariables());
		rule = BpmUtil.getTitleByRule(rule, map);
		return rule;
	}

	private ProcessRun initProcessRun() {
		ProcessRun processRun = new ProcessRun();

		SysUser curUser = ContextUtil.getCurrentUser();
		processRun.setCreator(curUser.getFullname());
		processRun.setCreatorId(curUser.getUserId());

		processRun.setCreatetime(new Date());
		processRun.setStatus(ProcessRun.STATUS_RUNNING);
		try {
			processRun.setRunId(Long.valueOf(UniqueIdUtil.genId()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return processRun;
	}

	public ProcessRun getByActInstanceId(String processInstanceId) {
		return this.dao.getByActInstanceId(processInstanceId);
	}

	public List<ProcessRun> getAllHistory(QueryFilter queryFilter) {
		return this.dao.getAllHistory(queryFilter);
	}

	public List<ProcessRun> getMyAttend(QueryFilter filter) {
		return this.dao.getMyAttend(filter);
	}

	public int updateProcessNameByDefId(Long defId, String processName) {
		return this.dao.updateProcessNameByDefId(defId, processName);
	}

	public void delByIds(Long[] ids) {
		if ((ids == null) || (ids.length == 0))
			return;
		for (Long uId : ids) {
			ProcessRun processRun = (ProcessRun) getById(uId);
			if (processRun.getStatus() != ProcessRun.STATUS_FINISH) {
				this.runtimeService.deleteProcessInstance(processRun.getActInstId(), "deleted by process run instance cascade.");
			}
			delById(uId);
		}
	}

	public List<ProcessRun> getMyProcessRun(Long creatorId, String subject, Short status, PageBean pb) {
		return this.dao.getMyProcessRun(creatorId, subject, status, pb);
	}
}
