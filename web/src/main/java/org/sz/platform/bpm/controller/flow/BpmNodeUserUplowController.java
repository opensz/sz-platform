package org.sz.platform.bpm.controller.flow;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.util.ContextUtil;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.bpm.model.flow.BpmNodeUserUplow;
import org.sz.platform.bpm.service.flow.BpmNodeUserUplowService;
import org.sz.platform.system.model.Demension;
import org.sz.platform.system.service.DemensionService;
import org.sz.platform.system.service.SysUserService;

@Controller
@RequestMapping({ "/platform/bpm/bpmNodeUserUplow/" })
public class BpmNodeUserUplowController extends BaseController {

	@Resource
	private BpmNodeUserUplowService bpmNodeUserUplowService;

	@Resource
	private DemensionService demensionService;

	@Resource
	private SysUserService sysUserService;

	@RequestMapping({ "dialog" })
	public ModelAndView dialog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List demensionList = this.demensionService.getAll();
		demensionList.add(Demension.positionDem);

		ModelAndView mv = getAutoView().addObject("demensionList",
				demensionList).addObject("uplowtypeList",
				BpmNodeUserUplow.UPLOWTYPE_MAP);

		return mv;
	}

	@RequestMapping({ "getByUserId" })
	public ModelAndView getByUserId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		String json = RequestUtil.getString(request, "json");
		json = new String(json.getBytes("ISO-8859-1"), "utf-8");

		JSONArray ja = JSONArray.fromObject(json);
		List uplowList = (List) JSONArray.toCollection(ja,
				BpmNodeUserUplow.class);

		List userList = this.sysUserService.getByUserIdAndUplow(ContextUtil
				.getCurrentUserId().longValue(), uplowList);

		return mv.addObject("userList", userList);
	}
}
