package com.github.eostermueller.havoc.launcher;

import java.util.List;

public interface ConfigLookup {
	public void setConfiguration(Configuration val);
	String getValue(String variableName) throws ConfigVariableNotFoundException;
	List<String> getAllFieldNames();
}
