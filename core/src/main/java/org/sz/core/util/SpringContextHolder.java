package org.sz.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext contex)
			throws BeansException {
		applicationContext = contex;
	}

	public static ApplicationContext getContext() {
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static Object getBean(Class cls) {
		return applicationContext.getBean(cls);
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

}
