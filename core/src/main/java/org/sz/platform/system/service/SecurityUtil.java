package org.sz.platform.system.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.sz.core.cache.ICache;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.ContextUtil;
import org.sz.core.util.StringUtil;
import org.sz.platform.system.model.ResourcesUrlExt;
import org.sz.platform.system.model.SubSystem;
import org.sz.platform.system.model.SysRole;

public class SecurityUtil {
	private static Map<Long, Map<String, Collection<ConfigAttribute>>> urlRoleMap = null;

	private static Map<Long, Map<String, Set<String>>> urlParaMap = null;

	private static Map<Long, Set<String>> systemRoleMap = null;

	private static Map<Long, Map<String, Collection<ConfigAttribute>>> functionRoleMap = null;

	public static Map<Long, Map<String, Set<String>>> getUrlParaMap() {
		return urlParaMap;
	}

	public static Map<Long, Map<String, Collection<ConfigAttribute>>> getUrlRoleMap() {
		return urlRoleMap;
	}

	public static Map<Long, Set<String>> getSystemRoleMap() {
		return systemRoleMap;
	}

	public static Map<Long, Map<String, Collection<ConfigAttribute>>> getFunctionRoleMap() {
		return functionRoleMap;
	}

	public static void loadRes(SysRoleService sysRoleService,
			SubSystemService subSystemService) {
		urlRoleMap = new HashMap();
		urlParaMap = new HashMap();
		systemRoleMap = new HashMap();
		functionRoleMap = new HashMap();

		List<SubSystem> sysList = subSystemService.getLocalSystem();
		if ((sysList == null) || (sysList.size() == 0))
			return;

		for (SubSystem sys : sysList) {
			Long systemId = Long.valueOf(sys.getSystemId());
			loadRes(sysRoleService, subSystemService, systemId);
		}
	}

	private static void putFuncRoleList(Long systemId,
			List<ResourcesUrlExt> funcRoleList) {
		Map functionRole = getResources(funcRoleList);
		if ((functionRole != null) && (functionRole.size() > 0))
			functionRoleMap.put(systemId, functionRole);
	}

	private static Map<String, Collection<ConfigAttribute>> getResources(
			List<ResourcesUrlExt> funcRoleList) {
		if (BeanUtils.isEmpty(funcRoleList))
			return null;
		Map functionRole = new HashMap();
		for (ResourcesUrlExt table : funcRoleList) {
			String function = table.getFunction();
			String role = table.getRole();
			if (StringUtil.isEmpty(function)) {
				continue;
			}
			function = function.trim();
			if (functionRole.containsKey(function)) {
				if (StringUtil.isNotEmpty(role))
					((Collection) functionRole.get(function))
							.add(new SecurityConfig(role));
			} else {
				Collection collectoin = new HashSet();
				if (StringUtil.isNotEmpty(role))
					collectoin.add(new SecurityConfig(role));
				functionRole.put(function, collectoin);
			}
		}
		return functionRole;
	}

	public static void loadRes(SysRoleService sysRoleService,
			SubSystemService subSystemService, Long systemId) {
		List urlList = sysRoleService.getUrlRightMap(systemId);
		List listRole = sysRoleService.getBySystemId(systemId);
		List funcRoleList = sysRoleService.getFunctionRoleList(systemId);
		putResources(systemId.longValue(), urlList);
		putSystemRole(systemId, listRole);
		putFuncRoleList(systemId, funcRoleList);
	}

	private static void putResources(long systemId,
			List<ResourcesUrlExt> urlList) {
		if (urlRoleMap.containsKey(Long.valueOf(systemId)))
			urlRoleMap.remove(Long.valueOf(systemId));
		if (urlParaMap.containsKey(Long.valueOf(systemId))) {
			urlParaMap.remove(Long.valueOf(systemId));
		}

		if (BeanUtils.isEmpty(urlList))
			return;

		Map urlRole = new HashMap();

		Map urlPara = new HashMap();

		for (ResourcesUrlExt resource : urlList)
			if (resource != null) {
				String fullUrl = resource.getUrl();
				String role = resource.getRole();

				if (!StringUtil.isEmpty(fullUrl)) {
					fullUrl = fullUrl.trim();

					String parameter = "";
					String url = fullUrl;

					if (fullUrl.indexOf("?") > -1) {
						String[] aryUrl = fullUrl.split("\\?");
						url = aryUrl[0];
						parameter = aryUrl[1];
					}

					if (urlPara.containsKey(url)) {
						Set paramList = (Set) urlPara.get(url);
						paramList.add(parameter);
					} else {
						Set paramList = new HashSet();
						paramList.add(parameter);
						urlPara.put(url, paramList);
					}

					if (urlRole.containsKey(fullUrl)) {
						Collection roleList = (Collection) urlRole.get(fullUrl);
						if (StringUtil.isNotEmpty(role))
							roleList.add(new SecurityConfig(role));
					} else {
						Collection collectoin = new HashSet();
						if (StringUtil.isNotEmpty(role))
							collectoin.add(new SecurityConfig(role));
						urlRole.put(fullUrl, collectoin);
					}
				}
			}
		if (BeanUtils.isNotEmpty(urlRole))
			urlRoleMap.put(Long.valueOf(systemId), urlRole);
		if (BeanUtils.isNotEmpty(urlPara))
			urlParaMap.put(Long.valueOf(systemId), urlPara);
	}

	private static void putSystemRole(Long systemId, List<SysRole> listRole) {
		if (systemRoleMap.containsKey(systemId)) {
			systemRoleMap.remove(systemId);
		}
		Set roleSet = new HashSet();
		for (SysRole role : listRole) {
			roleSet.add(role.getAlias());
		}
		systemRoleMap.put(systemId, roleSet);
	}

	public static void removeUserRoleCache(Long userId) {
		ICache cache = (ICache) ContextUtil.getBean(ICache.class);
		Map userRoleMap = (Map) cache.getByKey("UserRole");
		if (userRoleMap == null)
			return;
		userRoleMap.remove(userId);
	}

	public static void removeUserRoleCache() {
		ICache cache = (ICache) ContextUtil.getBean(ICache.class);
		Map userRoleMap = (Map) cache.getByKey("UserRole");
		if (userRoleMap == null)
			return;
		cache.delByKey("UserRole");
	}
}
