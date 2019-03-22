package com.github.eostermueller.tjp.launcher.agent.runner;


import com.github.eostermueller.tjp.launcher.agent.AbstractStateMachine;
import com.github.eostermueller.tjp.launcher.agent.DefaultFactory;
import com.github.eostermueller.tjp.launcher.agent.Messages;
import com.github.eostermueller.tjp.launcher.agent.ProcessKey;
import com.github.eostermueller.tjp.launcher.agent.State;
import com.github.eostermueller.tjp.launcher.agent.StateMachine;
import com.github.eostermueller.tjp.launcher.agent.StdoutStateChanger;
import com.github.eostermueller.tjp.launcher.agent.TjpException;

public abstract class AbstractProcessRunner extends AbstractStateMachine implements StateMachine {

	public AbstractProcessRunner(ProcessKey key) throws TjpException {
		this.setProcessKey(key);
		setState(State.STOPPED);
	}
	
}

