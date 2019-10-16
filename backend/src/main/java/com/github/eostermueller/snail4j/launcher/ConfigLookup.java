package com.github.eostermueller.snail4j.launcher;

import java.util.List;

import com.github.eostermueller.snail4j.Snail4jException;

public interface ConfigLookup {
	public void setConfiguration(Configuration val);
	String getValue(String variableName) throws ConfigVariableNotFoundException, Snail4jException;
	List<String> getAllFieldNames();
}
