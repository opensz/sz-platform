package org.sz.platform.bpm.controller.flow;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.query.QueryFilter;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmDefVar;
import org.sz.platform.bpm.model.flow.BpmDefinition;
import org.sz.platform.bpm.service.flow.BpmDefVarService;
import org.sz.platform.bpm.service.flow.BpmDefinitionService;
import org.sz.platform.bpm.service.flow.BpmService;

@Controller
@RequestMapping({ "/platform/bpm/bpmDefVar/" })
public class BpmDefVarController extends BaseController {

	@Resource
	private BpmDefVarService bpmDefVarService;

	@Resource
	private BpmService bpmService;

	@Resource
	public BpmDefinitionService bpmDefinitionService;

	@RequestMapping({ "list" })
	@Action(description = "查看流程变量定义分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService
				.getById(defId);
		String actDefId = bpmDefinition.getActDefId();
		Long actDeployId = bpmDefinition.getActDeployId();
		QueryFilter q = new WebQueryFilter(request, "bpmDefVarItem", false);
		if (defId.longValue() != 0L) {
			q.getFilters().put("defId", defId);
		}

		List list = this.bpmDefVarService.getAll(q);
		ModelAndView mv = getAutoView().addObject("bpmDefVarList", list)
				.addObject("defId", defId)
				.addObject("actDeployId", actDeployId)
				.addObject("actDefId", actDefId)
				.addObject("bpmDefinition", bpmDefinition);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除流程变量定义")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "varId");
			this.bpmDefVarService.delByIds(lAryId);
			message = new ResultMessage(1, "删除流程变量成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑流程变量定义")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		Long varId = Long.valueOf(RequestUtil.getLong(request, "varId"));
		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService
				.getById(defId);
		String actDefId = bpmDefinition.getActDefId();
		Long actDeployId = bpmDefinition.getActDeployId();

		String returnUrl = RequestUtil.getPrePage(request);
		BpmDefVar bpmDefVar = null;
		if (varId.longValue() != 0L)
			bpmDefVar = (BpmDefVar) this.bpmDefVarService.getById(varId);
		else {
			bpmDefVar = new BpmDefVar();
		}
		Map nodeMap = this.bpmService.getExecuteNodesMap(actDefId, true);
		return getAutoView().addObject("bpmDefVar", bpmDefVar)
				.addObject("returnUrl", returnUrl).addObject("defId", defId)
				.addObject("nodeMap", nodeMap)
				.addObject("actDeployId", actDeployId)
				.addObject("actDefId", actDefId);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看流程变量定义明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actDefId = RequestUtil.getString(request, "actDefId");
		long id = RequestUtil.getLong(request, "varId");
		BpmDefVar bpmDefVar = (BpmDefVar) this.bpmDefVarService.getById(Long
				.valueOf(id));
		return getAutoView().addObject("bpmDefVar", bpmDefVar).addObject(
				"actDefId", actDefId);
	}

	@RequestMapping({ "getByDeployNode" })
	public ModelAndView getByDeployNode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String deployId = RequestUtil.getString(request, "deployId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		List varList = null;
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		if (defId.longValue() != 0L)
			varList = this.bpmDefVarService.getVarsByFlowDefId(defId
					.longValue());
		else {
			varList = this.bpmDefVarService
					.getByDeployAndNode(deployId, nodeId);
		}
		return getAutoView().addObject("bpmDefVarList", varList);
	}
}
