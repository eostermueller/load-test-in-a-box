package com.github.eostermueller.havoc.workload.model;

import java.util.Locale;

public class Message {
	Locale locale;
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	String message;
	public String getLocaleKey() {
		return locale.toLanguageTag();
	}

}
