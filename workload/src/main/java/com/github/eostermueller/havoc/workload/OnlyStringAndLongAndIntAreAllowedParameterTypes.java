package com.github.eostermueller.havoc.workload;

import java.lang.reflect.Method;

public class OnlyStringAndLongAndIntAreAllowedParameterTypes extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String illegalType;
	private Method method = null;
	public OnlyStringAndLongAndIntAreAllowedParameterTypes() {
		
	}
	public OnlyStringAndLongAndIntAreAllowedParameterTypes(Method method, String val) {
		this.illegalType = val;
		this.method = method;
	}
	
	@Override
	public String getMessage() {
		return "In method [" + this.getMethod().getName() + "] in class [" + this.getMethod().getDeclaringClass().getName() + "], the type [" + this.getIllegalType() + "] is not allowed for a method parameter. only long and String are allowed.";
	}
	private Method getMethod() {
		return this.method;
	}
	public String getIllegalType() {
		return illegalType;
	}

}
