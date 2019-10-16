package com.github.eostermueller.snail4j.launcher;


import com.github.eostermueller.snail4j.Snail4jException;

public abstract class AbstractProcessRunner extends AbstractStateMachine implements StateMachine {

	public AbstractProcessRunner(ProcessKey key) throws Snail4jException {
		this.setProcessKey(key);
		setState(State.STOPPED);
	}
	
}

