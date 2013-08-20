package org.sz.core.web.util;

import java.util.Locale;
import org.springframework.context.MessageSource;

import org.sz.core.util.SpringContextHolder;

public class ResourceUtil {
	public static String getText(String msgKey, Object arg, Locale local) {
		MessageSource messageSource = (MessageSource) SpringContextHolder
				.getBean(MessageSource.class);
		return messageSource.getMessage(msgKey, new Object[] { arg }, local);
	}

	public static String getText(String msgKey, Object[] args, Locale local) {
		MessageSource messageSource = (MessageSource) SpringContextHolder
				.getBean(MessageSource.class);
		return messageSource.getMessage(msgKey, args, local);
	}
}
