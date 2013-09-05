package org.sz.platform.bpm.controller.flow;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.bpm.graph.ShapeMeta;
import org.sz.core.bpm.model.ProcessCmd;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.ContextUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmDefinition;
import org.sz.platform.bpm.model.flow.ExecutionStack;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.model.flow.TaskNodeStatus;
import org.sz.platform.bpm.model.flow.TaskOpinion;
import org.sz.platform.bpm.service.flow.BpmDefinitionService;
import org.sz.platform.bpm.service.flow.BpmNodeSetService;
import org.sz.platform.bpm.service.flow.BpmNodeUserService;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.ExecutionStackService;
import org.sz.platform.bpm.service.flow.ProcessRunService;
import org.sz.platform.bpm.service.flow.TaskOpinionService;
import org.sz.platform.bpm.service.form.BpmFormDefService;
import org.sz.platform.bpm.web.BpmWebUtil;

@Controller
@RequestMapping({ "/platform/bpm/processRun/" })
public class ProcessRunController extends BaseController {

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private BpmService bpmService;

	@Resource
	private BpmNodeUserService bpmNodeUserService;

	@Resource
	private ExecutionStackService executionStackService;

	@Resource
	private TaskOpinionService taskOpinionService;

	@Resource
	private TaskService taskService;

	@Resource
	private BpmDefinitionService bpmDefinitionService;

	@Resource
	private BpmNodeSetService bpmNodeSetService;

	@Resource
	private BpmFormDefService bpmFormDefService;

	@RequestMapping({ "list" })
	@Action(description = "查看流程实例扩展分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.processRunService.getAll(new WebQueryFilter(request,
				"processRunItem"));
		ModelAndView mv = getAutoView().addObject("processRunList", list);
		return mv;
	}

	@RequestMapping({ "recover" })
	@ResponseBody
	public String recover(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String failResult = "0";
		String successResult = "1";
		Long runId = Long.valueOf(RequestUtil.getLong(request, "runId"));
		ProcessRun processRun = (ProcessRun) this.processRunService
				.getById(runId);
		List<TaskEntity> taskList = this.bpmService.getTasks(processRun
				.getActInstId());

		TaskOpinion taskOpinion = this.taskOpinionService.getLatestUserOpinion(
				processRun.getActInstId(), ContextUtil.getCurrentUserId());
		if (taskOpinion == null)
			return failResult;

		ExecutionStack executionStack = this.executionStackService
				.getLastestStack(processRun.getActInstId(),
						taskOpinion.getTaskKey());
		if (executionStack == null)
			return failResult;

		List subStackList = this.executionStackService
				.getByParentIdAndEndTimeNotNull(executionStack.getStackId());
		if (subStackList.size() > 0)
			return failResult;

		for (TaskEntity taskEntity : taskList) {
			ExecutionStack curTaskExecution = this.executionStackService
					.getLastestStack(processRun.getActInstId(),
							taskEntity.getTaskDefinitionKey());

			if (curTaskExecution.getParentId().equals(
					executionStack.getStackId())) {
				this.taskService.setVariable(taskEntity.getId(),
						"approvalStatus_" + taskEntity.getTaskDefinitionKey(),
						TaskOpinion.STATUS_RECOVER);
				ProcessCmd processCmd = new ProcessCmd();
				processCmd.setTaskId(taskEntity.getId());

				processCmd.setRecover(true);

				this.processRunService.nextProcess(processCmd);

				saveResultMessage(request.getSession(), "成功将任务追回！", 1);

				return successResult;
			}
		}
		return failResult;
	}

	@RequestMapping({ "history" })
	@Action(description = "查看流程实例扩展分页列表")
	public ModelAndView history(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new WebQueryFilter(request, "processRunItem");
		List list = this.processRunService.getAllHistory(queryFilter);
		ModelAndView mv = getAutoView().addObject("processRunList", list);
		return mv;
	}

	@RequestMapping({ "myStart" })
	@Action(description = "查看我发起的流程列表")
	public ModelAndView myStart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request, "processRunItem");
		filter.addFilter("creatorId", ContextUtil.getCurrentUserId());
		List list = this.processRunService.getAll(filter);
		ModelAndView mv = getAutoView().addObject("processRunList", list);

		return mv;
	}

	@RequestMapping({ "myAttend" })
	@Action(description = "查看我参与审批流程列表")
	public ModelAndView myAttend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request, "processRunItem");
		filter.addFilter("assignee", ContextUtil.getCurrentUserId().toString());
		List list = this.processRunService.getMyAttend(filter);
		ModelAndView mv = getAutoView().addObject("processRunList", list);
		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除流程实例扩展")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "runId");
			this.processRunService.delByIds(lAryId);
			message = new ResultMessage(1, "删除流程实例成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除流程实例失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑流程实例扩展")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long runId = Long.valueOf(RequestUtil.getLong(request, "runId"));
		String returnUrl = RequestUtil.getPrePage(request);
		ProcessRun processRun = null;
		if (runId.longValue() != 0L)
			processRun = (ProcessRun) this.processRunService.getById(runId);
		else {
			processRun = new ProcessRun();
		}
		return getAutoView().addObject("processRun", processRun).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看流程实例扩展明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "runId");
		ProcessRun processRun = (ProcessRun) this.processRunService
				.getById(Long.valueOf(id));
		if (processRun == null) {
			throw new RuntimeException("流程实例已经删除!");
		}

		BpmDefinition bpmDefinition = bpmDefinitionService.getById(processRun
				.getDefId());

		List hisTasks = this.bpmService.getHistoryTasks(processRun
				.getActInstId());
		return getAutoView().addObject("processRun", processRun)
				.addObject("hisTasks", hisTasks)
				.addObject("bpmDefinition", bpmDefinition);
	}

	@RequestMapping({ "userImage" })
	public ModelAndView userImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String processDefinitionId = null;
		String taskId = request.getParameter("taskId");
		String actInstanceId = "";
		if (StringUtils.isNotEmpty(taskId)) {
			TaskEntity taskEntity = this.bpmService.getTask(taskId);
			processDefinitionId = taskEntity.getProcessDefinitionId();
			actInstanceId = taskEntity.getProcessInstanceId();
		} else {
			String runId = request.getParameter("runId");
			ProcessRun processRun = (ProcessRun) this.processRunService
					.getById(new Long(runId));
			request.setAttribute("processInstanceId", processRun.getActInstId());
			actInstanceId = processRun.getActInstId();
			processDefinitionId = processRun.getActDefId();
		}

		String defXml = this.bpmService
				.getDefXmlByProcessDefinitionId(processDefinitionId);
		ShapeMeta shapeMeta = BpmWebUtil.transGraph(defXml);
		ModelAndView modelAndView = getAutoView();

		modelAndView.addObject("defXml", defXml)
				.addObject("processInstanceId", actInstanceId)
				.addObject("shapeMeta", shapeMeta);

		return modelAndView;
	}

	@RequestMapping({ "getFlowStatusByInstanceId" })
	@ResponseBody
	public List<TaskNodeStatus> getFlowStatusByInstanceId(
			HttpServletRequest request) {
		String instanceId = RequestUtil.getString(request, "instanceId");
		List list = this.bpmService.getNodeCheckStatusInfo(instanceId);
		return list;
	}

	@RequestMapping({ "taskUser" })
	public ModelAndView taskUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Set assignees = new HashSet();

		Set candidateUsers = new HashSet();

		String nodeId = request.getParameter("nodeId");
		String processInstanceId = request.getParameter("processInstanceId");

		ModelAndView modelAndView = getAutoView();
		modelAndView.addObject("assignees", assignees).addObject(
				"candidateUsers", candidateUsers);
		boolean found = false;

		List<TaskEntity> taskList = this.bpmService.getTasks(processInstanceId);

		for (TaskEntity task : taskList) {
			if (task.getTaskDefinitionKey().equals(nodeId)) {
				found = true;
				if (task.getAssignee() != null) {
					assignees.add(task.getAssignee());
				} else {
					List<Long> cUIds = this.bpmService
							.getCandidateUsers(new Long(task.getId()));
					for (Long uId : cUIds) {
						candidateUsers.add(uId.toString());
					}
				}
			}
		}
		if (found)
			return modelAndView;

		List<TaskOpinion> list = this.taskOpinionService.getByActInstIdTaskKey(
				processInstanceId, nodeId);
		for (TaskOpinion taskOpinion : list) {
			if (taskOpinion.getExeUserId() != null) {
				assignees.add(taskOpinion.getExeUserId().toString());
			}
		}

		if (assignees.size() > 0)
			return modelAndView;

		candidateUsers.addAll(this.bpmNodeUserService.getExeUserIdsByInstance(
				processInstanceId, nodeId, ""));

		return modelAndView;
	}

	@RequestMapping({ "nodeForms" })
	public ModelAndView nodeForms(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String runId = request.getParameter("runId");
		ProcessRun processRun = (ProcessRun) this.processRunService
				.getById(new Long(runId));
		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService
				.getById(processRun.getDefId());
		List bpmNodeSetList = this.bpmNodeSetService.getByDefId(processRun
				.getDefId());
		ModelAndView view = getAutoView()
				.addObject("bpmDefinition", bpmDefinition)
				.addObject("bpmNodeSetList", bpmNodeSetList)
				.addObject("processRun", processRun);

		return view;
	}

	@RequestMapping({ "formHtml" })
	public ModelAndView formHtml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long curUserId = ContextUtil.getCurrentUserId();
		String runId = request.getParameter("runId");
		ProcessRun processRun = (ProcessRun) this.processRunService
				.getById(new Long(runId));

		String nodeId = request.getParameter("nodeId");
		String ctxPath = request.getContextPath();
		Map formMap = null;
		try {
			formMap = this.bpmFormDefService.loadForm(processRun, nodeId,
					curUserId, ctxPath);
		} catch (ActivitiException ex) {
			throw new Exception("该流程实例已经结束!");
		}

		Boolean isExtForm = (Boolean) formMap.get("isExtForm");
		String form = (String) formMap.get("form");

		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService
				.getById(processRun.getDefId());

		ModelAndView view = getAutoView()
				.addObject("bpmDefinition", bpmDefinition)
				.addObject("processRun", processRun).addObject("form", form)
				.addObject("isExtForm", isExtForm);

		return view;
	}
}
