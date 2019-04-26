package com.github.eostermueller.havoc.workload.model;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class MethodWrapper  {
	List<MethodParameter> parameters = new CopyOnWriteArrayList<MethodParameter>(); 
	//Map<String, String> descriptions  = new ConcurrentHashMap<String,String>();
	//Descriptor descriptor = new Descriptor(); 
	
//	Method method = null;
//	public Method getMethod() {
//		return method;
//	}
//	public void setMethod(Method method) {
//		this.method = method;
//	}

//	public Descriptor getDescriptor() {
//		return descriptor;
//	}



//	public void setDescriptor(Descriptor descriptor) {
//		this.descriptor = descriptor;
//	}
	private String declaringClassName;
	private String name;
	
	
	public void setDeclaringClassName(String val) {
		this.declaringClassName = val;
	}
	

	
	public String getDeclaringClassName() {
		return declaringClassName;
		
	}

	
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
