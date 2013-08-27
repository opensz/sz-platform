package org.sz.platform.bpm.controller.form;

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
import org.sz.core.web.controller.BaseFormController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.form.BpmTableTemprights;
import org.sz.platform.bpm.service.flow.BpmTableTemprightsService;

@Controller
@RequestMapping({ "/platform/form/bpmTableTemprights/" })
public class BpmTableTemprightsFormController extends BaseFormController {

	@Resource
	private BpmTableTemprightsService bpmTableTemprightsService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新查看业务数据模板的权限信息")
	public void save(HttpServletRequest request, HttpServletResponse response,
			BpmTableTemprights bpmTableTemprights, BindingResult bindResult)
			throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		int type = RequestUtil.getInt(request, "type");
		String[] rightType = request.getParameterValues("rightType");
		String[] ownerId = request.getParameterValues("ownerId");
		String[] ownerName = request.getParameterValues("ownerName");
		try {
			this.bpmTableTemprightsService.saveRights(id, type, rightType,
					ownerId, ownerName);
			writeResultMessage(response.getWriter(), "设置权限成功!", 1);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), "设置权限失败!", 0);
		}
	}

	@ModelAttribute
	protected BpmTableTemprights getFormObject(@RequestParam("id") Long id,
			Model model) throws Exception {
		this.logger.debug("enter BpmTableTemprights getFormObject here....");
		BpmTableTemprights bpmTableTemprights = null;
		if (id != null)
			bpmTableTemprights = (BpmTableTemprights) this.bpmTableTemprightsService
					.getById(id);
		else {
			bpmTableTemprights = new BpmTableTemprights();
		}
		return bpmTableTemprights;
	}
}
