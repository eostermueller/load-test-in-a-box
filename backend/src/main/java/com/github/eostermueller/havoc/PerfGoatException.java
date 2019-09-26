package com.github.eostermueller.havoc;

import com.github.eostermueller.havoc.launcher.Event;
import com.github.eostermueller.havoc.launcher.EventHistory;

public class PerfGoatException extends Exception {

	Exception cause = null;
	public PerfGoatException(String s) {
		super(s);
	}
	public PerfGoatException(Exception c) {
		cause = c;
	}
	@Override
	public Exception getCause() {
		return this.cause;
	}
	
	public String debug() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("### Exception ###");
		sb.append("My message: [" + this.getMessage() + "]\n");
		sb.append("Cause message: [" + this.getCause().getMessage() + "]\n");
		sb.append("Cause type: [" + this.getCause().getClass().getName() + "]\n");
		
		return sb.toString();
	}
	
}
