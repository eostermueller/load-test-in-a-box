package com.github.eostermueller.snail4j.launcher;

import java.util.List;

import com.github.eostermueller.snail4j.Snail4jException;


/**
 * Used to start/stop an entire suite, as well as starting/stopping a single process.
 * 
 * @author erikostermueller
 *
 */
public interface StateMachine {

	ProcessKey getProcessKey();
	void start() throws Snail4jException;
	void stop() throws Snail4jException;

	State getState();
	void setState(State state) throws Snail4jException;
	
	void setStdoutStateChanger(StdoutStateChanger stateChanger);	
	StdoutStateChanger getStdoutStateChanger();
	
	List<StateChangeListener> getListeners();
	void setListeners(List<StateChangeListener> listeners);
	void registerStateChangeListener(StateChangeListener scl);
	void fireStateChange(ProcessKey key, State newState) throws Snail4jException;
	String toHumanReadableString();

	
}
