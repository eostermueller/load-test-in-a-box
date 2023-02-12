package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.Snail4jException;

public abstract class LongSystemPropertyImpl implements LongSystemProperty {

	public long getValue() throws Snail4jException {
		long rc = getLongParameter();
		return rc;
	}
	private long getLongParameter() throws Snail4jException {
		long rc = this.getDefaultValue();
		String value = System.getProperty(this.getDashDPropertyName());
		if (value != null && value.length() > 0) {
			try {
				rc = Long.parseLong(value);
			} catch (NumberFormatException e) {
				throw new SystemPropertyException(this,e);
			}
		}
		return rc;
	}
	
}
