package com.github.eostermueller.tjp.launcher.agent;

import java.util.EventListener;//nice to have, not a 'must have'

/**
 * Notifies when key state changes happen in the life (like 'started', 'stopped', etc...)
 * of a stdout-based process.  For an example of how to use this, see
 * com.github.eostermueller.tjp.launcher.agent.BasicProcessManagementTest
 * 
 * 
 * Most needs, like for tracking start/stop of a web application, will be met using stateHasChanged().
 * But when every line of stdout matters, like for stdout of a load generator (like jmeter) 
 * the processItem() method will be called.  Just provide a null implementation if you don't need it.
 * 
 *   
 * @author erikostermueller
 *
 */
public interface StateChangeListener extends EventListener {
	void stateHasChanged(ProcessKey processKey,State newState) throws TjpException;	
}
