package org.sz.platform.system.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.SysDataSource;
import org.sz.platform.system.service.SysDataSourceService;

@Controller
@RequestMapping({ "/platform/system/sysDataSource/" })
public class SysDataSourceController extends BaseController {

	@Resource
	private SysDataSourceService service;

	@RequestMapping({ "list" })
	@Action(description = "查看系统数据源列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "page", defaultValue = "1") int page)
			throws Exception {
		List list = this.service.getAll(new WebQueryFilter(request,
				"sysDataSourceItem"));
		ModelAndView mv = getAutoView().addObject("sysDataSourceList", list);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除系统数据源")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.service.delByIds(lAryId);
			message = new ResultMessage(1, "删除系统数据源成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除系统数据源失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑系统数据源")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		String returnUrl = RequestUtil.getPrePage(request);
		SysDataSource sysDataSource = null;
		if (id.longValue() != 0L)
			sysDataSource = (SysDataSource) this.service.getById(id);
		else {
			sysDataSource = new SysDataSource();
		}

		return getAutoView().addObject("sysDataSource", sysDataSource)
				.addObject("returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		SysDataSource po = (SysDataSource) this.service.getById(Long
				.valueOf(id));
		return getAutoView().addObject("sysDataSource", po);
	}

	@ResponseBody
	@RequestMapping({ "testConnectById" })
	public List<Map<String, Object>> testConnectById(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");

		List result = this.service.testConnectById(lAryId);
		return result;
	}

	@ResponseBody
	@RequestMapping({ "testConnectByForm" })
	public List<Map<String, Object>> testConnectByForm(
			HttpServletRequest request, HttpServletResponse response,
			SysDataSource po) throws Exception {
		List result = this.service.testConnectByForm(po);
		return result;
	}
}
