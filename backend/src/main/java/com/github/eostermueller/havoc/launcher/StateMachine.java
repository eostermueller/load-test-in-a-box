package com.github.eostermueller.havoc.launcher;

import java.util.List;

import com.github.eostermueller.havoc.PerfGoatException;


/**
 * Used to start/stop an entire suite, as well as starting/stopping a single process.
 * 
 * @author erikostermueller
 *
 */
public interface StateMachine {

	ProcessKey getProcessKey();
	void start() throws PerfGoatException;
	void stop() throws PerfGoatException;

	State getState();
	void setState(State state) throws PerfGoatException;
	
	void setStdoutStateChanger(StdoutStateChanger stateChanger);	
	StdoutStateChanger getStdoutStateChanger();
	
	List<StateChangeListener> getListeners();
	void setListeners(List<StateChangeListener> listeners);
	void registerStateChangeListener(StateChangeListener scl);
	void fireStateChange(ProcessKey key, State newState) throws PerfGoatException;
	String toHumanReadableString();

	
}
