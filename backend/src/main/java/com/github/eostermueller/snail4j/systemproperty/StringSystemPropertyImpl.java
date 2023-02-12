package com.github.eostermueller.snail4j.systemproperty;


public abstract class StringSystemPropertyImpl implements StringSystemProperty {

	public String getValue() {
		String rc = getStringParameter(
				this.getDashDPropertyName(),
				this.getDefaultValue()
				);
		return rc;
	}
	private String getStringParameter(String dashDVariableName, String boolDefault) {
		String value = System.getProperty(dashDVariableName,this.getDefaultValue() );
		return value;
	}
	
}
