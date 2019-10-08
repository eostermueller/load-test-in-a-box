package com.github.eostermueller.havoc;

import java.util.Arrays;

public class PerfGoatException extends Exception {
	long timestamp = -1;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static FixedLengthQueue<PerfGoatException> history = new FixedLengthQueue<PerfGoatException>();
	Exception cause = null;
	public static FixedLengthQueue<PerfGoatException> getExceptionHistory() {
		return history;
	}
	public PerfGoatException(String s) {
		super(s);
		history.add(this);
		this.timestamp = System.currentTimeMillis();
	}
	public PerfGoatException(Exception c) {
		cause = c;
		history.add(this);
		this.timestamp = System.currentTimeMillis();
	}
	@Override
	public Exception getCause() {
		return this.cause;
	}
	
	public String toHumanReadableString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<p>### Exception ###");
		sb.append("My message: [" + this.getMessage() + "]");
		if (this.getCause() != null) {
			sb.append("Cause message: [" + this.getCause().getMessage() + "]");
			sb.append("Cause type:    [" + this.getCause().getClass().getName() + "]\n");
		}
		sb.append( Arrays.asList(this.getStackTrace()).toString());
		
		return sb.toString();
	}
	
}
