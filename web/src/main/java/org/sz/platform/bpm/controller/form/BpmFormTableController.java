package org.sz.platform.bpm.controller.form;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.customertable.BaseTableMeta;
import org.sz.core.customertable.ColumnModel;
import org.sz.core.customertable.IDbView;
import org.sz.core.customertable.TableModel;
import org.sz.core.customertable.impl.TableMetaFactory;
import org.sz.core.engine.FreemarkEngine;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.FileUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.dao.form.BpmFormDefDao;
import org.sz.platform.bpm.model.form.BpmFormField;
import org.sz.platform.bpm.model.form.BpmFormTable;
import org.sz.platform.bpm.service.form.BpmFormFieldService;
import org.sz.platform.bpm.service.form.BpmFormRuleService;
import org.sz.platform.bpm.service.form.BpmFormTableService;
import org.sz.platform.system.service.IdentityService;
import org.sz.platform.system.service.SysDataSourceService;

@Controller
@RequestMapping({ "/platform/form/bpmFormTable/" })
public class BpmFormTableController extends BaseController {

	@Resource
	private BpmFormTableService service;

	@Resource
	private BpmFormFieldService bpmFormFieldService;

	@Resource
	private SysDataSourceService sysDataSourceService;

	@Resource
	private BpmFormRuleService bpmFormRuleService;

	@Resource
	private IdentityService identityService;

	@Resource
	private FreemarkEngine freemarkEngine;

	@Resource
	private Properties configproperties;

	@Resource
	private BpmFormDefDao bpmFormDefDao;

	@RequestMapping({ "defExtTable{viewName}" })
	public ModelAndView defExtTable(HttpServletRequest request,
			@PathVariable String viewName) throws Exception {
		ModelAndView mv = getAutoView();

		if (viewName.equals("1")) {
			List dsList = this.sysDataSourceService.getAll();
			mv.addObject("dsList", dsList);
		} else if (viewName.equals("2")) {
			String dataSource = RequestUtil.getString(request, "dataSource");
			String tableName = RequestUtil.getString(request, "tableName");
			mv.addObject("dataSource", dataSource);
			mv.addObject("tableName", tableName);
		}
		return mv;
	}

	@RequestMapping({ "edit" })
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView();
		Long tableId = Long.valueOf(RequestUtil.getLong(request, "tableId"));

		boolean canEditTbColName = true;

		if (tableId.longValue() > 0L) {
			// 暂时修改成false，原因是方便修改自定义表数据
			// canEditTbColName =
			// !this.bpmFormDefDao.isTableHasFormDef(tableId);
			canEditTbColName = false;
		}
		List mainTableList = this.service.getAllUnpublishedMainTable();
		mv.addObject("canEditTbColName", Boolean.valueOf(canEditTbColName))
				.addObject("tableId", tableId)
				.addObject("mainTableList", mainTableList);

		return mv;
	}

	@RequestMapping({ "copy" })
	public ModelAndView copy(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView(
				"/platform/form/bpmFormTableEdit.jsp");
		Long tableId = 1367046006987l;

		boolean canEditTbColName = true;

		List mainTableList = this.service.getAllUnpublishedMainTable();
		mv.addObject("canEditTbColName", Boolean.valueOf(canEditTbColName))
				.addObject("tableId", tableId)
				.addObject("mainTableList", mainTableList);

		return mv;
	}

	@ResponseBody
	@RequestMapping({ "getByTableId" })
	public Map<String, Object> getByTableId(HttpServletRequest request) {
		Long tableId = Long.valueOf(RequestUtil.getLong(request, "tableId"));
		Map map = new HashMap();
		BpmFormTable bpmFormTable = (BpmFormTable) this.service
				.getById(tableId);
		List fieldList = this.bpmFormFieldService.getAllByTableId(tableId);
		map.put("bpmFormTable", bpmFormTable);
		map.put("fieldList", fieldList);
		return map;
	}

	@ResponseBody
	@RequestMapping({ "getTableById" })
	public Map<String, Object> getTableById(HttpServletRequest request) {
		Long tableId = Long.valueOf(RequestUtil.getLong(request, "tableId"));
		Map map = new HashMap();
		BpmFormTable bpmFormTable = (BpmFormTable) this.service
				.getById(tableId);

		if (bpmFormTable == null) {
			throw new RuntimeException("表ID不存在!");
		}
		bpmFormTable.setFieldList(this.bpmFormFieldService
				.getAllByTableId(tableId));

		List<BpmFormTable> subTableList = this.service
				.getSubTableByMainTableId(tableId);
		if (subTableList != null && subTableList.size() > 0) {
			for (BpmFormTable subTable : subTableList) {
				subTable.setFieldList(this.bpmFormFieldService
						.getAllByTableId(subTable.getTableId()));
			}
			bpmFormTable.setSubTableList(subTableList);
		}
		map.put("table", bpmFormTable);
		return map;
	}

	@RequestMapping({ "dialog" })
	public ModelAndView dialog(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView();

		List bpmFormTableList = this.service
				.getAllMainTable(new WebQueryFilter(request, "bpmFormTableItem"));

		mv.addObject("bpmFormTableList", bpmFormTableList);

		return mv;
	}

	@RequestMapping({ "columnDialog" })
	public ModelAndView columnDialog(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = getAutoView();
		int isAdd = RequestUtil.getInt(request, "isAdd", 0);

		List validRuleList = this.bpmFormRuleService.getAll();

		List identityList = this.identityService.getAll();
		mv.addObject("validRuleList", validRuleList);
		mv.addObject("identityList", identityList);
		mv.addObject("isAdd", Integer.valueOf(isAdd));
		return mv;
	}

	@RequestMapping({ "selectView" })
	public ModelAndView selectView(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView();
		List dsList = this.sysDataSourceService.getAll();
		mv.addObject("dsList", dsList);
		return mv;
	}

	@RequestMapping({ "getModelByView" })
	public ModelAndView getModelByView(HttpServletRequest request)
			throws Exception {
		String ds = RequestUtil.getString(request, "dataSource");
		String viewName = RequestUtil.getString(request, "selList");

		IDbView idbView = TableMetaFactory.getDbView(ds);
		TableModel tableModel = idbView.getModelByViewName(viewName);

		ModelAndView mv = getAutoView();
		mv.addObject("table", tableModel);

		return mv;
	}

	@RequestMapping({ "editView" })
	public ModelAndView editView(HttpServletRequest request) throws Exception {
		String viewName = RequestUtil.getString(request, "viewName");
		String viewComment = RequestUtil.getString(request, "viewComment");
		String pkName = RequestUtil.getString(request, "pkName");
		String[] aryCol = request.getParameterValues("column");
		String[] aryDbType = request.getParameterValues("dbtype");
		String[] aryComment = request.getParameterValues("comment");
		TableModel tableModel = new TableModel();
		tableModel.setName(viewName);
		tableModel.setComment(viewComment);

		for (int i = 0; i < aryCol.length; i++) {
			ColumnModel colModel = new ColumnModel();
			colModel.setName(aryCol[i]);
			colModel.setColumnType(aryDbType[i]);
			colModel.setComment(aryComment[i]);
			tableModel.addColumnModel(colModel);
		}
		Map map = new HashMap();
		map.put("table", tableModel);
		map.put("pkName", pkName);
		String html = this.freemarkEngine.mergeTemplateIntoString("view.ftl",
				map);

		ModelAndView mv = getAutoView();
		mv.addObject("html", html);
		mv.addObject("viewName", viewName);
		return mv;
	}

	@RequestMapping({ "saveView" })
	public void saveView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String viewpath = this.configproperties.getProperty("viewpath");
		String viewCharset = this.configproperties.getProperty("viewCharset");
		String viewName = RequestUtil.getString(request, "viewName");

		String content = request.getParameter("txtViewHtml");
		if (!viewpath.endsWith(File.separator))
			viewpath = viewpath + File.separator;
		viewpath = viewpath + viewName + ".aspx";

		Pattern regex = Pattern.compile(
				"<div\\s*style=\"display:none\"\\s*>(.*?)</div>", 98);
		Matcher regexMatcher = regex.matcher(content);
		while (regexMatcher.find()) {
			String tag = regexMatcher.group(0);
			String innerContent = regexMatcher.group(1);

			content = content.replace(tag, innerContent);
		}
		content = content.replace("&lt;", "<");
		content = content.replace("&gt;", ">");

		FileUtil.writeFile(viewpath, content, viewCharset);
		PrintWriter writer = response.getWriter();
		ResultMessage resultMessage = new ResultMessage(1, "保存视图信息成功!");
		writeResultMessage(writer, resultMessage);
	}

	@ResponseBody
	@RequestMapping({ "getTableModel" })
	public Map<String, Object> getTableModel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dataSource = RequestUtil.getString(request, "dataSource");
		String tableName = RequestUtil.getString(request, "tableName");
		BaseTableMeta meta = TableMetaFactory.getMetaData(dataSource);
		TableModel tableModel = meta.getTableByName(tableName);
		BpmFormTable table = new BpmFormTable();
		table.setTableName(tableName);
		table.setTableDesc(tableModel.getComment());

		List fieldList = convertFieldList(tableModel);

		List identityList = this.identityService.getAll();

		Map map = new HashMap();
		map.put("identityList", identityList);
		map.put("table", table);
		map.put("fieldList", fieldList);

		return map;
	}

	private List<BpmFormField> convertFieldList(TableModel tableModel) {
		List fieldList = new ArrayList();
		List<ColumnModel> colList = tableModel.getColumnList();
		for (ColumnModel model : colList) {
			BpmFormField field = new BpmFormField();
			field.setIsPk(Integer.valueOf(model.getIsPk() ? 1 : 0));
			field.setFieldName(model.getName());
			field.setFieldDesc(model.getComment());
			field.setCharLen(Integer.valueOf(model.getCharLen()));
			field.setIntLen(Integer.valueOf(model.getIntLen()));
			field.setDecimalLen(Integer.valueOf(model.getDecimalLen()));
			field.setFieldType(model.getColumnType());
			short isRequired = (short) (model.getIsNull() ? 0 : 1);
			field.setIsRequired(Short.valueOf(isRequired));
			fieldList.add(field);
		}
		return fieldList;
	}

	@RequestMapping({ "getTableList" })
	@ResponseBody
	public Map getTableList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map resultMap = new HashMap();
		try {
			String ds = RequestUtil.getString(request, "ds");
			String table = RequestUtil.getString(request, "table");
			BaseTableMeta meta = TableMetaFactory.getMetaData(ds);
			Map map = meta.getTablesByName(table);
			resultMap.put("success", "true");
			resultMap.put("tables", map);
		} catch (Exception ex) {
			resultMap.put("success", "false");
		}
		return resultMap;
	}

	@RequestMapping({ "getViewList" })
	@ResponseBody
	public Map getViewList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map resultMap = new HashMap();
		try {
			String ds = RequestUtil.getString(request, "ds");
			String table = RequestUtil.getString(request, "table");
			IDbView idbView = TableMetaFactory.getDbView(ds);
			List list = idbView.getViews(table);
			resultMap.put("success", "true");
			resultMap.put("views", list);
		} catch (Exception ex) {
			resultMap.put("success", "false");
		}
		return resultMap;
	}

	@RequestMapping({ "setRelation" })
	public ModelAndView setRelation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		long tableId = RequestUtil.getLong(request, "tableId");

		String dsName = RequestUtil.getString(request, "dsName");

		List fieldList = this.bpmFormFieldService.getByTableId(Long
				.valueOf(tableId));

		List tables = this.service.getByDsSubTable(dsName);

		BpmFormTable bpmFormTable = (BpmFormTable) this.service.getById(Long
				.valueOf(tableId));

		String tableName = bpmFormTable.getTableName();

		tables.remove(tableName);
		String pkField = bpmFormTable.getPkField();
		if (StringUtil.isNotEmpty(pkField))
			pkField = "";
		mv.addObject("pkField", bpmFormTable.getPkField());
		mv.addObject("tables", tables);
		mv.addObject("fieldList", fieldList);

		mv.addObject("tableName", tableName);
		mv.addObject("dataSource", dsName);
		mv.addObject("relation", bpmFormTable.getTableRelation());

		return mv;
	}

	@RequestMapping({ "saveRelation" })
	public void saveRelation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		try {
			String relation = request.getParameter("relation");
			String tablename = RequestUtil.getString(request, "tablename");
			String dataSource = RequestUtil.getString(request, "dataSource");

			this.service.saveRelation(dataSource, tablename, relation);

			writeResultMessage(writer, "设置成功", 1);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(writer, "设置失败", 0);
		}
	}

	@RequestMapping({ "isTableNameExternalExisted" })
	public void isTableNameExternalExisted(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		String tablename = RequestUtil.getString(request, "tablename");
		String dataSource = RequestUtil.getString(request, "dataSource");
		boolean rtn = this.service.isTableNameExternalExisted(tablename,
				dataSource);
		writer.print(rtn);
	}

	@RequestMapping({ "list" })
	@Action(description = "查看自定义表分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "page", defaultValue = "1") int page)
			throws Exception {
		List list = this.service.getAll(new WebQueryFilter(request,
				"bpmFormTableItem"));
		ModelAndView mv = getAutoView().addObject("bpmFormTableList", list);

		return mv;
	}

	@RequestMapping({ "editExt" })
	@Action(description = "编辑外部表")
	public ModelAndView editExt(HttpServletRequest request) throws Exception {
		Long tableId = Long.valueOf(RequestUtil.getLong(request, "tableId"));
		String returnUrl = RequestUtil.getPrePage(request);
		return getAutoView().addObject("tableId", tableId).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "delTableById" })
	@Action(description = "删除外部表定义表")
	public void delTableById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage resultMessage;
		try {
			Long tableId = Long
					.valueOf(RequestUtil.getLong(request, "tableId"));
			this.service.delExtTableById(tableId);
			resultMessage = new ResultMessage(1, "删除表定义成功!");
		} catch (Exception ex) {
			resultMessage = new ResultMessage(0, "删除表定义失败!");
		}
		addMessage(resultMessage, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看自定义表明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long tableId = Long.valueOf(RequestUtil.getLong(request, "tableId"));
		ModelAndView mv = getAutoView();
		BpmFormTable table = (BpmFormTable) this.service.getById(tableId);
		List fields = this.bpmFormFieldService.getByTableId(tableId);
		mv.addObject("table", table).addObject("fields", fields);

		String mainTable = "未分配";
		if (table.getIsMain().shortValue() == 1) {
			List subList = this.service.getSubTableByMainTableId(tableId);
			mv.addObject("subList", subList);
		} else {
			Long mainTableId = table.getMainTableId();
			if (mainTableId.longValue() > 0L) {
				BpmFormTable tb = (BpmFormTable) this.service
						.getById(mainTableId);
				mainTable = tb.getTableName();
				mv.addObject("mainTable", mainTable);
			}
		}
		return mv;
	}

	@RequestMapping({ "generateTable" })
	@Action(description = "生成表")
	public void publish(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage resultObj = null;
		PrintWriter out = response.getWriter();
		Long tableId = Long.valueOf(RequestUtil.getLong(request, "tableId"));
		try {
			this.service.generateTable(tableId, ContextUtil.getCurrentUser()
					.getFullname());
			resultObj = new ResultMessage(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			resultObj = new ResultMessage(0, e.getCause().toString());
		}
		out.print(resultObj);
	}

	@RequestMapping({ "subTable" })
	@Action(description = "查看子表")
	public ModelAndView subTable(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("tableId") Long tableId)
			throws Exception {
		BpmFormTable table = (BpmFormTable) this.service.getById(tableId);
		List subTables = this.service.getSubTableByMainTableId(tableId);

		return getAutoView().addObject("table", table).addObject("subTables",
				subTables);
	}

	@RequestMapping({ "linkSubtable" })
	public void linkSubtable(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage resultObj = null;
		PrintWriter out = response.getWriter();
		try {
			Long mainTableId = Long.valueOf(RequestUtil.getLong(request,
					"mainTableId"));
			Long subTableId = Long.valueOf(RequestUtil.getLong(request,
					"subTableId"));
			this.service.linkSubtable(mainTableId, subTableId);
			resultObj = new ResultMessage(1, "关联表成功!");
		} catch (Exception e) {
			e.printStackTrace();
			String msg = getText("record.update.fail", new Object[] { "子表" });
			resultObj = new ResultMessage(0, msg + e.getCause());
		}
		out.print(resultObj);
	}

	@RequestMapping({ "unlinkSubTable" })
	@Action(description = "移除子表关系")
	public void unlinkSubTable(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("subTableId") Long subTableId) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		this.service.unlinkSubTable(subTableId);
		response.sendRedirect(preUrl);
	}

	@ResponseBody
	@RequestMapping({ "getExtTableByTableId" })
	public Map<String, Object> getExtTableByTableId(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("tableId") Long tableId)
			throws Exception {
		Map map = new HashMap();

		BpmFormTable table = (BpmFormTable) this.service.getById(tableId);

		List fieldList = this.bpmFormFieldService.getAllByTableId(tableId);

		List identityList = this.identityService.getAll();

		List validRuleList = this.bpmFormRuleService.getAll();

		map.put("table", table);
		map.put("fieldList", fieldList);

		map.put("identityList", identityList);

		map.put("validRuleList", validRuleList);

		return map;
	}

	@ResponseBody
	@RequestMapping({ "getAllUnassignedSubTable" })
	public List<BpmFormTable> getAllUnassignedSubTable(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("tableName") String tableName) throws Exception {
		List allUnassignedSubTable = this.service.getAllUnassignedSubTable();
		return allUnassignedSubTable;
	}

	@RequestMapping({ "delByTableId" })
	public void delByTableId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long tableId = Long.valueOf(RequestUtil.getLong(request, "tableId"));
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		boolean rtn = this.bpmFormDefDao.isTableHasFormDef(tableId);
		if (rtn) {
			message = new ResultMessage(0, "该表已定义表单不能删除!");
		} else {
			this.service.delByTableId(tableId);
			message = new ResultMessage(1, "表定义已成功删除!");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "delExtTableById" })
	public void delExtTableById(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long tableId = Long.valueOf(RequestUtil.getLong(request, "tableId"));
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		boolean rtn = this.bpmFormDefDao.isTableHasFormDef(tableId);
		if (rtn) {
			message = new ResultMessage(0, "该表已定义表单不能删除!");
			addMessage(message, request);
		} else {
			this.service.delExtTableById(tableId);
			message = new ResultMessage(1, "表定义已成功删除!");
			addMessage(message, request);
		}
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "assignMainTable" })
	public ModelAndView assignMainTable(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long subTableId = Long.valueOf(RequestUtil.getLong(request,
				"subTableId"));
		List mainTableList = this.service.getAssignableMainTable();
		ModelAndView mv = getAutoView();
		mv.addObject("mainTableList", mainTableList).addObject("subTableId",
				subTableId);

		return mv;
	}

	@RequestMapping({ "exportXml" })
	@Action(description = "导出自定义表")
	public void exportXml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long[] tableId = RequestUtil.getLongAryByStr(request, "tableId");
		if (BeanUtils.isNotEmpty(tableId)) {
			String strXml = this.service.exportXml(tableId);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ tableId[0].toString() + ".xml");
			response.getWriter().write(strXml);
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	@RequestMapping({ "importXml" })
	@Action(description = "导入自定义表")
	public void importXml(MultipartHttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MultipartFile fileLoad = request.getFile("xmlFile");
		String msg = this.service.importXml(fileLoad.getInputStream());
		ResultMessage message = null;
		if (msg.length() == 0) {
			message = new ResultMessage(1, "导入成功!");
		} else {
			message = new ResultMessage(0, msg);
		}
		writeResultMessage(response.getWriter(), message);
	}
}
