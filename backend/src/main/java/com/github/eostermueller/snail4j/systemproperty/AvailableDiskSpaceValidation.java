package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;

public class AvailableDiskSpaceValidation extends BooleanSystemPropertyImpl {

	@Override
	public boolean getDefaultValue() {
		return true;
	}

	@Override
	public String getHumanReadableDocumentation() throws CannotFindSnail4jFactoryClass {
		return DefaultFactory.getFactory().getMessages().getDeleteSutDoc();
	}

	@Override
	public String getDashDProperty() {
		return "com.github.eostermueller.snail4j.delete.sut";
	}

}
