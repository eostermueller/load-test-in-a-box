package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;

public class AvailableMemoryValidation extends BooleanSystemPropertyImpl {

	@Override
	public boolean getDefaultValue() {
		return true;
	}


	@Override
	public String getHumanReadableDocumentation() throws CannotFindSnail4jFactoryClass {
		return DefaultFactory.getFactory().getMessages().getAvailableMemoryValidationDoc();
	}

	@Override
	public String getDashDPropertyName() {
		return "com.github.eostermueller.snail4j.available.memory.size.validation";
	}

}
