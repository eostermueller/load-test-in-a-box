package com.github.eostermueller.snail4j;

import java.util.List;

import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;

public class Snail4jMultiException extends Snail4jException {
	private List<String> errors;

	public Snail4jMultiException(List<String> errors) throws CannotFindSnail4jFactoryClass {
		
		super(DefaultFactory.getFactory().getMessages().multipleProblems());
		this.errors = errors;
	}

}
