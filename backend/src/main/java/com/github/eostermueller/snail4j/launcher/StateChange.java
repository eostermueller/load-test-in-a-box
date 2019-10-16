package com.github.eostermueller.snail4j.launcher;

public class StateChange {
	public StateChange(ProcessKey processKey, State state) {
		this.state = state;
		this.processKey = processKey;
		this.timestamp = System.currentTimeMillis();
	}
	long timestamp = 0;
	State state = null;
	ProcessKey processKey = null;
	public String toHumanReadableString() {
		StringBuilder sb = new StringBuilder();
		sb.append("process: " + this.processKey.toString() + "\n");
		sb.append("state: " + this.state.toString() );
		sb.append("timestamp: " + String.valueOf(this.timestamp));
		return sb.toString();
	}
}
