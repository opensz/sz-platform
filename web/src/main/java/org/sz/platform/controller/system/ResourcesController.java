package org.sz.platform.controller.system;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.query.WebQueryFilter;
import org.sz.core.web.util.AppUtil;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.model.system.Resources;
import org.sz.platform.service.system.ResourcesService;
import org.sz.platform.service.system.ResourcesUrlService;
import org.sz.platform.service.system.SubSystemService;

@Controller
@RequestMapping({ "/platform/system/resources/" })
public class ResourcesController extends BaseController {

	@Resource
	private ResourcesUrlService resourcesUrlService;

	@Resource
	private ResourcesService resourcesService;

	@Resource
	private SubSystemService subSystemService;

	@RequestMapping({ "tree" })
	public ModelAndView tree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		List subSystemList = this.subSystemService.getAll();
		Long currentSystemId = AppUtil.getCurrentSystemId(request);
		mv.addObject("subSystemList", subSystemList).addObject(
				"currentSystemId", currentSystemId);
		return mv;
	}

	@RequestMapping({ "list" })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Resources> list = this.resourcesService.getAll(new WebQueryFilter(
				request, "resourcesItem"));
		if ((list != null) && (list.size() > 0)) {
			for (Resources res : list) {
				res.setIcon(request.getContextPath() + res.getIcon());
			}
		}
		ModelAndView mv = getAutoView().addObject("resourcesList", list);
		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description="删除子系统资源")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "resId");
		this.resourcesService.delByIds(lAryId);
		String returnUrl = RequestUtil.getString(request, "returnUrl",
				RequestUtil.getPrePage(request));
		response.sendRedirect(returnUrl);
	}

	@RequestMapping({ "edit" })
	@Action(description="编辑子系统资源")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView();
		long systemId = RequestUtil.getLong(request, "systemId", 0L);
		long parentId = RequestUtil.getLong(request, "parentId", 0L);
		long resId = RequestUtil.getLong(request, "resId", 0L);

		String returnUrl = RequestUtil.getString(request, "returnUrl",
				RequestUtil.getPrePage(request));
		Resources resources = null;
		if (resId != 0L) {
			resources = (Resources) this.resourcesService.getById(Long
					.valueOf(resId));
			if ((resources != null) && (resources.getIsFolder() != null)
					&& (resources.getIsFolder().equals(Resources.IS_FOLDER_N))) {
				List resourcesUrlList = this.resourcesUrlService
						.getByResId(resources.getResId().longValue());
				mv.addObject("resourcesUrlList", resourcesUrlList);
			}
		} else {
			Resources parent = this.resourcesService
					.getParentResourcesByParentId(systemId, parentId,
							request.getContextPath());
			resources = new Resources();

			resources.setIsFolder(Resources.IS_FOLDER_N);
			resources.setIsDisplayInMenu(Resources.IS_DISPLAY_IN_MENU_Y);
			resources.setIsOpen(Resources.IS_OPEN_Y);

			resources.setSystemId(Long.valueOf(systemId));
			resources.setParentId(parent.getResId());
			resources.setSn(Integer.valueOf(1));
		}
		if (resources != null) {
			resources.setIcon(request.getContextPath() + resources.getIcon());
		}
		return mv.addObject("resources", resources).addObject("returnUrl",
				returnUrl);
	}

	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "resId");
		Resources resources = (Resources) this.resourcesService.getById(Long
				.valueOf(id));
		if (resources != null) {
			resources.setIcon(request.getContextPath() + resources.getIcon());
		}
		List resourcesUrlList = this.resourcesUrlService.getByResId(id);
		return getAutoView().addObject("resources", resources).addObject(
				"resourcesUrlList", resourcesUrlList);
	}

	@RequestMapping({ "getSystemTreeData" })
	@ResponseBody
	public List<Resources> getSystemTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long systemId = RequestUtil.getLong(request, "systemId", 0L);
		List list = this.resourcesService.getBySystemId(systemId,
				request.getContextPath());
		Resources parent = this.resourcesService.getParentResourcesByParentId(
				systemId, 0L, request.getContextPath());
		list.add(parent);
		return list;
	}

	@RequestMapping({ "getSysRolResTreeChecked" })
	@ResponseBody
	public List<Resources> getSysRolResTreeChecked(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long systemId = RequestUtil.getLong(request, "systemId", 0L);
		long roleId = RequestUtil.getLong(request, "roleId", 0L);

		List resourcesList = this.resourcesService.getBySysRolResChecked(
				Long.valueOf(systemId), Long.valueOf(roleId),
				request.getContextPath());

		Resources parent = this.resourcesService.getParentResourcesByParentId(
				systemId, 0L, request.getContextPath());
		resourcesList.add(parent);

		return resourcesList;
	}

	@RequestMapping({ "getChildTreeData" })
	@ResponseBody
	public List<Resources> getChildTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long systemId = RequestUtil.getLong(request, "systemId", 0L);
		long parentId = RequestUtil.getLong(request, "parentId", 0L);
		long resId = RequestUtil.getLong(request, "resId", 0L);

		List list = this.resourcesService.getChildByParentId(systemId, resId,
				request.getContextPath());
		if ((parentId == 0L) && (resId == 0L)) {
			Resources parent = this.resourcesService
					.getParentResourcesByParentId(systemId, parentId,
							request.getContextPath());
			list.add(parent);
		}
		return list;
	}

	@RequestMapping({ "/icons" })
	public ModelAndView icons(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		int iconType = RequestUtil.getInt(request, "iconType");
		String iconTypeStr = "/styles/default/images/resicon";
		if (iconType == 1)
			iconTypeStr = "/styles/default/images/logo";
		String iconPath = request.getSession().getServletContext()
				.getRealPath(iconTypeStr);
		String[] iconList = getIconList(request, iconPath);
		mv.addObject("iconList", iconList);
		mv.addObject("iconPath", request.getContextPath() + iconTypeStr);
		return mv;
	}

	@RequestMapping({ "/getExtendIcons" })
	public void getExtendIcons(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer iconsb = new StringBuffer("");
		Integer result = 1;
		try {
			String iconTypeStr = "/styles/default/images/extendIcon";
			String iconPath = request.getSession().getServletContext()
					.getRealPath(iconTypeStr);
			String[] iconList = getIconList(request, iconPath);
			if (iconList != null && iconList.length > 0) {
				for (String icon : iconList) {
					iconsb.append(icon.substring(0, icon.lastIndexOf(".")));
					iconsb.append(",");
				}
			}
		} catch (Exception exc) {
			result = 0;
			exc.printStackTrace();
		}

		StringBuffer sb = new StringBuffer("{");
		sb.append("\"result\":");
		sb.append(result);
		sb.append(",");
		sb.append("\"message\":");
		sb.append("\"");
		sb.append(iconsb.toString());
		sb.append("\"");
		sb.append("}");
		PrintWriter out = response.getWriter();
		out.print(sb.toString());
	}

	private String[] getIconList(HttpServletRequest request, String iconPath) {
		File iconFolder = new File(iconPath);
		String[] fileNameList = iconFolder.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String ext = name.substring(name.lastIndexOf(".") + 1)
						.toUpperCase();

				return "PNG|JPG|JPEG|GIF".contains(ext);
			}
		});
		return fileNameList;
	}

	@RequestMapping({ "sort" })
	@Action(description = "子系统资源排序", operateType = "系统操作")
	public void sort(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage resultObj = null;
		PrintWriter out = response.getWriter();

		Long[] lAryId = RequestUtil.getLongAryByStr(request, "resIds");
		if ((lAryId != null) && (lAryId.length > 0)) {
			for (int i = 0; i < lAryId.length; i++) {
				Resources orgPo = (Resources) this.resourcesService
						.getById(lAryId[i]);
				int sn = i + 1;
				orgPo.setSn(Integer.valueOf(sn));
				this.resourcesService.update(orgPo);
			}
		}

		resultObj = new ResultMessage(1, "排序分类完成");
		out.print(resultObj);
	}

	@RequestMapping({ "move" })
	@Action(description="转移子系统资源")
	public void move(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage resultObj = null;
		PrintWriter out = response.getWriter();
		long targetId = RequestUtil.getLong(request, "targetId", 0L);
		long systemId = RequestUtil.getLong(request, "systemId", 0L);
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "originalIds");
		if ((lAryId != null) && (lAryId.length > 0)) {
			Resources target = null;
			if (targetId != 0L)
				target = (Resources) this.resourcesService.getById(Long
						.valueOf(targetId));
			else {
				target = this.resourcesService.getParentResourcesByParentId(
						systemId, 0L, request.getContextPath());
			}

			for (int i = 0; i < lAryId.length; i++) {
				Resources orgPo = (Resources) this.resourcesService
						.getById(lAryId[i]);
				if (orgPo != null) {
					orgPo.setParentId(target.getResId());
					this.resourcesService.update(orgPo);
				}
			}
		}

		resultObj = new ResultMessage(1, "转移子系统资源完成");
		out.print(resultObj);
	}
}
