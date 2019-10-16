package com.github.eostermueller.snail4j.launcher;

import java.io.File;

import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.Snail4jInstaller;
import com.github.eostermueller.snail4j.processmodel.LoadGenerator;
import com.github.eostermueller.snail4j.processmodel.ProcessModelBuilder;
import com.github.eostermueller.snail4j.processmodel.SystemUnderTest;

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

	ProcessModelBuilder createProcessModelBuilder();

	Snail4jInstaller createNewInstaller(Configuration cfg);

	ConfigLookup createConfigLookup();

	CommandLine createNewCommandLine(String val);

	SystemUnderTest createSystemUnderTest() throws Snail4jException;

	LoadGenerator createLoadGenerator() throws Snail4jException;

	Messages createMessages();
	

}