package org.sz.platform.bpm.controller.flow;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmNodeSign;
import org.sz.platform.bpm.service.flow.BpmNodeSignService;

@Controller
@RequestMapping({ "/platform/bpm/bpmNodeSign/" })
public class BpmNodeSignController extends BaseController {

	@Resource
	private BpmNodeSignService bpmNodeSignService;

	@RequestMapping({ "list" })
	@Action(description = "查看会签任务投票规则分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.bpmNodeSignService.getAll(new WebQueryFilter(request,
				"bpmNodeSignItem"));
		ModelAndView mv = getAutoView().addObject("bpmNodeSignList", list);
		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除会签任务投票规则")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "signId");
		this.bpmNodeSignService.delByIds(lAryId);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑会签任务投票规则")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		String actDefId = RequestUtil.getString(request, "actDefId");
		String nodeId = RequestUtil.getString(request, "nodeId");

		BpmNodeSign bpmNodeSign = this.bpmNodeSignService.getByDefIdAndNodeId(
				actDefId, nodeId);
		if (bpmNodeSign == null) {
			bpmNodeSign = new BpmNodeSign();
			bpmNodeSign.setActDefId(actDefId);
			bpmNodeSign.setNodeId(nodeId);
		}
		return getAutoView().addObject("bpmNodeSign", bpmNodeSign);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看会签任务投票规则明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "signId");
		BpmNodeSign bpmNodeSign = (BpmNodeSign) this.bpmNodeSignService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("bpmNodeSign", bpmNodeSign);
	}
}
