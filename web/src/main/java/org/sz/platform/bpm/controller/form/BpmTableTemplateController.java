package org.sz.platform.bpm.controller.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.engine.FreemarkEngine;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.ContextUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.form.BpmFormData;
import org.sz.platform.bpm.model.form.BpmTableTemplate;
import org.sz.platform.bpm.model.form.SubTable;
import org.sz.platform.bpm.service.form.BpmFormHandlerService;
import org.sz.platform.bpm.service.form.BpmFormTemplateService;
import org.sz.platform.bpm.service.form.BpmTableTemplateService;
import org.sz.platform.system.model.GlobalType;
import org.sz.platform.system.model.SysRole;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.service.GlobalTypeService;
import org.sz.platform.system.service.SysOrgService;
import org.sz.platform.system.service.SysRoleService;

@Controller
@RequestMapping({ "/platform/form/bpmTableTemplate/" })
public class BpmTableTemplateController extends BaseController {

	@Resource
	private BpmTableTemplateService bpmTableTemplateService;

	@Resource
	private BpmFormTemplateService bpmFormTemplateService;

	@Resource
	private SysRoleService sysRoleService;

	@Resource
	private BpmFormHandlerService bpmFormHandlerService;

	@Resource
	private FreemarkEngine freemarkEngine;

	@Resource
	private SysOrgService sysOrgService;

	@Resource
	private GlobalTypeService globalTypeService;

	@RequestMapping({ "list" })
	@Action(description = "查看表格业务数据的模板分页列表", operateType = "业务数据模板管理")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request, "bpmTableTemplateItem");
		Long typeId = Long.valueOf(RequestUtil.getLong(request, "typeId", 0L));
		List list = getList(filter, typeId);
		ModelAndView mv = getAutoView().addObject("bpmTableTemplateList", list);
		return mv;
	}

	@RequestMapping({ "myList" })
	@Action(description = "查看我的业务数据模板", operateType = "业务数据模板管理")
	public ModelAndView myList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request, "bpmTableTemplateItem");
		Long typeId = Long.valueOf(RequestUtil.getLong(request, "typeId", 0L));
		List list = getList(filter, typeId);
		ModelAndView mv = getAutoView().addObject("bpmTableTemplateList", list);
		return mv;
	}

	private List<BpmTableTemplate> getList(QueryFilter filter, Long typeId) {
		if (typeId.longValue() != 0L) {
			GlobalType globalType = (GlobalType) this.globalTypeService
					.getById(typeId);
			if (globalType != null) {
				filter.getFilters().put("nodePath", globalType.getNodePath());
			}
		}
		SysUser curUser = ContextUtil.getCurrentUser();

		String roleIds = this.sysRoleService.getRoleIdsByUserId(curUser
				.getUserId());
		if (StringUtils.isNotEmpty(roleIds)) {
			filter.addFilter("roleIds", roleIds);
		}

		String orgIds = this.sysOrgService.getOrgIdsByUserId(curUser
				.getUserId());
		if (StringUtils.isNotEmpty(orgIds)) {
			filter.addFilter("orgIds", orgIds);
		}

		if (!curUser.getAuthorities().contains(SysRole.ROLE_GRANT_SUPER)) {
			Long userId = curUser.getUserId();
			filter.getFilters().put("userId", userId);

			return this.bpmTableTemplateService.getByUserIdFilter(filter);
		}
		return this.bpmTableTemplateService.getList(filter);
	}

	@RequestMapping({ "del" })
	@Action(description = "删除查看表格业务数据的模板", operateType = "业务数据模板管理")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.bpmTableTemplateService.delByIds(lAryId);
			message = new ResultMessage(1, "删除查看表格业务数据的模板成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}

		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑查看表格业务数据的模板", operateType = "业务数据模板管理")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		int ifEdit = RequestUtil.getInt(request, "ifEdit");
		String returnUrl = RequestUtil.getPrePage(request);
		BpmTableTemplate bpmTableTemplate = null;
		bpmTableTemplate = new BpmTableTemplate();
		if (id.longValue() != 0L) {
			bpmTableTemplate = (BpmTableTemplate) this.bpmTableTemplateService
					.getById(id);
		} else {
			Long tableId = Long
					.valueOf(RequestUtil.getLong(request, "tableId"));
			if (tableId.longValue() != 0L)
				bpmTableTemplate.setTableId(tableId);
		}
		List listTemplate = this.bpmFormTemplateService.getListTemplate("form");

		List detailTemplate = this.bpmFormTemplateService.getDetailTemplate("form");

		return getAutoView().addObject("bpmTableTemplate", bpmTableTemplate)
				.addObject("returnUrl", returnUrl)
				.addObject("listTemplate", listTemplate)
				.addObject("detailTemplate", detailTemplate)
				.addObject("ifEdit", Integer.valueOf(ifEdit));
	}

	@RequestMapping({ "detail" })
	@Action(description = "查看查看表格业务数据 字表信息", operateType = "业务数据模板管理")
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		BpmTableTemplate bpmTableTemplate = (BpmTableTemplate) this.bpmTableTemplateService
				.getById(id);
		Long tableId = Long.valueOf(RequestUtil.getLong(request, "tableId"));
		String pkValue = RequestUtil.getString(request, "pkValue");
		BpmFormData bpmFormData = this.bpmFormHandlerService.getByKey(
				tableId.longValue(), pkValue);

		Map map = new HashMap();
		Map data = new HashMap();

		for (Map.Entry entry : bpmFormData.getMainFields().entrySet()) {
			data.put(((String) entry.getKey()).toLowerCase(), entry.getValue());
		}
		map.put("data", data);
		Map subDatas = new HashMap();
		for (SubTable subTable : bpmFormData.getSubTableList()) {
			Map subMap = new HashMap();

			List newList = new ArrayList();
			for (Map<String, Object> mapEntry : subTable.getDataList()) {
				Map newMap = new HashMap();
				for (Map.Entry subFieldMap : mapEntry.entrySet()) {
					newMap.put(((String) subFieldMap.getKey()).toLowerCase(),
							subFieldMap.getValue());
				}

				newList.add(newMap);
			}
			subMap.put("dataList", newList);
			subDatas.put(subTable.getTableName(), subMap);
		}
		map.put("subDatas", subDatas);
		String html = this.freemarkEngine.parseByStringTemplate(map,
				bpmTableTemplate.getHtmlDetail());

		return getAutoView().addObject("html", html);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看查看表格业务数据的模板明细", operateType = "业务数据模板管理")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		int pageIndex = RequestUtil.getInt(request, "p");
		int pageSize = RequestUtil.getInt(request, "z");
		String nextUrl = RequestUtil.getUrl(request);
		BpmTableTemplate bpmTableTemplate = (BpmTableTemplate) this.bpmTableTemplateService
				.getById(Long.valueOf(id));

		// Map params = new HashMap();
		Map params = RequestUtil.getFtlQueryMap(request);
		params.remove("id");
		PageBean pageBean = new PageBean();

		if (pageIndex != 0) {
			pageBean.setCurrentPage(pageIndex);
		}

		if (pageSize != 0) {
			pageBean.setPagesize(pageSize);
		}
		SysUser curUser = ContextUtil.getCurrentUser();
		List data = this.bpmFormHandlerService.getAll(bpmTableTemplate,
				curUser, params, pageBean);

		Map map = new HashMap();
		map.put("list", data);
		map.put("id", bpmTableTemplate.getId());
		String html = this.freemarkEngine.parseByStringTemplate(map,
				bpmTableTemplate.getHtmlList());

		Map pageModel = new HashMap();
		pageModel.put("tableIdCode", "");
		pageModel.put("pageBean", pageBean);
		pageModel.put("showExplain", Boolean.valueOf(true));
		pageModel.put("showPageSize", Boolean.valueOf(true));
		pageModel.put("baseHref", nextUrl);
		pageModel.put("id", id);
		String pageHtml = this.freemarkEngine.mergeTemplateIntoString(
				"page.ftl", pageModel);

		ModelAndView mv = getAutoView();
		mv.addObject("html", html);
		mv.addObject("titleStr", bpmTableTemplate.getTemplateName());
		mv.addObject("pageHtml", pageHtml);
		mv.addObject("id", Long.valueOf(id));

		return mv;
	}
}
