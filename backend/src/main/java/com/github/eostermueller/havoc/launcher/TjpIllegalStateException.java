package com.github.eostermueller.havoc.launcher;

public class TjpIllegalStateException extends IllegalStateException {

	private State state = null;
	public TjpIllegalStateException(String msg) {
		super(msg);
	}
	public TjpIllegalStateException(String msg, State state) {
		super(msg);
		this.state = state;
	}

}
