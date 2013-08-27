package org.sz.platform.bpm.controller.flow;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmDefinition;
import org.sz.platform.bpm.service.flow.BpmDefRightsService;
import org.sz.platform.bpm.service.flow.BpmDefinitionService;
import org.sz.platform.system.model.GlobalType;
import org.sz.platform.system.service.GlobalTypeService;

@Controller
@RequestMapping({ "/platform/bpm/bpmDefRight/" })
public class BpmDefRightController extends BaseController {

	@Resource
	private BpmDefRightsService bpmDefRightService;

	@Resource
	private BpmDefinitionService bpmDefinitionService;

	@Resource
	private GlobalTypeService globalTypeService;

	@RequestMapping({ "list" })
	@Action(description = "查看流程定义权限分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		int type = RequestUtil.getInt(request, "type");

		ModelAndView mv = getAutoView();

		Map rightsMap = this.bpmDefRightService.getRights(id, type);

		if (type == 0) {
			BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService
					.getById(id);
			mv.addObject("bpmDefinition", bpmDefinition);
		} else {
			GlobalType globalType = (GlobalType) this.globalTypeService
					.getById(id);
			mv.addObject("globalType", globalType);
		}

		mv.addObject("rightsMap", rightsMap).addObject("id", id)
				.addObject("type", Integer.valueOf(type));

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除流程定义权限")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "rightId");
		this.bpmDefRightService.delByIds(lAryId);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "save" })
	@Action(description = "编辑流程定义权限")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));
		int type = RequestUtil.getInt(request, "type");

		String[] rightType = request.getParameterValues("rightType");
		String[] ownerId = request.getParameterValues("ownerId");
		String[] ownerName = request.getParameterValues("ownerName");
		try {
			this.bpmDefRightService.saveRights(id, type, rightType, ownerId,
					ownerName);
			writeResultMessage(response.getWriter(), "设置权限成功!", 1);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), "设置权限失败!", 0);
		}
	}
}
