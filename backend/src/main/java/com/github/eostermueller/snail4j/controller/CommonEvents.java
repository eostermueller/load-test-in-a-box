package com.github.eostermueller.snail4j.controller;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Event;
import com.github.eostermueller.snail4j.launcher.Messages;

public class CommonEvents {
	Messages m = null; 
	public CommonEvents() throws CannotFindSnail4jFactoryClass {
		Messages m = DefaultFactory.getFactory().getMessages();
	}
	public void startSystemUnderTest() {
	};
	public void stopSystemUnderTest(){};
	public void updateConfig(){};
	public void startLoadGenerator(long pid) throws CannotFindSnail4jFactoryClass{
		fireEvent(m.getLoadGeneratorStartMessage( String.valueOf(pid)));
	};
	public void stopLoadGenerator(){};
	private void fireEvent(String eventText) throws CannotFindSnail4jFactoryClass {
		DefaultFactory.getFactory().getEventHistory().getEvents().add(
				Event.create(eventText) );
	}

}
