package org.sz.core.web.listener;

import java.io.PrintStream;
import java.util.Map;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.sz.core.model.OnlineUser;
import org.sz.core.util.AppUtil;
//import org.sz.core.util.ContextUtil;
//import org.sz.platform.model.system.SysUser;

public class UserSessionListener implements HttpSessionAttributeListener {
	public void attributeAdded(HttpSessionBindingEvent event) {
		if ("SPRING_SECURITY_LAST_USERNAME".equals(event.getName())) {
//			SysUser user = ContextUtil.getCurrentUser();
//			Long userId = user.getUserId();
//			if (!AppUtil.getOnlineUsers().containsKey(userId)) {
//				OnlineUser onlineUser = new OnlineUser();
//
//				onlineUser.setUserId(user.getUserId());
//				onlineUser.setUsername(ContextUtil.getCurrentUser()
//						.getUsername());
//				onlineUser
//						.setOrgId(ContextUtil.getCurrentUser().getUserOrgId());
//				onlineUser
//						.setOrgName(ContextUtil.getCurrentUser().getOrgName());
//				AppUtil.getOnlineUsers().put(user.getUserId(), onlineUser);
//			}
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		if ("SPRING_SECURITY_LAST_USERNAME".equals(event.getName())) {
//			SysUser user = ContextUtil.getCurrentUser();
//
//			if (user != null)
//				AppUtil.getOnlineUsers().remove(user.getUserId());
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		System.out.println(event.getName());
	}
}
