package com.github.eostermueller.snail4j.launcher;


import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.processmodel.ProcessModelSingleton;
import com.github.eostermueller.snail4j.util.FixedLengthQueue;

public class SequentialProcessSuite extends AbstractSequentialProcessSuite implements Suite {
	
	public SequentialProcessSuite(ProcessKey keySuite) {
		this.setProcessKey(keySuite);
		this.registerStateChangeListener(this.getChildStateChangeListener() );
	}

	public StateChangeListener getChildStateChangeListener() {
		return childStateChangeListener;
	}

	StateChangeListener childStateChangeListener = new StateChangeListener() {

		private void childStateHasChanged(ProcessKey thisKey, State newState) throws Snail4jException {
			switch(newState) {
			case START_IN_PROGRESS:
				if (SequentialProcessSuite.this.isFirstRunner(thisKey)) {
					SequentialProcessSuite.this.fireStateChange(SequentialProcessSuite.this.getProcessKey(), State.START_IN_PROGRESS);
				}					
				break;
			case STARTED:
				if (SequentialProcessSuite.this.isFinalRunner(thisKey)) {
					SequentialProcessSuite.this.fireStateChange(SequentialProcessSuite.this.getProcessKey(), State.STARTED);
				} else {
					StateMachine nextRunner = null; 
					StateMachine runner = find( thisKey );
					if (runner !=null) {
						nextRunner = SequentialProcessSuite.this.findNextRunner(runner.getProcessKey());
						if (nextRunner!=null) 
							nextRunner.start();
					} else {
						throw new TjpIllegalStateException(DefaultFactory.getFactory().getMessages().unknownProcessStateTransition(thisKey, newState) );
					}
				} 
				break;
			}
			
		}
		/**
		 * Start the "next" state, once previous has started.
		 */
		@Override
		public void stateHasChanged(ProcessKey thisKey, State newState) throws Snail4jException {

//			if (thisKey==null)
//				return;
//			System.out.println("New state: [" + newState + "] key ["  + thisKey.getKey() + "]");
			switch (thisKey.getLevel()) {
				case CHILD: //start/stop changes of child processes.
					this.childStateHasChanged(thisKey, newState);
				case PARENT: //summary state change of the entire suite.  at STARTED if all processes have been STARTED.
					this.parentStateHasChanged(thisKey, newState);
			}
		}
		
		/**
		 * Will need this when tracking current state.
		 * @param thisKey
		 * @param newState
		 */
		private void parentStateHasChanged(ProcessKey thisKey, State newState) {
			
			
		}
	};
	/** 
	 * 
	 */
	@Override
	public void addRunnerInOrder(StateMachine child) {

		/** This is where the parent (aka suite) asks to be 
		 *  apprised of all child state changes.
		 */
		child  											 
			.registerStateChangeListener(				 
				this.getChildStateChangeListener());	
		
		this.getRunners().add(child);
	}

	@Override
	public void start() throws Snail4jException {
		
		StateMachine firstRunner = this.getRunners().get(0);
		firstRunner.start();
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFinalRunner(ProcessKey pk) {
		int indexOfRunner = this.indexOf( pk );
		return (indexOfRunner == this.getRunners().size()-1);
	}

	@Override
	public boolean isFirstRunner(ProcessKey pk) {
		int indexOfRunner = this.indexOf( pk );
		return (indexOfRunner == 0);
	}

	@Override
	public String toHumanReadableString() {
		StringBuilder sb = new StringBuilder();
		
		
		for(StateMachine stateMachine : getRunners()) {
			sb.append("\n###### State Machine:\n");
			sb.append(stateMachine.toHumanReadableString());
		}
		
		return sb.toString();
	}


}
