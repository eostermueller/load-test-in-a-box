package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.Snail4jException;

public interface BooleanSystemProperty extends SystemProperty {
	boolean getDefaultValue();
	boolean getValue() throws Snail4jException;
}
