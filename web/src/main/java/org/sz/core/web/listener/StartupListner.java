package org.sz.core.web.listener;

import javax.servlet.ServletContextEvent;
import org.springframework.web.context.ContextLoaderListener;
import org.sz.core.util.SpringContextHolder;

public class StartupListner extends ContextLoaderListener {
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		// AppUtil.init(event.getServletContext());
	}
}
