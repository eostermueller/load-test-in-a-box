package com.github.eostermueller.snail4j.processmodel;

import java.io.IOException;

import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;

public interface SystemUnderTest {

	void start() throws ConfigVariableNotFoundException, IOException, Snail4jException;
	void stop() throws Snail4jException;
}
