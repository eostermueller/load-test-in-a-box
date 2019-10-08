package com.github.eostermueller.havoc.processmodel;

import com.github.eostermueller.havoc.FixedLengthQueue;
import com.github.eostermueller.havoc.PerfGoatException;
import com.github.eostermueller.havoc.launcher.CannotFindTjpFactoryClass;
import com.github.eostermueller.havoc.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.havoc.launcher.DefaultFactory;
import com.github.eostermueller.havoc.launcher.StateChange;
import com.github.eostermueller.havoc.launcher.Suite;

public class ProcessModelSingleton {
	PerfGoatException causeOfSystemFailure = null;
	public PerfGoatException getCauseOfSystemFailure() {
		return causeOfSystemFailure;
	}
	public void setCauseOfSystemFailure(PerfGoatException causeOfSystemFailure) {
		this.causeOfSystemFailure = causeOfSystemFailure;
	}
	/**
	 * @st0lenFr0m: https://stackoverflow.com/questions/7855700/why-is-volatile-used-in-double-checked-locking
	 */
	   private volatile static ProcessModelSingleton instance;
	    private ProcessModelSingleton() {}
	    public static ProcessModelSingleton getInstance() throws ConfigVariableNotFoundException, PerfGoatException {
	        if (instance == null) {
	            synchronized (ProcessModelSingleton.class) {
	                if (instance == null) {
	        			ProcessModelBuilder builder = DefaultFactory.getFactory().createProcessModelBuilder();
	        			Suite suite = builder.build();
	                    instance = new ProcessModelSingleton();
	        			instance.setProcessModel(suite);
	                }
	            }
	        }
	        return instance;
	    }	
	private Suite suite;
	private FixedLengthQueue<StateChange> stateChangeHistory = new FixedLengthQueue<StateChange>();
	
	public Suite getProcessModel() {
		return suite;
	}
	public void setProcessModel(Suite suite) {
		this.suite = suite;
	}
	public FixedLengthQueue<StateChange> getStateChangeHistory() {
		return stateChangeHistory;
	}
	public void setStateChangeHistory(FixedLengthQueue<StateChange> stateChangeHistory) {
		this.stateChangeHistory = stateChangeHistory;
	}
	
	

}
