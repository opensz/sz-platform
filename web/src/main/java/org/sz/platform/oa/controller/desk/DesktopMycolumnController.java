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
import org.sz.platform.oa.model.desk.DesktopColumn;
import org.sz.platform.oa.model.desk.DesktopLayout;
import org.sz.platform.oa.model.desk.DesktopMycolumn;
import org.sz.platform.oa.service.desk.DesktopColumnService;
import org.sz.platform.oa.service.desk.DesktopLayoutService;
import org.sz.platform.oa.service.desk.DesktopMycolumnService;

@Controller
@RequestMapping({ "/platform/system/desktopMycolumn/" })
public class DesktopMycolumnController extends BaseController {

	@Resource
	private DesktopMycolumnService desktopMycolumnService;

	@Resource
	private DesktopColumnService desktopColumnService;

	@Resource
	private DesktopLayoutService desktopLayoutService;

	@Resource
	private FreemarkEngine freemarkEngine;
	private String columnWidth;

	@RequestMapping({ "list" })
	@Action(description = "查看桌面个人栏目分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.desktopMycolumnService.getAll(new WebQueryFilter(request,
				"desktopMycolumnItem"));
		ModelAndView mv = getAutoView().addObject("desktopMycolumnList", list);
		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除桌面个人栏目")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.desktopMycolumnService.delByIds(lAryId);
			message = new ResultMessage(1, "删除桌面个人栏目成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑桌面个人栏目")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		String returnUrl = RequestUtil.getPrePage(request);
		DesktopMycolumn desktopMycolumn = null;
		if (id.longValue() != 0L)
			desktopMycolumn = (DesktopMycolumn) this.desktopMycolumnService
					.getById(id);
		else {
			desktopMycolumn = new DesktopMycolumn();
		}
		return getAutoView().addObject("desktopMycolumn", desktopMycolumn)
				.addObject("returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看桌面个人栏目明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		DesktopMycolumn desktopMycolumn = (DesktopMycolumn) this.desktopMycolumnService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("desktopMycolumn", desktopMycolumn);
	}

	@RequestMapping({ "getMycolumnData" })
	@ResponseBody
	public ModelAndView getLayoutcolData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ctxPath = request.getContextPath();
		String html = getSelfDeskData(ctxPath);
		return getAutoView().addObject("html", html);
	}

	@RequestMapping({ "show" })
	@Action(description = "显示个人桌面")
	public ModelAndView show(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DesktopLayout bean = this.desktopMycolumnService
				.getShowData(ContextUtil.getCurrentUserId().longValue());
		Map desktopLayoutmap = new HashMap();
		Map desktopColumnmap = new HashMap();
		List<DesktopColumn> desktopColumnList = this.desktopColumnService
				.getAll();

		for (DesktopColumn dc : desktopColumnList) {
			desktopColumnmap.put("" + dc.getId(), dc.getName());
		}
		desktopLayoutmap.put("cols", "" + bean.getCols());
		desktopLayoutmap.put("widths", bean.getWidth());
		desktopLayoutmap.put("id", "" + bean.getId());
		return getAutoView().addObject("desktopLayoutmap", desktopLayoutmap)
				.addObject("desktopColumnmap", desktopColumnmap)
				.addObject("desktop", "show");
	}

	private String getSelfDeskData(String ctxPath) throws Exception {
		long userId = ContextUtil.getCurrentUserId().longValue();
		Map mapData = this.desktopMycolumnService.getMyDeskData(
				Long.valueOf(userId), ctxPath);
		return this.freemarkEngine.mergeTemplateIntoString(
				"desktop/getDeskTop.ftl", mapData);
	}

	@RequestMapping({ "news" })
	@Action(description = "个人桌面重置布局")
	public ModelAndView news(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List desktopLayout = this.desktopLayoutService.getAll();
		DesktopLayout bean = this.desktopMycolumnService
				.getShowData(ContextUtil.getCurrentUserId().longValue());
		List desktopColumnList = this.desktopColumnService.getAll();
		String ctxPath = request.getContextPath();
		String html = getSelfDeskData(ctxPath);
		return getAutoView().addObject("desktopLayoutmap", bean)
				.addObject("desktopColumnList", desktopColumnList)
				.addObject("desktopLayout", desktopLayout)
				.addObject("html", html);
	}

	@RequestMapping({ "change" })
	@Action(description = "个人桌面编辑布局")
	public ModelAndView change(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DesktopLayout bean = this.desktopMycolumnService
				.getShowData(ContextUtil.getCurrentUserId().longValue());
		Map desktopLayoutmap = new HashMap();
		Map desktopColumnmap = new HashMap();
		List<DesktopColumn> desktopColumnList = this.desktopColumnService
				.getAll();

		for (DesktopColumn dc : desktopColumnList) {
			desktopColumnmap.put("" + dc.getId(), dc.getName());
		}
		desktopLayoutmap.put("cols", "" + bean.getCols());
		desktopLayoutmap.put("widths", bean.getWidth());
		desktopLayoutmap.put("id", "" + bean.getId());
		return getAutoView().addObject("desktopLayoutmap", desktopLayoutmap)
				.addObject("desktopColumnmap", desktopColumnmap)
				.addObject("desktop", "change");
	}

	@RequestMapping({ "warm" })
	@Action(description = "提醒")
	public ModelAndView warm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return getAutoView();
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
			DesktopMycolumn desktopMy = new DesktopMycolumn();
			desktopMy.setCol(Short.valueOf((short) jsoO.getInt("col")));
			desktopMy.setSn(Integer.valueOf(jsoO.getInt("sn")));
			desktopMy.setColumnId(Long.valueOf(jsoO.getLong("columnId")));
			desktopMy.setId(Long.valueOf(UniqueIdUtil.genId()));
			desktopMy.setLayoutId(layoutId);
			desktopMy.setUserId(userId);
			list.add(desktopMy);
		}
		ResultMessage resultObj = null;
		PrintWriter out = response.getWriter();
		if ((list.size() < 0) || (layoutId.longValue() == 0L)) {
			resultObj = new ResultMessage(0, "桌面布局异常，不能保存");
		} else {
			this.desktopMycolumnService.saveMycolumn(list, layoutId, userId);
			resultObj = new ResultMessage(1, "保存成功");
		}
		out.print(resultObj);
	}

	public String getColumnWidth() {
		return this.columnWidth;
	}

	public void setColumnWidth(String columnWidth) {
		this.columnWidth = columnWidth;
	}
}
