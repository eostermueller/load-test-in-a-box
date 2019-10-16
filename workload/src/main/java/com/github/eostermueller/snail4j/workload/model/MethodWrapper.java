package com.github.eostermueller.snail4j.workload.model;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class MethodWrapper  {
	List<MethodParameter> parameters = new CopyOnWriteArrayList<MethodParameter>(); 



	private String declaringClassName;
	private String name;
	
	
	public void setDeclaringClassName(String val) {
		this.declaringClassName = val;
	}
	

	
	public String getDeclaringClassName() {
		return declaringClassName;
		
	}

	@JsonIgnore
	public int getParameterCount() {
		return this.getParameters().size();
		//return this.getMethod().getParameterCount();
	}

	
	public List<MethodParameter> getParameters() {
		return this.parameters;
	}

	
	public MethodParameter getParameter(int i) {
		return this.getParameters().get(i);
	}
	
	public void setMethodName(String v) {
		this.name = v;
	}
	public String getMethodName() {
		return this.name;
	}
	public String humanReadable() {
		StringBuilder sb = new StringBuilder();
		sb.append(" getDeclaringClassName: " + this.getDeclaringClassName() );
		sb.append("getMethodName: " + this.getMethodName()  );
		return sb.toString();
	}

}
