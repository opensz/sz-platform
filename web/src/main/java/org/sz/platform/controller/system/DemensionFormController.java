package org.sz.platform.controller.system;

import java.util.HashMap;
import java.util.Map;

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
import org.sz.platform.model.system.Demension;
import org.sz.platform.service.system.DemensionService;

@Controller
@RequestMapping({ "/platform/system/demension/" })
public class DemensionFormController extends BaseFormController {

	@Resource
	private DemensionService demensionService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新SYS_DEMENSION")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Demension demension, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("demension", demension,
				bindResult, request);

		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		if (demension.getDemId() == null) {
			Map param = new HashMap();
			param.put("demName", demension.getDemName());
			boolean isTrue = this.demensionService.getNotExists(param);
			if (isTrue) {
				demension.setDemId(Long.valueOf(UniqueIdUtil.genId()));
				this.demensionService.add(demension);
				resultMsg = getText("record.added", new Object[] { "维度" });
				writeResultMessage(response.getWriter(), resultMsg, 1);
			} else {
				resultMsg = getText("该维度已经存在！");
				writeResultMessage(response.getWriter(), resultMsg, 0);
			}
		} else {
			this.demensionService.update(demension);
			resultMsg = getText("record.updated", new Object[] { "维度" });
			writeResultMessage(response.getWriter(), resultMsg, 1);
		}
	}

	@ModelAttribute
	protected Demension getFormObject(@RequestParam("demId") Long demId,
			Model model) throws Exception {
		this.logger.debug("enter Demension getFormObject here....");
		Demension demension = null;
		if (demId != null)
			demension = (Demension) this.demensionService.getById(demId);
		else {
			demension = new Demension();
		}
		return demension;
	}
}
