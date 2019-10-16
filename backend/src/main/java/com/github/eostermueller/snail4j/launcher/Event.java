package com.github.eostermueller.snail4j.launcher;


import com.github.eostermueller.snail4j.FixedLengthQueue;
import com.github.eostermueller.snail4j.Snail4jException;

public class Event {
	private static int maxExceptionsPerEvent;
	long timestamp = -1;
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	static {
			try {
				maxExceptionsPerEvent = DefaultFactory.getFactory()
						.getConfiguration()
						.getMaxExceptionCountPerEvent();
			} catch (CannotFindTjpFactoryClass e) {
				e.printStackTrace();
			}
	}
	public static Event create(String desc, Snail4jException exception) {
		Event e = new Event();
		e.exceptions.add(exception);
		e.setDescription(desc);
		e.setTimestamp(System.currentTimeMillis());
		return e;
	}
	public static Event create(String desc) {
		Event e = new Event();
		e.setDescription(desc);
		e.setTimestamp(System.currentTimeMillis());
		return e;
	}

	FixedLengthQueue<Exception> exceptions 
		= new FixedLengthQueue<Exception>(
				maxExceptionsPerEvent
				); 
	public FixedLengthQueue<Exception> getExceptions() {
		return exceptions;
	}
	public void setExceptions(FixedLengthQueue<Exception> exceptions) {
		this.exceptions = exceptions;
	}

	String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String debug() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Event [" + this.getDescription() + "] timestamp[" + String.valueOf(this.getTimestamp()) + "]");
		
		for(Snail4jException te : this.getExceptions().toArray( new Snail4jException[] {} ) ) {
			sb.append( te.toHumanReadableString() );
		}
		
		return sb.toString();
	}

	
	
}
