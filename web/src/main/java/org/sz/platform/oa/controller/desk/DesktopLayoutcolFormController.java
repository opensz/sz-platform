package org.sz.platform.oa.controller.desk;

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
import org.sz.platform.oa.model.desk.DesktopLayoutcol;
import org.sz.platform.oa.service.desk.DesktopLayoutcolService;

@Controller
@RequestMapping({ "/platform/system/desktopLayoutcol/" })
public class DesktopLayoutcolFormController extends BaseFormController {

	@Resource
	private DesktopLayoutcolService desktopLayoutcolService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新桌面栏目管理表")
	public void save(HttpServletRequest request, HttpServletResponse response,
			DesktopLayoutcol desktopLayoutcol, BindingResult bindResult)
			throws Exception {
		ResultMessage resultMessage = validForm("desktopLayoutcol",
				desktopLayoutcol, bindResult, request);
		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		if (desktopLayoutcol.getId() == null) {
			desktopLayoutcol.setId(Long.valueOf(UniqueIdUtil.genId()));
			this.desktopLayoutcolService.add(desktopLayoutcol);
			resultMsg = getText("record.added", new Object[] { "桌面栏目管理表" });
		} else {
			this.desktopLayoutcolService.update(desktopLayoutcol);
			resultMsg = getText("record.updated", new Object[] { "桌面栏目管理表" });
		}
		writeResultMessage(response.getWriter(), resultMsg, 1);
	}

	@ModelAttribute
	protected DesktopLayoutcol getFormObject(@RequestParam("id") Long id,
			Model model) throws Exception {
		this.logger.debug("enter DesktopLayoutcol getFormObject here....");
		DesktopLayoutcol desktopLayoutcol = null;
		if (id != null)
			desktopLayoutcol = (DesktopLayoutcol) this.desktopLayoutcolService
					.getById(id);
		else {
			desktopLayoutcol = new DesktopLayoutcol();
		}
		return desktopLayoutcol;
	}
}
