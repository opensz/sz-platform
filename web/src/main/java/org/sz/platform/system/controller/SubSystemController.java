package org.sz.platform.system.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.SubSystem;
import org.sz.platform.system.service.SubSystemService;

@Controller
@RequestMapping({ "/platform/system/subSystem/" })
public class SubSystemController extends BaseController {

	@Resource
	private SubSystemService service;

	@RequestMapping({ "tree" })
	@ResponseBody
	public List<SubSystem> tree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.service.getAll();
		SubSystem root = new SubSystem();
		root.setSystemId(0L);
		root.setParentId(Long.valueOf(-1L));
		root.setSysName("所有系统");
		list.add(root);

		return list;
	}

	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.service.getAll(new WebQueryFilter(request,
				"subSystemItem"));
		ModelAndView mv = getAutoView().addObject("subSystemList", list);

		return mv;
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑加班情况")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		String returnUrl = RequestUtil.getPrePage(request);
		SubSystem subSystem = null;
		if (id.longValue() != 0L) {
			subSystem = (SubSystem) this.service.getById(id);
		} else {
			subSystem = new SubSystem();
		}
		return getAutoView().addObject("subSystem", subSystem).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "del" })
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.service.delByIds(lAryId);
			message = new ResultMessage(1, "删除子系统成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除子系统失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		SubSystem po = (SubSystem) this.service.getById(Long.valueOf(id));
		return getAutoView().addObject("subSystem", po);
	}
}