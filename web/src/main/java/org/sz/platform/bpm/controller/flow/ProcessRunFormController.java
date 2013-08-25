package org.sz.platform.bpm.controller.flow;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.sz.core.annotion.Action;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.bpm.model.flow.ProcessRun;
import org.sz.platform.bpm.service.flow.ProcessRunService;

@Controller
@RequestMapping({ "/platform/bpm/processRun/" })
public class ProcessRunFormController extends BaseFormController {

	@Resource
	private ProcessRunService processRunService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新流程实例扩展")
	public void save(HttpServletRequest request, HttpServletResponse response,
			ProcessRun processRun, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("processRun", processRun,
				bindResult, request);

		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		if (processRun.getRunId() == null) {
			processRun.setRunId(Long.valueOf(UniqueIdUtil.genId()));
			this.processRunService.add(processRun);
			resultMsg = getText("record.added", new Object[] { "流程实例扩展" });
		} else {
			this.processRunService.update(processRun);
			resultMsg = getText("record.updated", new Object[] { "流程实例扩展" });
		}
		writeResultMessage(response.getWriter(), resultMsg, 1);
	}

	@ModelAttribute
	protected ProcessRun getFormObject(@RequestParam("runId") Long runId,
			Model model) throws Exception {
		this.logger.debug("enter ProcessRun getFormObject here....");
		ProcessRun processRun = null;
		if (runId != null)
			processRun = (ProcessRun) this.processRunService.getById(runId);
		else {
			processRun = new ProcessRun();
		}
		return processRun;
	}
}
