package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.Snail4jException;

public interface SystemPropertyManager {
	
	boolean getBoolean(BooleanSystemProperty systemProperty) throws Snail4jException;
	long getLong(LongSystemProperty systemProperty) throws Snail4jException;
	String getString(StringSystemProperty systemProperty);
}
