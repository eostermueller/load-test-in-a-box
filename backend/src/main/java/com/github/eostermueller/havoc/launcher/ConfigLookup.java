package com.github.eostermueller.havoc.launcher;

import java.util.List;

import com.github.eostermueller.havoc.PerfGoatException;

public interface ConfigLookup {
	public void setConfiguration(Configuration val);
	String getValue(String variableName) throws ConfigVariableNotFoundException, PerfGoatException;
	List<String> getAllFieldNames();
}
