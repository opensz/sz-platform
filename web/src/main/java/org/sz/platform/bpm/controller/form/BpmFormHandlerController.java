package org.sz.platform.bpm.controller.form;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.util.ContextUtil;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmNodeSet;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.model.form.BpmFormDef;
import org.sz.platform.bpm.service.flow.BpmNodeSetService;
import org.sz.platform.bpm.service.flow.ProcessRunService;
import org.sz.platform.bpm.service.form.BpmFormDefService;
import org.sz.platform.bpm.service.form.BpmFormHandlerService;
import org.sz.platform.bpm.util.FormDataUtil;

@Controller
@RequestMapping({ "/platform/form/bpmFormHandler/" })
public class BpmFormHandlerController extends BaseController {

	@Resource
	private BpmFormHandlerService service;

	@Resource
	private BpmFormDefService bpmFormDefService;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private BpmNodeSetService bpmNodeSetService;

	@RequestMapping({ "edit" })
	@Action(description = "表单预览", operateType = "自定义表单数据处理")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long formDefId = Long
				.valueOf(RequestUtil.getLong(request, "formDefId"));
		String pkValue = request.getParameter("pkValue");
		String returnUrl = RequestUtil.getPrePage(request);
		BpmFormDef bpmFormDef = null;

		if (formDefId.longValue() != 0L) {
			bpmFormDef = (BpmFormDef) this.bpmFormDefService.getById(formDefId);
			String html = this.service.obtainHtml(bpmFormDef,
					ContextUtil.getCurrentUserId(), pkValue, "", "", "");
			bpmFormDef.setHtml(html);
		} else {
			bpmFormDef = new BpmFormDef();
			bpmFormDef.setHtml(RequestUtil.getString(request, "html"));
		}
		return getAutoView().addObject("bpmFormDef", bpmFormDef).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "bizForm" })
	@Action(description = "显示业务表单。", operateType = "自定义表单数据处理")
	public ModelAndView bizForm(HttpServletRequest request) throws Exception {
		Long formKey = Long.valueOf(RequestUtil.getLong(request, "formKey"));
		String pkValue = request.getParameter("pk");
		Long runId = RequestUtil.getLong(request, "runId");
		if (runId > 0L) {
			// 判断ProcessRun 是否传入
			ProcessRun processRun = processRunService.getById(runId);
			pkValue = processRun.getBusinessKey();

			// 默认查询流程Gloabal对应的表单Key
			BpmNodeSet bpmNodeSet = bpmNodeSetService.getBySetType(
					processRun.getDefId(), BpmNodeSet.SetType_GloabalForm);
			formKey = bpmNodeSet.getFormKey();
		}

		String returnUrl = RequestUtil.getPrePage(request);
		BpmFormDef bpmFormDef = null;
		if (formKey.longValue() != 0L) {
			bpmFormDef = this.bpmFormDefService
					.getDefaultVersionByFormKey(formKey);
			String html = this.service.obtainHtml(bpmFormDef,
					ContextUtil.getCurrentUserId(), pkValue, "", "", "");
			bpmFormDef.setHtml(html);
		}
		return getAutoView().addObject("bpmFormDef", bpmFormDef).addObject(
				"returnUrl", returnUrl);
	}

	@RequestMapping({ "extForm" })
	@Action(description = "显示业务表单", operateType = "Extjs表单查看")
	public ModelAndView extForm(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView();
		Long runId = RequestUtil.getLong(request, "runId");
		if (runId > 0L) {
			// 判断ProcessRun 是否传入
			ProcessRun processRun = processRunService.getById(runId);
			mv.addObject("processRun", processRun);
		}
		return mv;
	}

	@RequestMapping({ "save" })
	@Action(description = "添加或更新", operateType = "自定义表单数据处理")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String data = request.getParameter("data");

		this.service.handFormData(FormDataUtil.parseJson(data));

		writeResultMessage(response.getWriter(), "成功", 1);
	}
}
