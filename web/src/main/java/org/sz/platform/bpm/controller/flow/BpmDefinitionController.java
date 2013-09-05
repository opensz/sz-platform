package org.sz.platform.bpm.controller.flow;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.bpm.graph.ShapeMeta;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmDefinition;
import org.sz.platform.bpm.model.flow.BpmNode;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.model.flow.BpmNodeUser;
import org.sz.platform.bpm.model.flow.BpmNodeUserUplow;
import org.sz.platform.bpm.model.flow.NodeUserMap;
import org.sz.platform.bpm.service.flow.BpmDefinitionService;
import org.sz.platform.bpm.service.flow.BpmFormRunService;
import org.sz.platform.bpm.service.flow.BpmNodeSetService;
import org.sz.platform.bpm.service.flow.BpmNodeUserService;
import org.sz.platform.bpm.service.flow.BpmNodeUserUplowService;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.ProcessRunService;
import org.sz.platform.bpm.util.BpmWebUtil;
import org.sz.platform.system.model.GlobalType;
import org.sz.platform.system.model.SysRole;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.GlobalTypeService;
import org.sz.platform.system.service.SysOrgService;
import org.sz.platform.system.service.SysRoleService;

@Controller
@RequestMapping({ "/platform/bpm/bpmDefinition/" })
public class BpmDefinitionController extends BaseController {

	@Resource
	private BpmService bpmService;

	@Resource
	private BpmDefinitionService bpmDefinitionService;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private GlobalTypeService globalTypeService;

	@Resource
	private BpmNodeUserService bpmNodeUserService;

	@Resource
	private BpmNodeSetService bpmNodeSetService;

	@Resource
	private SysRoleService sysRoleService;

	@Resource
	private SysOrgService sysOrgService;

	@Resource
	private BpmNodeUserUplowService bpmNodeUserUplowService;

	@Resource
	private BpmFormRunService bpmFormRunService;

	@RequestMapping({ "bpmnXml" })
	public ModelAndView bpmnXml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "defId");
		BpmDefinition po = (BpmDefinition) this.bpmDefinitionService
				.getById(Long.valueOf(id));
		if (po.getActDeployId() != null) {
			String bpmnXml = this.bpmService.getDefXmlByDeployId(po
					.getActDeployId().toString());

			if (bpmnXml
					.startsWith("<?xml version=\"1.0\" encoding=\"utf-8\"?>")) {
				bpmnXml = bpmnXml.replace(
						"<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
			}
			if (bpmnXml.startsWith("<?xml version=\"1.0\" encoding=\"GBK\"?>")) {
				bpmnXml = bpmnXml.replace(
						"<?xml version=\"1.0\" encoding=\"GBK\"?>", "");
			}
			bpmnXml = bpmnXml.trim();

			request.setAttribute("bpmnXml", bpmnXml);
		}
		return getAutoView();
	}

	@RequestMapping({ "designXml" })
	public ModelAndView designXml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "defId");
		BpmDefinition po = (BpmDefinition) this.bpmDefinitionService
				.getById(Long.valueOf(id));
		String defXml = po.getDefXml();
		if (defXml.trim().startsWith(
				"<?xml version=\"1.0\" encoding=\"utf-8\"?>")) {
			defXml = defXml.replace(
					"<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
		}
		request.setAttribute("designXml", defXml);
		return getAutoView();
	}

	@RequestMapping({ "list" })
	@Action(description = "查看流程定义分页列表,含按分类查询所有流程")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request, "bpmDefinitionItem");
		Long typeId = Long.valueOf(RequestUtil.getLong(request, "typeId", 0L));
		List list = getList(filter, typeId);
		ModelAndView mv = getAutoView().addObject("bpmDefinitionList", list);
		return mv;
	}

	@RequestMapping({ "myList" })
	@Action(description = "查看我的流程定义分页列表")
	public ModelAndView myList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request, "bpmDefinitionItem");
		Long typeId = Long.valueOf(RequestUtil.getLong(request, "typeId", 0L));
		filter.addFilter("status", BpmDefinition.STATUS_DEPLOYED);
		List list = getList(filter, typeId);

		ModelAndView mv = getAutoView().addObject("bpmDefinitionList", list);
		return mv;
	}

	private List<BpmDefinition> getList(QueryFilter filter, Long typeId) {
		if (typeId.longValue() != 0L) {
			GlobalType globalType = (GlobalType) this.globalTypeService
					.getById(typeId);
			if (globalType != null) {
				filter.getFilters().put("nodePath", globalType.getNodePath());
			}
		}
		SysUser curUser = ContextUtil.getCurrentUser();

		String roleIds = this.sysRoleService.getRoleIdsByUserId(curUser
				.getUserId());
		if (StringUtils.isNotEmpty(roleIds)) {
			filter.addFilter("roleIds", roleIds);
		}

		String orgIds = this.sysOrgService.getOrgIdsByUserId(curUser
				.getUserId());
		if (StringUtils.isNotEmpty(orgIds)) {
			filter.addFilter("orgIds", orgIds);
		}

		if (!curUser.getAuthorities().contains(SysRole.ROLE_GRANT_SUPER)) {
			Long userId = curUser.getUserId();
			filter.getFilters().put("userId", userId);

			return this.bpmDefinitionService.getByUserIdFilter(filter);
		}
		return this.bpmDefinitionService.getAllForAdmin(filter);
	}

	@RequestMapping({ "versions" })
	@Action(description = "查看某一流程的所有历史版本")
	public ModelAndView versions(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService
				.getById(defId);
		List list = this.bpmDefinitionService.getAllHistoryVersions(defId);
		ModelAndView mv = getAutoView().addObject("bpmDefinitionList", list)
				.addObject("bpmDefinition", bpmDefinition);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除流程定义")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);

		String isOnlyVersion = request.getParameter("isOnlyVersion");
		boolean onlyVersion = "true".equals(isOnlyVersion) ? true : false;
		try {
			String lAryId = RequestUtil.getString(request, "defId");
			if (StringUtil.isEmpty(lAryId)) {
				message = new ResultMessage(1, "没有传入流程定义ID!");
			} else {
				String[] aryDefId = lAryId.split(",");
				for (String defId : aryDefId) {
					Long lDefId = Long.valueOf(Long.parseLong(defId));

					this.bpmDefinitionService.delDefbyDeployId(lDefId,
							onlyVersion);
				}
				message = new ResultMessage(1, "删除流程定义成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(0, "删除流程定义失败:" + e.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看流程定义明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "defId");
		BpmDefinition po = (BpmDefinition) this.bpmDefinitionService
				.getById(Long.valueOf(id));
		ModelAndView modelAndView = getAutoView();
		if (po.getTypeId() != null) {
			GlobalType globalType = (GlobalType) this.globalTypeService
					.getById(po.getTypeId());
			modelAndView.addObject("globalType", globalType);
		}
		if (po.getActDeployId() != null) {
			String defXml = this.bpmService.getDefXmlByDeployId(po
					.getActDeployId().toString());
			modelAndView.addObject("defXml", defXml);
		}

		return modelAndView.addObject("bpmDefinition", po);
	}

	@RequestMapping({ "detail" })
	@Action(description = "查看流程定义明细")
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "defId");
		BpmDefinition po = (BpmDefinition) this.bpmDefinitionService
				.getById(Long.valueOf(id));
		ModelAndView modelAndView = getAutoView();
		if (po.getTypeId() != null) {
			GlobalType globalType = (GlobalType) this.globalTypeService
					.getById(po.getTypeId());
			modelAndView.addObject("globalType", globalType);
		}

		return modelAndView.addObject("bpmDefinition", po);
	}

	@RequestMapping({ "nodeSet" })
	@Action(description = "流程节点设置")
	public ModelAndView nodeSet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long defId = RequestUtil.getLong(request, "defId");
		BpmDefinition po = (BpmDefinition) this.bpmDefinitionService
				.getById(Long.valueOf(defId));
		ModelAndView modelAndView = getAutoView();
		if (po.getActDeployId() != null) {
			String defXml = this.bpmService.getDefXmlByDeployId(po
					.getActDeployId().toString());
			modelAndView.addObject("defXml", defXml);
			ShapeMeta shapeMeta = BpmWebUtil.transGraph(defXml);
			modelAndView.addObject("shapeMeta", shapeMeta);
		}
		return modelAndView.addObject("bpmDefinition", po);
	}

	@RequestMapping({ "userSet" })
	@Action(description = "人员设置")
	public ModelAndView userSet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService
				.getById(defId);
		List<BpmNodeSet> setList = this.bpmNodeSetService.getByDefId(defId);

		List<NodeUserMap> nodeUserMapList = new ArrayList<NodeUserMap>();

		for (BpmNodeSet nodeSet : setList) {
			List<BpmNodeUser> userList = this.bpmNodeUserService
					.getBySetId(nodeSet.getSetId());
			NodeUserMap nodeUserMap = new NodeUserMap(nodeSet.getSetId(),
					nodeSet.getNodeId(), nodeSet.getNodeName(), userList);
			nodeUserMapList.add(nodeUserMap);
		}
		ModelAndView modelView = getAutoView();
		modelView.addObject("nodeSetList", setList);
		modelView.addObject("bpmDefinition", bpmDefinition);
		modelView.addObject("nodeUserMapList", nodeUserMapList);
		modelView.addObject("defId", defId);
		return modelView;
	}

	@RequestMapping({ "design" })
	@Action(description = "流程在线设计")
	public ModelAndView design(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long defId = RequestUtil.getLong(request, "defId");
		String copyId = RequestUtil.getString(request, "copyId");
		String typeId = RequestUtil.getString(request, "typeId");
		if (defId > 0L) {
			BpmDefinition po = (BpmDefinition) this.bpmDefinitionService
					.getById(Long.valueOf(defId));
			request.setAttribute("proDefinition", po);
			request.setAttribute("subject", po.getSubject());
		} else {
			request.setAttribute("subject", "未命名");
		}
		Long uId = ContextUtil.getCurrentUserId();

		String sb = this.globalTypeService.getXmlByCatkey("FLOW_TYPE");
		request.setAttribute("xmlRecord", sb);

		request.setAttribute("uId", uId);
		request.setAttribute("defId", Long.valueOf(defId));

		// 增加copyId
		if (!"".equals(copyId)) {
			request.setAttribute("copyId", copyId);
		}
		return getAutoView();
	}

	@RequestMapping({ "flexDefSave" })
	@Action(description = "流程在线设计，保存，发布操作")
	public ModelAndView flexDefSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BpmDefinition bpmDefinition = getFromRequest(request);

		Boolean isDeploy = Boolean.valueOf(Boolean.parseBoolean(request
				.getParameter("deploy")));
		String actFlowDefXml = "";
		try {
			actFlowDefXml = BpmWebUtil.transform(bpmDefinition.getDefKey(),
					bpmDefinition.getSubject(), bpmDefinition.getDefXml());
			this.bpmDefinitionService.saveOrUpdate(bpmDefinition,
					isDeploy.booleanValue(), actFlowDefXml);
			response.getWriter().print("success");
		} catch (Exception ex) {
			response.getWriter().print(ex.getMessage());
		}

		return null;
	}

	@RequestMapping({ "copyBpmDef" })
	@Action(description = "拷贝流程定义")
	public void copyBpmDef(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		Long copyId = RequestUtil.getLong(request, "copyId");
		if (copyId > 0L) {
			bpmDefinitionService.copyFormDef(copyId, true);
			message = new ResultMessage(1, "流程定义拷贝成功");
		} else {
			message = new ResultMessage(0, "没有指定要拷贝的流程定义");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	private BpmDefinition getFromRequest(HttpServletRequest request) {
		Long proTypeId = Long.valueOf(RequestUtil.getLong(request,
				"bpmDefinition.typeId"));
		String subject = RequestUtil
				.getString(request, "bpmDefinition.subject");
		String defKey = RequestUtil.getString(request, "bpmDefinition.defKey");
		String descp = RequestUtil.getString(request, "bpmDefinition.descp");
		String defXml = RequestUtil.getString(request, "bpmDefinition.defXml");
		String reason = RequestUtil.getString(request, "bpmDefinition.reason");
		Long defId = Long.valueOf(RequestUtil.getLong(request,
				"bpmDefinition.defId"));

		BpmDefinition bpmDefinition = null;
		if ((defId != null) && (defId.longValue() > 0L)) {
			bpmDefinition = (BpmDefinition) this.bpmDefinitionService
					.getById(defId);
		} else if (StringUtil.isNotEmpty(defKey)) {
			bpmDefinition = this.bpmDefinitionService
					.getMainDefByActDefKey(defKey);
		}
		if (bpmDefinition == null) {
			bpmDefinition = new BpmDefinition();
			if (StringUtils.isNotEmpty(defKey)) {
				bpmDefinition.setDefKey(defKey);

				bpmDefinition
						.setTaskNameRule("{流程标题:title}-{发起人:startUser}-{发起时间:startTime}");
			}
		}

		if ((proTypeId != null) && (proTypeId.longValue() > 0L)) {
			bpmDefinition.setTypeId(proTypeId);
		}
		if (StringUtils.isNotEmpty(subject)) {
			bpmDefinition.setSubject(subject);
		}
		if (StringUtils.isNotEmpty(reason)) {
			bpmDefinition.setReason(reason);
		}
		if (StringUtils.isNotEmpty(descp)) {
			bpmDefinition.setDescp(descp);
		}
		if (StringUtils.isNotEmpty(defXml)) {
			bpmDefinition.setDefXml(defXml);
		}

		return bpmDefinition;
	}

	@RequestMapping({ "flexGet" })
	@Action(description = "流程在线设计，查看详细信息")
	public ModelAndView flexGet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long defId = RequestUtil.getLong(request, "defId");
		BpmDefinition bpmDefinition = null;
		if (defId > 0L) {
			bpmDefinition = this.bpmDefinitionService.getById(Long
					.valueOf(defId));
		} else {
			bpmDefinition = new BpmDefinition();
			Long proTypeId = Long
					.valueOf(RequestUtil.getLong(request, "defId"));
			if (proTypeId.longValue() > 0L) {
				bpmDefinition.setTypeId(new Long(proTypeId.longValue()));
			}

			// 判断copyId 是否存在
			Long copyId = RequestUtil.getLong(request, "copyId");
			if (copyId > 0L) {
				BpmDefinition copyBpmDefinition = this.bpmDefinitionService
						.getById(copyId);

				bpmDefinition.setDefXml(copyBpmDefinition.getDefXml());
				bpmDefinition.setTypeId(copyBpmDefinition.getTypeId());
				bpmDefinition.setSubject(copyBpmDefinition.getSubject());
				bpmDefinition.setDefKey("case_" + UniqueIdUtil.genId());
				bpmDefinition.setDescp(copyBpmDefinition.getDescp());
				bpmDefinition.setDefId(0L);
				bpmDefinition.setVersionNo(1); // 默认给1
			}
		}
		StringBuffer msg = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><Result>");
		msg.append("<defId>" + bpmDefinition.getDefId() + "</defId>");
		msg.append("<defXml>" + bpmDefinition.getDefXml() + "</defXml>");
		if (bpmDefinition.getTypeId() != null) {
			GlobalType proType = (GlobalType) this.globalTypeService
					.getById(bpmDefinition.getTypeId());
			msg.append("<typeName>" + proType.getTypeName() + "</typeName>");
			msg.append("<typeId>" + proType.getTypeId() + "</typeId>");
		}
		msg.append("<subject>" + bpmDefinition.getSubject() + "</subject>");
		msg.append("<defKey>" + bpmDefinition.getDefKey() + "</defKey>");
		msg.append("<descp>" + bpmDefinition.getDescp() + "</descp>");
		msg.append("<versionNo>" + bpmDefinition.getVersionNo()
				+ "</versionNo>");
		msg.append("</Result>");

		PrintWriter out = response.getWriter();
		out.println(msg.toString());
		return null;
	}

	@RequestMapping({ "saveUser" })
	@Action(description = "保存流程的节点人员设置")
	public ModelAndView saveUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		String[] assignTypes = request.getParameterValues("assignType");
		String[] nodeIds = request.getParameterValues("nodeId");
		String[] cmpIdss = request.getParameterValues("cmpIds");
		String[] cmpNamess = request.getParameterValues("cmpNames");
		String[] nodeUserIds = request.getParameterValues("nodeUserId");

		String[] compTypes = request.getParameterValues("compType");

		BpmDefinition bpmDefintion = (BpmDefinition) this.bpmDefinitionService
				.getById(defId);

		if ((assignTypes != null) && (assignTypes.length > 0)) {
			for (int i = 0; i < assignTypes.length; i++) {
				BpmNodeUser bnUser = null;
				if (StringUtils.isNotEmpty(nodeUserIds[i])) {
					long nodeUserId = new Long(nodeUserIds[i]).longValue();

					bnUser = (BpmNodeUser) this.bpmNodeUserService.getById(Long
							.valueOf(nodeUserId));
					bnUser.setCmpIds(cmpIdss[i]);
					bnUser.setCmpNames(cmpNamess[i]);
					bnUser.setCompType(new Short(compTypes[i]));
					bnUser.setSn(Integer.valueOf(i));
					this.bpmNodeUserService.update(bnUser);

					if ((bnUser.getAssignType().shortValue() == 6)
							&& (cmpIdss[i] != null)
							&& (cmpIdss[i].length() > 0)) {
						JSONArray ja = JSONArray.fromObject(cmpIdss[i]);
						List uplowList = (List) JSONArray.toCollection(ja,
								BpmNodeUserUplow.class);
						this.bpmNodeUserUplowService.upd(nodeUserId, uplowList);
					}
				} else {
					long nodeUserId = UniqueIdUtil.genId();

					bnUser = new BpmNodeUser();
					bnUser.setCmpIds(cmpIdss[i]);
					bnUser.setCmpNames(cmpNamess[i]);
					BpmNodeSet bpmNodeSet = this.bpmNodeSetService
							.getByActDefIdNodeId(bpmDefintion.getActDefId(),
									nodeIds[i]);
					if (bpmNodeSet != null) {
						bnUser.setSetId(bpmNodeSet.getSetId());
					}
					bnUser.setActDefId(bpmDefintion.getActDefId());
					bnUser.setAssignType(new Short(assignTypes[i]));
					bnUser.setCompType(new Short(compTypes[i]));
					bnUser.setNodeId(nodeIds[i]);
					bnUser.setNodeUserId(Long.valueOf(nodeUserId));
					bnUser.setSn(Integer.valueOf(i));
					this.bpmNodeUserService.add(bnUser);

					if ((bnUser.getAssignType().shortValue() == 6)
							&& (cmpIdss[i] != null)
							&& (cmpIdss[i].length() > 0)) {
						JSONArray ja = JSONArray.fromObject(cmpIdss[i]);
						List uplowList = (List) JSONArray.toCollection(ja,
								BpmNodeUserUplow.class);
						this.bpmNodeUserUplowService.upd(nodeUserId, uplowList);
					}
				}
			}
		}
		addMessage(new ResultMessage(1, "成功设置节点人员！"), request);

		return new ModelAndView("redirect:userSet.xht?defId=" + defId);
	}

	@RequestMapping({ "delBpmNodeUser" })
	@ResponseBody
	public String delBpmNodeUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long nodeUserId = Long.valueOf(RequestUtil.getLong(request,
				"nodeUserId"));
		this.bpmNodeUserService.delById(nodeUserId);
		return "{success:true}";
	}

	@RequestMapping({ "instance" })
	public ModelAndView instance(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		List list = this.processRunService.getAll(new WebQueryFilter(request,
				"processRunItem"));
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService
				.getById(defId);

		mv.addObject("bpmDefinition", bpmDefinition);
		mv.addObject("processRunList", list);
		return mv;
	}

	@RequestMapping({ "deploy" })
	@Action(description = "发布流程")
	public void deploy(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService
				.getById(defId);

		String defXml = bpmDefinition.getDefXml();
		String actDefXml = BpmWebUtil.transform(bpmDefinition.getDefKey(),
				bpmDefinition.getSubject(), defXml);
		this.bpmDefinitionService.deploy(bpmDefinition, actDefXml);
		ResultMessage message = new ResultMessage(1, "发布流程成功!");
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "setCondition" })
	public ModelAndView setCondition(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String deployId = RequestUtil.getString(request, "deployId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		long defId = RequestUtil.getLong(request, "defId");

		ProcessDefinitionEntity proDefEntity = this.bpmService
				.getProcessDefinitionByDeployId(deployId);

		ActivityImpl curActivity = proDefEntity.findActivity(nodeId);

		List incomeNodes = new ArrayList();
		List outcomeNodes = new ArrayList();

		List<PvmTransition> inTrans = curActivity.getIncomingTransitions();
		for (PvmTransition tran : inTrans) {
			ActivityImpl act = (ActivityImpl) tran.getSource();
			String id = act.getId();
			String nodeName = (String) act.getProperty("name");
			String nodeType = (String) act.getProperty("type");
			Boolean isMultiple = Boolean.valueOf(act
					.getProperty("multiInstance") != null);
			incomeNodes.add(new BpmNode(id, nodeName, nodeType, isMultiple));
		}

		String xml = this.bpmService.getDefXmlByDeployId(deployId);
		Map conditionMap = BpmWebUtil.getDecisionConditions(xml, nodeId);

		List<PvmTransition> outTrans = curActivity.getOutgoingTransitions();
		for (PvmTransition tran : outTrans) {
			ActivityImpl act = (ActivityImpl) tran.getDestination();
			String id = act.getId();
			String nodeName = (String) act.getProperty("name");
			String nodeType = (String) act.getProperty("type");
			Boolean isMultiple = Boolean.valueOf(act
					.getProperty("multiInstance") != null);
			BpmNode bpmNode = new BpmNode(id, nodeName, nodeType, isMultiple);
			String condition = (String) conditionMap.get(id);
			if (condition != null) {
				bpmNode.setCondition(condition);
			}
			outcomeNodes.add(bpmNode);
		}
		ModelAndView mv = getAutoView().addObject("defId", Long.valueOf(defId))
				.addObject("nodeId", nodeId).addObject("deployId", deployId)
				.addObject("incomeNodes", incomeNodes)
				.addObject("outcomeNodes", outcomeNodes);

		return mv;
	}

	@RequestMapping({ "saveCondition" })
	public void saveCondition(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		try {
			long defId = RequestUtil.getLong(request, "defId");
			String nodeId = RequestUtil.getString(request, "nodeId");
			String tasks = request.getParameter("tasks");
			String conditions = request.getParameter("conditions");
			String[] aryTasks = tasks.split(",");
			String[] aryCondition = conditions.split(",");
			Map map = new HashMap();

			for (int i = 0; i < aryTasks.length; i++) {
				map.put(aryTasks[i], aryCondition[i]);
			}

			this.bpmService.saveCondition(defId, nodeId, map);
			writeResultMessage(writer, "保存流程xml成功", 1);
		} catch (Exception e) {
			writeResultMessage(writer, "出错:" + e.getMessage(), 0);
		}
	}

	@RequestMapping({ "flowImg" })
	public ModelAndView flowImg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actDefId = request.getParameter("actDefId");

		String defXml = this.bpmService
				.getDefXmlByProcessDefinitionId(actDefId);
		ShapeMeta shapeMeta = BpmWebUtil.transGraph(defXml);

		ModelAndView modelAndView = getAutoView();

		modelAndView.addObject("shapeMeta", shapeMeta);
		modelAndView.addObject("actDefId", actDefId);

		return modelAndView;
	}

	@RequestMapping({ "nodeUser" })
	public ModelAndView nodeUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Set candidateUsers = new HashSet();
		String processDefinitionId = request
				.getParameter("processDefinitionId");
		String nodeId = request.getParameter("nodeId");
		candidateUsers.addAll(this.bpmNodeUserService.getExeUserIds(
				processDefinitionId, nodeId, ContextUtil.getCurrentUserId()
						.toString()));
		ModelAndView modelAndView = getAutoView();
		modelAndView.addObject("candidateUsers", candidateUsers);
		return modelAndView;
	}

	@RequestMapping({ "otherParam" })
	public ModelAndView otherParam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long defId = RequestUtil.getLong(request, "defId");
		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService
				.getById(Long.valueOf(defId));
		ModelAndView modelAndView = getAutoView();
		modelAndView.addObject("bpmDefinition", bpmDefinition).addObject(
				"defId", bpmDefinition.getDefId());

		return modelAndView;
	}

	@RequestMapping({ "saveParam" })
	public void saveParam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		long defId = RequestUtil.getLong(request, "defId");
		String taskNameRule = RequestUtil.getString(request, "taskNameRule");
		short toFirstNode = (short) RequestUtil.getInt(request, "toFirstNode");
		short needStartForm = (short) RequestUtil.getInt(request,
				"needStartForm", 0);
		BpmDefinition bpmDefinition = new BpmDefinition();
		bpmDefinition.setDefId(Long.valueOf(defId));
		bpmDefinition.setTaskNameRule(taskNameRule);
		bpmDefinition.setToFirstNode(Short.valueOf(toFirstNode));
		bpmDefinition.setNeedStartForm(Short.valueOf(needStartForm));
		int count = this.bpmDefinitionService.saveParam(bpmDefinition);
		if (count > 0) {
			ResultMessage message = new ResultMessage(1, "设置参数成功");
			out.print(message.toString());
		} else {
			ResultMessage message = new ResultMessage(0, "参数设置失败");
			out.print(message.toString());
		}
	}

	@RequestMapping({ "selector" })
	public ModelAndView selector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request, "bpmDefinitionItem");
		Long typeId = Long.valueOf(RequestUtil.getLong(request, "typeId", 0L));
		filter.addFilter("status", BpmDefinition.STATUS_DEPLOYED);
		List list = getList(filter, typeId);

		ModelAndView mv = getAutoView().addObject("bpmDefinitionList", list);
		return mv;
	}

	@RequestMapping({ "exportXml" })
	@Action(description = "导出流程定义信息")
	public void exportXml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long[] lActDeployId = RequestUtil.getLongAryByStr(request,
				"actDeployId");
		if (BeanUtils.isNotEmpty(lActDeployId)) {
			String strXml = this.bpmDefinitionService.exportXml(lActDeployId);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ lActDeployId[0].toString() + ".xml");
			response.getWriter().write(strXml);
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	@RequestMapping({ "importXml" })
	@Action(description = "导入流程定义信息")
	public void importXml(MultipartHttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MultipartFile fileLoad = request.getFile("xmlFile");
		String fileStr = new String(fileLoad.getBytes(), "utf-8");
		try {
			this.bpmDefinitionService.importXml(fileStr);
			response.getWriter().print("导入成功！");
		} catch (Exception ex) {
			response.getWriter().print(ex.getMessage());
		}
	}

	@RequestMapping({ "editForkJoin" })
	public ModelAndView editForkJoin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actDefId = request.getParameter("actDefId");
		String nodeId = request.getParameter("nodeId");

		BpmNodeSet bpmNodeSet = this.bpmNodeSetService.getByActDefIdNodeId(
				actDefId, nodeId);

		ModelAndView view = getAutoView();

		if (bpmNodeSet != null) {
			view.addObject("bpmNodeSet", bpmNodeSet);
		}

		Map nodeMap = this.bpmService.getExecuteNodesMap(actDefId, false);
		nodeMap.remove(nodeId);

		view.addObject("actDefId", actDefId);
		view.addObject("nodeId", nodeId);
		view.addObject("nodeMap", nodeMap);

		return view;
	}

	@RequestMapping({ "saveForkJoin" })
	public void saveForkJoin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actDefId = request.getParameter("actDefId");
		String nodeId = request.getParameter("nodeId");
		String nodeType = request.getParameter("nodeType");

		String joinTaskKey = request.getParameter("joinTaskKey");
		String joinTaskName = request.getParameter("joinTaskName");

		BpmNodeSet bpmNodeSet = this.bpmNodeSetService.getByActDefIdNodeId(
				actDefId, nodeId);
		BpmNodeSet joinKeyNodeSet = this.bpmNodeSetService
				.getByActDefIdJoinTaskKey(actDefId, joinTaskKey);

		if ((joinKeyNodeSet != null)
				&& ((bpmNodeSet == null) || ((bpmNodeSet != null) && (!bpmNodeSet
						.getSetId().equals(joinKeyNodeSet.getSetId()))))) {
			writeResultMessage(response.getWriter(), "该汇总节点[" + joinTaskName
					+ "]已经由 [" + joinKeyNodeSet.getNodeName() + "]节点设置了！", 0);
			return;
		}

		if (bpmNodeSet != null) {
			bpmNodeSet.setNodeType(new Short(nodeType));
			bpmNodeSet.setJoinTaskKey(joinTaskKey);
			bpmNodeSet.setJoinTaskName(joinTaskName);
			this.bpmNodeSetService.update(bpmNodeSet);
			writeResultMessage(response.getWriter(), "成功设置分发或汇总", 1);
		}
	}

	@RequestMapping({ "taskNodes" })
	public ModelAndView taskNodes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actDefId = RequestUtil.getString(request, "actDefId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		Map taskDefNodeMap = this.bpmService.getTaskNodes(actDefId, nodeId);
		ModelAndView view = getAutoView();
		view.addObject("taskNodeMap", taskDefNodeMap);
		return view;
	}

	@RequestMapping({ "getCanDirectStart" })
	@ResponseBody
	public BpmDefinition getCanDirectStart(HttpServletRequest request)
			throws Exception {
		Long defId = RequestUtil.getLong(request, "defId");
		BpmDefinition bpmDefinition = null;
		if (defId > 0L) {
			bpmDefinition = bpmDefinitionService.getById(defId);
		} else {
			String actDefKey = RequestUtil.getString(request, "actDefKey");
			if (StringUtils.isNotBlank(actDefKey)) {
				bpmDefinition = bpmDefinitionService
						.getMainDefByActDefKey(actDefKey);
			}
		}
		// boolean rtn = this.bpmFormRunService.getCanDirectStart(defId);
		return bpmDefinition;
	}
}
