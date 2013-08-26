package org.sz.platform.bpm.controller.form;

import java.io.IOException;
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
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.form.BpmFormRule;
import org.sz.platform.bpm.service.form.BpmFormRuleService;

@Controller
@RequestMapping({ "/platform/form/bpmFormRule/" })
public class BpmFormRuleController extends BaseController {

	@Resource
	private BpmFormRuleService bpmFormRuleService;

	@RequestMapping({ "list" })
	@Action(description = "查看表单验证规则分页列表", operateType = "表单规则验证")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.bpmFormRuleService.getAll(new WebQueryFilter(request,
				"bpmFormRuleItem"));
		ModelAndView mv = getAutoView().addObject("bpmFormRuleList", list);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除表单验证规则", operateType = "表单规则验证")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.bpmFormRuleService.delByIds(lAryId);
			this.bpmFormRuleService.generateJS();
			message = new ResultMessage(1, "删除表单验证规则成功!");
		} catch (Exception ex) {
			message = new ResultMessage(0, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑表单验证规则", operateType = "表单规则验证")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		String returnUrl = RequestUtil.getPrePage(request);
		BpmFormRule bpmFormRule = null;
		if (id.longValue() != 0L)
			bpmFormRule = (BpmFormRule) this.bpmFormRuleService.getById(id);
		else {
			bpmFormRule = new BpmFormRule();
		}
		return getAutoView().addObject("bpmFormRule", bpmFormRule).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "get" })
	@Action(description = "查看表单验证规则明细", operateType = "表单规则验证")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		BpmFormRule bpmFormRule = (BpmFormRule) this.bpmFormRuleService
				.getById(Long.valueOf(id));
		return getAutoView().addObject("bpmFormRule", bpmFormRule);
	}

	@RequestMapping({ "getAllRules" })
	@ResponseBody
	public Map getAllRules(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map resultMap = new HashMap();
		List list = this.bpmFormRuleService.getAll(new WebQueryFilter(request,
				"bpmFormRuleItem"));
		resultMap.put("data", list);
		return resultMap;
	}

}