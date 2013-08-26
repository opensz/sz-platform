package org.sz.platform.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.encrypt.EncryptUtil;
import org.sz.core.model.OnlineUser;
import org.sz.core.query.QueryFilter;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.AppUtil;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.dao.UserPositionDao;
import org.sz.platform.system.model.SubSystem;
import org.sz.platform.system.model.SysOrg;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.model.SysUserOrg;
import org.sz.platform.system.service.DemensionService;
import org.sz.platform.system.service.SubSystemService;
import org.sz.platform.system.service.SysOrgService;
import org.sz.platform.system.service.SysUserOrgService;
import org.sz.platform.system.service.SysUserParamService;
import org.sz.platform.system.service.SysUserService;
import org.sz.platform.system.service.UserRoleService;

@Controller
@RequestMapping({ "/platform/system/sysUser/" })
public class SysUserController extends BaseController {

	@Resource
	protected SysUserService sysUserService;

	@Resource
	protected DemensionService demensionService;

	@Resource
	protected SubSystemService subSystemService;

	@Resource
	protected SysUserParamService sysUserParamService;

	@Resource
	protected SysUserOrgService sysUserOrgService;

	@Resource
	protected UserRoleService userRoleService;

	@Resource
	protected UserPositionDao userPositionDao;

	@Resource
	protected SysOrgService sysOrgService;

	protected final String defaultUserImage = "commons/image/default_image_male.jpg";

	@RequestMapping({ "list" })
	@Action(description = "查看用户表分页列表", operateType = "用户操作")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long userId = Long.valueOf(RequestUtil.getLong(request, "userId"));
		QueryFilter filter = new WebQueryFilter(request, "sysUserItem");

		SysUser sysUser = ContextUtil.getCurrentUser();
		Long userOrgId = sysUser.getUserOrgId();
		if (userOrgId != null && userOrgId > 0L) {
			filter.addFilter("path", userOrgId);
		} else {
			filter.addFilter("path", null);
		}

		List listDept = sysOrgService.getOrgsByDemIdOrAll(1l, "3");
		List list = this.sysUserService.getUserByQuery(filter);
		ModelAndView mv = getAutoView().addObject("sysUserList", list)
				.addObject("userId", userId);
		mv.addObject("params", filter.getFilters());
		mv.addObject("listDept", listDept);
		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除用户表")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "userId");
			// String ids=lAryId.

			SubSystem currentSystem = (SubSystem) request.getSession()
					.getAttribute("CURRENT_SYSTEM");
			Long systemId = 0l;
			if (currentSystem != null) {
				systemId = currentSystem.getSystemId();
			}
			this.sysUserService.updStatus(lAryId, systemId);// 删除用户修改 by maoyang

			// this.sysUserService.delDcomUserAndShift(lAryId,currentSystem.getSystemId());
			// this.sycnUserService.deleteUserToItsm(ids);
			message = new ResultMessage(1, "删除用户成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除用户失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑用户表")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView();
		String returnUrl = RequestUtil.getPrePage(request);
		Long currentUserId = this.getCurrentUser().getUserId(); // 当前登陆人userId
		Long userId = Long.valueOf(RequestUtil.getLong(request, "userId"));
		int bySelf = RequestUtil.getInt(request, "bySelf");
		Long orgId = this.getCurrentUser().getUserOrgId();
		Long deptId = null;
		SysUser sysUser = null;
		List deptList = new ArrayList();
		List orgList = new ArrayList();
		List roleList = null;
		List posList = null;
		if (userId.longValue() != 0L) {
			sysUser = (SysUser) this.sysUserService.getById(userId);
			roleList = this.userRoleService.getByUserId(userId);
			posList = this.userPositionDao.getByUserId(userId);

			List<SysUserOrg> list = this.sysUserOrgService
					.getOrgByUserId(userId);
			// 客户id
			for (SysUserOrg sysUserOrg : list) {
				// if (sysUserOrg.getIsDept() != null && sysUserOrg.getIsDept()
				// == 1) {
				// deptList.add(sysUserOrg);
				// } else {
				orgList.add(sysUserOrg);
				// }

			}
		} else if (userId == 0L) {
			sysUser = (SysUser) this.sysUserService.getById(currentUserId);
			// roleList = this.userRoleService.getByUserId(currentUserId);
			// posList = this.userPositionDao.getByUserId(currentUserId);

			List<SysUserOrg> list = this.sysUserOrgService
					.getOrgByUserId(currentUserId);
			// 客户id
			for (SysUserOrg sysUserOrg : list) {
				orgList.add(sysUserOrg);
			}

			sysUser = new SysUser();

		}
		String pictureLoad = "commons/image/default_image_male.jpg";
		if ((sysUser != null) && (StringUtil.isNotEmpty(sysUser.getPicture()))) {
			pictureLoad = sysUser.getPicture();
		}
		Map map = new HashMap();
		map.put("enabled", true);

		mv.addObject("roleList", roleList).addObject("posList", posList)
				.addObject("orgList", orgList).addObject("deptList", deptList);

		mv.addObject("orgId", orgId);
		// mv.addObject("currentUserId",currentUserId);
		return mv.addObject("sysUser", sysUser).addObject("userId", userId)
				.addObject("returnUrl", returnUrl)
				.addObject("pictureLoad", pictureLoad)
				.addObject("bySelf", Integer.valueOf(bySelf));
	}

	@RequestMapping({ "modifyPwdView" })
	@Action(description = "修改密码")
	public ModelAndView modifyPwdView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long userId = RequestUtil.getLong(request, "userId");
		SysUser sysUser = (SysUser) this.sysUserService.getById(Long
				.valueOf(userId));
		return getAutoView().addObject("sysUser", sysUser).addObject("userId",
				Long.valueOf(userId));
	}

	@RequestMapping({ "modifyPwd" })
	@Action(description = "修改密码")
	public void modifyPwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String primitivePassword = RequestUtil.getString(request,
				"primitivePassword");
		String enPassword = EncryptUtil.encryptSha256(primitivePassword);
		String newPassword = RequestUtil.getString(request, "newPassword");
		Long userId = Long.valueOf(RequestUtil.getLong(request, "userId"));
		SysUser sysUser = (SysUser) this.sysUserService.getById(userId);
		String password = sysUser.getPassword();
		if ((StringUtil.isEmpty(newPassword))
				|| (StringUtil.isEmpty(primitivePassword))) {
			writeResultMessage(response.getWriter(), "输入的密码不能为空", 0);
		} else if (!enPassword.equals(password)) {
			writeResultMessage(response.getWriter(), "你输入的原始密码不正确", 0);
		} else if (primitivePassword.equals(newPassword))
			writeResultMessage(response.getWriter(), "你修改的密码和原始密码相同", 0);
		else
			try {
				this.sysUserService.updPwd(userId, newPassword);
				writeResultMessage(response.getWriter(), "修改密码成功", 1);
			} catch (Exception ex) {
				writeResultMessage(response.getWriter(), "修改密码失败!", 0);
			}
	}

	@RequestMapping({ "resetPwdView" })
	@Action(description = "重置密码")
	public ModelAndView resetPwdView(HttpServletRequest request)
			throws Exception {
		String returnUrl = RequestUtil.getPrePage(request);
		Long userId = Long.valueOf(RequestUtil.getLong(request, "userId"));
		SysUser sysUser = (SysUser) this.sysUserService.getById(userId);
		return getAutoView().addObject("sysUser", sysUser)
				.addObject("userId", userId).addObject("returnUrl", returnUrl);
	}

	@RequestMapping({ "resetPwd" })
	@Action(description = "重置密码")
	public void resetPwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String password = RequestUtil.getString(request, "password");
		Long userId = Long.valueOf(RequestUtil.getLong(request, "userId"));
		try {
			this.sysUserService.updPwd(userId, password);
			writeResultMessage(response.getWriter(), "重置密码成功!", 1);
		} catch (Exception ex) {
			writeResultMessage(response.getWriter(), "重置密码失败!", 0);
		}
	}

	@RequestMapping({ "editStatusView" })
	@Action(description = "设置用户状态")
	public ModelAndView editStatusView(HttpServletRequest request)
			throws Exception {
		String returnUrl = RequestUtil.getPrePage(request);
		Long userId = Long.valueOf(RequestUtil.getLong(request, "userId"));
		SysUser sysUser = (SysUser) this.sysUserService.getById(userId);
		return getAutoView().addObject("sysUser", sysUser)
				.addObject("userId", userId).addObject("returnUrl", returnUrl);
	}

	@RequestMapping({ "editStatus" })
	@Action(description = "设置用户状态")
	public void editStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long userId = Long.valueOf(RequestUtil.getLong(request, "userId"));
		int isLock = RequestUtil.getInt(request, "isLock");
		int status = RequestUtil.getInt(request, "status");
		try {
			this.sysUserService.updStatus(userId,
					Short.valueOf((short) status),
					Short.valueOf((short) isLock));
			writeResultMessage(response.getWriter(), "修改用户状态成功!", 1);
		} catch (Exception ex) {
			writeResultMessage(response.getWriter(), "修改用户状态失败!", 0);
		}
	}

	@RequestMapping({ "get" })
	@Action(description = "查看用户表明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long userId = RequestUtil.getLong(request, "userId");
		String canReturn = RequestUtil.getString(request, "canReturn");
		SysUser sysUser = (SysUser) this.sysUserService.getById(Long
				.valueOf(userId));
		String pictureLoad = "commons/image/default_image_male.jpg";
		if ((sysUser != null) && (StringUtil.isNotEmpty(sysUser.getPicture()))) {
			pictureLoad = sysUser.getPicture();
		}

		List roleList = this.userRoleService.getByUserId(Long.valueOf(userId));
		List posList = this.userPositionDao.getByUserId(Long.valueOf(userId));
		List orgList = this.sysUserOrgService.getOrgByUserId(Long
				.valueOf(userId));

		List userParamList = this.sysUserParamService.getByUserId(userId);

		return getAutoView().addObject("sysUser", sysUser)
				.addObject("roleList", roleList).addObject("posList", posList)
				.addObject("orgList", orgList)
				.addObject("pictureLoad", pictureLoad)
				.addObject("userParamList", userParamList)
				.addObject("canReturn", canReturn);
	}

	// 人员选择组件后台方法
	@RequestMapping({ "dialog" })
	public ModelAndView dialog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		SysUser user = this.getCurrentUser(); // 当前登陆人userId
		List demensionList = this.demensionService.getAll();
		mv.addObject("demensionList", demensionList);
		List subSystemList = this.subSystemService.getAll();
		mv.addObject("subSystemList", subSystemList);

		String isSingle = RequestUtil.getString(request, "isSingle", "false");
		mv.addObject("isSingle", isSingle);
		return mv;
	}

	@RequestMapping({ "selector" })
	public ModelAndView selector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = null;
		ModelAndView result = getAutoView();
		String searchBy = RequestUtil.getString(request, "searchBy");
		String orgId = RequestUtil.getString(request, "orgId");
		SysOrg sysOrg = null;
		if (orgId != null && !orgId.equals("")) {
			sysOrg = this.sysOrgService.getById(Long.valueOf(orgId));
		}

		QueryFilter filter = new WebQueryFilter(request, "sysUserItem");
		if ("4".equals(searchBy)) {

			String demId = RequestUtil.getString(request, "path");

			if (demId.equals("-1"))
				list = this.sysUserService.getUserNoOrg(filter);
			else {
				list = this.sysUserService.getDistinctUserByOrgPath(filter);
			}
			list = this.sysUserService.getOnlineUser(list);
		} else if ("2".equals(searchBy)) {// 根据org 客户查询

			if (sysOrg != null) {
				filter.addFilter("path", sysOrg.getPath());
			}
			if (filter.getFilters().get("path") != null
					&& !filter.getFilters().get("path").equals("")) {

				list = this.sysUserService.getDistinctUserByOrgPath(filter);
			} else {
				list = this.sysUserService.getUserByQuery(filter);
			}

		} else if ("3".equals(searchBy)) {// 根据岗位搜索
			if (sysOrg != null) {
				filter.addFilter("nodePath", sysOrg.getPath());
			}
			list = this.sysUserService.getDistinctUserByPosPath(filter);
		} else if ("1".equals(searchBy)) {
			list = this.sysUserService.getUserByRoleId(filter);
		} else {
			list = this.sysUserService.getUserByQuery(filter);
		}
		result.addObject("sysUserList", list);

		String isSingle = RequestUtil.getString(request, "isSingle", "false");
		result.addObject("isSingle", isSingle);
		result.addObject("params", filter.getFilters());
		result.addObject("orgId", orgId);
		result.addObject("searchBy", searchBy);
		return result;
	}

	@RequestMapping({ "getTreeData" })
	@ResponseBody
	public List<OnlineUser> getTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<Long, OnlineUser> onlineUsers = AppUtil.getOnlineUsers();
		List onlineList = new ArrayList();
		for (Long sessionId : onlineUsers.keySet()) {
			OnlineUser onlineUser = new OnlineUser();
			onlineUser = (OnlineUser) onlineUsers.get(sessionId);
			onlineList.add(onlineUser);
		}
		return onlineList;
	}
}
