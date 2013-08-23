package org.sz.platform.controller.system;

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
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseFormController;
import org.sz.platform.model.system.Resources;
import org.sz.platform.service.system.ResourcesService;
import org.sz.platform.service.system.ResourcesUrlService;

@Controller
@RequestMapping({ "/platform/system/resources/" })
public class ResourcesFormController extends BaseFormController {

	@Resource
	private ResourcesService resourcesService;

	@Resource
	private ResourcesUrlService resourcesUrlService;

	@RequestMapping({ "save" })
	@Action(description = "添加或更新子系统资源")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Resources resources, BindingResult bindResult) throws Exception {
		ResultMessage resultMessage = validForm("resources", resources,
				bindResult, request);

		if (resultMessage.getResult() == 0) {
			writeResultMessage(response.getWriter(), resultMessage);
			return;
		}
		String resultMsg = null;
		String icon = resources.getIcon();

		icon = icon.replace(request.getContextPath(), "");
		resources.setIcon(icon);

		String defaultUrl = resources.getDefaultUrl();
		if (defaultUrl != null) {
			defaultUrl = defaultUrl.trim();
			if (defaultUrl.equals("")) {
				defaultUrl = null;
			}
			resources.setDefaultUrl(defaultUrl);
		}

		String[] aryName = request.getParameterValues("name");
		String[] aryUrl = request.getParameterValues("url");

		if (resources.getResId() == null) {
			Integer rtn = this.resourcesService.isAliasExists(resources);
			if (rtn.intValue() > 0) {
				writeResultMessage(response.getWriter(), "别名在系统中已存在!", 0);

				return;
			}
			try {
				this.resourcesService.addRes(resources, aryName, aryUrl);
				resultMsg = getText("record.added", new Object[] { "子系统资源" });
				writeResultMessage(response.getWriter(), resultMsg, 1);
			} catch (Exception e) {
				writeResultMessage(response.getWriter(), "添加资源失败!", 0);
			}
		} else {
			Integer rtn = this.resourcesService.isAliasExistsForUpd(resources);
			if (rtn.intValue() > 0) {
				writeResultMessage(response.getWriter(), "别名在系统中已存在!", 0);

				return;
			}
			try {
				this.resourcesService.updRes(resources, aryName, aryUrl);
				resultMsg = getText("record.updated", new Object[] { "子系统资源" });
				writeResultMessage(response.getWriter(), resultMsg, 1);
			} catch (Exception e) {
				writeResultMessage(response.getWriter(), "更新资源失败!", 0);
			}
		}
	}

	@ModelAttribute
	protected Resources getFormObject(@RequestParam("resId") Long resId,
			Model model) throws Exception {
		this.logger.debug("enter Resources getFormObject here....");
		Resources resources = null;
		if (resId != null)
			resources = (Resources) this.resourcesService.getById(resId);
		else {
			resources = new Resources();
		}
		return resources;
	}
}
