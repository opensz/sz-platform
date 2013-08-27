package org.sz.core.engine;

import org.springframework.mail.MailSender;

import org.sz.core.engine.FreemarkEngine;

public class MailEngine {
	public String defaultFrom;
	public MailSender mailSender;
	FreemarkEngine freemarkEngine;

	public String getDefaultFrom() {
		return this.defaultFrom;
	}

	public void setDefaultFrom(String defaultFrom) {
		this.defaultFrom = defaultFrom;
	}

	public MailSender getMailSender() {
		return this.mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public FreemarkEngine getFreemarkEngine() {
		return this.freemarkEngine;
	}

	public void setFreemarkEngine(FreemarkEngine freemarkEngine) {
		this.freemarkEngine = freemarkEngine;
	}
}
