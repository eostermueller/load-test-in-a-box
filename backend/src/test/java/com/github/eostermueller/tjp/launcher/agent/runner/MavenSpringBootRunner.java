package com.github.eostermueller.tjp.launcher.agent.runner;

import java.util.List;

import com.github.eostermueller.tjp.launcher.agent.Messages;
import com.github.eostermueller.tjp.launcher.agent.ProcessKey;
import com.github.eostermueller.tjp.launcher.agent.State;
import com.github.eostermueller.tjp.launcher.agent.StateChangeListener;
import com.github.eostermueller.tjp.launcher.agent.StateMachine;
import com.github.eostermueller.tjp.launcher.agent.StdoutStateChanger;

public class MavenSpringBootRunner implements StateMachine {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setState(State state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ProcessKey getProcessKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStdoutStateChanger(StdoutStateChanger stateChanger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StdoutStateChanger getStdoutStateChanger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateChangeListener> getListeners() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void fireStateChange(ProcessKey key, State newState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListeners(List<StateChangeListener> listeners) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerStateChangeListener(StateChangeListener scl) {
		// TODO Auto-generated method stub
		
	}

}
