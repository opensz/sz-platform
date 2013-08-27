package org.sz.platform.system.controller;

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
import org.sz.platform.system.model.Script;
import org.sz.platform.system.service.ScriptService;

@Controller
@RequestMapping({ "/platform/system/script/" })
public class ScriptFormController extends BaseFormController {

	@Resource
	private ScriptService scriptService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新脚本管理")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Script script, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("script", script, bindResult,
				request);

		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		if (script.getId() == null) {
			script.setId(Long.valueOf(UniqueIdUtil.genId()));
			this.scriptService.add(script);
			resultMsg = getText("record.added", new Object[] { "脚本管理" });
		} else {
			this.scriptService.update(script);
			resultMsg = getText("record.updated", new Object[] { "脚本管理" });
		}
		writeResultMessage(response.getWriter(), resultMsg, 1);
	}

	@ModelAttribute
	protected Script getFormObject(@RequestParam("id") Long id, Model model)
			throws Exception {
		this.logger.debug("enter Script getFormObject here....");
		Script script = null;
		if (id != null)
			script = (Script) this.scriptService.getById(id);
		else {
			script = new Script();
		}
		return script;
	}
}
