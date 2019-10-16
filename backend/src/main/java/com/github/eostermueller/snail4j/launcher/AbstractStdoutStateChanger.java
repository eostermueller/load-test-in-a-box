package com.github.eostermueller.snail4j.launcher;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.eostermueller.snail4j.Snail4jException;

public abstract class AbstractStdoutStateChanger implements StdoutStateChanger {
	

	public List<StateChangeListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<StateChangeListener> listeners) {
		this.listeners = listeners;
	}

	List<StateChangeListener> listeners = new CopyOnWriteArrayList<StateChangeListener>();
	
	@Override
	public abstract void evaluateStdoutLine(String s) throws Snail4jException;

	@Override
	public void registerStateChangeListener(StateChangeListener scl) {
		this.getListeners().add(scl);
	}
	
	public void fireStateChange(ProcessKey processKey, State newState) throws Snail4jException {
		for( StateChangeListener sscl : this.getListeners() ) {
			sscl.stateHasChanged(processKey, newState);
		}
	}

}
