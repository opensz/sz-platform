package org.sz.platform.bpm.controller.form;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.engine.FreemarkEngine;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.ContextUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.form.BpmFormDef;
import org.sz.platform.bpm.model.form.BpmFormField;
import org.sz.platform.bpm.model.form.BpmFormTable;
import org.sz.platform.bpm.model.form.BpmFormTemplate;
import org.sz.platform.bpm.service.flow.BpmService;
import org.sz.platform.bpm.service.form.BpmFormDefService;
import org.sz.platform.bpm.service.form.BpmFormFieldService;
import org.sz.platform.bpm.service.form.BpmFormRightsService;
import org.sz.platform.bpm.service.form.BpmFormTableService;
import org.sz.platform.bpm.service.form.BpmFormTemplateService;

import freemarker.template.TemplateException;

@Controller
@RequestMapping({ "/platform/form/bpmFormDef/" })
public class BpmFormDefController extends BaseController {

	@Resource
	private BpmFormDefService service;

	@Resource
	private BpmFormTableService bpmFormTableService;

	@Resource
	private BpmFormFieldService bpmFormFieldService;

	@Resource
	private BpmFormTemplateService bpmFormTemplateService;

	@Resource
	private BpmFormRightsService bpmFormRightsService;

	@Resource
	private FreemarkEngine freemarkEngine;

	@Resource
	private BpmService bpmService;

	@RequestMapping({ "manage" })
	@Action(description = "自定义表单管理页面", operateType = "自定义表单")
	public ModelAndView manage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();

		return mv;
	}

	@RequestMapping({ "list" })
	@Action(description = "查看自定义表单分页列表", operateType = "自定义表单")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "page", defaultValue = "1") int page)
			throws Exception {
		Long categoryId = Long.valueOf(RequestUtil.getLong(request,
				"categoryId"));
		QueryFilter filter = new WebQueryFilter(request, "bpmFormDefItem");
		List list = this.service.getAll(filter);
		Map publishedCounts = new HashMap();
		Map defaultVersions = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			BpmFormDef formDef = (BpmFormDef) list.get(i);
			Integer publishedCount = this.service.getCountByFormKey(formDef
					.getFormKey());
			publishedCounts.put(formDef.getFormDefId(), publishedCount);
			BpmFormDef defaultVersion = this.service
					.getDefaultVersionByFormKey(formDef.getFormKey());
			if (defaultVersion == null)
				continue;
			defaultVersions.put(formDef.getFormDefId(), defaultVersion);
		}

		ModelAndView mv = getAutoView().addObject("bpmFormDefList", list)
				.addObject("publishedCounts", publishedCounts)
				.addObject("defaultVersions", defaultVersions)
				.addObject("categoryId", categoryId);

		return mv;
	}

	@RequestMapping({ "newVersion" })
	@Action(description = "新建表单版本", operateType = "自定义表单")
	public void newVersion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		String preUrl = RequestUtil.getPrePage(request);
		Long formDefId = Long
				.valueOf(RequestUtil.getLong(request, "formDefId"));
		ResultMessage msg;
		try {
			this.service.newVersion(formDefId);
			msg = new ResultMessage(1, "新建表单版本成功!");
		} catch (Exception ex) {
			msg = new ResultMessage(0, "新建表单版本失败!");
		}
		addMessage(msg, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "gatherInfo" })
	@Action(description = "收集信息", operateType = "自定义表单")
	public ModelAndView gatherInfo(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "page", defaultValue = "1") int page)
			throws Exception {
		long categoryId = RequestUtil.getLong(request, "categoryId");

		ModelAndView mv = getAutoView().addObject("categoryId",
				Long.valueOf(categoryId));

		return mv;
	}

	@RequestMapping({ "selectTemplate" })
	@Action(description = "选择模板", operateType = "自定义表单")
	public ModelAndView selectTemplate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String subject = RequestUtil.getString(request, "subject");
		Long categoryId = Long.valueOf(RequestUtil.getLong(request,
				"categoryId"));
		String formDesc = RequestUtil.getString(request, "formDesc");
		Long tableId = Long.valueOf(RequestUtil.getLong(request, "tableId"));
		int isSimple = RequestUtil.getInt(request, "isSimple", 0);

		ModelAndView mv = getAutoView();
		BpmFormTable table = (BpmFormTable) this.bpmFormTableService
				.getById(tableId);

		if (table.getIsMain().shortValue() == 1) {
			List subTables = this.bpmFormTableService
					.getSubTableByMainTableId(tableId);
			List mainTableTemplates = this.bpmFormTemplateService
					.getAllMainTableTemplate();
			List subTableTemplates = this.bpmFormTemplateService
					.getAllSubTableTemplate();
			mv.addObject("mainTable", table).addObject("subTables", subTables)
					.addObject("mainTableTemplates", mainTableTemplates)
					.addObject("subTableTemplates", subTableTemplates);
		} else {
			List subTables = new ArrayList();
			subTables.add(table);
			List subTableTemplates = this.bpmFormTemplateService
					.getAllSubTableTemplate();
			mv.addObject("subTables", subTables).addObject("subTableTemplates",
					subTableTemplates);
		}
		mv.addObject("subject", subject).addObject("categoryId", categoryId)
				.addObject("tableId", tableId).addObject("formDesc", formDesc)
				.addObject("isSimple", Integer.valueOf(isSimple));

		return mv;
	}

	@RequestMapping({ "rightsDialog" })
	@Action(description = "流程表单授权", operateType = "自定义表单")
	public ModelAndView rightsDialog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actDefId = RequestUtil.getString(request, "actDefId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		Long formKey = Long.valueOf(RequestUtil.getLong(request, "formKey"));

		Map nodeMap = this.bpmService.getExecuteNodesMap(actDefId, true);
		ModelAndView mv = getAutoView();
		mv.addObject("nodeMap", nodeMap);
		mv.addObject("nodeId", nodeId);
		mv.addObject("actDefId", actDefId);
		mv.addObject("formKey", formKey);
		return mv;
	}

	@ResponseBody
	@RequestMapping({ "getControls" })
	@Action(description = "获取表单控件", operateType = "自定义表单")
	public Map<String, String> getMacroTemplate(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("templateId") Long templateId,
			@RequestParam("tableId") Long tableId) throws TemplateException,
			IOException {
		Map map = new HashMap();
		BpmFormTemplate template = (BpmFormTemplate) this.bpmFormTemplateService
				.getById(templateId);
		String macro;
		BpmFormTable table;
		if (template != null) {
			template = this.bpmFormTemplateService.getByTemplateAlias(template
					.getMacroTemplateAlias());
			macro = template.getHtml();
			table = (BpmFormTable) this.bpmFormTableService.getById(tableId);
			List<BpmFormField> fields = this.bpmFormFieldService
					.getByTableId(tableId);
			for (BpmFormField field : fields) {
				String fieldname = field.getFieldName();

				field.setFieldName(new StringBuilder()
						.append(table.getIsMain().shortValue() == 1 ? "m:"
								: "s:").append(table.getTableName())
						.append(":").append(field.getFieldName()).toString());
				Map data = new HashMap();
				data.put("field", field);
				map.put(fieldname, this.freemarkEngine.parseByStringTemplate(
						data,
						new StringBuilder().append(macro)
								.append("<@input field=field/>").toString()));
			}
		}
		return map;
	}

	@RequestMapping({ "delByFormKey" })
	@Action(description = "删除自定义表单", operateType = "自定义表单")
	public void delByFormKey(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		Long formKey = Long.valueOf(RequestUtil.getLong(request, "formKey"));
		int rtn = this.service.getFlowUsed(formKey);
		ResultMessage msg;
		if (rtn > 0)
			msg = new ResultMessage(0, "该表单已和流程进行了关联，不能被删除!");
		else {
			try {
				this.service.delByFormKey(formKey);
				msg = new ResultMessage(1, "删除表单成功!");
			} catch (Exception e) {
				msg = new ResultMessage(0, "删除表单失败!");
			}
		}
		addMessage(msg, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑自定义表单", operateType = "自定义表单")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView();

		Long formDefId = Long
				.valueOf(RequestUtil.getLong(request, "formDefId"));
		String returnUrl = RequestUtil.getPrePage(request);
		BpmFormDef bpmFormDef = null;
		if (formDefId.longValue() != 0L) {
			bpmFormDef = (BpmFormDef) this.service.getById(formDefId);
		} else {
			bpmFormDef = new BpmFormDef();
			bpmFormDef.setTableId(Long.valueOf(RequestUtil.getLong(request,
					"tableId")));
			bpmFormDef.setCategoryId(Long.valueOf(RequestUtil.getLong(request,
					"categoryId")));
			bpmFormDef.setFormDesc(RequestUtil.getString(request, "formDesc"));
			bpmFormDef.setSubject(RequestUtil.getString(request, "subject"));

			Long[] templateTableId = RequestUtil.getLongAryByStr(request,
					"templateTableId");
			Long[] templateId = RequestUtil.getLongAryByStr(request,
					"templatesId");

			String reult = genTemplate(templateTableId, templateId);
			bpmFormDef.setHtml(reult);

			if(templateId != null && templateId.length > 0){
				//设置主表模板ID
				bpmFormDef.setTemplateId(templateId[0]);
			}
			
			mv.addObject("templateId",
					RequestUtil.getString(request, "templateId")).addObject(
					"templateTableId",
					RequestUtil.getString(request, "templateTableId"));
		}

		mv.addObject("bpmFormDef", bpmFormDef)
				.addObject("returnUrl", returnUrl);

		return mv;
	}

	@RequestMapping({ "get" })
	@Action(description = "查看自定义表单明细", operateType = "自定义表单")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "formDefId");
		BpmFormDef bpmFormDef = (BpmFormDef) this.service.getById(Long
				.valueOf(id));
		return getAutoView().addObject("bpmFormDef", bpmFormDef);
	}

	@ResponseBody
	@RequestMapping({ "getAllFieldsByTableId" })
	public Map<String, Object> getAllFieldsByTableId(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("tableId") Long tableId) throws Exception {
		Map fields = new HashMap();

		BpmFormTable mainTable = (BpmFormTable) this.bpmFormTableService
				.getById(tableId);
		List mainTableFields = this.bpmFormFieldService.getByTableId(tableId);
		fields.put("mainTable", mainTable);
		fields.put("mainTableFields", mainTableFields);

		List subTables = this.bpmFormTableService
				.getSubTableByMainTableId(tableId);
		fields.put("subTables", subTables);

		return fields;
	}

	@ResponseBody
	@RequestMapping({ "getPermissionByTableFormKey" })
	public Map<String, List<JSONObject>> getPermissionByTableFormKey(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long tableId = Long.valueOf(RequestUtil.getLong(request, "tableId"));
		Long formKey = Long.valueOf(RequestUtil.getLong(request, "formKey"));
		Map permission = this.bpmFormRightsService.getPermissionByTableId(
				tableId, formKey);
		return permission;
	}

	@ResponseBody
	@RequestMapping({ "getPermissionByFormNode" })
	public Map<String, List<JSONObject>> getPermissionByFormNode(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String actDefId = RequestUtil.getString(request, "actDefId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		Long formKey = Long.valueOf(RequestUtil.getLong(request, "formKey"));
		Map permission = this.bpmFormRightsService.getPermissionByFormNode(
				actDefId, nodeId, formKey);
		return permission;
	}

	@RequestMapping({ "savePermission" })
	public void savePermission(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		String permission = request.getParameter("permission");
		String actDefId = RequestUtil.getString(request, "actDefId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		Long formKey = Long.valueOf(RequestUtil.getLong(request, "formKey"));
		JSONObject jsonObject = JSONObject.fromObject(permission);
		ResultMessage msg;
		try {
			this.bpmFormRightsService.save(actDefId, nodeId,
					formKey.longValue(), jsonObject);
			msg = new ResultMessage(1, "表单权限保存成功!");
		} catch (Exception ex) {
			msg = new ResultMessage(0, "表单权限保存失败!");
		}
		out.print(msg);
	}

	@RequestMapping({ "genByTemplate" })
	public void genByTemplate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long[] templateTableId = RequestUtil.getLongAryByStr(request,
				"templateTableId");
		Long[] templateId = RequestUtil.getLongAryByStr(request, "templatesId");
		PrintWriter out = response.getWriter();
		String html = genTemplate(templateTableId, templateId);
		out.println(html);
	}

	private String genTemplate(Long[] tableIds, Long[] tableTemplateIds)
			throws TemplateException, IOException {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tableIds.length; i++) {
			Map fieldsMap = new HashMap();
			BpmFormTable table = (BpmFormTable) this.bpmFormTableService
					.getById(tableIds[i]);
			List<BpmFormField> fields = this.bpmFormFieldService
					.getByTableId(tableIds[i]);
			fieldsMap.put("table", table);
			fieldsMap.put("fields", fields);
			for (BpmFormField field : fields) {
				field.setFieldName(new StringBuilder()
						.append(table.getIsMain().shortValue() == 1 ? "m:"
								: "s:").append(table.getTableName())
						.append(":").append(field.getFieldName()).toString());
			}

			BpmFormTemplate tableTemplate = (BpmFormTemplate) this.bpmFormTemplateService
					.getById(tableTemplateIds[i]);
			BpmFormTemplate macroTemplate = this.bpmFormTemplateService
					.getByTemplateAlias(tableTemplate.getMacroTemplateAlias());
			String macroHtml = "";
			if (macroTemplate != null) {
				macroHtml = macroTemplate.getHtml();
			}
			String result = this.freemarkEngine.parseByStringTemplate(
					fieldsMap,
					new StringBuilder().append(macroHtml)
							.append(tableTemplate.getHtml()).toString());
			if (table.getIsMain().shortValue() == 1) {
				sb.append(result);
			} else {
				sb.append("<div type=\"subtable\" tableName=\"");
				sb.append(table.getTableName());
				sb.append("\">\n");
				sb.append(result);
				sb.append("</div>\n");
			}
		}
		return sb.toString();
	}

	@RequestMapping({ "publish" })
	@Action(description = "发布", operateType = "自定义表单")
	public void publish(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long formDefId = Long
				.valueOf(RequestUtil.getLong(request, "formDefId"));
		String prevPage = RequestUtil.getPrePage(request);
		ResultMessage resultObj = null;
		try {
			this.service.publish(formDefId, ContextUtil.getCurrentUser()
					.getFullname());
			resultObj = new ResultMessage(1, "发布版本成功!");
		} catch (Exception e) {
			e.printStackTrace();
			resultObj = new ResultMessage(0, e.getCause().toString());
		}
		addMessage(resultObj, request);
		response.sendRedirect(prevPage);
	}

	@RequestMapping({ "versions" })
	@Action(description = "查看版本", operateType = "自定义表单")
	public ModelAndView versions(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView result = getAutoView();

		String formKey = request.getParameter("formKey");

		List versions = this.service.getByFormKey(Long.valueOf(Long
				.parseLong(formKey)));

		result.addObject("versions", versions);
		return result;
	}

	@RequestMapping({ "setDefaultVersion" })
	@Action(description = "设置默认版本", operateType = "自定义表单")
	public void setDefaultVersion(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("formDefId") Long formDefId,
			@RequestParam("formKey") Long formKey) throws Exception {
		ResultMessage resultObj = new ResultMessage(1, "设置默认版本成功!");
		String preUrl = RequestUtil.getPrePage(request);
		this.service.setDefaultVersion(formDefId, formKey);
		addMessage(resultObj, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "selector" })
	@Action(description = "选择器", operateType = "自定义表单")
	public ModelAndView selector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new WebQueryFilter(request, "bpmFormDefItem");
		List list = this.service.getPublished(queryFilter);
		ModelAndView mv = getAutoView().addObject("bpmFormDefList", list);
		return mv;
	}

	@RequestMapping({ "getFlowUsed" })
	public void getFlowUsed(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		long formDefId = RequestUtil.getLong(request, "formDefId");
		int rtn = this.service.getFlowUsed(Long.valueOf(formDefId));
		out.println(rtn);
	}
}
