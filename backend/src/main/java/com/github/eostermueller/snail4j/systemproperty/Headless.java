package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;

public class Headless extends BooleanSystemPropertyImpl {

	@Override
	public String getHumanReadableDocumentation() throws CannotFindSnail4jFactoryClass {
		return DefaultFactory.getFactory().getMessages().docForHeadlessProperty();
	}

	@Override
	public String getDashDProperty() {
		return "com.github.eostermueller.snail4j.config.headless";
	}

	/**
	 * From a security standpoint, it is safer to by default NOT allow remote access, and headless=false does that.
	 */
	@Override
	public boolean getDefaultValue() {
		return false;
	}
}
