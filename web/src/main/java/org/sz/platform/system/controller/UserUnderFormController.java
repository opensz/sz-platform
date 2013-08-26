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
import org.sz.platform.system.model.UserUnder;
import org.sz.platform.system.service.UserUnderService;

@Controller
@RequestMapping({ "/platform/system/userUnder/" })
public class UserUnderFormController extends BaseFormController {

	@Resource
	private UserUnderService userUnderService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新下属管理")
	public void save(HttpServletRequest request, HttpServletResponse response,
			UserUnder userUnder, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("userUnder", userUnder,
				bindResult, request);

		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		if (userUnder.getId() == null) {
			userUnder.setId(Long.valueOf(UniqueIdUtil.genId()));
			this.userUnderService.add(userUnder);
			resultMsg = getText("record.added", new Object[] { "下属管理" });
		} else {
			this.userUnderService.update(userUnder);
			resultMsg = getText("record.updated", new Object[] { "下属管理" });
		}
		writeResultMessage(response.getWriter(), resultMsg, 1);
	}

	@ModelAttribute
	protected UserUnder getFormObject(@RequestParam("id") Long id, Model model)
			throws Exception {
		this.logger.debug("enter UserUnder getFormObject here....");
		UserUnder userUnder = null;
		if (id != null)
			userUnder = (UserUnder) this.userUnderService.getById(id);
		else {
			userUnder = new UserUnder();
		}
		return userUnder;
	}
}
