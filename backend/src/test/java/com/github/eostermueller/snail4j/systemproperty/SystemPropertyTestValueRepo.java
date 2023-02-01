package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.Snail4jException;

public interface SystemPropertyTestValueRepo {
	void setBoolean(BooleanSystemProperty systemProperty, boolean val) throws Snail4jException;
	void setLong(LongSystemProperty systemProperty, long val) throws Snail4jException;
	void setString(StringSystemProperty systemProperty, String s) ;
}
