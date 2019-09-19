package com.github.eostermueller.tjp.launcher;


import com.github.eostermueller.havoc.PerfGoatException;

public class Event {
	private static int maxExceptionsPerEvent;
	
	static {
			try {
				maxExceptionsPerEvent = DefaultFactory.getFactory()
						.getConfiguration()
						.getMaxExceptionCountPerEvent();
			} catch (CannotFindTjpFactoryClass e) {
				e.printStackTrace();
			}
	}
	public static Event create(String desc, PerfGoatException exception) {
		Event e = new Event();
		e.exceptions.add(exception);
		e.setDescription(desc);
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
		
		sb.append("Event [" + this.getDescription() + "]");
		
		for(PerfGoatException te : this.getExceptions().toArray( new PerfGoatException[] {} ) ) {
			sb.append( te.debug() );
		}
		
		return sb.toString();
	}

	
	
}
