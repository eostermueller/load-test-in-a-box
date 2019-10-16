package com.github.eostermueller.snail4j.launcher;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.FixedLengthQueue;
import com.github.eostermueller.snail4j.Snail4jException;

public class EventHistory {
	private static int maxEventCount = 1000;
	
	static {
			try {
				maxEventCount = DefaultFactory.getFactory()
						.getConfiguration()
						.getMaxExceptionCountPerEvent();
			} catch (CannotFindTjpFactoryClass e) {
				e.printStackTrace();
			}
	}

	FixedLengthQueue<Event> events 
		= new FixedLengthQueue<Event>(
				maxEventCount
				);

	public FixedLengthQueue<Event> getEvents() {
		return events;
	}

	public void setEvents(FixedLengthQueue<Event> events) {
		this.events = events;
	} 
	public void addException(String Description, Exception e) throws CannotFindTjpFactoryClass {
		Snail4jException te = new Snail4jException(e);
		Event event = Event.create(Description, te);
		this.addEvent(event);
	}

	private void addEvent(Event event) throws CannotFindTjpFactoryClass {
		events.add(event);
	}

	public String debug() {
		StringBuilder sb = new StringBuilder();
		
		for(Event event : this.getEvents().toArray( new Event[]{} ) ) {
			sb.append( event.debug() );
		}
		
		
		return sb.toString();
	}

}
