package org.sz.platform.bpm.controller.form;

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
import org.sz.platform.bpm.model.form.BpmFormRule;
import org.sz.platform.bpm.service.form.BpmFormRuleService;

@Controller
@RequestMapping({ "/platform/form/bpmFormRule/" })
public class BpmFormRuleFormController extends BaseFormController {

	@Resource
	private BpmFormRuleService bpmFormRuleService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新表单验证规则", operateType = "表单验证规则")
	public void save(HttpServletRequest request, HttpServletResponse response,
			BpmFormRule bpmFormRule, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("bpmFormRule", bpmFormRule,
				bindResult, request);
		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		if (bpmFormRule.getId() == null) {
			bpmFormRule.setId(Long.valueOf(UniqueIdUtil.genId()));
			this.bpmFormRuleService.add(bpmFormRule);
			resultMsg = getText("record.added", new Object[] { "表单验证规则" });
		} else {
			this.bpmFormRuleService.update(bpmFormRule);
			resultMsg = getText("record.updated", new Object[] { "表单验证规则" });
		}

		this.bpmFormRuleService.generateJS();
		writeResultMessage(response.getWriter(), resultMsg, 1);
	}

	@ModelAttribute
	protected BpmFormRule getFormObject(@RequestParam("id") Long id, Model model)
			throws Exception {
		this.logger.debug("enter BpmFormRule getFormObject here....");
		BpmFormRule bpmFormRule = null;
		if (id != null)
			bpmFormRule = (BpmFormRule) this.bpmFormRuleService.getById(id);
		else {
			bpmFormRule = new BpmFormRule();
		}
		return bpmFormRule;
	}
}
