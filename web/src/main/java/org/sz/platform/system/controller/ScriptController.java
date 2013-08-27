package org.sz.platform.system.controller;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.query.PageBean;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.BeanUtils;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.Script;
import org.sz.platform.system.service.DictionaryService;
import org.sz.platform.system.service.ScriptService;
import org.sz.platform.system.service.SysFileService;

@Controller
@RequestMapping({ "/platform/system/script/" })
public class ScriptController extends BaseController {

	@Resource
	private ScriptService scriptService;

	@Resource
	private DictionaryService dictionaryService;

	@Resource
	private SysFileService sysFileService;

	@RequestMapping({ "list" })
	@Action(description = "查看脚本管理分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List categoryList = this.scriptService.getDistinctCategory();
		List list = this.scriptService.getAll(new WebQueryFilter(request,
				"scriptItem"));
		ModelAndView mv = getAutoView().addObject("scriptList", list);
		mv.addObject("categoryList", categoryList);

		return mv;
	}

	@RequestMapping({ "dialog" })
	@Action(description = "脚本选择")
	public ModelAndView dialog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List categoryList = this.scriptService.getDistinctCategory();
		QueryFilter queryFilter = new WebQueryFilter(request, "scriptItem");
		PageBean pageBean = queryFilter.getPageBean();
		pageBean.setPagesize(10);
		List list = this.scriptService.getAll(queryFilter);
		ModelAndView mv = getAutoView().addObject("scriptList", list);
		mv.addObject("categoryList", categoryList);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除脚本管理")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.scriptService.delByIds(lAryId);
			message = new ResultMessage(1, "删除脚本成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑脚本管理")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		String returnUrl = RequestUtil.getPrePage(request);
		Script script = null;
		if (id.longValue() != 0L)
			script = (Script) this.scriptService.getById(id);
		else {
			script = new Script();
		}
		return getAutoView().addObject("script", script).addObject("returnUrl",
				returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看脚本管理明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		Script script = (Script) this.scriptService.getById(Long.valueOf(id));
		return getAutoView().addObject("script", script);
	}

	@RequestMapping({ "export" })
	@Action(description = "导出脚本")
	public void export(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
		if (BeanUtils.isNotEmpty(lAryId)) {
			Calendar now = Calendar.getInstance();
			String localTime = now.get(1) + "-" + now.get(2) + "-" + now.get(5);
			String strXml = this.scriptService.exportXml(lAryId);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename=Script" + localTime + ".xml");
			response.getWriter().write(strXml);
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	@RequestMapping({ "importXml" })
	@Action(description = "导入自定义表", operateType = "自定义表")
	public void importXml(MultipartHttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MultipartFile fileLoad = request.getFile("xmlFile");
		String msg = this.scriptService.importXml(fileLoad.getInputStream());
		ResultMessage message = null;
		if (msg.length() == 0) {
			message = new ResultMessage(1, "导入成功!");
		} else {
			message = new ResultMessage(0, msg);
		}
		writeResultMessage(response.getWriter(), message);
	}
}
