package org.sz.platform.system.controller;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.sz.core.encrypt.EncryptUtil;
import org.sz.core.web.controller.BaseController;
import org.sz.core.web.servlet.ValidCode;
import org.sz.core.web.util.RequestUtil;
import org.sz.platform.system.model.SysUser;
import org.sz.platform.system.model.SysUserOrg;
import org.sz.platform.system.service.SubSystemService;
import org.sz.platform.system.service.SysUserOrgService;
import org.sz.platform.system.service.SysUserService;

@Controller
@RequestMapping({ "/login.xht" })
public class LoginController extends BaseController {

	@Resource
	private SysUserService sysUserService;

	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager = null;

	@Resource
	private SubSystemService subSystemService;

	@Resource
	private SysUserOrgService sysUserOrgService;

	@Resource
	private Properties configproperties;
	private String rememberPrivateKey = "szbpmPrivateKey";
	public static final String TRY_MAX_COUNT = "tryMaxCount";
	public static final int maxTryCount = 5;
	private String succeedUrl = "/platform/console/main.xht";

	@RequestMapping({ "*" })
	public void login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("username") String username,
			@RequestParam("password") String password) throws IOException {

		String sourceUrl = request.getHeader("Referer");

		String validCodeEnabled = this.configproperties
				.getProperty("validCodeEnabled");
		boolean error = false;
		try {
			if ((validCodeEnabled != null) && ("true".equals(validCodeEnabled))) {
				String validCode = (String) request.getSession().getAttribute(
						ValidCode.SessionName_Randcode);
				String code = request.getParameter("validCode");
				if ((validCode == null)
						|| (org.apache.commons.lang.StringUtils.isEmpty(code))
						|| (!validCode.equals(code))) {
					request.getSession().setAttribute(
							"SPRING_SECURITY_LAST_EXCEPTION", "验证码不正确！");
					error = true;
					return;
				}
			}
			if ((org.apache.commons.lang.StringUtils.isEmpty(username))
					|| (org.apache.commons.lang.StringUtils.isEmpty(password))) {
				request.getSession().setAttribute(
						"SPRING_SECURITY_LAST_EXCEPTION", "用户名密码为空!");
				error = true;
				return;
			}
			SysUser sysUser = this.sysUserService.getByAccount(username);

			String encrptPassword = EncryptUtil.encryptSha256(password);
			if ((sysUser == null)
					|| (!encrptPassword.equals(sysUser.getPassword()))) {
				request.getSession().setAttribute(
						"SPRING_SECURITY_LAST_EXCEPTION", "用户名密码输入错误!");
				error = true;
				return;
			}

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					username, password);
			SecurityContext securityContext = SecurityContextHolder
					.getContext();
			Authentication auth = this.authenticationManager
					.authenticate(authRequest);
			securityContext.setAuthentication(auth);

			request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME",
					username);
			request.getSession().removeAttribute(
					"SPRING_SECURITY_LAST_EXCEPTION");
			writeRememberMeCookie(request, response, username, encrptPassword);
		} catch (LockedException session) {
			request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION",
					username + ":用户被锁定!");
			error = true;

		} catch (DisabledException session) {
			request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION",
					username + ":用户被禁用!");
			error = true;

		} catch (AccountExpiredException session) {
			request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION",
					username + ":用户已过期!");
			error = true;

		} finally {
			HttpSession session;
			Integer tryCount;
			if (error == true) {
				session = request.getSession();
				tryCount = (Integer) session.getAttribute("tryMaxCount");
				if (tryCount == null) {
					session.setAttribute("tryMaxCount", Integer.valueOf(1));
				} else {
					if (tryCount.intValue() > 4)
						session.setAttribute("tryMaxCount",
								Integer.valueOf(tryCount.intValue() + 1));
				}
			}

			response.sendRedirect(request.getContextPath() + this.succeedUrl);

		}
	}

	private void writeRememberMeCookie(HttpServletRequest request,
			HttpServletResponse response, String username, String enPassword) {
		String rememberMe = request.getParameter("rememberMe");
		if ("1".equals(rememberMe)) {
			long tokenValiditySeconds = 1209600L;
			long tokenExpiryTime = System.currentTimeMillis()
					+ tokenValiditySeconds * 1000L;
			String signatureValue = DigestUtils.md5Hex(username + ":"
					+ tokenExpiryTime + ":" + enPassword + ":"
					+ this.rememberPrivateKey);
			String tokenValue = username + ":" + tokenExpiryTime + ":"
					+ signatureValue;
			String tokenValueBase64 = new String(Base64.encodeBase64(tokenValue
					.getBytes()));

			Cookie cookie = new Cookie("SPRING_SECURITY_REMEMBER_ME_COOKIE",
					tokenValueBase64);
			cookie.setMaxAge(157680000);
			cookie.setPath(org.springframework.util.StringUtils
					.hasLength(request.getContextPath()) ? request
					.getContextPath() : "/");

			response.addCookie(cookie);
		}
	}

	public static void main(String[] args) {
		String str = EncryptUtil.encryptSha256("1");
		System.out.println(str);
	}
}
