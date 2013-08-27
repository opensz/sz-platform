package org.sz.platform.bpm.controller.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.customertable.BaseTableMeta;
import org.sz.core.customertable.IDbView;
import org.sz.core.customertable.TableModel;
import org.sz.core.customertable.impl.TableMetaFactory;
import org.sz.core.engine.FreemarkEngine;
import org.sz.core.query.PageBean;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.form.BpmFormDialog;
import org.sz.platform.bpm.service.form.BpmFormDialogService;
import org.sz.platform.system.service.SysDataSourceService;

import flex.messaging.log.Log;

@Controller
@RequestMapping({ "/platform/form/bpmFormDialog/" })
public class BpmFormDialogController extends BaseController {

	@Resource
	private BpmFormDialogService bpmFormDialogService;

	@Resource
	private SysDataSourceService sysDataSourceService;

	@Resource
	private FreemarkEngine freemarkEngine;

	@RequestMapping({ "list" })
	// @Action(description = "查看通用表单对话框分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.bpmFormDialogService.getAll(new WebQueryFilter(
				request, "bpmFormDialogItem"));
		ModelAndView mv = getAutoView().addObject("bpmFormDialogList", list);

		return mv;
	}

	@ResponseBody
	@RequestMapping({ "getAllDialogs" })
	public List<BpmFormDialog> getAllDialogs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<BpmFormDialog> list = this.bpmFormDialogService.getAll();
		for (BpmFormDialog bd : list) {
			bd.setPageBean(null);
		}
		return list;
	}

	@RequestMapping({ "del" })
	// @Action(description = "删除通用表单对话框")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.bpmFormDialogService.delByIds(lAryId);
			message = new ResultMessage(1, "删除通用表单对话框成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	// @Action(description = "编辑通用表单对话框")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		String returnUrl = RequestUtil.getPrePage(request);
		BpmFormDialog bpmFormDialog = null;
		if (id.longValue() != 0L)
			bpmFormDialog = (BpmFormDialog) this.bpmFormDialogService
					.getById(id);
		else {
			bpmFormDialog = new BpmFormDialog();
		}
		List dsList = this.sysDataSourceService.getAll();

		return getAutoView().addObject("bpmFormDialog", bpmFormDialog)
				.addObject("returnUrl", returnUrl).addObject("dsList", dsList);
	}

	@RequestMapping({ "get" })
	// @Action(description = "查看通用表单对话框明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		BpmFormDialog bpmFormDialog = (BpmFormDialog) this.bpmFormDialogService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("bpmFormDialog", bpmFormDialog);
	}

	@RequestMapping({ "dialogObj" })
	// @Action(description = "查看通用表单对话框明细")
	@ResponseBody
	public Map<String, Object> dialogObj(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String alias = RequestUtil.getString(request, "alias");
		Map map = new HashMap();
		BpmFormDialog bpmFormDialog = this.bpmFormDialogService
				.getByAlias(alias);
		if (bpmFormDialog != null) {
			bpmFormDialog.setPageBean(null);
			map.put("bpmFormDialog", bpmFormDialog);
			map.put("success", Integer.valueOf(1));
		} else {
			map.put("success", Integer.valueOf(0));
		}

		return map;
	}

	@RequestMapping({ "getByDsObjectName" })
	@Action(description = "根据对象名称对象类型")
	@ResponseBody
	public Map getByDsObjectName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dsName = RequestUtil.getString(request, "dsName");
		String objectName = RequestUtil.getString(request, "objectName");
		int istable = RequestUtil.getInt(request, "istable");
		Map map = new HashMap();
		try {
			if (istable == 1) {
				BaseTableMeta meta = TableMetaFactory.getMetaData(dsName);
				Map tableMap = meta.getTablesByName(objectName);
				map.put("tables", tableMap);
			} else {
				IDbView dbView = TableMetaFactory.getDbView(dsName);
				List views = dbView.getViews(objectName);
				map.put("views", views);
			}
			map.put("success", "true");
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger.debug("getByDsObjectName:" + ex.getMessage());
			}
			map.put("success", "false");
		}
		return map;
	}

	public Map getObjectByDsObjectName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dsName = RequestUtil.getString(request, "dsName");
		String objectName = RequestUtil.getString(request, "objectName");
		int istable = RequestUtil.getInt(request, "istable");
		Map map = new HashMap();
		try {
			TableModel tableModel;
			if (istable == 1) {
				BaseTableMeta meta = TableMetaFactory.getMetaData(dsName);
				tableModel = meta.getTableByName(objectName);
			} else {
				IDbView dbView = TableMetaFactory.getDbView(dsName);
				tableModel = dbView.getModelByViewName(objectName);
			}
			map.put("tableModel", tableModel);
			map.put("success", "true");
		} catch (Exception ex) {
			map.put("success", "false");
		}
		return map;
	}

	@RequestMapping({ "setting" })
	public ModelAndView setting(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		String dsName = "";
		String objectName = "";
		int istable = 0;
		int style = 0;
		ModelAndView mv = getAutoView();
		if (id == 0L) {
			dsName = RequestUtil.getString(request, "dsName");
			objectName = RequestUtil.getString(request, "objectName");
			istable = RequestUtil.getInt(request, "istable");
			style = RequestUtil.getInt(request, "style");
		} else {
			BpmFormDialog bpmFormDialog = (BpmFormDialog) this.bpmFormDialogService
					.getById(Long.valueOf(id));
			dsName = bpmFormDialog.getDsalias();
			objectName = bpmFormDialog.getObjname();
			istable = bpmFormDialog.getIstable().intValue();
			style = bpmFormDialog.getStyle().intValue();
			mv.addObject("bpmFormDialog", bpmFormDialog);
		}
		TableModel tableModel;
		if (istable == 1) {
			BaseTableMeta meta = TableMetaFactory.getMetaData(dsName);
			tableModel = meta.getTableByName(objectName);
		} else {
			IDbView dbView = TableMetaFactory.getDbView(dsName);
			tableModel = dbView.getModelByViewName(objectName);
		}

		mv.addObject("tableModel", tableModel).addObject("style",
				Integer.valueOf(style));

		return mv;
	}

	@RequestMapping({ "getTreeData" })
	@ResponseBody
	public List getTreeData(HttpServletRequest request) throws Exception {
		String alias = RequestUtil.getString(request, "alias");

		return this.bpmFormDialogService.getTreeData(alias);
	}

	@RequestMapping({ "show" })
	public ModelAndView show(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map paramsMap = RequestUtil.getQueryMap(request);
		String alias = RequestUtil.getString(request, "dialog_alias_");
		String nextUrl = RequestUtil.getUrl(request);
		BpmFormDialog bpmFormDialog = this.bpmFormDialogService.getData(alias,
				paramsMap);
		ModelAndView mv = getAutoView();
		mv.addObject("bpmFormDialog", bpmFormDialog);

		if (bpmFormDialog.getNeedpage().intValue() == 1) {
			PageBean pageBean = bpmFormDialog.getPageBean();
			Map pageModel = new HashMap();
			pageModel.put("tableIdCode", "");
			pageModel.put("pageBean", pageBean);
			pageModel.put("showExplain", Boolean.valueOf(true));
			pageModel.put("showPageSize", Boolean.valueOf(true));
			pageModel.put("baseHref", nextUrl);
			String pageHtml = this.freemarkEngine.mergeTemplateIntoString(
					"page.ftl", pageModel);
			mv.addObject("pageHtml", pageHtml);
		}
		return mv;
	}
}
