package com.github.eostermueller.snail4j.launcher;

import java.util.Collections;
import java.util.Comparator;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.util.FixedLengthQueue;

public class EventHistory {
	private static int maxEventCount = 1000;
	
	static {
			try {
				maxEventCount = DefaultFactory.getFactory()
						.getConfiguration()
						.getMaxExceptionCountPerEvent();
			} catch (Snail4jException e) {
				e.printStackTrace();
			}
	}

	FixedLengthQueue<Event> events 
		= new FixedLengthQueue<Event>(
				maxEventCount
				);

	private void sortDecendingByTimestamp() {
		 Collections.sort(events, new Comparator<Event>() {
		     @Override
		     public int compare(Event o1, Event o2) {
		         return Long.compare(o2.getTimestamp(), o1.getTimestamp());
		     }
		 });		
	}
	public FixedLengthQueue<Event> getEvents() {
		sortDecendingByTimestamp();
		return events;
	}

	public void setEvents(FixedLengthQueue<Event> events) {
		this.events = events;
	} 
	public void addException(String Description, Exception e) throws CannotFindSnail4jFactoryClass {
		Snail4jException te = new Snail4jException(e);
		Event event = Event.create(Description, te);
		this.addEvent(event);
	}

	private void addEvent(Event event) throws CannotFindSnail4jFactoryClass {
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
