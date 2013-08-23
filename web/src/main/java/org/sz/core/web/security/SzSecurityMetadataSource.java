package org.sz.core.web.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.sz.core.util.StringUtil;
import org.sz.core.web.util.AppUtil;
import org.sz.platform.model.system.SysRole;
import org.sz.platform.service.system.SecurityUtil;
import org.sz.platform.service.system.SubSystemService;
import org.sz.platform.service.system.SysRoleService;

public class SzSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource, BeanPostProcessor {
	private SysRoleService sysRoleService;
	private SubSystemService subSystemService;
	private HashSet<String> anonymousUrls = new HashSet();

	boolean isInit = false;

	public void setAnonymousUrls(HashSet<String> anonymousUrls) {
		this.anonymousUrls = anonymousUrls;
	}

	public void loadRes(Long systemId) {
		SecurityUtil.loadRes(this.sysRoleService, this.subSystemService,
				systemId);
	}

	public void loadRes() {
		SecurityUtil.loadRes(this.sysRoleService, this.subSystemService);
	}

	private String getUrl(String url, Set<String> params, String queryString) {
		boolean hasEmpty = false;

		for (String parameter : params) {
			if (StringUtil.isEmpty(parameter)) {
				hasEmpty = true;
			}
		}
		if (StringUtil.isEmpty(queryString)) {
			if (hasEmpty) {
				return url;
			}
			return "";
		}

		for (String parameter : params)
			if (!StringUtil.isEmpty(parameter)) {
				Set set = getParamsSet(parameter);
				Set queryStringSet = getParamsSet(queryString);

				if (queryStringSet.containsAll(set)) {
					url = url + "?" + parameter;
					return url;
				}
			}
		if (hasEmpty)
			return url;
		return "";
	}

	private Set<String> getParamsSet(String parameter) {
		Set set = new HashSet();
		String[] aryPara = parameter.split("&");
		for (String para : aryPara) {
			if (para.indexOf("=") > -1)
				set.add(para);
		}
		return set;
	}

	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		Collection configAttribute = new HashSet();

		FilterInvocation filterInvocation = (FilterInvocation) object;
		HttpServletRequest request = filterInvocation.getRequest();

		String url = request.getRequestURI();
		url = removeCtx(url, request.getContextPath());

		if (this.anonymousUrls.contains(url)) {
			configAttribute.add(SysRole.ROLE_CONFIG_ANONYMOUS);
			return configAttribute;
		}

		String queryString = request.getQueryString();

		Long systemId = AppUtil.getCurrentSystemId(request);

		if (systemId == null) {
			configAttribute.add(SysRole.ROLE_CONFIG_PUBLIC);
			return configAttribute;
		}

		Map roleMap = (Map) SecurityUtil.getUrlRoleMap().get(systemId);
		Map paraMap = (Map) SecurityUtil.getUrlParaMap().get(systemId);

		if ((roleMap == null) || (paraMap == null)) {
			configAttribute.add(SysRole.ROLE_CONFIG_PUBLIC);
			return configAttribute;
		}

		if (!paraMap.containsKey(url)) {
			configAttribute.add(SysRole.ROLE_CONFIG_PUBLIC);
			return configAttribute;
		}

		Set params = (Set) paraMap.get(url);

		String urlParams = getUrl(url, params, queryString);

		if ("".equals(urlParams)) {
			configAttribute.add(SysRole.ROLE_CONFIG_PUBLIC);
		} else {
			configAttribute = (Collection) roleMap.get(urlParams);
		}
		return configAttribute;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	private static String removeCtx(String url, String contextPath) {
		url = url.trim();
		if (StringUtil.isEmpty(contextPath))
			return url;
		if (StringUtil.isEmpty(url))
			return "";
		if (url.startsWith(contextPath)) {
			url = url.replaceFirst(contextPath, "");
		}
		return url;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if ((bean instanceof SysRoleService)) {
			this.sysRoleService = ((SysRoleService) bean);
		}
		if ((bean instanceof SubSystemService)) {
			this.subSystemService = ((SubSystemService) bean);
		}

		if ((this.sysRoleService != null) && (this.subSystemService != null)
				&& (!this.isInit)) {
			loadRes();
			this.isInit = true;
		}

		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}
}
