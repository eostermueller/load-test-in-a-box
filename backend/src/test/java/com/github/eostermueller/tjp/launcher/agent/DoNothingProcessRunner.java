package com.github.eostermueller.tjp.launcher.agent;


import java.util.List;

import com.github.eostermueller.havoc.PerfGoatException;
import com.github.eostermueller.tjp.launcher.agent.runner.AbstractProcessRunner;

/**
 * Consider using this approach for remote logging.
 * https://dzone.com/articles/tailing-file-spring-websocket
 * https://howtodoinjava.com/log4j/log4j-socketappender-and-socket-server-example/
 * https://stackoverflow.com/questions/11759196/log4j-how-to-use-socketappender
 * @author erikostermueller
 *
 */
public class DoNothingProcessRunner extends AbstractProcessRunner implements StateMachine {
	public DoNothingProcessRunner(ProcessKey key) throws PerfGoatException {
		super(key);
	}

	public DoNothingProcessRunner(ProcessKey key, State myState) throws PerfGoatException {
		super(key);
		setState(myState);
	}
	
	@Override
	public void start() throws PerfGoatException {
		
		if (!getState().equals(State.STOPPED)) {
			throw new TjpIllegalStateException( DefaultFactory.getFactory().getMessages().testMustBeStoppedBeforeAttemptingToStart( this.getProcessKey().getKey() ) );
		}
		setState(State.START_IN_PROGRESS);
		
	}
	@Override
	public void stop() throws PerfGoatException {
		if (!getState().equals(State.STARTED)) {
			String pk = "not-yet-created";
			if (this.getProcessKey()!=null)
				pk = this.getProcessKey().getKey();
			
			throw new TjpIllegalStateException( DefaultFactory.getFactory().getMessages().testMustBeStartedBeforeAttemptingToStop(
					pk, 
					getState(), 
					State.STOPPED ) );
		}
		
		setState(State.STOP_IN_PROGRESS);
	}

	@Override
	public List<StateChangeListener> getListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setListeners(List<StateChangeListener> listeners) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerStateChangeListener(StateChangeListener scl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireStateChange(ProcessKey key, State newState) {
		// TODO Auto-generated method stub
		
	}
}
