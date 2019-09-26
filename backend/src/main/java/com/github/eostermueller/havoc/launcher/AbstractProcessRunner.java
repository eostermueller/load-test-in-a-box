package com.github.eostermueller.havoc.launcher;


import com.github.eostermueller.havoc.PerfGoatException;

public abstract class AbstractProcessRunner extends AbstractStateMachine implements StateMachine {

	public AbstractProcessRunner(ProcessKey key) throws PerfGoatException {
		this.setProcessKey(key);
		setState(State.STOPPED);
	}
	
}

