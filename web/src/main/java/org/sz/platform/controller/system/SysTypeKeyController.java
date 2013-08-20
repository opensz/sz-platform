package org.sz.platform.controller.system;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.PinyinUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.model.system.SysTypeKey;
import org.sz.platform.service.system.SysTypeKeyService;

@Controller
@RequestMapping({ "/platform/system/sysTypeKey/" })
public class SysTypeKeyController extends BaseController {

	@Resource
	private SysTypeKeyService sysTypeKeyService;

	@RequestMapping({ "list" })
	@Action(description = "查看系统分类键定义分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new WebQueryFilter(request, "sysTypeKeyItem",
				false);
		List list = this.sysTypeKeyService.getAll(queryFilter);
		ModelAndView mv = getAutoView().addObject("sysTypeKeyList", list);

		return mv;
	}

	@RequestMapping({ "getPingyinByName" })
	@Action(description = "根据汉字获取对应的拼音")
	public void getPingyinByName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		String typeName = RequestUtil.getString(request, "typeName");
		String str = PinyinUtil.getFirstSpell(typeName);
		out.print(str);
	}

	@RequestMapping({ "del" })
	@Action(description = "删除系统分类键定义")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "typeId");
			this.sysTypeKeyService.delByIds(lAryId);
			message = new ResultMessage(1, "删除系统分类键定义成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑系统分类键定义")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long typeId = Long.valueOf(RequestUtil.getLong(request, "typeId"));
		String returnUrl = RequestUtil.getPrePage(request);
		SysTypeKey sysTypeKey = null;
		if (typeId.longValue() != 0L)
			sysTypeKey = (SysTypeKey) this.sysTypeKeyService.getById(typeId);
		else {
			sysTypeKey = new SysTypeKey();
		}
		return getAutoView().addObject("sysTypeKey", sysTypeKey).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看系统分类键定义明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "typeId");
		SysTypeKey sysTypeKey = (SysTypeKey) this.sysTypeKeyService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("sysTypeKey", sysTypeKey);
	}

	@RequestMapping({ "saveSequence" })
	@Action(description = "排序操作")
	public void saveSequence(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message;
		try {
			Long[] ids = RequestUtil.getLongAryByStr(request, "ids");
			this.sysTypeKeyService.saveSequence(ids);
			message = new ResultMessage(1, "分类排序成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "分类排序失败!");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
