package org.sz.core.web.listener;

import org.sz.core.util.AppUtil;
import org.sz.core.web.listener.ServerListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sz.core.sms.impl.ModemMessage;

//import org.sz.platform.service.form.BpmFormTemplateService;

public class ServerListener implements ServletContextListener {
	private Log logger = LogFactory.getLog(ServerListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		ModemMessage.getInstance().stopService();
		this.logger.debug("[contextDestroyed]停止短信猫服务。");
	}

	public void contextInitialized(ServletContextEvent event) {
		this.logger.debug("[contextInitialized]开始初始化表单模版。");

		// BpmFormTemplateService service =
		// (BpmFormTemplateService)AppUtil.getBean(BpmFormTemplateService.class);
		// try {
		// service.init();
		// } catch (Exception e) {
		// logger.debug(e.getMessage());
		// }

		this.logger.debug("[contextInitialized]初始化表单模版成功。");
	}
}
