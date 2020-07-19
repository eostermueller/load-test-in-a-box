package com.github.eostermueller.snail4j.launcher;


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

	EventHistory getEventHistory();

	Configuration getConfiguration() throws Snail4jException;
	void setConfiguration(Configuration cfg) throws Snail4jException;

	ProcessModelBuilder createProcessModelBuilder() throws Snail4jException;

	Snail4jInstaller createNewInstaller() throws CannotFindSnail4jFactoryClass;

	ConfigLookup createConfigLookup() throws Snail4jException;

	CommandLine createNewCommandLine(String val) throws Snail4jException;

	SystemUnderTest createSystemUnderTest() throws Snail4jException;

	LoadGenerator createLoadGenerator() throws Snail4jException;

	Messages createMessages();

	ConfigReaderWriter getConfigReaderWriter();

	Configuration getConfiguration(BootstrapConfig bootstrapConfig) throws Snail4jException;

	String getConfigurationClassName();
	

}