package com.github.eostermueller.tjp.launcher.agent;

import com.github.eostermueller.tjp.launcher.agent.history.EventHistory;

public interface Factory {

	/**
	 * @param languageTag https://en.wikipedia.org/wiki/IETF_language_tag
	 * @return
	 */
	void setLocaleForMessages(String languageTag);

	Messages getMessages();
	
	/**
	 * 
	 * @return
	 */
	long getJvmLifetimeUniqueId();

	Configuration getConfiguration();
	
	EventHistory getEventHistory();
	

}