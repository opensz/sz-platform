package org.sz.platform.controller.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sz.core.annotion.Action;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.PinyinUtil;
import org.sz.core.web.ResultMessage;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.model.system.GlobalType;
import org.sz.platform.model.system.SysRole;
import org.sz.platform.model.system.SysTypeKey;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.service.system.GlobalTypeService;
import org.sz.platform.service.system.SysOrgService;
import org.sz.platform.service.system.SysRoleService;
import org.sz.platform.service.system.SysTypeKeyService;

@Controller
@RequestMapping({ "/platform/system/globalType/" })
public class GlobalTypeController extends BaseController {

	@Resource
	private GlobalTypeService globalTypeService;

	@Resource
	private SysTypeKeyService sysTypeKeyService;

	@Resource
	private SysRoleService sysRoleService;

	@Resource
	private SysOrgService sysOrgService;

	@RequestMapping({ "getPingyin" })
	@ResponseBody
	//@Action(description = "级联删除分类，即同时删除其所有子分类")
	public String getPingyin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String typeName = RequestUtil.getString(request, "typeName");
		String nodeKey = PinyinUtil.getFullSpell(typeName);
		return nodeKey;
	}

	@RequestMapping({ "getByParentId" })
	@ResponseBody
	public List<GlobalType> getByParentId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long parentId = Long.valueOf(RequestUtil.getLong(request, "parentId",
				-1L));
		Long catId = Long.valueOf(RequestUtil.getLong(request, "catId", 0L));
		List list = this.globalTypeService.getByParentId(parentId);

		if (catId.equals(parentId)) {
			SysTypeKey sysTypeKey = (SysTypeKey) this.sysTypeKeyService
					.getById(catId);
			GlobalType globalType = new GlobalType();
			globalType.setTypeName(sysTypeKey.getTypeName());
			globalType.setCatKey(sysTypeKey.getTypeKey());

			globalType.setTypeId(sysTypeKey.getTypeId());
			globalType.setParentId(Long.valueOf(0L));
			globalType.setType(sysTypeKey.getType());
			if (list.size() == 0) {
				globalType.setIsParent("false");
			} else {
				globalType.setIsParent("true");
				globalType.setOpen("true");
			}
			globalType.setNodePath(sysTypeKey.getTypeId() + ".");
			list.add(0, globalType);
		}

		return list;
	}

	@RequestMapping({ "tree" })
	public ModelAndView tree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = this.sysTypeKeyService.getAll();
		SysTypeKey sysTypeKey = (SysTypeKey) list.get(0);
		ModelAndView mv = getAutoView().addObject("typeList", list).addObject(
				"sysTypeKey", sysTypeKey);

		return mv;
	}

	@RequestMapping({ "del" })
	@Action(description = "删除系统分类")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ResultMessage message = null;
		try {
			Long typeId = Long.valueOf(RequestUtil.getLong(request, "typeId"));
			this.globalTypeService.delByTypeId(typeId);
			message = new ResultMessage(1, "删除系统分类成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除系统分类失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	@RequestMapping({ "get" })
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "typeId");
		GlobalType po = (GlobalType) this.globalTypeService.getById(Long
				.valueOf(id));
		return getAutoView().addObject("globalType", po);
	}

	@RequestMapping({ "sort" })
	@Action(description = "系统分类排序", operateType = "系统操作")
	public void sort(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage resultObj = null;
		PrintWriter out = response.getWriter();
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "typeIds");
		if (BeanUtils.isNotEmpty(lAryId)) {
			for (int i = 0; i < lAryId.length; i++) {
				Long typeId = lAryId[i];
				long sn = i + 1;
				this.globalTypeService.updSn(typeId, Long.valueOf(sn));
			}
		}
		resultObj = new ResultMessage(1, "排序分类完成");
		out.print(resultObj);
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
			this.globalTypeService.move(Long.valueOf(targetId),
					Long.valueOf(dragId), moveType);
			resultObj = new ResultMessage(1, "转移分类完成");
		} catch (Exception ex) {
			resultObj = new ResultMessage(0, "转移分类失败!");
		}
		out.print(resultObj);
	}

	@RequestMapping({ "edit" })
	@Action(description = "编辑系统分类")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		return getEditView(request);
	}

	@RequestMapping({ "dialog" })
	@Action(description = "编辑系统分类")
	public ModelAndView dialog(HttpServletRequest request) throws Exception {
		return getEditView(request);
	}

	private ModelAndView getEditView(HttpServletRequest request)
			throws Exception {
		int isRoot = RequestUtil.getInt(request, "isRoot", 0);
		Long parentId = Long.valueOf(RequestUtil.getLong(request, "parentId"));
		Long typeId = Long.valueOf(RequestUtil.getLong(request, "typeId"));
		int isPrivate = RequestUtil.getInt(request, "isPrivate", 0);

		String parentName = "";
		GlobalType globalType = null;
		boolean isDict = false;
		boolean isAdd = false;
		if (typeId.longValue() > 0L) {
			globalType = (GlobalType) this.globalTypeService.getById(typeId);
			parentId = globalType.getParentId();
			isDict = globalType.getCatKey().equals("DIC");
		} else {
			GlobalType tmpGlobalType = this.globalTypeService
					.getInitGlobalType(isRoot, parentId.longValue());
			parentName = tmpGlobalType.getTypeName();
			isDict = tmpGlobalType.getCatKey().equals("DIC");
			globalType = new GlobalType();
			globalType.setType(tmpGlobalType.getType());
			isAdd = true;
		}
		return getAutoView().addObject("globalType", globalType)
				.addObject("parentId", parentId)
				.addObject("isRoot", Integer.valueOf(isRoot))
				.addObject("isAdd", Boolean.valueOf(isAdd))
				.addObject("parentName", parentName)
				.addObject("isDict", Boolean.valueOf(isDict))
				.addObject("isPrivate", Integer.valueOf(isPrivate));
	}

	@RequestMapping({ "getByCatKey" })
	@ResponseBody
	public List<GlobalType> getByCatKey(HttpServletRequest request) {
		String catKey = RequestUtil.getString(request, "catKey");
		System.out.println("[catKey]:" + catKey);
		boolean hasRoot = RequestUtil.getInt(request, "hasRoot", 1) == 1;

		List list = this.globalTypeService.getByCatKey(catKey, hasRoot);
		if (list != null && list.size() > 0) {
			System.out.println("[catKey]:" + list.get(0));
		}
		return list;
	}

	@RequestMapping({ "getByCatKeyForBpm" })
	@ResponseBody
	public Set<GlobalType> getByCatKeyForBpm(HttpServletRequest request) {
		SysUser curUser = ContextUtil.getCurrentUser();

		String roleIds = this.sysRoleService.getRoleIdsByUserId(curUser
				.getUserId());

		String orgIds = this.sysOrgService.getOrgIdsByUserId(curUser
				.getUserId());
		Set globalTypeSet = null;

		if (!curUser.getAuthorities().contains(SysRole.ROLE_GRANT_SUPER)) {
			globalTypeSet = this.globalTypeService.getByBpmRightCat(
					curUser.getUserId(), roleIds, orgIds, true);
		} else {
			globalTypeSet = new HashSet();
			globalTypeSet.addAll(this.globalTypeService.getByCatKey(
					"FLOW_TYPE", true));
		}

		return globalTypeSet;
	}

	@RequestMapping({ "getByCatKeyForForm" })
	@ResponseBody
	public Set<GlobalType> getByCatKeyForForm(HttpServletRequest request) {
		SysUser curUser = ContextUtil.getCurrentUser();

		String roleIds = this.sysRoleService.getRoleIdsByUserId(curUser
				.getUserId());

		String orgIds = this.sysOrgService.getOrgIdsByUserId(curUser
				.getUserId());
		Set globalTypeSet = null;

		if (!curUser.getAuthorities().contains(SysRole.ROLE_GRANT_SUPER)) {
			globalTypeSet = this.globalTypeService.getByFormRightCat(
					curUser.getUserId(), roleIds, orgIds, true);
		} else {
			globalTypeSet = new HashSet();
			globalTypeSet.addAll(this.globalTypeService.getByCatKey(
					"FORM_TYPE", true));
		}
		return globalTypeSet;
	}

	@RequestMapping({ "getPersonType" })
	@ResponseBody
	public List<GlobalType> getPersonType(HttpServletRequest request) {
		String catKey = RequestUtil.getString(request, "catKey");
		boolean hasRoot = RequestUtil.getInt(request, "hasRoot", 1) == 1;
		Long userId = ContextUtil.getCurrentUserId();

		List list = this.globalTypeService.getPersonType(catKey, userId,
				hasRoot);
		return list;
	}
}
