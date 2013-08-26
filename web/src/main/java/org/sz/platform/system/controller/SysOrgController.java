package org.sz.platform.system.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.model.OnlineUser;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.AppUtil;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.dao.SysUserOrgDao;
import org.sz.platform.system.model.Demension;
import org.sz.platform.system.model.SysOrg;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.model.SysUserOrg;
import org.sz.platform.system.service.DemensionService;
import org.sz.platform.system.service.SysOrgService;
import org.sz.platform.system.service.SysUserOrgService;
import org.sz.platform.system.service.SysUserService;

@Controller
@RequestMapping({ "/platform/system/sysOrg/" })
public class SysOrgController extends BaseController {

	@Resource
	protected SysUserService sysUserService;

	@Resource
	protected SysOrgService sysOrgService;

	@Resource
	protected DemensionService demensionService;

	@Resource
	protected SysUserOrgDao sysUserOrgDao;

	@Resource
	protected Properties configproperties;

	@Resource
	protected SysUserOrgService sysUserOrgService;

	@RequestMapping({ "getDem" })
	@ResponseBody
	public List<Demension> getDem(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.demensionService.getAll();
	}

	@RequestMapping({ "selector" })
	public ModelAndView selector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request, "sysOrg");
		ModelAndView mv = getAutoView();
		// filter.getFilters().clear();
		filter.addFilter("orgSupId", null);
		SysUser sysUser = ContextUtil.getCurrentUser();
		Long userOrgId = sysUser.getUserOrgId();
		Long orgId = Long.valueOf(RequestUtil.getLong(request, "orgId"));
		Long orgSupId = Long.valueOf(RequestUtil.getLong(request, "orgSupId"));// 父节点id
		Long demId = Long.valueOf(RequestUtil.getLong(request, "demId"));
		String orgType = RequestUtil.getString(request, "type");
		if (demId.longValue() != 0L) {
			filter.addFilter("demId", demId);
		}
		SysOrg org = (SysOrg) this.sysOrgService.getById(orgId);
		if (org != null) {
			filter.addFilter("path", org.getPath());
		}
		if (orgType.equals("org")) { // 客户
			filter.addFilter("orgId", userOrgId);
			if (orgSupId != null && orgSupId > 0L) {
				filter.addFilter("customerId", orgSupId);
			}
			List<SysOrg> orgList = this.sysOrgService.getCustomer(filter);
			mv.addObject("sysOrgList", orgList).addObject("type", orgType);
			return mv;
			// orgTypes="6";
		} else if (orgType.equals("dept")) { // 客户
			filter.addFilter("orgType", 3);
			if (orgSupId.longValue() > 0L) {
				filter.addFilter("path", orgSupId);
			} else {
				filter.addFilter("path", userOrgId);
			}
		} else if (orgType.equals("project")) {
			filter.addFilter("demId", "2");
		} else if (orgType.equals("serviceCenter")) { // center
			filter.addFilter("orgType", "1,2");
			filter.addFilter("path", userOrgId);
		}
		if (filter.getFilters().get("path") == null && orgSupId > 0L) {
			filter.addFilter("path", orgSupId);
		}
		List sysOrgList = this.sysOrgService.getAll(filter);

		String orgName = RequestUtil.getString(request, "orgName");
		if (StringUtil.isEmpty(orgName)) {
			// sysOrgList = this.sysOrgService.coverTreeList(Long.valueOf(org !=
			// null ? org.getOrgId().longValue() : 1L), sysOrgList);
		}
		mv.addObject("sysOrgList", sysOrgList).addObject("type", orgType);
		return mv;
	}

	@RequestMapping({ "dialog" })
	public ModelAndView dialog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List demensionList = this.demensionService.getAll();
		String type = RequestUtil.getString(request, "type");
		String orgId = RequestUtil.getString(request, "orgId");
		ModelAndView mv = getAutoView().addObject("demensionList",
				demensionList);
		mv.addObject("type", type);
		mv.addObject("orgId", orgId);
		return mv;
	}

	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List demensionList = this.demensionService.getAll();
		ModelAndView mv = getAutoView().addObject("demensionList",
				demensionList);
		String isCustomer = RequestUtil.getString(request, "isCustomer");
		if (isCustomer != null && !isCustomer.equals("")) {
			mv.addObject("type", "org");
		}
		return mv;
	}

	@RequestMapping({ "view" })
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long orgId = Long.valueOf(RequestUtil.getLong(request, "orgId"));
		SysOrg sysOrg = (SysOrg) this.sysOrgService.getById(orgId);
		String path = "";
		String paramPath = "";
		if (sysOrg != null) {
			path = sysOrg.getPath();
			paramPath = path.replace(".", ",");
		}
		ModelAndView mv = getAutoView().addObject("orgId", orgId)
				.addObject("path", path).addObject("paramPath", paramPath);
		return mv;
	}

	@RequestMapping({ "listById" })
	public ModelAndView listById(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "page", defaultValue = "1") int page)
			throws Exception {
		Long orgId = Long.valueOf(RequestUtil.getLong(request, "orgId"));
		SysOrg sysOrg = (SysOrg) this.sysOrgService.getById(orgId);
		ModelAndView mv = getAutoView();
		if (sysOrg == null) {
			return mv.addObject("sysOrg", sysOrg);
		}
		String path = sysOrg.getPath();
		QueryFilter filter = new WebQueryFilter(request, "sysOrgItem");
		filter.getFilters().put("path", path);
		// filter.addFilter("orgType", "1,2,3");
		List list = this.sysOrgService.getOrgByOrgId(filter);
		return mv.addObject("sysOrgList", list).addObject("orgId", orgId)
				.addObject("sysOrg", Integer.valueOf(1));
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑组织架构")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long demId = Long.valueOf(RequestUtil.getLong(request, "demId", 0L));
		Long orgId = Long.valueOf(RequestUtil.getLong(request, "orgId"));
		Long parentId = Long.valueOf(RequestUtil.getLong(request, "parentId"));
		Long orgSupTempId = Long.valueOf(RequestUtil.getLong(request,
				"orgSupTempId"));
		String action = RequestUtil.getString(request, "action");
		SysOrg sysOrg = null;
		SysOrg parentSysOrg = null;
		String orgTypeStr = null;

		Demension demension = (Demension) this.demensionService.getById(demId);
		if ("add".equals(action)) {
			sysOrg = new SysOrg();
			SysOrg supSysOrg = (SysOrg) this.sysOrgService.getById(orgId);
			if (demId == 2L) {
				sysOrg.setOrgSupId(parentId);
			}
			if (supSysOrg == null) {
				// sysOrg.setOrgSupId(demId);
				sysOrg.setOrgType(SysOrg.BEGIN_TYPE);
			} else {
				// supSysOrg = (SysOrg)this.sysOrgService.getById(orgId);
				sysOrg.setOrgSupId(supSysOrg.getOrgId());
				sysOrg.setOrgSupName(supSysOrg.getOrgName());
				sysOrg.setOrgType(supSysOrg.getOrgType());
			}
		} else {
			sysOrg = (SysOrg) this.sysOrgService.getById(orgId);
			SysOrg charge = this.sysUserOrgService.getChageNameByOrgId(orgId);
			if (charge != null) {
				sysOrg.setOwnUser(charge.getOwnUser());
				sysOrg.setOwnUserName(charge.getOwnUserName());
			}
		}
		orgTypeStr = sysOrg.getOrgType().toString();

		Map map = new HashMap();
		map.put("enabled", true);

		return getAutoView().addObject("sysOrg", sysOrg)
				.addObject("demension", demension).addObject("action", action)
				.addObject("orgTypeStr", orgTypeStr)
				.addObject("parentId", parentId)
				.addObject("orgSupTempId", orgSupTempId);
	}

	@RequestMapping({ "orgdel" })
	public void orgdel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long orgId = Long.valueOf(RequestUtil.getLong(request, "orgId"));
			this.sysOrgService.delById(orgId);

			message = new ResultMessage(1, "删除组织及其所有子组织成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除组织及其所有子组织失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orgId = RequestUtil.getLong(request, "orgId");
		String ownerName = "";
		SysOrg sysOrg = (SysOrg) this.sysOrgService
				.getById(Long.valueOf(orgId));
		if (sysOrg != null) {
			SysOrg charge = this.sysUserOrgService.getChageNameByOrgId(Long
					.valueOf(orgId));
			Long demId = sysOrg.getDemId();
			if (sysOrg.getDemId().longValue() != 0L) {
				sysOrg.setDemName(((Demension) this.demensionService
						.getById(demId)).getDemName());
				ownerName = charge.getOwnUserName();
			}
		}
		Map map = new HashMap();
		map.put("enabled", true);

		return getAutoView().addObject("sysOrg", sysOrg)
				.addObject("userNameCharge", ownerName)
				.addObject("orgId", Long.valueOf(orgId));
	}

	@RequestMapping({ "type" })
	public ModelAndView type(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String flag = RequestUtil.getString(request, "flag");
		long orgId = RequestUtil.getLong(request, "orgId");
		String userNameStr = "";
		String userNameCharge = "";

		List<SysUserOrg> userlist = this.sysUserOrgDao.getByOrgId(Long
				.valueOf(orgId));
		for (SysUserOrg userOrg : userlist) {
			if (userNameStr.isEmpty())
				userNameStr = userOrg.getUserName();
			else {
				userNameStr = userNameStr + "," + userOrg.getUserName();
			}
			String isCharge = "";
			if (BeanUtils.isNotEmpty(userOrg.getIsCharge())) {
				isCharge = userOrg.getIsCharge().toString();
			}

			if (SysUserOrg.CHARRGE_YES.equals(isCharge)) {
				if (userNameCharge.isEmpty())
					userNameCharge = userOrg.getUserName().toString();
				else {
					userNameCharge = userNameCharge + ","
							+ userOrg.getUserName();
				}
			}
		}
		SysOrg po = (SysOrg) this.sysOrgService.getById(Long.valueOf(orgId));
		return getAutoView().addObject("sysOrg", po)
				.addObject("userNameStr", userNameStr)
				.addObject("userNameCharge", userNameCharge)
				.addObject("orgId", Long.valueOf(orgId))
				.addObject("flag", flag);
	}

	private SysOrg getRootSysOrg(Long demId, String orgName) throws Exception {
		SysOrg org = new SysOrg();
		org.setOrgId(demId);
		org.setOrgSupId(Long.valueOf(0L));
		org.setPath(demId.toString());
		org.setDemId(demId);
		org.setOrgType(SysOrg.BEGIN_TYPE);
		org.setIsRoot(Short.valueOf("1"));
		org.setOrgName(orgName);
		return org;
	}

	@RequestMapping({ "getTreeData" })
	@ResponseBody
	public List<SysOrg> getTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request);
		Long currentUserId = this.getCurrentUser().getUserId(); // 当前登陆人userId
		Long demId = Long.valueOf(RequestUtil.getLong(request, "demId", 0L));
		Long orgId = Long.valueOf(RequestUtil.getLong(request, "orgId", 0L));
		Long orgSupId = Long.valueOf(RequestUtil.getLong(request, "orgSupId",
				0L));
		Long noDefault = Long.valueOf(RequestUtil.getLong(request, "noDefault",
				0L)); // 不设定默认值
		Long isCustomer = Long.valueOf(RequestUtil.getLong(request,
				"isCustomer", 0L)); // 是否为维护客户org
		String orgType = RequestUtil.getString(request, "type");

		String orgTypes = null;
		if (demId == 2L) { // 设置为项目维度
			orgType = "project";
			orgTypes = "8";
		}

		else {
			if (orgType.equals("org")) { // 客户
				// orgTypes="6";
				orgTypes = "6";
				// orgTypes="1,2,6";
			} else {
				if (orgType.equals("dept")) { // 选择部门
					orgTypes = "3";
					orgSupId = orgId;
				} else if (orgType.equals("serviceCenter")) { // center
					orgTypes = "1,2";
				} else if (orgType.equals("project")) { // center
					orgTypes = "8";
				} else if (orgType.equals("")) {// 为空时
					orgTypes = "1,2,3";

				}
				if (orgSupId > 0L) {
					filter.addFilter("orgSupId", orgSupId);
				} else {
					SysUser sysUser = ContextUtil.getCurrentUser();
					Long userOrgId = sysUser.getUserOrgId();
					if (userOrgId != null && userOrgId > 0L) {
						filter.addFilter("orgSupId", userOrgId);
					} else {
						filter.addFilter("orgSupId", null);
					}
				}
			}

		}

		filter.addFilter("demId", demId);
		filter.addFilter("orgType", orgTypes);
		List<Demension> demens = null;
		if (demId.longValue() != 0L) {
			demens = new ArrayList();
			demens.add(this.demensionService.getById(demId));
		} else {
			demens = this.demensionService.getAll();
		}
		List<SysOrg> orgList = this.sysOrgService.getOrgsByDemIdOrParam(filter
				.getFilters());
		for (Demension demension : demens) {
			if (demension.getDemId() == 2L) {// 项目
				SysUser user = (SysUser) request.getSession().getAttribute(
						"sysUser");
				orgList.add(getRootSysOrg(user.getUserOrgId(),
						user.getOrgName()));
			}
		}

		for (SysOrg sysOrg : orgList) {
			SysOrg charge = this.sysUserOrgService.getChageNameByOrgId(sysOrg
					.getOrgId());
			sysOrg.setOwnUser(charge.getOwnUser());
			sysOrg.setOwnUserName(charge.getOwnUserName());
		}
		return orgList;
	}

	@RequestMapping({ "getCustomerTreeData" })
	@ResponseBody
	/**
	 * 客户树
	 */
	public List<SysOrg> getCustomerTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request);
		SysUser user = this.getCurrentUser(); // 当前登陆人userId
		filter.addFilter("orgId", user.getUserOrgId());
		filter.setPageBean(null);
		List<SysOrg> orgList = this.sysOrgService.getCustomer(filter);
		SysOrg org = new SysOrg();

		org.setOrgSupId(Long.valueOf(0L));

		org.setOrgType(SysOrg.BEGIN_TYPE);
		org.setIsRoot(Short.valueOf("1"));
		org.setOrgName(user.getOrgName());
		orgList.add(org);
		return orgList;
	}

	@RequestMapping({ "getOrgTreeData" })
	@ResponseBody
	public List<SysOrg> getOrgTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new WebQueryFilter(request);
		Long currentUserId = this.getCurrentUser().getUserId(); // 当前登陆人userId
		Long demId = Long.valueOf(RequestUtil.getLong(request, "demId", 0L));
		String orgSupId = RequestUtil.getString(request, "orgSupId");
		String orgType = RequestUtil.getString(request, "type");
		String orgTypes = null;
		filter.addFilter("demId", demId);
		filter.addFilter("orgType", orgType);
		filter.addFilter("orgSupId", orgSupId);
		List<Demension> demens = null;
		if (demId.longValue() != 0L) {
			demens = new ArrayList();
			demens.add(this.demensionService.getById(demId));
		} else {
			demens = this.demensionService.getAll();
		}
		List<SysOrg> orgList = this.sysOrgService.getOrgsByDemIdOrParam(filter
				.getFilters());
		for (Demension demension : demens) {
			// orgList.add(getRootSysOrg(demension.getDemId(),
			// demension.getDemName()));
		}
		for (SysOrg sysOrg : orgList) {
			SysOrg charge = this.sysUserOrgService.getChageNameByOrgId(sysOrg
					.getOrgId());
			sysOrg.setOwnUser(charge.getOwnUser());
			sysOrg.setOwnUserName(charge.getOwnUserName());
		}
		return orgList;
	}

	@RequestMapping({ "getTreeOnlineData" })
	@ResponseBody
	public List<SysOrg> getTreeOnlineData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<Long, OnlineUser> onlineUsers = AppUtil.getOnlineUsers();
		List<OnlineUser> onlineList = new ArrayList<OnlineUser>();
		for (Long userId : onlineUsers.keySet()) {
			OnlineUser onlineUser = (OnlineUser) onlineUsers.get(userId);
			SysOrg sysOrg = this.sysOrgService.getPrimaryOrgByUserId(onlineUser
					.getUserId());
			if (sysOrg != null) {
				onlineUser.setOrgId(sysOrg.getOrgId());
				onlineUser.setOrgName(sysOrg.getOrgName());
			}
			onlineList.add(onlineUser);
		}
		String depTreeRootId = this.configproperties
				.getProperty("depTreeRootId");
		Long demId = Long.valueOf(RequestUtil.getLong(request, "demId", 0L));
		Demension demension = (Demension) this.demensionService.getById(demId);
		Map<Long, SysOrg> orgMap = this.sysOrgService.getOrgMapByDemId(demId);
		orgMap.put(Long.valueOf(0L), getRootSysOrg(Long.valueOf(-1L), "未分配组织"));
		for (OnlineUser onlineUser : onlineList) {
			Long onlineUserId = onlineUser.getOrgId();
			SysOrg sysOrg = (SysOrg) orgMap.get(onlineUserId);
			if (sysOrg != null) {
				int onlineNum = sysOrg.getOnlineNum().intValue();
				((SysOrg) orgMap.get(onlineUserId)).setOnlineNum(Integer
						.valueOf(onlineNum + 1));
			} else {
				((SysOrg) orgMap.get(Long.valueOf(0L))).setOnlineNum(Integer
						.valueOf(((SysOrg) orgMap.get(Long.valueOf(0L)))
								.getOnlineNum().intValue() + 1));
			}
		}
		List orgList = new ArrayList();

		for (SysOrg sysOrg : orgMap.values()) {
			String newName = sysOrg.getOrgName() + "(" + sysOrg.getOnlineNum()
					+ ")";
			sysOrg.setOrgName(newName);
			orgList.add(sysOrg);
		}

		if (!StringUtils.isEmpty(depTreeRootId)) {
			SysOrg org = getRootSysOrg(demId, "全部");
			if (demension != null)
				org.setOrgName(demension.getDemName());
			orgList.add(org);
		}
		return orgList;
	}

	@RequestMapping({ "move" })
	@Action(description = "转移分类")
	public void move(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage resultObj = null;
		PrintWriter out = response.getWriter();
		long targetId = RequestUtil.getLong(request, "targetId", 0L);
		long dragId = RequestUtil.getLong(request, "dragId", 0L);
		String moveType = RequestUtil.getString(request, "moveType");
		try {
			this.sysOrgService.move(Long.valueOf(targetId),
					Long.valueOf(dragId), moveType);
			resultObj = new ResultMessage(1, "转移分类完成");
		} catch (Exception ex) {
			resultObj = new ResultMessage(0, "转移分类失败!");
		}
		out.print(resultObj);
	}
}
