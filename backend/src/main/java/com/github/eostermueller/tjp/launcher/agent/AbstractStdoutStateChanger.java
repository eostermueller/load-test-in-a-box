package com.github.eostermueller.tjp.launcher.agent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractStdoutStateChanger implements StdoutStateChanger {
	

	public List<StateChangeListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<StateChangeListener> listeners) {
		this.listeners = listeners;
	}

	List<StateChangeListener> listeners = new CopyOnWriteArrayList<StateChangeListener>();
	
	@Override
	public abstract void evaluateStdoutLine(String s) throws TjpException;

	@Override
	public void registerStateChangeListener(StateChangeListener scl) {
		this.getListeners().add(scl);
	}
	
	public void fireStateChange(ProcessKey processKey, State newState) throws TjpException {
		for( StateChangeListener sscl : this.getListeners() ) {
			sscl.stateHasChanged(processKey, newState);
		}
	}

}
