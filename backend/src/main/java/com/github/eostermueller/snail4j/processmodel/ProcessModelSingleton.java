package com.github.eostermueller.snail4j.processmodel;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.snail4j.launcher.StateChange;
import com.github.eostermueller.snail4j.launcher.Suite;
import com.github.eostermueller.snail4j.util.FixedLengthQueue;

public class ProcessModelSingleton {
	Exception causeOfSystemFailure = null;
	public SystemUnderTest getSystemUnderTest() {
		return systemUnderTest;
	}
	public void setSystemUnderTest(SystemUnderTest systemUnderTest) {
		this.systemUnderTest = systemUnderTest;
	}
	public LoadGenerator getLoadGenerator() {
		return loadGenerator;
	}
	public void setLoadGenerator(LoadGenerator loadGenerator) {
		this.loadGenerator = loadGenerator;
	}
	private SystemUnderTest systemUnderTest = null;
	private LoadGenerator loadGenerator = null;
	
	public Exception getCauseOfSystemFailure() {
		return causeOfSystemFailure;
	}
	public void setCauseOfSystemFailure(Exception e) {
		this.causeOfSystemFailure = e;
	}
	/**
	 * @st0lenFr0m: https://stackoverflow.com/questions/7855700/why-is-volatile-used-in-double-checked-locking
	 */
	   private volatile static ProcessModelSingleton instance;
	   
	   private ProcessModelSingleton(SystemUnderTest sut, LoadGenerator lg) {
	    	this.systemUnderTest = sut;
	    	this.loadGenerator = lg;
	   }
	   public static ProcessModelSingleton getInstance() throws ConfigVariableNotFoundException, Snail4jException {
	        if (instance == null) {
	            synchronized (ProcessModelSingleton.class) {
	                if (instance == null) {
	        			SystemUnderTest systemUnderTest = DefaultFactory.getFactory().createSystemUnderTest();
	        			LoadGenerator loadGenerator = DefaultFactory.getFactory().createLoadGenerator();
	        			
	                    instance = new ProcessModelSingleton(systemUnderTest, loadGenerator);
	                }
	            }
	        }
	        return instance;
	    }	
//	private Suite suite;
//	private FixedLengthQueue<StateChange> stateChangeHistory = new FixedLengthQueue<StateChange>();
//	
//	public Suite getProcessModel() {
//		return suite;
//	}
//	public void setProcessModel(Suite suite) {
//		this.suite = suite;
//	}
//	public FixedLengthQueue<StateChange> getStateChangeHistory() {
//		return stateChangeHistory;
//	}
//	public void setStateChangeHistory(FixedLengthQueue<StateChange> stateChangeHistory) {
//		this.stateChangeHistory = stateChangeHistory;
//	}
}
