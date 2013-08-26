package org.sz.platform.system.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.ContextUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.SysUserAgent;
import org.sz.platform.system.service.SysUserAgentService;

@Controller
@RequestMapping({ "/platform/system/sysUserAgent/" })
public class SysUserAgentController extends BaseController {

	@Resource
	private SysUserAgentService sysUserAgentService;

	// @Resource
	// private BpmAgentService bpmAgentService;

	@RequestMapping({ "list" })
	@Action(description = "查看SYS_USER_AGENT分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request, "sysUserAgentItem");
		filter.addFilter("agentuserid", ContextUtil.getCurrentUserId());
		List list = this.sysUserAgentService.getAll(filter);
		ModelAndView mv = getAutoView().addObject("sysUserAgentList", list);
		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除SYS_USER_AGENT")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "agentid");
			this.sysUserAgentService.delByIds(lAryId);
			message = new ResultMessage(1, "删除代理授权成功！");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除代理授权失败：" + e.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑SYS_USER_AGENT")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView();
		Long agentid = Long.valueOf(RequestUtil.getLong(request, "agentid"));
		String returnUrl = RequestUtil.getPrePage(request);
		SysUserAgent sysUserAgent = null;
		if (agentid.longValue() != 0L) {
			sysUserAgent = (SysUserAgent) this.sysUserAgentService
					.getById(agentid);
			// List bpmAgentList = this.bpmAgentService.getByAgentId(agentid);
			// mv.addObject("bpmAgentList", bpmAgentList);
		} else {
			sysUserAgent = new SysUserAgent();
		}
		return mv.addObject("sysUserAgent", sysUserAgent).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看SYS_USER_AGENT明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "agentid");
		SysUserAgent sysUserAgent = (SysUserAgent) this.sysUserAgentService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("sysUserAgent", sysUserAgent);
	}
}
