package com.github.eostermueller.tjp.launcher.agent;

import java.util.List;


/**
 * Used to start/stop an entire suite, as well as starting/stopping a single process.
 * 
 * @author erikostermueller
 *
 */
public interface StateMachine {

	ProcessKey getProcessKey();
	void start() throws TjpException;
	void stop() throws TjpException;

	State getState();
	void setState(State state) throws TjpException;
	
	void setStdoutStateChanger(StdoutStateChanger stateChanger);	
	StdoutStateChanger getStdoutStateChanger();
	
	List<StateChangeListener> getListeners();
	void setListeners(List<StateChangeListener> listeners);
	void registerStateChangeListener(StateChangeListener scl);
	void fireStateChange(ProcessKey key, State newState) throws TjpException;

	
}
