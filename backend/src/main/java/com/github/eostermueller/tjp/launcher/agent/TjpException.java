package com.github.eostermueller.tjp.launcher.agent;

import com.github.eostermueller.tjp.launcher.agent.history.Event;
import com.github.eostermueller.tjp.launcher.agent.history.EventHistory;

public class TjpException extends Exception {

	Exception cause = null;
	public TjpException(String s) {
		super(s);
	}
	public TjpException(Exception c) {
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
