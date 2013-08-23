package org.sz.core.web.security;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.sz.core.encrypt.EncryptUtil;

public class SzPreAuthenticatedProcessingFilter extends
		AbstractPreAuthenticatedProcessingFilter {

	protected static Log log = LogFactory
			.getLog(SzPreAuthenticatedProcessingFilter.class);

	protected String usernameName = "username";
	protected String authcodeName = "authcode";
	protected String parterIdName = "parterId";

	private static Properties configproperties = null;
	
	@Autowired
	public void setConfigproperties(Properties configproperties){
		SzPreAuthenticatedProcessingFilter.configproperties = configproperties;
	}

	public SzPreAuthenticatedProcessingFilter() {
		super();
		// setCheckForPrincipalChanges(true); //是否需要检查用户已更改
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {

		String parterId = request.getParameter(parterIdName);
		String username = request.getParameter(usernameName);
		String authcode = request.getParameter(authcodeName);

		if (validatePreAuth(parterId, username, authcode, request)) {
			return username;
		}
		return null;
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "itsm";
	}

	protected static boolean validatePreAuth(String partnerId, String username,
			String authcode, HttpServletRequest request) {
		if (partnerId == null || username == null) {
			return false;
		}
		String parterEncrypt = getPartnerEncrypt(partnerId.trim());
		String encryptCode = null;
		try {
			encryptCode = EncryptUtil.encryptMd5(username.trim()
					+ parterEncrypt);
		} catch (Exception e) {
		}
		if (encryptCode.equals(authcode)) {
			System.out.print("partnerId:" + partnerId + "   username:"
					+ username);
			return true;
		}
		return false;
	}

	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed) {
		super.unsuccessfulAuthentication(request, response, failed);
	}

	protected static String getPartnerEncrypt(String partnerId) {

		String parterEncrypt = null;

		// 1. 读取合作伙伴验证码
		if (partnerId != null) {

			parterEncrypt = configproperties.getProperty("partner."
					+ partnerId.trim() + ".encrypt");
		}

		// 2. 默认合作伙伴验证码
		if (parterEncrypt == null) {
			parterEncrypt = "szpatform";
		}
		return parterEncrypt;
	}

	public String getUsernameName() {
		return usernameName;
	}

	public void setUsernameName(String usernameName) {
		this.usernameName = usernameName;
	}

	public String getAuthcodeName() {
		return authcodeName;
	}

	public void setAuthcodeName(String authcodeName) {
		this.authcodeName = authcodeName;
	}

	public String getParterIdName() {
		return parterIdName;
	}

	public void setParterIdName(String parterIdName) {
		this.parterIdName = parterIdName;
	}

}
