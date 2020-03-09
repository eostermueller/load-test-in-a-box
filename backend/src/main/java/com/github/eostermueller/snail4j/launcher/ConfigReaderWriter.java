package com.github.eostermueller.snail4j.launcher;

import java.io.File;

import com.github.eostermueller.snail4j.Snail4jException;

public interface ConfigReaderWriter {

	void write(File configFile, Configuration cfg) throws Snail4jException;

	Configuration read(File configFile, Class configurationClass) throws Snail4jException;

	String toJson(Configuration cfg) throws Snail4jException;

	Configuration toObject(String json, Class cfgClass) throws Snail4jException;
}
