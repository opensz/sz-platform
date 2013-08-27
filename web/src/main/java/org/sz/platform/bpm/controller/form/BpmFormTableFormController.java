package org.sz.platform.bpm.controller.form;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.bpm.model.form.BpmFormField;
import org.sz.platform.bpm.model.form.BpmFormTable;
import org.sz.platform.bpm.service.form.BpmFormTableService;

@Controller
@RequestMapping({ "/platform/form/bpmFormTable/" })
public class BpmFormTableFormController extends BaseFormController {

	@Resource
	private BpmFormTableService bpmFormTableService;

	@RequestMapping({ "saveTable" })
	public void saveTable(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tableJson = request.getParameter("table");
		String fieldsJson = request.getParameter("fields");

		BpmFormTable table = (BpmFormTable) JSONObject.toBean(
				JSONObject.fromObject(tableJson), BpmFormTable.class);
		BpmFormField[] fields = (BpmFormField[]) JSONArray.toArray(
				JSONArray.fromObject(fieldsJson), BpmFormField.class);
		String msg = "";
		// table.setTableId(null);
		try {
			if (table.getTableId() == null) {
				if (this.bpmFormTableService.isTableNameExisted(table
						.getTableName())) {
					msg = "表名已存在";
					writeResultMessage(response.getWriter(), msg, 0);
					return;
				}
				int rtn = this.bpmFormTableService.add(table, fields);
				if (rtn == -1) {
					msg = "不要输入字段【curentUserId_】,该字段为保留字段!";
					writeResultMessage(response.getWriter(), msg, 0);
					return;
				}
				msg = getText("record.added", new Object[] { "自定义表" });
				writeResultMessage(response.getWriter(), msg, 1);
			} else {
				boolean isExist = this.bpmFormTableService
						.isTableNameExistedForUpd(table.getTableId(),
								table.getTableName());
				if (isExist) {
					msg = "输入的表名在系统中已经存在!";
					writeResultMessage(response.getWriter(), msg, 0);
					return;
				}
				int rtn = this.bpmFormTableService.upd(table, fields);
				if (rtn == -1) {
					msg = "不要输入字段【curentUserId_】,该字段为保留字段!";
					writeResultMessage(response.getWriter(), msg, 0);
					return;
				}
				if (rtn == -2) {
					msg = "自定义数据表中已经有数据，字段不能设置为非空，请检查添加的字段!";
					writeResultMessage(response.getWriter(), msg, 0);
					return;
				}
				if (rtn == 0) {
					msg = getText("record.updated", new Object[] { "自定义表" });
					writeResultMessage(response.getWriter(), msg, 1);
				}
			}

		} catch (Exception ex) {
			writeResultMessage(response.getWriter(), ex.getMessage(), 0);
		}
	}

	@RequestMapping({ "saveExtTable" })
	public void saveExtTable(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tableJson = request.getParameter("table");
		String fieldsJson = request.getParameter("fields");

		BpmFormTable table = (BpmFormTable) JSONObject.toBean(
				JSONObject.fromObject(tableJson), BpmFormTable.class);
		BpmFormField[] fields = (BpmFormField[]) JSONArray.toArray(
				JSONArray.fromObject(fieldsJson), BpmFormField.class);
		String msg = "";
		try {
			if (table.getTableId() == null) {
				String tableName = table.getTableName();
				String dsAlias = table.getDsAlias();
				if (this.bpmFormTableService.isTableNameExternalExisted(
						tableName, dsAlias)) {
					msg = "表名已存在";
					writeResultMessage(response.getWriter(), msg, 0);
					return;
				}
				this.bpmFormTableService.addExt(table, fields);
				msg = getText("record.added", new Object[] { "外部表定义表" });
				writeResultMessage(response.getWriter(), msg, 1);
			} else {
				int rtn = this.bpmFormTableService.upd(table, fields);
				msg = "该表已定义表单，不能在进行修改!";
				if (rtn == 0) {
					msg = getText("record.updated", new Object[] { "自定义表" });
				}

				writeResultMessage(response.getWriter(), msg, 1);
			}
		} catch (Exception ex) {
			writeResultMessage(response.getWriter(), ex.getMessage(), 0);
		}
	}
}
