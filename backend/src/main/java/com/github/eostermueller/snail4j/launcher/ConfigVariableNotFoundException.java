package com.github.eostermueller.snail4j.launcher;

import java.util.List;

import com.github.eostermueller.snail4j.Snail4jException;

public class ConfigVariableNotFoundException extends Snail4jException {
	public ConfigVariableNotFoundException(Exception c) {
		super(c);
	}
	public ConfigVariableNotFoundException(String msg) {
		super(msg);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -169554873410779861L;
	private String variableName = null;
	private List<String> allVariableNames;
	String getVariableName() {
		return this.variableName;
	}
	String setVariableName(String var) {
		return this.variableName;
	}
	@Override
	public String getMessage() {
		return "Variable [" + this.getVariableName() + "] not found.  Must be one of [" + this.getAllVariableNames().toString() + "]";
	}
	public void setAllVariableNames(List<String> val) {
		this.allVariableNames = val;
	}
	public List<String> getAllVariableNames() {
		return allVariableNames;
	}

}
