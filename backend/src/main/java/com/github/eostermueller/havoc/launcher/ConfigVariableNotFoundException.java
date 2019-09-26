package com.github.eostermueller.havoc.launcher;

import java.util.List;

public class ConfigVariableNotFoundException extends Exception {
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
