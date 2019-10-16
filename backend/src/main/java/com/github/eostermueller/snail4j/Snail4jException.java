package com.github.eostermueller.snail4j;

import java.util.Arrays;

public class Snail4jException extends Exception {
	long timestamp = -1;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static FixedLengthQueue<Snail4jException> history = new FixedLengthQueue<Snail4jException>();
	Exception cause = null;
	public static FixedLengthQueue<Snail4jException> getExceptionHistory() {
		return history;
	}
	public Snail4jException(String s) {
		super(s);
		history.add(this);
		this.timestamp = System.currentTimeMillis();
	}
	public Snail4jException(Exception c) {
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
