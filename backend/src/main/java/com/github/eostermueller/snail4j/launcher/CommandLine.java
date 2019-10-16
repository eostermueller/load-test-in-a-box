package com.github.eostermueller.snail4j.launcher;

import com.github.eostermueller.snail4j.Snail4jException;

public interface CommandLine {

	void setCommandLine(String str);

	String[] getUnprocessedCommandLine();

	String[] getProcessedCommandLine() throws ConfigVariableNotFoundException, Snail4jException;

	void setConfigLookup(ConfigLookup lkup);

}
