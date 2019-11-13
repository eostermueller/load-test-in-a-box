package com.github.eostermueller.snail4j.processmodel;

import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.snail4j.launcher.Suite;

public interface ProcessModelBuilder {

	Suite build() throws CannotFindSnail4jFactoryClass, ConfigVariableNotFoundException, Snail4jException;

}