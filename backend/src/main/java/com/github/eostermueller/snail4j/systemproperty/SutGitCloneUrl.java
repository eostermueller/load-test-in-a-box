package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;

public class SutGitCloneUrl extends StringSystemPropertyImpl {

	@Override
	public String getDefaultValue() {
		return null;
	}

	@Override
	public String getDashDPropertyName() {
		return "com.github.eostermueller.snail4j.sutGitCloneUrl";
	}

	@Override
	public String getHumanReadableDocumentation() throws CannotFindSnail4jFactoryClass {
		return DefaultFactory.getFactory().getMessages().getSutGitCloneUrlDoc();
	}

}
