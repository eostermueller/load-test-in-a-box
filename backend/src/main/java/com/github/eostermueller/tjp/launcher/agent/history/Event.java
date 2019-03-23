package com.github.eostermueller.tjp.launcher.agent.history;


import com.github.eostermueller.tjp.launcher.agent.CannotFindTjpFactoryClass;
import com.github.eostermueller.tjp.launcher.agent.DefaultFactory;
import com.github.eostermueller.tjp.launcher.agent.TjpException;

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
	public static Event create(String desc, TjpException exception) {
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
		
		for(TjpException te : this.getExceptions().toArray( new TjpException[] {} ) ) {
			sb.append( te.debug() );
		}
		
		return sb.toString();
	}

	
	
}