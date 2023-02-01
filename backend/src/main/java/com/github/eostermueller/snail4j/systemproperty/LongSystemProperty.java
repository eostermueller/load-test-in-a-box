package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.Snail4jException;

public interface LongSystemProperty extends SystemProperty {
	long getDefaultValue();
	long getValue() throws Snail4jException;
}
