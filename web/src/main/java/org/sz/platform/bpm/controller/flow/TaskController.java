package org.sz.platform.bpm.controller.flow;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.bpm.model.ProcessCmd;
import org.sz.core.bpm.util.BpmConst;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.dao.form.BpmFormHandlerDao;
import org.sz.platform.bpm.dao.form.BpmFormTableDao;
import org.sz.platform.bpm.model.flow.BpmDefinition;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.model.flow.BpmNodeSign;
import org.sz.platform.bpm.model.flow.ExecutionStack;
import org.sz.platform.bpm.model.flow.NodeTranUser;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.model.flow.TaskFork;
import org.sz.platform.bpm.model.flow.TaskOpinion;
import org.sz.platform.bpm.model.flow.TaskSignData;
import org.sz.platform.bpm.model.form.BpmFormData;
import org.sz.platform.bpm.model.form.BpmFormDef;
import org.sz.platform.bpm.model.form.BpmFormTable;
import org.sz.platform.bpm.model.form.PkValue;
import org.sz.platform.bpm.service.flow.BpmAgentService;
import org.sz.platform.bpm.service.flow.BpmDefinitionService;
import org.sz.platform.bpm.service.flow.BpmFormRunService;
import org.sz.platform.bpm.service.flow.BpmNodeSetService;
import org.sz.platform.bpm.service.flow.BpmNodeSignService;
import org.sz.platform.bpm.service.flow.BpmNodeUserService;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.ExecutionStackService;
import org.sz.platform.bpm.service.flow.ProcessRunService;
import org.sz.platform.bpm.service.flow.TaskApprovalItemsService;
import org.sz.platform.bpm.service.flow.TaskOpinionService;
import org.sz.platform.bpm.service.flow.TaskSignDataService;
import org.sz.platform.bpm.service.flow.TaskUserService;
import org.sz.platform.bpm.service.form.BpmFormDefService;
import org.sz.platform.bpm.service.form.BpmFormHandlerService;
import org.sz.platform.bpm.util.BpmWebUtil;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.model.SysUserAgent;
import org.sz.platform.system.service.SysUserAgentService;
import org.sz.platform.system.service.SysUserService;

/**
 * 任务控制及处理
 * 
 */
@Controller
@RequestMapping({ "/platform/bpm/task/" })
public class TaskController extends BaseController {
	protected Log logger = LogFactory.getLog(TaskController.class);

	@Resource
	protected BpmService bpmService;
	@Resource
	protected TaskService taskService;

	@Resource
	protected ProcessRunService processRunService;

	@Resource
	protected BpmDefinitionService bpmDefinitionService;

	@Resource
	protected BpmNodeSetService bpmNodeSetService;

	@Resource
	protected TaskSignDataService taskSignDataService;

	@Resource
	protected BpmNodeSignService bpmNodeSignService;

	@Resource
	protected ExecutionStackService executionStackService;

	@Resource
	protected SysUserService sysUserService;

	@Resource
	protected SysUserAgentService sysUserAgentService;

	@Resource
	protected BpmAgentService bpmAgentService;

	@Resource
	protected BpmFormHandlerService bpmFormHandlerService;

	@Resource
	protected BpmFormDefService bpmFormDefService;

	@Resource
	protected BpmNodeUserService bpmNodeUserService;

	@Resource
	protected TaskUserService taskUserService;

	@Resource
	protected BpmFormRunService bpmFormRunService;

	@Resource
	protected TaskApprovalItemsService taskAppItemService;

	@Resource
	protected BpmFormTableDao bpmFormTableDao;

	@Resource
	protected TaskOpinionService taskOpinionService;

	@Resource
	protected BpmFormHandlerDao bpmFormHandlerDao;

	protected Map getForm(BpmNodeSet bpmNodeSet, String path) throws Exception {
		if (bpmNodeSet == null)
			return null;
		boolean isExtForm = false;
		String form = "";
		if (bpmNodeSet.getFormType() == BpmConst.OnLineForm) {
			Long formDefId = bpmNodeSet.getFormDefId();
			if ((formDefId != null) && (formDefId.longValue() > 0L)) {
				BpmFormDef bpmFormDef = (BpmFormDef) this.bpmFormDefService
						.getById(formDefId);
				if (bpmFormDef != null) {
					BpmFormTable bpmFormTable = (BpmFormTable) this.bpmFormTableDao
							.getById(bpmFormDef.getTableId());
					bpmFormDef.setTableName(bpmFormTable.getTableName());
					form = this.bpmFormHandlerService.obtainHtml(bpmFormDef,
							ContextUtil.getCurrentUserId(), null, "", "", "");
				}
			}
		} else {
			form = path + bpmNodeSet.getFormUrl();
			isExtForm = true;
		}
		Map map = new HashMap();
		map.put("isExtForm", Boolean.valueOf(isExtForm));
		map.put("form", form);
		return map;
	}

	@RequestMapping({ "startFlowForm" })
	// @Action(description = "跳至启动流程页面")
	public ModelAndView startFlowForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long serviceItemId = Long.valueOf(RequestUtil.getLong(request,
				"serviceItemId"));
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		Long deskRequestId = Long.valueOf(RequestUtil.getLong(request,
				"deskRequestId"));

		BpmDefinition bpmDefinition = null;
		if (defId > 0L) {
			bpmDefinition = this.bpmDefinitionService.getById(defId);
		} else {
			String actDefKey = RequestUtil.getString(request, "actDefKey");
			if (StringUtils.isNotBlank(actDefKey)) {
				bpmDefinition = this.bpmDefinitionService
						.getMainDefByActDefKey(actDefKey);
			}
		}

		String actDefId = bpmDefinition.getActDefId();

		short toFirstNode = bpmDefinition.getToFirstNode().shortValue();

		BpmNodeSet bpmNodeSet = this.bpmFormRunService.getStartBpmNodeSet(
				actDefId, Short.valueOf(toFirstNode));

		String ctxPath = request.getContextPath();

		Map map = getForm(bpmNodeSet, ctxPath);

		boolean isFormEmpty = false;
		Boolean isExtForm = Boolean.valueOf(false);
		String form = "";
		if (map == null) {
			isFormEmpty = true;
		} else {
			form = map.get("form").toString();
			isExtForm = (Boolean) map.get("isExtForm");
			if (form.isEmpty()) {
				isFormEmpty = true;
			}
		}

		String businessType = bpmDefinition.getBusinessType();

		ModelAndView mv = getAutoView(businessType);
		mv.addObject("bpmDefinition", bpmDefinition);
		mv.addObject("form", form);
		mv.addObject("defId", defId);
		mv.addObject("isExtForm", isExtForm);
		mv.addObject("isFormEmpty", Boolean.valueOf(isFormEmpty));

		return mv;
	}

	@RequestMapping({ "startFlow" })
	// @Action(description = "启动流程")
	public void startFlow(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			ProcessCmd processCmd = BpmWebUtil.getProcessCmd(request);
			Long userId = ContextUtil.getCurrentUserId();
			processCmd.setCurrentUserId(userId.toString());
			this.processRunService.startProcess(processCmd);
			ResultMessage resultMessage = new ResultMessage(1, "启动流程成功!");
			out.print(resultMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.logger.debug("startFlow:" + ex.getMessage());
			ResultMessage resultMessage = new ResultMessage(0, "启动流程失败:"
					+ ex.getMessage());
			out.print(resultMessage);
		}
	}

	@ResponseBody
	@RequestMapping({ "ajaxStartFlow" })
	public String ajaxStartFlow(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ProcessCmd processCmd = BpmWebUtil.getProcessCmd(request);
			Long userId = ContextUtil.getCurrentUserId();
			processCmd.setCurrentUserId(userId.toString());
			ProcessRun processRun = this.processRunService
					.startProcess(processCmd);

			if (processRun != null
					&& StringUtils.isNotBlank(processRun.getActInstId())) {
				List<Task> taskList = this.taskService.createTaskQuery()
						.processInstanceId(processRun.getActInstId()).list();
				if (taskList != null && taskList.size() > 0) {
					return taskList.get(0).getId();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	@RequestMapping({ "back" })
	public ModelAndView back(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");

		if (StringUtils.isEmpty(taskId)) {
			return getAutoView();
		}
		TaskEntity taskEntity = this.bpmService.getTask(taskId);
		String taskToken = (String) this.taskService.getVariableLocal(
				taskEntity.getId(), TaskFork.TAKEN_VAR_NAME);
		ExecutionStack executionStack = this.executionStackService
				.getLastestStack(taskEntity.getProcessInstanceId(),
						taskEntity.getTaskDefinitionKey(), taskToken);
		if ((executionStack != null) && (executionStack.getParentId() != null)
				&& (executionStack.getParentId().longValue() != 0L)) {
			ExecutionStack parentStack = (ExecutionStack) this.executionStackService
					.getById(executionStack.getParentId());
			String assigneeNames = "";
			if (StringUtils.isNotEmpty(parentStack.getAssignees())) {
				String[] uIds = parentStack.getAssignees().split("[,]");
				int i = 0;
				for (String uId : uIds) {
					SysUser sysUser = (SysUser) this.sysUserService
							.getById(new Long(uId));
					if (sysUser == null)
						continue;
					if (i++ > 0) {
						assigneeNames = assigneeNames + ",";
					}
					assigneeNames = assigneeNames + sysUser.getFullname();
				}
			}
			request.setAttribute("assigneeNames", assigneeNames);
			request.setAttribute("parentStack", parentStack);
		}

		request.setAttribute("taskId", taskId);

		return getAutoView();
	}

	@RequestMapping({ "jumpBack" })
	public ModelAndView jumpBack(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProcessCmd processCmd = BpmWebUtil.getProcessCmd(request);
		processCmd.setCurrentUserId(ContextUtil.getCurrentUserId().toString());
		this.processRunService.nextProcess(processCmd);
		return new ModelAndView("redirect:list.xht");
	}

	@RequestMapping({ "toSign" })
	public ModelAndView toSign(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		ModelAndView modelView = getAutoView();

		if (StringUtils.isNotEmpty(taskId)) {
			TaskEntity taskEntity = this.bpmService.getTask(taskId);
			String nodeId = this.bpmService.getExecution(
					taskEntity.getExecutionId()).getActivityId();
			String actInstId = taskEntity.getProcessInstanceId();
			Integer maxSignNums = this.taskSignDataService.getMaxSignNums(
					actInstId, nodeId, TaskSignData.NOT_COMPLETED);
			List<TaskSignData> signDataList = this.taskSignDataService
					.getByActInstIdNodeIdSignNums(actInstId, nodeId,
							maxSignNums);

			BpmNodeSign bpmNodeSign = this.bpmNodeSignService
					.getByDefIdAndNodeId(taskEntity.getProcessDefinitionId(),
							nodeId);

			modelView.addObject("signDataList", signDataList);
			modelView.addObject("task", taskEntity);
			modelView.addObject("curUser", ContextUtil.getCurrentUser());
			modelView.addObject("bpmNodeSign", bpmNodeSign);
		}

		return modelView;
	}

	@RequestMapping({ "addSign" })
	@ResponseBody
	public String addSign(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		String signUserIds = request.getParameter("signUserIds");
		if ((StringUtils.isNotEmpty(taskId))
				&& (StringUtils.isNotEmpty(signUserIds))) {
			this.taskSignDataService.addSign(signUserIds, taskId);
		}
		return "{success:true}";
	}

	@RequestMapping({ "jump" })
	@ResponseBody
	public String jump(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String taskId = request.getParameter("taskId");
		String destTask = request.getParameter("destTask");
		this.bpmService.transTo(taskId, destTask, null);

		return "{success:true}";
	}

	@RequestMapping({ "saveSign" })
	@ResponseBody
	public String saveSign(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		String isAgree = request.getParameter("isAgree");
		String content = request.getParameter("content");

		this.taskSignDataService.signVoteTask(taskId, content, new Short(
				isAgree));

		return "{success:true}";
	}

	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request, "taskItem");
		List<TaskEntity> list = this.bpmService.getTasks(filter);
		ModelAndView mv = getAutoView().addObject("taskList", list);

		return mv;
	}

	@RequestMapping({ "forMe" })
	public ModelAndView forMe(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request, "taskItem");
		List list = this.bpmService.getMyTasks(filter);
		ModelAndView mv = getAutoView().addObject("taskList", list);
		return mv;
	}

	@RequestMapping({ "ccTask" })
	public ModelAndView ccTask(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request, "taskItem");
		List<TaskEntity> list = this.bpmService.getMyCcTasks(filter);
		ModelAndView mv = getAutoView().addObject("taskList", list);
		return mv;
	}

	public String myEvent(Object param) throws Exception {
		return this.bpmService.getMyEvents(param);
	}

	@RequestMapping({ "detail" })
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long taskId = Long.valueOf(RequestUtil.getLong(request, "taskId"));
		Task task = (Task) this.taskService.createTaskQuery()
				.taskId(taskId.toString()).singleResult();

		ProcessRun processRun = this.processRunService.getByActInstanceId(task
				.getProcessInstanceId());

		BpmDefinition bpmDefinition = this.bpmDefinitionService
				.getById(processRun.getDefId());
		ModelAndView modelView = getAutoView();
		modelView.addObject("bpmDefinition", bpmDefinition);
		modelView.addObject("task", task);
		modelView.addObject("processRun", processRun);
		if (StringUtils.isNotEmpty(processRun.getBusinessUrl())) {
			String businessUrl = StringUtil.formatParamMsg(
					processRun.getBusinessUrl(),
					new Object[] { processRun.getBusinessKey() }).toString();
			modelView.addObject("businessUrl", businessUrl);
		}
		return modelView;
	}

	@RequestMapping({ "toStart" })
	public ModelAndView toStart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ctxPath = request.getContextPath();
		String taskId = RequestUtil.getString(request, "taskId");
		boolean showChooseCaseNo = false;
		// businessType 为ITSM3的时候，传入的caseType
		String caseType = RequestUtil
				.getString(request, "caseType", "incident");

		String returnUrl = null;

		if (StringUtils.isEmpty(returnUrl)) {
			returnUrl = "list.xht";
		}

		TaskEntity taskEntity = this.bpmService.getTask(taskId);

		if (taskEntity == null) {
			return new ModelAndView("redirect:notExist.xht");
		}
		String instanceId = taskEntity.getProcessInstanceId();
		String taskName = taskEntity.getTaskDefinitionKey();
		String actDefId = taskEntity.getProcessDefinitionId();
		Long userId = ContextUtil.getCurrentUserId();

		BpmDefinition bpmDefinition = this.bpmDefinitionService
				.getByActDefId(actDefId);

		//if (bpmDefinition.getIsIso() == 1) {
		//	showChooseCaseNo = true;
		//}

		ProcessRun processRun = this.processRunService
				.getByActInstanceId(instanceId);

		BpmNodeSet bpmNodeSet = this.bpmNodeSetService.getByActDefIdNodeId(
				actDefId, taskName);

		String form = "";
		Boolean isExtForm = Boolean.valueOf(false);
		Boolean isEmptyForm = Boolean.valueOf(false);
		String sourceCaseId = "";
		String sourceCaseNo = "";
		String destCaseNo = "";
		String changeReason = "";
		if (bpmDefinition != null) {
			Map formMap = this.bpmFormDefService.loadForm(processRun, taskName,
					userId, ctxPath);
			Object formDataObj = formMap.get("bpmFormData");
			if (formDataObj != null) {
				BpmFormData bpmFormData = (BpmFormData) formDataObj;
				Map mapField = bpmFormData.getMainFields();
				if (mapField.get("sourcecaseid_") != null) {
					sourceCaseId = mapField.get("sourcecaseid_").toString();
				}
				if (mapField.get("sourcecaseno_") != null) {
					sourceCaseNo = mapField.get("sourcecaseno_").toString();
				}

				if (mapField.get("case_no") != null) {
					destCaseNo = mapField.get("case_no").toString();
				}

				if (mapField.get("changereason_") != null) {
					changeReason = mapField.get("changereason_").toString();
				}

			}
			isExtForm = (Boolean) formMap.get("isExtForm");
			form = (String) formMap.get("form");
			isEmptyForm = (Boolean) formMap.get("isEmptyForm");
		}

		boolean isSignTask = this.bpmService.isSignTask(taskEntity);

		boolean isAllowBack = false;
		if (BpmNodeSet.BACK_ALLOW.equals(bpmNodeSet.getIsAllowBack())) {
			isAllowBack = getIsAllowBackByTask(taskEntity);
		}

		List taskAppItems = this.taskAppItemService.getApprovalByActDefId(
				taskEntity.getProcessDefinitionId(),
				taskEntity.getTaskDefinitionKey());

		// 检查业务分类, add by bobo, 2013
		String businessType = bpmDefinition.getBusinessType();

		String buinessKey = processRun.getBusinessKey();

		Long caseId = null;

		if (StringUtil.isNotEmpty(buinessKey)) {
			caseId = Long.valueOf(buinessKey);
		}

		ModelAndView mv = getAutoView(businessType);

		mv.addObject("task", taskEntity);
		mv.addObject("returnUrl", returnUrl);
		mv.addObject("bpmNodeSet", bpmNodeSet);
		mv.addObject("processRun", processRun);
		mv.addObject("bpmDefinition", bpmDefinition);
		mv.addObject("isAllowBack", Boolean.valueOf(isAllowBack));
		mv.addObject("isSignTask", Boolean.valueOf(isSignTask));
		mv.addObject("form", form);
		mv.addObject("isExtForm", isExtForm);
		mv.addObject("isEmptyForm", isEmptyForm);
		mv.addObject("taskAppItems", taskAppItems);
		mv.addObject("buinessType", businessType);
		mv.addObject("showChooseCaseNo", showChooseCaseNo);
		mv.addObject("sourceCaseNo", sourceCaseNo);
		mv.addObject("sourceCaseId", sourceCaseId);
		mv.addObject("changeReason", changeReason);

		return mv;
	}

	/**
	 * ISO特殊流程处理 拷贝工单
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping({ "copyTask" })
	public void copyTask(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		Long destCaseId = RequestUtil.getLong(request, "destCaseId");
		Long sourceCaseId = RequestUtil.getLong(request, "sourceCaseId");
		String sourceCaseNo = RequestUtil.getString(request, "sourceCaseNo");
		String destCaseNo = RequestUtil.getString(request, "destCaseNo");

		// 原因
		String changeReason = RequestUtil.getString(request, "changeReason");
		Long tableId = RequestUtil.getLong(request, "tableId");

		BpmFormData bpmFormData = bpmFormHandlerService.getByKey(tableId,
				sourceCaseId.toString());
		Object flowRunIdObj = bpmFormData.getMainFields().get("flowrunid_");
		Long flowRunId = null;
		if (flowRunIdObj != null) {
			flowRunId = Long.valueOf(flowRunIdObj.toString());
		}
		// 转换fieldName
		bpmFormData.setMainFields(BpmFormTable.fieldNameMapConvert(bpmFormData
				.getMainFields()));

		PkValue pkValue = bpmFormData.getPkValue();
		pkValue.setValue(destCaseId);
		pkValue.setIsAdd(false);
		bpmFormData.setPkValue(pkValue);

		bpmFormData.getMainFields().put("sourcecaseid_", sourceCaseId);
		bpmFormData.getMainFields().put("changereason_", changeReason);
		bpmFormData.getMainFields().put("sourcecaseno_", sourceCaseNo);
		// 注意此处的key ,要保存数据有些字段, 设置当前工单的caseNo 是从前台传过来的
		bpmFormData.getMainFields().put("F_case_no", destCaseNo);

		// bpmFormHandlerService.handFormData(bpmFormData);
		bpmFormHandlerService.copyTask(bpmFormData,
				String.valueOf(sourceCaseId), flowRunId);
		//
		// 成功之后转到toStart
		response.sendRedirect(request.getContextPath()
				+ "/platform/bpm/task/toStart.xht?taskId=" + taskId
				+ "&readOnly=false");
	}

	@RequestMapping({ "opinionEdit" })
	public ModelAndView opinionEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ctxPath = request.getContextPath();
		Long opinionId = RequestUtil.getLong(request, "opinionId");
		TaskOpinion taskOpinion = taskOpinionService.getById(opinionId);
		ModelAndView mv = getAutoView("ITSM3");
		mv.addObject("taskOpinion", taskOpinion);
		return mv;
	}

	@RequestMapping({ "getForm" })
	public ModelAndView getForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ctxPath = request.getContextPath();
		String taskId = RequestUtil.getString(request, "taskId");

		TaskEntity taskEntity = this.bpmService.getTask(taskId);

		if (taskEntity == null) {
			return new ModelAndView("redirect:notExist.xht");
		}
		String instanceId = taskEntity.getProcessInstanceId();
		String taskName = taskEntity.getTaskDefinitionKey();
		String actDefId = taskEntity.getProcessDefinitionId();
		Long userId = ContextUtil.getCurrentUserId();

		BpmDefinition bpmDefinition = this.bpmDefinitionService
				.getByActDefId(actDefId);

		String businessType = bpmDefinition.getBusinessType();

		ProcessRun processRun = this.processRunService
				.getByActInstanceId(instanceId);

		String buinessKey = processRun.getBusinessKey();

		String form = "";
		Boolean isExtForm = Boolean.valueOf(false);
		Boolean isEmptyForm = Boolean.valueOf(false);

		if (bpmDefinition != null) {
			Map formMap = this.bpmFormDefService.loadForm(processRun, taskName,
					userId, ctxPath);
			isExtForm = (Boolean) formMap.get("isExtForm");
			form = (String) formMap.get("form");
			isEmptyForm = (Boolean) formMap.get("isEmptyForm");
		}

		ModelAndView mv = getAutoView(businessType);
		mv.addObject("task", taskEntity);
		mv.addObject("form", form);
		mv.addObject("bpmDefinition", bpmDefinition);
		mv.addObject("isExtForm", isExtForm);
		mv.addObject("isEmptyForm", isEmptyForm);
		mv.addObject("processRun", processRun);

		return mv;
	}

	protected boolean getIsAllowBackByTask(TaskEntity taskEntity) {
		boolean isAllowBack = false;

		ExecutionStack executionStack = this.executionStackService
				.getLastestStack(taskEntity.getProcessInstanceId(),
						taskEntity.getTaskDefinitionKey());
		if ((executionStack != null) && (executionStack.getParentId() != null)
				&& (executionStack.getParentId().longValue() != 0L)) {
			ExecutionStack parentStack = (ExecutionStack) this.executionStackService
					.getById(executionStack.getParentId());
			if (parentStack != null) {
				isAllowBack = true;
			}
		}

		return isAllowBack;
	}

	// 暂存
	@RequestMapping({ "temporary" })
	public void temporary(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			ProcessCmd taskCmd = BpmWebUtil.getProcessCmd(request);
			taskCmd.setCurrentUserId(ContextUtil.getCurrentUserId().toString());
			processRunService.temporaryTask(taskCmd);
			ResultMessage resultMessage = new ResultMessage(1, "暂存任务成功!");
			out.print(resultMessage);
		} catch (Exception ex) {

			ex.printStackTrace();
			ResultMessage resultMessage = new ResultMessage(0, "暂存任务失败!");
			out.print(resultMessage);
		}
	}

	@RequestMapping({ "complete" })
	public void complete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		this.logger.debug("任务完成跳转....");
		try {
			ProcessCmd taskCmd = BpmWebUtil.getProcessCmd(request);
			taskCmd.setCurrentUserId(ContextUtil.getCurrentUserId().toString());
			this.processRunService.nextProcess(taskCmd);

			ResultMessage resultMessage = new ResultMessage(1, "任务成功完成!");
			out.print(resultMessage);
		} catch (Exception ex) {

			ex.printStackTrace();
			ResultMessage resultMessage = new ResultMessage(0, "任务跳转失败!");
			out.print(resultMessage);
		}
	}

	@RequestMapping({ "saveTaskChange" })
	public void saveTaskChange(HttpServletRequest request,
			HttpServletResponse response, TaskOpinion taskOpinion)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			TaskOpinion opinion = taskOpinionService.getById(taskOpinion
					.getOpinionId());
			opinion.setStartTime(taskOpinion.getStartTime());
			opinion.setEndTime(taskOpinion.getEndTime());
			opinion.setExeFullname(taskOpinion.getExeFullname());
			opinion.setExeUserId(taskOpinion.getExeUserId());
			opinion.setDurTime(taskOpinion.getDurTime());
			opinion.setOpinion(taskOpinion.getOpinion());
			taskOpinionService.update(opinion);
			ResultMessage resultMessage = new ResultMessage(1, "历史修改完成!");
			out.print(resultMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
			ResultMessage resultMessage = new ResultMessage(0, "历史修改失败!");
			out.print(resultMessage);
		}
	}

	@RequestMapping({ "claim" })
	// @Action(description = "锁定任务")
	public ModelAndView claim(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		int isAgent = RequestUtil.getInt(request, "isAgent");
		String assignee = ContextUtil.getCurrentUserId().toString();
		if (isAgent == 1) {
			List<Long> candidates = this.bpmService.getCandidateUsers(Long
					.valueOf(Long.parseLong(taskId)));
			QueryFilter agentQueryFilter = new WebQueryFilter(request);
			agentQueryFilter.getFilters().put("userId", assignee);
			List<Long> agents = this.bpmService
					.getAgentIdByTaskId(agentQueryFilter);
			for (Long candidate : candidates)
				if (agents.contains(candidate)) {
					assignee = candidate.toString();
					break;
				}
		}
		try {
			this.taskService.claim(taskId, assignee);
			saveSuccessResultMessage(request.getSession(), "成功锁定任务!");
		} catch (Exception ex) {
			saveSuccessResultMessage(request.getSession(), "任务已经完成或被其他用户锁定!");
		}
		return new ModelAndView("redirect:forMe.xht");
	}

	@RequestMapping({ "unlock" })
	// @Action(description = "解锁任务")
	public ModelAndView unlock(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");

		if (StringUtils.isNotEmpty(taskId)) {
			this.bpmService.updateTaskAssigneeNull(taskId);
			saveSuccessResultMessage(request.getSession(), "任务已经成功解锁!");
		}
		return new ModelAndView("redirect:forMe.xht");
	}

	@RequestMapping({ "freeJump" })
	public ModelAndView freeJump(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		Map jumpNodesMap = this.bpmService.getJumpNodes(taskId);
		ModelAndView view = getAutoView();
		view.addObject("jumpNodeMap", jumpNodesMap);
		return view;
	}

	@RequestMapping({ "getTaskUsers" })
	@ResponseBody
	public String getTaskUsers(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String nodeId = request.getParameter("nodeId");

		String taskId = request.getParameter("taskId");

		TaskEntity taskEntity = this.bpmService.getTask(taskId);

		List<String> userIdList = this.bpmNodeUserService
				.getExeUserIdsByInstance(taskEntity.getProcessInstanceId(),
						nodeId, "");
		StringBuffer sb = new StringBuffer("[");
		int i = 0;
		for (String userId : userIdList) {
			SysUser sysUser = (SysUser) this.sysUserService.getById(new Long(
					userId));
			if (sysUser != null) {
				i++;
				sb.append("{userId:").append(sysUser.getUserId())
						.append(",fullname:'").append(sysUser.getFullname())
						.append("'},");
			}
		}
		if (i > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		return sb.toString();
	}

	// @Action(description = "任务指派所属人")
	@RequestMapping({ "assign" })
	public ModelAndView assign(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskIds = request.getParameter("taskIds");
		String userId = request.getParameter("userId");

		if (StringUtils.isNotEmpty(taskIds)) {
			String[] tIds = taskIds.split("[,]");
			if (tIds != null) {
				for (String tId : tIds) {
					this.bpmService.assignTask(tId, userId);
				}
			}
		}
		saveSuccessResultMessage(request.getSession(), "成功为指定任务任务分配执行人员!");
		return new ModelAndView("redirect:list.xht");
	}

	@RequestMapping({ "setAssignee" })
	// @Action(description = "任务指派执行人")
	@ResponseBody
	public String setAssignee(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskIds");
		String userId = request.getParameter("userId");
		if ((StringUtils.isNotEmpty(taskId)) && (StringUtil.isNotEmpty(userId))) {
			this.bpmService.updateTaskAssignee(taskId, userId);
		}
		return "{success:true}";
	}

	@RequestMapping({ "setOwner" })
	// @Action(description = "任务指派所属人")
	@ResponseBody
	public String setOwner(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskIds");
		String userId = request.getParameter("userId");
		if ((StringUtils.isNotEmpty(taskId)) && (StringUtil.isNotEmpty(userId))) {
			this.bpmService.updateTaskOwner(taskId, userId);
		}
		return "{success:true}";
	}

	@RequestMapping({ "setDueDate" })
	// @Action(description = "设置任务到期时间")
	public ModelAndView setDueDate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskIds = request.getParameter("taskIds");
		String dueDates = request.getParameter("dueDates");
		if ((StringUtils.isNotEmpty(taskIds))
				&& (StringUtils.isNotEmpty(dueDates))) {
			String[] tIds = taskIds.split("[,]");
			String[] dates = dueDates.split("[,]");
			if (tIds != null) {
				for (int i = 0; i < dates.length; i++) {
					if (StringUtils.isNotEmpty(dates[i])) {
						Date dueDate = DateUtils.parseDate(dates[i],
								new String[] { "yyyy-MM-dd HH:mm:ss" });
						this.bpmService.setDueDate(tIds[i], dueDate);
					}
				}
			}
		}
		return new ModelAndView("redirect:list.xht");
	}

	@RequestMapping({ "delete" })
	// @Action(description = "删除任务")
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String taskId = request.getParameter("taskId");
			String taskIds = request.getParameter("id");
			if (StringUtils.isNotEmpty(taskId)) {
				this.bpmService.deleteTask(taskId);
			} else if (StringUtils.isNotEmpty(taskIds)) {
				String[] ids = taskIds.split("[,]");
				this.bpmService.deleteTasks(ids);
			}
			message = new ResultMessage(1, "删除任务成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除任务失败");
		}
		addMessage(message, request);
		return new ModelAndView("redirect:list.xht");
	}

	@RequestMapping({ "forAgent" })
	public ModelAndView forAgent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		Long userId = Long.valueOf(RequestUtil.getLong(request, "userId"));
		QueryFilter filter = new WebQueryFilter(request, "taskItem");

		filter.addFilter("curTime", new Date());
		List list = null;
		SysUserAgent sysUserAgent = null;

		if (userId.longValue() != 0L) {
			sysUserAgent = (SysUserAgent) this.sysUserAgentService
					.getById(userId);
		}
		if (sysUserAgent != null) {
			if (sysUserAgent.getIsall().intValue() == SysUserAgent.IS_ALL_FLAG) {
				list = this.bpmService.getTaskByUserId(
						sysUserAgent.getAgentuserid(), filter);
			} else {
				StringBuffer actDefId = new StringBuffer("");
				List<String> notInBpmAgentlist = this.bpmAgentService
						.getNotInByAgentId(sysUserAgent.getAgentid());
				for (String ba : notInBpmAgentlist) {
					actDefId.append("'").append(ba).append("',");
				}
				if (notInBpmAgentlist.size() > 0) {
					actDefId.deleteCharAt(actDefId.length() - 1);
				}
				list = this.bpmService.getAgentTasks(
						sysUserAgent.getAgentuserid(), actDefId.toString(),
						filter);
			}
		} else
			list = this.bpmService.getAllAgentTask(
					ContextUtil.getCurrentUserId(), filter);

		mv = getAutoView().addObject("taskList", list).addObject("userId",
				userId);

		return mv;
	}

	@RequestMapping({ "tranTaskUserMap" })
	public ModelAndView tranTaskUserMap(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		int selectPath = RequestUtil.getInt(request, "selectPath", 1);
		List<NodeTranUser> nodeTranUserList = this.bpmService
				.getNodeTaskUserMap(taskId, ContextUtil.getCurrentUserId());

		ModelAndView mv = getAutoView();
		mv.addObject("nodeTranUserList", nodeTranUserList);
		mv.addObject("selectPath", Integer.valueOf(selectPath));
		return mv;
	}

	@RequestMapping({ "miniDetail" })
	public ModelAndView miniDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");

		TaskEntity taskEntity = this.bpmService.getTask(taskId);

		if (taskEntity == null) {
			return new ModelAndView("/platform/bpm/taskNotExist.jsp");
		}

		Set candidateUsers = this.taskUserService.getUserCandidateUsers(taskId);

		ProcessRun processRun = this.processRunService
				.getByActInstanceId(taskEntity.getProcessInstanceId());

		BpmDefinition definition = this.bpmDefinitionService
				.getByActDefId(taskEntity.getProcessDefinitionId());

		List curTaskList = this.bpmService.getTasks(taskEntity
				.getProcessInstanceId());

		return getAutoView().addObject("taskEntity", taskEntity)
				.addObject("processRun", processRun)
				.addObject("candidateUsers", candidateUsers)
				.addObject("processDefinition", definition)
				.addObject("curTaskList", curTaskList);
	}

	@RequestMapping({ "changePath" })
	public ModelAndView changePath(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		TaskEntity taskEntity = this.bpmService.getTask(taskId);
		Map taskNodeMap = this.bpmService.getTaskNodes(
				taskEntity.getProcessDefinitionId(),
				taskEntity.getTaskDefinitionKey());
		return getAutoView().addObject("taskEntity", taskEntity)
				.addObject("taskNodeMap", taskNodeMap)
				.addObject("curUser", ContextUtil.getCurrentUser());
	}

	@RequestMapping({ "saveChangePath" })
	@ResponseBody
	public String saveChangePath(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProcessCmd processCmd = BpmWebUtil.getProcessCmd(request);
		this.processRunService.nextProcess(processCmd);
		saveSuccessResultMessage(request.getSession(), "更改任务执行的路径!");
		return "{success:true}";
	}

	@RequestMapping({ "end" })
	@ResponseBody
	public String end(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String taskId = request.getParameter("taskId");
		String voteContent = "由" + ContextUtil.getCurrentUser().getFullname()
				+ "进行完成操作！";
		ProcessCmd cmd = new ProcessCmd();
		cmd.setTaskId(taskId);
		cmd.setVoteAgree(Short.valueOf("0"));
		cmd.setVoteContent(voteContent);
		cmd.setOnlyCompleteTask(true);
		this.processRunService.nextProcess(cmd);
		saveSuccessResultMessage(request.getSession(), "成功完成任务!");
		return "{success:true}";
	}

	@RequestMapping({ "vadlidField" })
	@ResponseBody
	public String vadlidField(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tableName = request.getParameter("tableName");
		String field = request.getParameter("field");
		Map map = new HashMap();
		map.put("tableName", tableName);
		map.put("field", field);
		map.put("value", request.getParameter("value"));
		map.put("sql", "select count(F_" + field + ") from w_" + tableName
				+ " where F_" + field + "='" + request.getParameter("value")
				+ "'");
		SqlSession session = this.bpmFormTableDao.getSqlSessionFactory()
				.openSession();

		Object obj = session.selectOne("Common_getSingleBySql", map);

		if (obj != null && Long.valueOf(obj.toString()) < 1) {
			return "{success:true}";
		}
		return "{success:false}";
	}

}
