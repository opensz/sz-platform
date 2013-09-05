package org.sz.platform.bpm.controller.flow;

import net.sf.json.JSONArray;

import org.sz.core.annotion.Action;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmDefinition;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.model.flow.BpmNodeSign;
import org.sz.platform.bpm.model.flow.BpmNodeUser;
import org.sz.platform.bpm.model.flow.BpmNodeUserUplow;
import org.sz.platform.bpm.service.flow.BpmDefinitionService;
import org.sz.platform.bpm.service.flow.BpmNodeSetService;
import org.sz.platform.bpm.service.flow.BpmNodeSignService;
import org.sz.platform.bpm.service.flow.BpmNodeUserService;
import org.sz.platform.bpm.service.flow.BpmNodeUserUplowService;
import org.sz.platform.bpm.web.BpmWebUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({ "/platform/bpm/bpmNodeSet/" })
public class BpmNodeSetController extends BaseController {

	@Resource
	private BpmNodeSetService bpmNodeSetService;
	@Resource
	private BpmNodeUserService bpmNodeUserService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource
	private BpmNodeUserUplowService bpmNodeUserUplowService;
	@Resource
	private BpmNodeSignService bpmNodeSignService;

	@RequestMapping({ "list" })
	@Action(description = "查看节点设置分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));

		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService
				.getById(defId);

		List list = this.bpmNodeSetService.getByDefId(defId);

		BpmNodeSet startForm = this.bpmNodeSetService.getBySetType(defId,
				BpmNodeSet.SetType_StartForm);
		BpmNodeSet globalForm = this.bpmNodeSetService.getBySetType(defId,
				BpmNodeSet.SetType_GloabalForm);

		ModelAndView mv = getAutoView().addObject("bpmNodeSetList", list)
				.addObject("bpmDefinition", bpmDefinition)
				.addObject("startForm", startForm)
				.addObject("globalForm", globalForm);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除节点设置")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "setId");
		this.bpmNodeSetService.delByIds(lAryId);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑节点设置")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long setId = Long.valueOf(RequestUtil.getLong(request, "setId"));
		String returnUrl = RequestUtil.getPrePage(request);
		BpmNodeSet bpmNodeSet = null;
		if (setId != null)
			bpmNodeSet = (BpmNodeSet) this.bpmNodeSetService.getById(setId);
		else {
			bpmNodeSet = new BpmNodeSet();
		}
		return getAutoView().addObject("bpmNodeSet", bpmNodeSet).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看节点设置明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "setId");
		BpmNodeSet bpmNodeSet = (BpmNodeSet) this.bpmNodeSetService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("bpmNodeSet", bpmNodeSet);
	}

	@RequestMapping({ "save__" })
	@Action(description = "成功设置流程节点表单")
	public ModelAndView save__(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService
				.getById(defId);

		String[] nodeIds = request.getParameterValues("nodeId");
		String[] nodeNames = request.getParameterValues("nodeName");
		String[] formTypes = request.getParameterValues("formType");
		String[] aryFormKey = request.getParameterValues("formKey");
		String[] formUrls = request.getParameterValues("formUrl");
		String[] formDefNames = request.getParameterValues("formDefName");
		String[] aryBeforeHandler = request.getParameterValues("beforeHandler");
		String[] aryAfterHandler = request.getParameterValues("afterHandler");

		Map nodeMap = this.bpmNodeSetService.getMapByDefId(defId);

		List nodeList = new ArrayList();
		for (int i = 0; i < nodeIds.length; i++) {
			String nodeId = nodeIds[i];
			BpmNodeSet nodeSet = new BpmNodeSet();
			if (nodeMap.containsKey(nodeId)) {
				nodeSet = (BpmNodeSet) nodeMap.get(nodeId);
			}
			nodeSet.setNodeId(nodeId);
			nodeSet.setActDefId(bpmDefinition.getActDefId());
			nodeSet.setNodeName(nodeNames[i]);
			nodeSet.setFormUrl(formUrls[i]);
			nodeSet.setFormDefName(formDefNames[i]);

			if (!aryFormKey[i].isEmpty()) {
				nodeSet.setFormKey(Long.valueOf(Long.parseLong(aryFormKey[i])));
			}
			nodeSet.setFormType(new Short(formTypes[i]));

			String beforeHandler = aryBeforeHandler[i];
			String afterHandler = aryAfterHandler[i];

			beforeHandler = getHandler(beforeHandler);
			afterHandler = getHandler(afterHandler);

			nodeSet.setBeforeHandler(beforeHandler);
			nodeSet.setAfterHandler(afterHandler);
			nodeSet.setDefId(new Long(defId.longValue()));

			String[] jumpType = request
					.getParameterValues("jumpType_" + nodeId);
			if (jumpType != null)
				nodeSet.setJumpType(StringUtil.getArrayAsString(jumpType));
			else {
				nodeSet.setJumpType("");
			}
			String isAlloBack = request.getParameter("isAlloBack_" + nodeId);
			if (StringUtil.isNotEmpty(isAlloBack))
				nodeSet.setIsAllowBack(BpmNodeSet.BACK_ALLOW);
			else {
				nodeSet.setIsAllowBack(BpmNodeSet.BACK_DENY);
			}
			nodeSet.setSetType(BpmNodeSet.SetType_TaskNode);
			nodeList.add(nodeSet);
		}
		List list = getGlobalStart(request, bpmDefinition);
		nodeList.addAll(list);
		this.bpmNodeSetService.save(defId, nodeList);

		addMessage(new ResultMessage(1, "成功设置流程节点表单及跳转方式 !"), request);
		return new ModelAndView("redirect:list.xht?defId=" + defId + "&time="
				+ System.currentTimeMillis());
	}

	@RequestMapping({ "save" })
	@Action(description = "成功设置流程节点表单")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		String nodeId = RequestUtil.getString(request, "nodeId");
		String actDefId = RequestUtil.getString(request, "actDefId");

		String nodeName = RequestUtil.getString(request, "nodeName");
		String formType = RequestUtil.getString(request, "formType");
		String formKey = RequestUtil.getString(request, "formKey");
		String formUrl = RequestUtil.getString(request, "formUrl");
		String formDefName = RequestUtil.getString(request, "formDefName");
		String beforeHandler = RequestUtil.getString(request, "beforeHandler");
		String afterHandler = RequestUtil.getString(request, "afterHandler");

		Map<String, BpmNodeSet> nodeMap = this.bpmNodeSetService
				.getMapByDefId(defId);

		BpmNodeSet bpmNodeSet = new BpmNodeSet();
		if (nodeMap.containsKey(nodeId)) {
			bpmNodeSet = (BpmNodeSet) nodeMap.get(nodeId);
		}

		String isAudit = RequestUtil.getString(request, "isAudit");
		String isForkJoin = RequestUtil.getString(request, "isForkJoin");

		if (isAudit.equals("1")) {
			bpmNodeSet.setIsAudit(BpmNodeSet.AUDIT);
		} else {
			bpmNodeSet.setIsAudit(BpmNodeSet.NOT_AUDIT);
		}

		// if (isForkJoin.equals("1")) {
		// bpmNodeSet.setIsForkJoin(BpmNodeSet.FORKJOIN);
		// bpmNodeSet.setNodeType(new Short("1"));
		// } else {
		// bpmNodeSet.setIsForkJoin(BpmNodeSet.NOT_FORKJOIN);
		// bpmNodeSet.setNodeType(new Short("0"));
		// }

		bpmNodeSet.setNodeId(nodeId);
		bpmNodeSet.setActDefId(actDefId);
		bpmNodeSet.setNodeName(nodeName);
		bpmNodeSet.setFormUrl(formUrl);
		bpmNodeSet.setFormDefName(formDefName);

		if (!formKey.isEmpty()) {
			bpmNodeSet.setFormKey(Long.valueOf(Long.parseLong(formKey)));
		}
		bpmNodeSet.setFormType(new Short(formType));

		beforeHandler = getHandler(beforeHandler);
		afterHandler = getHandler(afterHandler);

		bpmNodeSet.setBeforeHandler(beforeHandler);
		bpmNodeSet.setAfterHandler(afterHandler);
		bpmNodeSet.setDefId(new Long(defId.longValue()));

		String[] jumpType = request.getParameterValues("jumpType");
		if (jumpType != null)
			bpmNodeSet.setJumpType(StringUtil.getArrayAsString(jumpType));
		else {
			bpmNodeSet.setJumpType("");
		}

		bpmNodeSet.setSetType(BpmNodeSet.SetType_TaskNode);

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

		String assignMode = RequestUtil.getString(request, "assignMode");
		bpmNodeSet.setAssignMode(new Short(assignMode));

		String type = request.getParameter("type");

		if ("multiUserTask".equals(type)) {
			long id = RequestUtil.getLong(request, "signId");
			BpmNodeSign bpmNodeSign = (BpmNodeSign) this.bpmNodeSignService
					.getById(Long.valueOf(id));

			String isAllowAdd = request.getParameter("isAllowAdd");
			String decideType = request.getParameter("decideType");
			String voteType = request.getParameter("voteType");
			long voteAmount = RequestUtil.getLong(request, "voteAmount");

			if (bpmNodeSign == null) {
				bpmNodeSign = new BpmNodeSign();
			}

			if (StringUtil.isNotEmpty(isAllowAdd))
				bpmNodeSign.setIsAllowAdd(BpmNodeSign.ADD_ALLOWED);
			else {
				bpmNodeSign.setIsAllowAdd(BpmNodeSign.ADD_DENY);
			}
			bpmNodeSign.setActDefId(actDefId);
			bpmNodeSign.setNodeId(nodeId);
			bpmNodeSign.setDecideType(new Short(decideType));
			bpmNodeSign.setVoteType(new Short(voteType));
			bpmNodeSign.setVoteAmount(voteAmount);

			if (bpmNodeSign.getSignId().longValue() == 0L) {
				bpmNodeSign.setSignId(Long.valueOf(UniqueIdUtil.genId()));
				this.bpmNodeSignService.add(bpmNodeSign);
			} else {
				this.bpmNodeSignService.update(bpmNodeSign);
			}

		}

		bpmNodeSetService.update(bpmNodeSet);

		try {
			writeResultMessage(response.getWriter(), "保存常用语成功!", 1);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					"保存常用语失败:" + e.getMessage(), 0);
		}
	}

	private List<BpmNodeSet> getGlobalStart(HttpServletRequest request,
			BpmDefinition bpmDefinition) {
		List list = new ArrayList();

		int globalFormType = RequestUtil.getInt(request, "globalFormType");
		if (globalFormType >= 0) {
			Long defaultFormKey = Long.valueOf(RequestUtil.getLong(request,
					"defaultFormKey"));
			String defaultFormName = RequestUtil.getString(request,
					"defaultFormName");
			String beforeHandlerGlobal = RequestUtil.getString(request,
					"beforeHandlerGlobal");
			String afterHandlerGlobal = RequestUtil.getString(request,
					"afterHandlerGlobal");
			beforeHandlerGlobal = getHandler(beforeHandlerGlobal);
			afterHandlerGlobal = getHandler(afterHandlerGlobal);
			String formUrlGlobal = RequestUtil.getString(request,
					"formUrlGlobal");
			BpmNodeSet bpmNodeSet = new BpmNodeSet();
			bpmNodeSet.setDefId(bpmDefinition.getDefId());
			bpmNodeSet.setActDefId(bpmDefinition.getActDefId());
			bpmNodeSet.setFormKey(defaultFormKey);
			bpmNodeSet.setFormDefName(defaultFormName);
			bpmNodeSet.setFormUrl(formUrlGlobal);
			bpmNodeSet.setBeforeHandler(beforeHandlerGlobal);
			bpmNodeSet.setAfterHandler(afterHandlerGlobal);
			bpmNodeSet.setFormType(Short.valueOf((short) globalFormType));
			bpmNodeSet.setSetType(BpmNodeSet.SetType_GloabalForm);

			if (globalFormType == BpmNodeSet.FORM_TYPE_ONLINE.shortValue()) {
				if (defaultFormKey.longValue() > 0L) {
					list.add(bpmNodeSet);
				}

			} else if (StringUtil.isNotEmpty(formUrlGlobal)) {
				bpmNodeSet.setFormKey(null);
				list.add(bpmNodeSet);
			}

		}

		int startFormType = RequestUtil.getInt(request, "startFormType");
		if (startFormType >= 0) {
			Long startFormKey = Long.valueOf(RequestUtil.getLong(request,
					"startFormKey"));
			String startFormName = RequestUtil.getString(request,
					"startFormName");
			String formUrlStart = RequestUtil
					.getString(request, "formUrlStart");

			String beforeHandlerStart = RequestUtil.getString(request,
					"beforeHandlerStart");
			String afterHandlerStart = RequestUtil.getString(request,
					"afterHandlerStart");

			beforeHandlerStart = getHandler(beforeHandlerStart);

			afterHandlerStart = getHandler(afterHandlerStart);

			BpmNodeSet bpmNodeSet = new BpmNodeSet();
			bpmNodeSet.setDefId(bpmDefinition.getDefId());
			bpmNodeSet.setActDefId(bpmDefinition.getActDefId());
			if (startFormKey.longValue() > 0L) {
				bpmNodeSet.setFormKey(startFormKey);
				bpmNodeSet.setFormDefName(startFormName);
			}
			bpmNodeSet.setFormUrl(formUrlStart);
			bpmNodeSet.setBeforeHandler(beforeHandlerStart);
			bpmNodeSet.setAfterHandler(afterHandlerStart);
			bpmNodeSet.setFormType(Short.valueOf((short) startFormType));
			bpmNodeSet.setSetType(BpmNodeSet.SetType_StartForm);

			if (startFormType == BpmNodeSet.FORM_TYPE_ONLINE.shortValue()) {
				if (startFormKey.longValue() > 0L) {
					list.add(bpmNodeSet);
				}

			} else if (StringUtil.isNotEmpty(formUrlStart)) {
				bpmNodeSet.setFormKey(null);
				list.add(bpmNodeSet);
			}

		}

		return list;
	}

	private String getHandler(String handler) {
		if ((StringUtil.isEmpty(handler)) || (handler.indexOf(".") == -1)) {
			handler = "";
		}
		return handler;
	}

	@RequestMapping({ "validHandler" })
	@Action(description = "验证处理器")
	public void validHandler(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String handler = RequestUtil.getString(request, "handler");
		int rtn = BpmWebUtil.isHandlerValid(handler);
		String template = "{\"result\":\"%s\",\"msg\":\"%s\"}";
		String msg = "";
		switch (rtn) {
		case 0:
			msg = "输入有效";
			break;
		case -1:
			msg = "输入格式无效";
			break;
		case -2:
			msg = "没有service类";
			break;
		case -3:
			msg = "没有对应的方法";
			break;
		default:
			msg = "其他错误";
		}

		String str = String.format(template,
				new Object[] { Integer.valueOf(rtn), msg });
		response.getWriter().print(str);
	}
}
