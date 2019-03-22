package com.github.eostermueller.havoc.workload.model;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.eostermueller.havoc.workload.DefaultFactory;
import com.github.eostermueller.havoc.workload.HavocException;

public class Descriptor {

	List<Message> messages = new CopyOnWriteArrayList<Message>();
	
	public Message getMessage(Locale locale) {
		String key = locale.toLanguageTag(); //ex: en-US
		return this.getMessage(key);
	}
	private Message getMessage(String key) {
		Message rc = null;
		for(Message m : this.getMessages() ) {
			if (m.getLocaleKey().equals(key))
				rc = m;
		}
		return rc;
	}
	private List<Message> getMessages() {
		return this.messages;
	}
	public void addMessage(Locale locale, String desc) throws HavocException {
		
		if (locale==null) {
			locale = DefaultFactory.getFactory().getDefaultLocale();
		}
		Message m = new Message();
		m.setLocale(locale);
		m.setMessage(desc);
		this.getMessages().add(m);
	}


}
