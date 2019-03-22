package com.github.eostermueller.tjp.launcher.agent.history;

import com.github.eostermueller.tjp.launcher.agent.CannotFindTjpFactoryClass;
import com.github.eostermueller.tjp.launcher.agent.DefaultFactory;
import com.github.eostermueller.tjp.launcher.agent.TjpException;

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
		TjpException te = new TjpException(e);
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
