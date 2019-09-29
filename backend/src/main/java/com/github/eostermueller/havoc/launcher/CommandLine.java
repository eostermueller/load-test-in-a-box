package com.github.eostermueller.havoc.launcher;

import com.github.eostermueller.havoc.PerfGoatException;

public interface CommandLine {

	void setCommandLine(String str);

	String[] getUnprocessedCommandLine();

	String[] getProcessedCommandLine() throws ConfigVariableNotFoundException, PerfGoatException;

	void setConfigLookup(ConfigLookup lkup);

}
