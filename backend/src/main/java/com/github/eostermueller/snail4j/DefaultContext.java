package com.github.eostermueller.snail4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Factory;
import com.github.eostermueller.snail4j.launcher.Messages;

public class DefaultContext implements Context {
	
	public DefaultContext() throws CannotFindSnail4jFactoryClass, Snail4jException {
		this.configuration = DefaultFactory.getFactory().getConfiguration();
	}
	private Configuration configuration;
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public Configuration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Configuration cfg) {
		this.configuration = cfg;
	}
	
	public Factory getFactory() throws CannotFindSnail4jFactoryClass {
		return DefaultFactory.getFactory();
	}
	public Logger getLogger() {
		return LOGGER;
	}
	@Override
	public NonPersistentParameters getNonPersistentParameters() throws CannotFindSnail4jFactoryClass {
		return DefaultFactory.getFactory().getNonPersistentParameters();
	}
	@Override
	public Messages getMessages() throws CannotFindSnail4jFactoryClass {
		return DefaultFactory.getFactory().createMessages();
	}

}
