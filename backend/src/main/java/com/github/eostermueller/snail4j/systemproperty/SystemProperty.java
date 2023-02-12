package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;

public interface SystemProperty {
	String getHumanReadableDocumentation() throws CannotFindSnail4jFactoryClass;
	
	/**
	 * Name of a JVM "System Property" used by load-test-in-a-box 
	 * https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
	 */
	String getDashDPropertyName();
}
