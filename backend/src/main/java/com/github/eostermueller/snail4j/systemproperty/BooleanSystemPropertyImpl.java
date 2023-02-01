package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.util.StringUtils;

public abstract class BooleanSystemPropertyImpl implements BooleanSystemProperty {

	public boolean getValue() throws Snail4jException {
		boolean rc = getBooleanParameter(
				this.getDashDProperty(),
				this.getDefaultValue()
				);
		return rc;
	}
	private boolean getBooleanParameter(String dashDVariableName, boolean boolDefault) throws Snail4jException {
		boolean rc = boolDefault;
		String value = System.getProperty(dashDVariableName);
		if (value != null && value.length() > 0) {
			rc = StringUtils.parseTrueOrFalse(value);
		}
		return rc;
	}
	
}
