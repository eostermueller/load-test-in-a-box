package com.github.eostermueller.havoc.workload.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.github.eostermueller.havoc.workload.HavocException;

public class ProcessingUnitImpl {
	private Description description = new Description();
	public String getDescription(String locale_with_underscore) {
		return this.getDescription().getDescription(locale_with_underscore);
	}
	public String addDescription(String locale_with_underscore, String value) {
		return this.getDescription().getDescriptions().put(locale_with_underscore, value);
	}
	public Description getDescription() {
		return description;
	}
	public void setDescription(Description description) {
		this.description = description;
	}
	
	
	private String useCaseName;
	private MethodWrapper method;
	boolean selected = false;
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setUseCaseName(String val) {
		this.useCaseName = val;
	}
	
	public String getUseCaseName() {
		return this.useCaseName;
	}
	
	public void setMethodWrapper(MethodWrapper val) {
		this.method = val;
	}

	
	public MethodWrapper getMethodWrapper() {
		return this.method;
	}
	
}
