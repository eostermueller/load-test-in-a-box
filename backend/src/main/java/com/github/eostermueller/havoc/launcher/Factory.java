package com.github.eostermueller.havoc.launcher;

import java.io.File;

import com.github.eostermueller.havoc.PerfGoatInstaller;
import com.github.eostermueller.havoc.processmodel.ProcessModelBuilder;

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

	Configuration getConfiguration(File folder);

	ConfigReaderWriter getConfigReaderWriter(Configuration cfg, File tmpFolder);

	ProcessModelBuilder getProcessModelBuilder();

	PerfGoatInstaller createNewInstaller(Configuration cfg);

	ConfigLookup createConfigLookup();

	CommandLine createNewCommandLine(String val);
	

}