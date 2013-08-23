package org.sz.core.web.security;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.sz.core.encrypt.Base64;
import org.sz.core.encrypt.EncryptUtil;
import org.sz.core.util.StringUtil;
import org.sz.core.web.util.AppUtil;
import org.sz.platform.model.system.SubSystem;
import org.sz.platform.model.system.SysRole;
import org.sz.platform.model.system.SysUser;
import org.sz.platform.service.system.SecurityUtil;

public class SzDecisionManager implements AccessDecisionManager {
	public Logger logger = LoggerFactory.getLogger(SzDecisionManager.class);

	private static boolean isInit = false;

	private static int isValid = -3;

	@Resource
	private Properties configproperties;
	
	private int validLicense(){
		
		// invoke sz licence4j
		return 0;
	}

	private int validKey() {
		if (isInit) {
			return isValid;
		}
		isInit = true;
	
		isValid = validLicense();
		if(isValid!=-100){
			return isValid;
		}

		String productKey = (String) this.configproperties.get("productKey");

		if (StringUtil.isEmpty(productKey)) {
			isValid = -1;  //no eval key
			return -1;
		}
		try {
			if (isFirstEvaluate(productKey)) {
				isValid = 0;
				return 0;
			}
			
			productKey = Base64.getFromBase64(EncryptUtil.decrypt(productKey
					.trim()));
			
			String[] aryProductKey = productKey.split(",");
			if (aryProductKey[0].equals("1")) {
				isValid = 0;
				return 0;
			}
			Long startTime = Long.valueOf(Long.parseLong(aryProductKey[1]));
			Long stopTime = Long.valueOf(Long.parseLong(aryProductKey[2]));
			Long currentTime = Long.valueOf(System.currentTimeMillis());
			if ((currentTime.longValue() > startTime.longValue())
					&& (currentTime.longValue() < stopTime.longValue())) {
				isValid = 0;
				return 0;
			}

			isValid = -3;   //eval key is expired
			return -3;
		} catch (Exception ex) {
			isValid = -2;  //eval key is wrong
		}
		return -2;	
	}

	public boolean isFirstEvaluate(String productKey) {
		if (productKey.trim().equals("http://www.servicezon.com")) {
			this.logger.info("试用产品，首次使用，有效期为一个月。");
			String encryptKey = generateEvaluateKey();
			PrintWriter printer = null;
			try {
				ClassPathResource resource = new ClassPathResource(
						"/conf/bpm.properties");
				BufferedReader reader = new BufferedReader(new FileReader(
						resource.getFile()));

				StringBuffer strbuf = new StringBuffer();
				while (true) {
					String line = reader.readLine();
					if (line == null) {
						break;
					}
					if (line.startsWith("productKey")) {
						line = "productKey=" + encryptKey;
					}
					strbuf.append(line + "\n");
				}
				printer = new PrintWriter(resource.getFile());
				printer.write(strbuf.toString());
				this.configproperties.put("productKey", encryptKey);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (printer != null) {
					printer.close();
				}
			}
			return true;
		}
		return false;
	}

	public String generateEvaluateKey() {
		Long startTime = Long.valueOf(System.currentTimeMillis());

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		Long endTime = Long.valueOf(calendar.getTimeInMillis());
		String key = "0," + startTime + "," + endTime;
		try {
			key = EncryptUtil.encrypt(Base64.getBase64(key));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}

	private String getMessage(int i) {
		String msg = "临时许可无效";
		switch (i){
		case -1:
			msg = "无临时许可";
			break;
		case -2:
			msg = "临时许可错误";
			break;
		case -3:
			msg = "临时许可过期";
			break;
		  default:
		}
		return msg;
	}

	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes.contains(SysRole.ROLE_CONFIG_ANONYMOUS)) {
			return;
		}

		int rtn = validKey();
		if (rtn != 0) {
			String msg = getMessage(rtn);

			throw new AccessDeniedException(msg);
		}

		if (authentication == null) {
			throw new AccessDeniedException("没有登录系统");
		}

		Object principal = authentication.getPrincipal();
		if (principal == null) {
			throw new AccessDeniedException("登录对象为空");
		}

		if (!(principal instanceof SysUser)) {
			throw new AccessDeniedException("登录对象必须为SysUser");
		}

		SysUser user = (SysUser) principal;

		Collection<GrantedAuthority> roles = user.getAuthorities();

		String mes = "主系统 >> >  >\nURL:" + object + "\n当前用户拥有角色:" + roles
				+ "\n 当前URL被分配给以下角色:" + configAttributes;

		this.logger.debug(mes);

		if (roles.contains(SysRole.ROLE_GRANT_SUPER)) {
			return;
		}

		if (configAttributes.contains(SysRole.ROLE_CONFIG_PUBLIC)) {
			return;
		}

		SubSystem currentSys = AppUtil.getCurrentSystem(((FilterInvocation) object).getHttpRequest());
		Map systemRoleMap;
		if (currentSys != null) {
			Long systemId = Long.valueOf(currentSys.getSystemId());

			systemRoleMap = SecurityUtil.getSystemRoleMap();

			Set roleSet = (Set) systemRoleMap.get(systemId);
			boolean canAccessSystem = false;

			for (GrantedAuthority hadRole : roles) {
				if (roleSet.contains(hadRole.getAuthority())) {
					canAccessSystem = true;
					break;
				}
			}
			if (!canAccessSystem) {
				throw new AccessDeniedException("没有访问该系统的权限!");
			}

		}

		for (GrantedAuthority hadRole : roles) {
			if (configAttributes.contains(new SecurityConfig(hadRole
					.getAuthority()))) {
				return;
			}
		}

		throw new AccessDeniedException("对不起,你没有访问该页面的权限!");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
}
