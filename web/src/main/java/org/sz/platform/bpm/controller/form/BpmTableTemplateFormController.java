package org.sz.platform.bpm.controller.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.sz.core.engine.FreemarkEngine;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.bpm.model.form.BpmFormField;
import org.sz.platform.bpm.model.form.BpmFormTable;
import org.sz.platform.bpm.model.form.BpmFormTemplate;
import org.sz.platform.bpm.model.form.BpmTableTemplate;
import org.sz.platform.bpm.service.form.BpmFormFieldService;
import org.sz.platform.bpm.service.form.BpmFormTableService;
import org.sz.platform.bpm.service.form.BpmFormTemplateService;
import org.sz.platform.bpm.service.form.BpmTableTemplateService;

@Controller
@RequestMapping({ "/platform/form/bpmTableTemplate/" })
public class BpmTableTemplateFormController extends BaseFormController {

	@Resource
	private BpmTableTemplateService bpmTableTemplateService;

	@Resource
	private BpmFormTableService bpmFormTableService;

	@Resource
	private BpmFormFieldService bpmFormFieldService;

	@Resource
	private BpmFormTemplateService bpmFormTemplateService;

	@Resource
	private FreemarkEngine freemarkEngine;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新查看表格业务数据的模板")
	public void save(HttpServletRequest request, HttpServletResponse response,
			BpmTableTemplate bpmTableTemplate, BindingResult bindResult)
			throws Exception {
		ResultMessage resultMessage = validForm("bpmTableTemplate",
				bpmTableTemplate, bindResult, request);
		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		if (bpmTableTemplate.getId() == null) {
			bpmTableTemplate.setId(Long.valueOf(UniqueIdUtil.genId()));

			Map fieldsMap = new HashMap();
			BpmFormTable table = (BpmFormTable) this.bpmFormTableService
					.getById(bpmTableTemplate.getTableId());
			List<BpmFormField> fields = this.bpmFormFieldService
					.getByTableId(bpmTableTemplate.getTableId());
			for (BpmFormField bpmformfield : fields) {
				bpmformfield.setFieldName(bpmformfield.getFieldName()
						.toLowerCase());
			}
			fieldsMap.put("table", table);
			fieldsMap.put("fields", fields);
			fieldsMap.put("templateId", bpmTableTemplate.getId());

			Map subFields = new HashMap();
			List subTable = new ArrayList();
			List<BpmFormTable> subtables = this.bpmFormTableService
					.getSubTableByMainTableId(bpmTableTemplate.getTableId());
			for (BpmFormTable subtable : subtables) {
				List<BpmFormField> subTableFields = this.bpmFormFieldService
						.getByTableId(subtable.getTableId());

				for (BpmFormField bpmformfield : subTableFields) {
					bpmformfield.setFieldName(bpmformfield.getFieldName()
							.toLowerCase());
				}
				subFields.put(subtable.getTableName(), subTableFields);
				subTable.add(subtable);
			}

			BpmFormTemplate tableTemplateList = (BpmFormTemplate) this.bpmFormTemplateService
					.getById(Long.valueOf(Long.parseLong(bpmTableTemplate
							.getHtmlList())));
			BpmFormTemplate tableTemplateDetail = (BpmFormTemplate) this.bpmFormTemplateService
					.getById(Long.valueOf(Long.parseLong(bpmTableTemplate
							.getHtmlDetail())));
			String listHtml = "";
			String detailHtml = "";
			if (tableTemplateList != null)
				listHtml = tableTemplateList.getHtml();
			if (tableTemplateDetail != null) {
				detailHtml = tableTemplateDetail.getHtml();
			}
			Map detailMap = new HashMap();

			detailMap.put("fields", fields);

			detailMap.put("subFields", subFields);

			detailMap.put("subTables", subTable);

			String listResult = this.freemarkEngine.parseByStringTemplate(
					fieldsMap, listHtml);
			String detailResult = this.freemarkEngine.parseByStringTemplate(
					detailMap, detailHtml);

			bpmTableTemplate.setHtmlList(listResult);
			bpmTableTemplate.setHtmlDetail(detailResult);
			this.bpmTableTemplateService.add(bpmTableTemplate);
			resultMsg = getText("record.added", new Object[] { "查看表格业务数据的模板" });
		} else {
			this.bpmTableTemplateService.update(bpmTableTemplate);
			resultMsg = getText("record.updated",
					new Object[] { "查看表格业务数据的模板" });
		}
		writeResultMessage(response.getWriter(), resultMsg, 1);
	}

	@ModelAttribute
	protected BpmTableTemplate getFormObject(@RequestParam("id") Long id,
			Model model) throws Exception {
		this.logger.debug("enter BpmTableTemplate getFormObject here....");
		BpmTableTemplate bpmTableTemplate = null;
		if (id != null)
			bpmTableTemplate = (BpmTableTemplate) this.bpmTableTemplateService
					.getById(id);
		else {
			bpmTableTemplate = new BpmTableTemplate();
		}
		return bpmTableTemplate;
	}
}
