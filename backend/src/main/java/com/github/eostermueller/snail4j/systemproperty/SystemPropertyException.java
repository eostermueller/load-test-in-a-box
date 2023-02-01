package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.Snail4jException;

public class SystemPropertyException extends Snail4jException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SystemProperty systemProperty;
	
	public SystemPropertyException(SystemProperty systemProperty, Exception e) {
		super(e);
		this.systemProperty = systemProperty;
	}
	public SystemProperty getSystemProperty() {
		return systemProperty;
	}
}
