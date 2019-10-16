package com.github.eostermueller.snail4j.processmodel;

import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindTjpFactoryClass;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.snail4j.launcher.Suite;

public interface ProcessModelBuilder {

	Suite build() throws CannotFindTjpFactoryClass, ConfigVariableNotFoundException, Snail4jException;

}