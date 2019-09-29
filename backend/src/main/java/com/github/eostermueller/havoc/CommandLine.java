package com.github.eostermueller.havoc;

import com.github.eostermueller.havoc.launcher.ConfigLookup;
import com.github.eostermueller.havoc.launcher.ConfigVariableNotFoundException;

public interface CommandLine {

	void setCommandLine(String str);

	String[] getUnprocessedCommandLine();

	String[] getProcessedCommandLine() throws ConfigVariableNotFoundException;

	void setConfigLookup(ConfigLookup lkup);

}
