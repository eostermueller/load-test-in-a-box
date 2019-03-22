package com.github.eostermueller.havoc.workload.model.json;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.eostermueller.havoc.workload.model.MethodParameter;
import com.github.eostermueller.havoc.workload.model.MethodWrapper;

public class TestMethodWrapper  {
	List<MethodParameter> parameters = new CopyOnWriteArrayList<MethodParameter>(); 
	Map<String, String> descriptions  = new ConcurrentHashMap<String,String>();
	private String methodName;
	private String declaringClassName;
	
	
	public void addDescription(Locale locale, String desc) {
		String key = locale.toLanguageTag();
		this.descriptions.put(key, desc);
	}
	public void setDeclaringClassName(String val) {
		this.declaringClassName = val;
	}
	
	
	
	
	public void setMethodName(String val) {
		this.methodName = val;
	}

	
	public String getMethodName() {
		return methodName;
	}

	
	public String getDeclaringClassName() {
		return declaringClassName;
	}

	
	public int getParameterCount() {
		return this.getParameters().size();
	}

	
	public List<MethodParameter> getParameters() {
		return this.parameters;
	}

	
	public MethodParameter getParameter(int i) {
		return this.getParameters().get(i);
	}

	
	public String getDescription(Locale locale) {
		String key = locale.toLanguageTag(); //ex: en-US
		return this.descriptions.get(key);
	}

}
