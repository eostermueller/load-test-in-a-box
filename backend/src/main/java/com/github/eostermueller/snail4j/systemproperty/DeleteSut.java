package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;

public class DeleteSut extends BooleanSystemPropertyImpl {

	@Override
	public boolean getDefaultValue() {
		return false;
	}


	@Override
	public String getHumanReadableDocumentation() throws CannotFindSnail4jFactoryClass {
		return DefaultFactory.getFactory().getMessages().getDocForDeleteSut();
	}

	@Override
	public String getDashDPropertyName() {
		return "com.github.eostermueller.snail4j.delete.sut";
	}

}
