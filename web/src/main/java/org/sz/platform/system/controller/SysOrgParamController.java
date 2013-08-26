package org.sz.platform.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.SysOrg;
import org.sz.platform.system.model.SysOrgParam;
import org.sz.platform.system.model.SysParam;
import org.sz.platform.system.service.SysOrgParamService;
import org.sz.platform.system.service.SysOrgService;
import org.sz.platform.system.service.SysParamService;
import org.sz.platform.system.service.SysUserService;

@Controller
@RequestMapping({ "/platform/system/sysOrgParam/" })
public class SysOrgParamController extends BaseController {

	@Resource
	private SysOrgParamService sysOrgParamService;

	@Resource
	private SysOrgService sysOrgService;

	@Resource
	private SysParamService sysParamService;

	@Resource
	private SysUserService sysUserService;

	// @Resource
	// private BpmNodeUserService bpmNodeUserService;

	@RequestMapping({ "editByOrgId" })
	@Action(description = "修改组织参数属性分页列表")
	public ModelAndView editByOrgId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String returnUrl = RequestUtil.getPrePage(request);
		long orgId = RequestUtil.getLong(request, "orgId");
		SysOrg sysOrg = (SysOrg) this.sysOrgService
				.getById(Long.valueOf(orgId));
		List list = this.sysOrgParamService.getAll(new WebQueryFilter(request,
				"sysOrgParamItem"));

		List sysParamList = this.sysParamService.getOrgParam();

		ModelAndView mv = getAutoView().addObject("sysOrgParamList", list)
				.addObject("sysParamList", sysParamList)
				.addObject("orgId", Long.valueOf(orgId))
				.addObject("sysOrg", sysOrg);

		return mv;
	}

	@RequestMapping({ "saveByOrgId" })
	@Action(description = "编辑组织参数属性")
	public void saveByOrgId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			long orgId = RequestUtil.getLong(request, "orgId");
			String[] paramId = request.getParameterValues("paramId");
			String[] paramValue = request.getParameterValues("paramValue");
			List valueList = coverBean(orgId, paramId, paramValue);
			this.sysOrgParamService.add(orgId, valueList);

			ResultMessage message = new ResultMessage(1, "编辑组织参数属性成功");

			out.print(message.toString());
		} catch (Exception e) {
			e.printStackTrace();
			ResultMessage message = new ResultMessage(0, "编辑组织参数属性失败"
					+ e.getMessage());

			out.print(message.toString());
		}
	}

	private List<SysOrgParam> coverBean(long orgId, String[] paramId,
			String[] paramValue) throws Exception {
		List list = new ArrayList();
		if ((paramId == null) || (paramId.length == 0))
			return list;
		for (int i = 0; i < paramId.length; i++) {
			String p = paramId[i];
			String v = paramValue[i];
			if ((p == null) || (p.equals("")))
				continue;
			SysOrgParam param = new SysOrgParam();
			param.setValueId(Long.valueOf(UniqueIdUtil.genId()));
			param.setParamId(Long.valueOf(Long.parseLong(p)));
			param.setParamValue(v);
			param.setOrgId(Long.valueOf(orgId));

			String dataType = ((SysParam) this.sysParamService.getById(Long
					.valueOf(Long.parseLong(p)))).getDataType();

			if ((SysParam.DATA_TYPE_MAP.get(dataType) != null)
					&& (((String) SysParam.DATA_TYPE_MAP.get(dataType))
							.equals("数字"))) {
				param.setParamIntValue(Long.valueOf(Long.parseLong(v)));
			} else if ((SysParam.DATA_TYPE_MAP.get(dataType) != null)
					&& (((String) SysParam.DATA_TYPE_MAP.get(dataType))
							.equals("日期"))) {
				param.setParamDateValue(SysParam.PARAM_DATE_FORMAT.parse(v));
			}

			list.add(param);
		}
		return list;
	}

	@RequestMapping({ "dialog" })
	public ModelAndView dialog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();

		Long nodeUserId = Long.valueOf(RequestUtil.getLong(request,
				"nodeUserId"));
		// BpmNodeUser nu =
		// (BpmNodeUser)this.bpmNodeUserService.getById(nodeUserId);
		// if (nu != null) {
		// String cmpIds = nu.getCmpIds();
		// cmpIds = sysParamService.setParamIcon(request.getContextPath(),
		// cmpIds);
		//
		// String cmpNames = nu.getCmpNames();
		// mv.addObject("cmpIds", cmpIds);
		// mv.addObject("cmpNames", cmpNames);
		// } else {
		String cmpIds = RequestUtil.getString(request, "cmpIds");
		cmpIds = sysParamService.setParamIcon(request.getContextPath(), cmpIds);

		String cmpNames = RequestUtil.getString(request, "cmpNames");
		mv.addObject("cmpIds", cmpIds);
		mv.addObject("cmpNames", cmpNames);
		// }

		List sysParamList = this.sysParamService.getOrgParam();

		mv.addObject("sysParamList", sysParamList).addObject("conditionUS",
				SysParam.CONDITION_US);

		return mv;
	}

	@RequestMapping({ "getByParamKey" })
	public ModelAndView getByParamKey(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		String orgParam = RequestUtil.getString(request, "orgParam");
		orgParam = new String(orgParam.getBytes("ISO8859_1"), "utf-8");
		List userList = this.sysUserService.getByOrgParam(orgParam);
		mv.addObject("userList", userList);
		return mv;
	}
}
