package com.github.eostermueller.havoc.launcher;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.eostermueller.havoc.PerfGoatException;


public abstract class AbstractStateMachine implements StateMachine {
	List<StateChangeListener> listeners = new CopyOnWriteArrayList<StateChangeListener>();

	
	private StdoutStateChanger stdoutStateChanger = null;
	@Override
	public void setStdoutStateChanger(StdoutStateChanger stdoutStateChanger) {
		this.stdoutStateChanger = stdoutStateChanger;
	}
	@Override
	public StdoutStateChanger getStdoutStateChanger() {
		return this.stdoutStateChanger;
	}
	
	public ProcessKey getProcessKey() {
		return processKey;
	}
	public void setProcessKey(ProcessKey processKey) {
		this.processKey = processKey;
	}
	ProcessKey processKey = null;
	
	@Override
	public State getState() {
		return state;
	}

	private State state;
	
	public void setState(State newState) throws PerfGoatException {
		this.state = newState;
		this.fireStateChange(this.getProcessKey(), newState);
	}
	@Override
	public 	void fireStateChange(ProcessKey key, State newState) throws PerfGoatException {
		for(StateChangeListener listener : this.getListeners() ) {
			listener.stateHasChanged(key, newState);
		}
	}

	public List<StateChangeListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<StateChangeListener> listeners) {
		this.listeners = listeners;
	}

	public void registerStateChangeListener(StateChangeListener scl) {
		this.getListeners().add(scl);
	}
}
