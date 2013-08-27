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
import org.sz.platform.bpm.model.form.BpmFormTemplate;
import org.sz.platform.bpm.service.form.BpmFormTemplateService;

@Controller
@RequestMapping({ "/platform/form/bpmFormTemplate/" })
public class BpmFormTemplateFormController extends BaseFormController {

	@Resource
	private BpmFormTemplateService bpmFormTemplateService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新表单模板")
	public void save(HttpServletRequest request, HttpServletResponse response,
			BpmFormTemplate bpmFormTemplate, BindingResult bindResult)
			throws Exception {
		ResultMessage resultMessage = validForm("bpmFormTemplate",
				bpmFormTemplate, bindResult, request);

		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		if (bpmFormTemplate.getTemplateId() == null) {
			bpmFormTemplate.setTemplateId(Long.valueOf(UniqueIdUtil.genId()));
			bpmFormTemplate.setCanEdit(1);
			String alias = bpmFormTemplate.getAlias();
			boolean isExist = this.bpmFormTemplateService.isExistAlias(alias);
			if (isExist) {
				resultMsg = getText("该别名已经存在！");
				writeResultMessage(response.getWriter(), resultMsg, 0);
				return;
			}
			this.bpmFormTemplateService.add(bpmFormTemplate);
			resultMsg = getText("record.added", new Object[] { "表单模板" });
		} else {
			this.bpmFormTemplateService.update(bpmFormTemplate);
			resultMsg = getText("record.updated", new Object[] { "表单模板" });
		}
		writeResultMessage(response.getWriter(), resultMsg, 1);
	}

	@ModelAttribute
	protected BpmFormTemplate getFormObject(
			@RequestParam("templateId") Long templateId, Model model)
			throws Exception {
		this.logger.debug("enter BpmFormTemplate getFormObject here....");
		BpmFormTemplate bpmFormTemplate = null;
		if (templateId != null)
			bpmFormTemplate = (BpmFormTemplate) this.bpmFormTemplateService
					.getById(templateId);
		else {
			bpmFormTemplate = new BpmFormTemplate();
		}
		return bpmFormTemplate;
	}
}
