package org.sz.platform.bpm.controller.form;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.sz.core.annotion.Action;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.bpm.dao.form.BpmFormTableDao;
import org.sz.platform.bpm.model.form.BpmFormDef;
import org.sz.platform.bpm.model.form.BpmFormTable;
import org.sz.platform.bpm.service.form.BpmFormDefService;
import org.sz.platform.bpm.util.FormUtil;

@Controller
@RequestMapping({ "/platform/form/bpmFormDef/" })
public class BpmFormDefFormController extends BaseFormController {

	@Resource
	private BpmFormDefService service;

	@Resource
	private BpmFormTableDao bpmFormTableDao;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新自定义表单", operateType = "自定义表单")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String data = request.getParameter("data");

		String permission = request.getParameter("permission");

		BpmFormDef bpmFormDef = (BpmFormDef) JSONObject.toBean(
				JSONObject.fromObject(data), BpmFormDef.class);
		JSONObject jsonObject = JSONObject.fromObject(permission);

		Long tableId = bpmFormDef.getTableId();
		BpmFormTable bpmFormTable = (BpmFormTable) this.bpmFormTableDao
				.getById(tableId);
		
		String template = FormUtil.getFreeMarkerTemplate(
				bpmFormDef.getHtml(), tableId, bpmFormTable.getTableName());
		bpmFormDef.setTemplate(template);

		try {		
			if (bpmFormDef.getFormDefId().longValue() == 0L) {
				this.service.addForm(bpmFormDef, jsonObject);
				String msg = getText("record.added", new Object[] { "自定义表单" });
				writeResultMessage(response.getWriter(), msg, 1);
			} else {
				this.service.updateForm(bpmFormDef, jsonObject);
				String msg = getText("record.updated", new Object[] { "自定义表单" });
				writeResultMessage(response.getWriter(), msg, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), "保存表单数据失败!", 0);
		}
	}
}
