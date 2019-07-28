package com.github.eostermueller.havoc.workload.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.eostermueller.havoc.workload.HavocException;

public class MethodParameter  {
	private Description description = new Description();
	public Description getDescription() {
		return description;
	}
	public void setDescription(Description description) {
		this.description = description;
	}
	public String getDescription(String locale_with_underscore) {
		return this.getDescription().getDescription(locale_with_underscore);
	}
	public String addDescription(String locale_with_underscore, String value) {
		return this.getDescription().getDescriptions().put(locale_with_underscore, value);
	}

	ParameterType parameterType = null;

	public ParameterType getParameterType() {
		return parameterType;
	}
//	public String getMessage(Locale locale) {
//		return this.getDescriptor().getMessage(locale).getMessage();
//	}
//	public void addMessage(Locale locale, String desc) throws HavocException {
//		this.getDescriptor().addMessage(locale, desc);
//	}
	

	public void setParameterType(ParameterType parameterType) {
		this.parameterType = parameterType;
	}

	String name = null;
	private long defaultLongValue;
	public long getDefaultIntValue() {
		return defaultIntValue;
	}


	public void setDefaultIntValue(int defaultIntValue) {
		this.defaultIntValue = defaultIntValue;
	}


	public String getDefaultStringValue() {
		return defaultStringValue;
	}


	public void setDefaultStringValue(String defaultStringValue) {
		this.defaultStringValue = defaultStringValue;
	}

	private int defaultIntValue;
	private String defaultStringValue;
	//private Descriptor descriptor = new Descriptor();
	
	
	public String getName() {
		return this.name;
	}
	
	@JsonIgnore
	public String getDefaultValue() {
		String rc = null;
		switch( this.getParameterType()) {
		case INTEGER:
			rc = String.valueOf(this.getDefaultIntValue());
			break;
		case LONG:
			rc = String.valueOf(this.getDefaultLongValue());
			break;
		case STRING:
			rc = this.getDefaultStringValue();
			default:
			
		}
		return rc;
	}

	public long getDefaultLongValue() {
		return this.defaultLongValue;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setDefaultLongValue(long longValue) {
		this.defaultLongValue = longValue;
	}
	
//	public Descriptor getDescriptor() {
//		return this.descriptor;
//	}
//	public void setDescriptor(Descriptor d) {
//		this.descriptor = d;
//	}
}
