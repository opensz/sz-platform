package org.sz.platform.system.webservice.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.jws.WebService;

import org.sz.platform.system.model.ResourcesUrlExt;
import org.sz.platform.system.model.SysRole;
import org.sz.platform.system.service.SysRoleService;
import org.sz.platform.system.webservice.api.SystemResourcesService;

@WebService
public class SystemResourcesServiceImpl implements SystemResourcesService {

	@Resource
	SysRoleService sysRoleService;
	public static Map<String, Boolean> hadModifyUrl = new HashMap();
	public static Map<String, Boolean> hadModifyFun = new HashMap();
	public static Map<String, Boolean> hadModifyRole = new HashMap();

	public List<ResourcesUrlExt> loadSecurityUrl(String alias) {
		List urlList = this.sysRoleService.getSubSystemResources(alias);

		return urlList;
	}

	public List<ResourcesUrlExt> loadSecurityFunction(String alias) {
		List urlList = this.sysRoleService.getSubSystemFunction(alias);
		return urlList;
	}

	public List<SysRole> loadSecurityRole(String alias, String roleName) {
		return this.sysRoleService.loadSecurityRole(alias, roleName);
	}

	private static boolean isModify(Map<String, Boolean> hadModify, String alias) {
		if (hadModify.get(alias) == null)
			hadModify.put(alias, Boolean.valueOf(true));
		boolean hadMod = ((Boolean) hadModify.get(alias)).booleanValue();
		if (hadMod == true)
			hadModify.put(alias, Boolean.valueOf(false));
		return hadMod;
	}

	public static void reSetModify() {
		hadModifyUrl.clear();
		hadModifyFun.clear();
		hadModifyRole.clear();
	}

	public List<ResourcesUrlExt> checkSecurityFunction(String alias) {
		if (isModify(hadModifyFun, alias))
			return this.sysRoleService.getSubSystemFunction(alias);
		return null;
	}

	public List<ResourcesUrlExt> checkSecurityUrl(String alias) {
		if (isModify(hadModifyUrl, alias))
			return this.sysRoleService.getSubSystemResources(alias);
		return null;
	}
}
