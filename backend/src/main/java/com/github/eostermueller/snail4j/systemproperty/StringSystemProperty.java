package com.github.eostermueller.snail4j.systemproperty;

public interface StringSystemProperty extends SystemProperty {
	String getDefaultValue();
	String getValue();
}
