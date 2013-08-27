package org.sz.platform.bpm.controller.flow;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.sz.core.annotion.Action;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.bpm.model.flow.BpmNodeRule;
import org.sz.platform.bpm.service.flow.BpmNodeRuleService;
import org.sz.platform.bpm.service.flow.BpmNodeSetService;

@Controller
@RequestMapping({ "/platform/bpm/bpmNodeRule/" })
public class BpmNodeRuleFormController extends BaseFormController {

	@Resource
	private BpmNodeRuleService bpmNodeRuleService;

	@Resource
	private BpmNodeSetService bpmNodeSetService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新流程节点规则")
	public void save(HttpServletRequest request, HttpServletResponse response,
			BpmNodeRule bpmNodeRule, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("bpmNodeRule", bpmNodeRule,
				bindResult, request);

		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		String tmp = bpmNodeRule.getTargetNode();
		String[] aryTmp = tmp.split(",");
		bpmNodeRule.setTargetNode(aryTmp[0]);
		bpmNodeRule.setTargetNodeName(aryTmp[1]);
		if (bpmNodeRule.getRuleId().longValue() == 0L) {
			bpmNodeRule.setRuleId(Long.valueOf(UniqueIdUtil.genId()));
			bpmNodeRule.setPriority(Long.valueOf(System.currentTimeMillis()));
			this.bpmNodeRuleService.add(bpmNodeRule);
			resultMsg = getText("record.added", new Object[] { "流程节点规则" });
		} else {
			this.bpmNodeRuleService.update(bpmNodeRule);
			resultMsg = getText("record.updated", new Object[] { "流程节点规则" });
		}
		writeResultMessage(response.getWriter(), resultMsg, 1);
	}

	@ModelAttribute
	protected BpmNodeRule getFormObject(@RequestParam("ruleId") Long ruleId,
			Model model) throws Exception {
		this.logger.debug("enter BpmNodeRule getFormObject here....");
		BpmNodeRule bpmNodeRule = null;
		if (ruleId.longValue() != 0L)
			bpmNodeRule = (BpmNodeRule) this.bpmNodeRuleService.getById(ruleId);
		else {
			bpmNodeRule = new BpmNodeRule();
		}

		return bpmNodeRule;
	}
}
