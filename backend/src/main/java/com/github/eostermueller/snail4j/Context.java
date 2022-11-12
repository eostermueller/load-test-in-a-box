package com.github.eostermueller.snail4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Factory;
import com.github.eostermueller.snail4j.launcher.Messages;

public interface Context {

	public Configuration getConfiguration();
	public void setConfiguration(Configuration cfg);
	public 	Logger getLogger();
	public Factory getFactory() throws CannotFindSnail4jFactoryClass;
	public NonPersistentParameters getNonPersistentParameters() throws CannotFindSnail4jFactoryClass;
	public Messages getMessages() throws CannotFindSnail4jFactoryClass;

}
