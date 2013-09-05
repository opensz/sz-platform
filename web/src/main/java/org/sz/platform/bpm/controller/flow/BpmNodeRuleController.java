package org.sz.platform.bpm.controller.flow;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmDefinition;
import org.sz.platform.bpm.model.flow.BpmNodeRule;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.service.flow.BpmDefinitionService;
import org.sz.platform.bpm.service.flow.BpmNodeRuleService;
import org.sz.platform.bpm.service.flow.BpmNodeSetService;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.web.BpmWebUtil;

@Controller
@RequestMapping({ "/platform/bpm/bpmNodeRule/" })
public class BpmNodeRuleController extends BaseController {

	@Resource
	private BpmNodeRuleService bpmNodeRuleService;

	@Resource
	private BpmService bpmService;

	@Resource
	private BpmDefinitionService bpmDefinitionService;

	@Resource
	private BpmNodeSetService bpmNodeSetService;

	@RequestMapping({ "list" })
	@Action(description = "查看流程节点规则分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.bpmNodeRuleService.getAll(new WebQueryFilter(request,
				"bpmNodeRuleItem"));
		ModelAndView mv = getAutoView().addObject("bpmNodeRuleList", list);
		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除流程节点规则")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "ruleId");
			this.bpmNodeRuleService.delByIds(lAryId);
			ResultMessage resObj = new ResultMessage(1, "删除规则成功");
			response.getWriter().print(resObj);
		} catch (Exception e) {
			ResultMessage resObj = new ResultMessage(0, "删除规则失败");
			response.getWriter().print(resObj);
		}
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑流程节点规则")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		String deployId = RequestUtil.getString(request, "deployId");
		String actDefId = RequestUtil.getString(request, "actDefId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		String nodeName = RequestUtil.getString(request, "nodeName");

		BpmNodeRule bpmNodeRule = new BpmNodeRule();
		String defXml = this.bpmService.getDefXmlByDeployId(deployId);
		BpmDefinition bpmDefinition = this.bpmDefinitionService
				.getByActDefId(actDefId);

		List nodeList = new ArrayList();
		nodeList.add(nodeId);
		Map activityList = BpmWebUtil.getTranstoActivitys(defXml, nodeList);

		BpmNodeSet bpmNodeSet = this.bpmNodeSetService.getByActDefIdNodeId(
				actDefId, nodeId);

		bpmNodeRule.setActDefId(actDefId);
		bpmNodeRule.setNodeId(nodeId);

		ModelAndView mv = getAutoView().addObject("activityList", activityList)
				.addObject("nodeName", nodeName)
				.addObject("bpmNodeRule", bpmNodeRule)
				.addObject("deployId", deployId)
				.addObject("actDefId", actDefId).addObject("nodeId", nodeId)
				.addObject("bpmNodeSet", bpmNodeSet)
				.addObject("defId", bpmDefinition.getDefId());

		return mv;
	}

	@RequestMapping({ "getByDefIdNodeId" })
	public void getByDefIdNodeId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String actDefId = RequestUtil.getString(request, "actDefId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		PrintWriter out = response.getWriter();
		List ruleList = this.bpmNodeRuleService.getByDefIdNodeId(actDefId,
				nodeId);
		String str = JSONArray.fromObject(ruleList).toString();
		out.print(str);
	}

	@RequestMapping({ "sortRule" })
	public void sortRule(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String ruleIds = RequestUtil.getString(request, "ruleids");
			this.bpmNodeRuleService.reSort(ruleIds);
			ResultMessage resObj = new ResultMessage(1, "规则排序成功");
			response.getWriter().print(resObj);
		} catch (Exception e) {
			ResultMessage resObj = new ResultMessage(0, "规则排序失败");
			response.getWriter().print(resObj);
		}
	}

	@RequestMapping({ "get" })
	@Action(description = "查看流程节点规则明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "ruleId");
		BpmNodeRule bpmNodeRule = (BpmNodeRule) this.bpmNodeRuleService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("bpmNodeRule", bpmNodeRule);
	}

	@RequestMapping({ "getById" })
	public void getById(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		long id = RequestUtil.getLong(request, "ruleId");
		BpmNodeRule bpmNodeRule = (BpmNodeRule) this.bpmNodeRuleService
				.getById(Long.valueOf(id));
		String rtn = JSONObject.fromObject(bpmNodeRule).toString();

		response.getWriter().print(rtn);
	}

	@RequestMapping({ "updateIsJumpForDef" })
	@ResponseBody
	public String updateIsJumpForDef(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String nodeId = request.getParameter("nodeId");
		String actDefId = request.getParameter("actDefId");
		String isJumpForDef = request.getParameter("isJumpForDef");
		this.logger.debug("nodeId:" + nodeId + " actDefId:" + actDefId
				+ " isJumpForDef:" + isJumpForDef);
		this.bpmNodeSetService.updateIsJumpForDef(nodeId, actDefId, new Short(
				isJumpForDef));
		return "{success:true}";
	}
}
