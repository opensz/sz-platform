package org.sz.platform.oa.controller.desk;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.engine.FreemarkEngine;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.oa.model.desk.DesktopLayout;
import org.sz.platform.oa.model.desk.DesktopLayoutcol;
import org.sz.platform.oa.service.desk.DesktopColumnService;
import org.sz.platform.oa.service.desk.DesktopLayoutService;
import org.sz.platform.oa.service.desk.DesktopLayoutcolService;
import org.sz.platform.oa.service.desk.DesktopMycolumnService;

@Controller
@RequestMapping({ "/platform/system/desktopLayoutcol/" })
public class DesktopLayoutcolController extends BaseController {

	@Resource
	private DesktopLayoutcolService desktopLayoutcolService;

	@Resource
	private DesktopColumnService desktopColumnService;

	@Resource
	private DesktopLayoutService desktopLayoutService;

	@Resource
	private DesktopMycolumnService desktopMycolumnService;

	@Resource
	private FreemarkEngine freemarkEngine;

	@RequestMapping({ "list" })
	@Action(description = "查看桌面栏目管理表分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.desktopLayoutcolService.getAll(new WebQueryFilter(
				request, "desktopLayoutcolItem"));

		this.desktopLayoutcolService.setListData(list);
		ModelAndView mv = getAutoView().addObject("desktopLayoutcolList", list);
		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除桌面栏目管理表")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
		ResultMessage message = null;
		try {
			this.desktopLayoutcolService.delByIds(lAryId);
			message = new ResultMessage(1, "删除桌面栏目管理表成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}

		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑桌面栏目管理表")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		String returnUrl = RequestUtil.getPrePage(request);
		DesktopLayoutcol desktopLayoutcol = null;
		Map desktopColumnmap = new HashMap();
		Map desktopLayoutmap = new HashMap();
		desktopLayoutcol = this.desktopLayoutcolService.editData(id,
				desktopColumnmap, desktopLayoutmap);
		return getAutoView().addObject("desktopLayoutcol", desktopLayoutcol)
				.addObject("returnUrl", returnUrl)
				.addObject("desktopColumnmap", desktopColumnmap)
				.addObject("desktopLayoutmap", desktopLayoutmap);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看桌面栏目管理表明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		DesktopLayoutcol desktopLayoutcol = (DesktopLayoutcol) this.desktopLayoutcolService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("desktopLayoutcol", desktopLayoutcol);
	}

	@RequestMapping({ "show" })
	@Action(description = "查看桌面显示")
	public ModelAndView show(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long layoutId = Long.valueOf(RequestUtil.getLong(request, "id"));
		String ctxPath = request.getContextPath();
		List list = this.desktopColumnService.getAll();
		List layoutcolList = this.desktopLayoutcolService
				.layoutcolData(layoutId);
		DesktopLayout desktopLayout = (DesktopLayout) this.desktopLayoutService
				.getById(layoutId);
		Map mapData = this.desktopMycolumnService.getDefaultDeskDataById(
				layoutId, ctxPath);
		String html = this.freemarkEngine.mergeTemplateIntoString(
				"desktop/getDeskTop.ftl", mapData);

		return getAutoView().addObject("desktopColumnList", list)
				.addObject("html", html)
				.addObject("desktopLayout", desktopLayout);
	}

	@RequestMapping({ "getLayoutcolData" })
	@ResponseBody
	public List<DesktopLayoutcol> getLayoutcolData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long layoutId = Long.valueOf(RequestUtil.getLong(request, "id"));
		List list = this.desktopLayoutcolService.layoutcolData(layoutId);
		return list;
	}

	@RequestMapping({ "saveCol" })
	public void saveCol(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long userId = ContextUtil.getCurrentUserId();
		Long layoutId = Long.valueOf(RequestUtil.getLong(request, "layoutId"));
		String dataStr = RequestUtil.getString(request, "data");
		JSONArray jsoA = JSONArray.fromObject(dataStr);
		List list = new ArrayList();
		for (int i = 0; i < jsoA.size(); i++) {
			JSONObject jsoO = jsoA.getJSONObject(i);
			DesktopLayoutcol desktopLayoutcol = new DesktopLayoutcol();
			desktopLayoutcol.setCol(Integer.valueOf(jsoO.getInt("col")));
			desktopLayoutcol.setSn(Integer.valueOf(jsoO.getInt("sn")));
			desktopLayoutcol
					.setColumnId(Long.valueOf(jsoO.getLong("columnId")));
			desktopLayoutcol.setId(Long.valueOf(UniqueIdUtil.genId()));
			desktopLayoutcol.setLayoutId(layoutId);
			list.add(desktopLayoutcol);
		}
		ResultMessage resultObj = null;
		PrintWriter out = response.getWriter();
		if ((list.size() < 0) || (layoutId.longValue() == 0L)) {
			resultObj = new ResultMessage(0, "栏目设置异常，不能保存");
		} else {
			this.desktopLayoutcolService.saveCol(list, layoutId);
			resultObj = new ResultMessage(1, "保存成功");
		}
		out.print(resultObj);
	}
}
