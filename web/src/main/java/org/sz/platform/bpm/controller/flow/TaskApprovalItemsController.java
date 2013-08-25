package org.sz.platform.bpm.controller.flow;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.model.flow.TaskApprovalItems;
import org.sz.platform.bpm.service.flow.BpmNodeSetService;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.flow.TaskApprovalItemsService;

@Controller
@RequestMapping({ "/platform/bpm/taskApprovalItems/" })
public class TaskApprovalItemsController extends BaseController {

	@Resource
	private TaskApprovalItemsService taskApprovalItemsService;

	@Resource
	private BpmService bpmService;

	@Resource
	private BpmNodeSetService bpmNodeSetService;

	@RequestMapping({ "get" })
	@Action(description = "切换节点下拉框")
	public void get(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = RequestUtil.getString(request, "nodeId");
		String actDefId = RequestUtil.getString(request, "actDefId");

		TaskApprovalItems nodeExpItems = this.taskApprovalItemsService
				.getTaskApproval(actDefId, nodeId,
						TaskApprovalItems.notGlobal.shortValue());
		String nodeExp = nodeExpItems == null ? "" : nodeExpItems.getExpItems();

		writeResultMessage(response.getWriter(), nodeExp, 1);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑常用语管理")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		String nodeId = RequestUtil.getString(request, "nodeId");
		String actDefId = RequestUtil.getString(request, "actDefId");

		Map nodeMap = this.bpmService.getExecuteNodesMap(actDefId, true);
		BpmNodeSet bns = this.bpmNodeSetService.getByDefIdNodeId(defId, nodeId);

		TaskApprovalItems defExpItems = this.taskApprovalItemsService
				.getFlowApproval(actDefId,
						TaskApprovalItems.global.shortValue());
		String defExp = defExpItems == null ? "" : defExpItems.getExpItems();

		TaskApprovalItems nodeExpItems = this.taskApprovalItemsService
				.getTaskApproval(actDefId, nodeId,
						TaskApprovalItems.notGlobal.shortValue());
		String nodeExp = nodeExpItems == null ? "" : nodeExpItems.getExpItems();

		return getAutoView().addObject("nodeMap", nodeMap)
				.addObject("nodeId", nodeId).addObject("actDefId", actDefId)
				.addObject("defExp", defExp).addObject("nodeExp", nodeExp)
				.addObject("setId", bns.getSetId());
	}

	@RequestMapping({ "save" })
	@Action(description = "添加或更新节点运行脚本")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String isGlobal = RequestUtil.getString(request, "isGlobal");
		String approvalItem = RequestUtil.getString(request, "approvalItem");
		String actDefId = RequestUtil.getString(request, "actDefId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		Long setId = Long.valueOf(RequestUtil.getLong(request, "setId"));

		if (isGlobal.equals("1"))
			this.taskApprovalItemsService.delFlowApproval(actDefId,
					TaskApprovalItems.global.shortValue());
		else {
			this.taskApprovalItemsService.delTaskApproval(actDefId, nodeId,
					TaskApprovalItems.notGlobal.shortValue());
		}
		try {
			this.taskApprovalItemsService.addTaskApproval(approvalItem,
					isGlobal, actDefId, setId, nodeId);
			writeResultMessage(response.getWriter(), "保存常用语成功!", 1);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					"保存常用语失败:" + e.getMessage(), 0);
		}
	}
}
