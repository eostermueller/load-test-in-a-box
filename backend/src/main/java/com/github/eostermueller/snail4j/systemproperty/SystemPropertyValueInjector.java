package com.github.eostermueller.snail4j.systemproperty;

public interface SystemPropertyValueInjector {

	void setBoolean(BooleanSystemProperty systemProperty, boolean value);
	void setLong(LongSystemProperty systemProperty, long value);
	void setString(StringSystemProperty systemProperty, String value);
}
