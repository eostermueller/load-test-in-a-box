package com.github.eostermueller.snail4j.install;

import com.github.eostermueller.snail4j.Snail4jException;

public class InstallException extends Snail4jException {

	public InstallException(Exception c) {
		super(c);
	}
	public InstallException(Exception c, String msg) {
		super(c, msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4022837623168289907L;

}
