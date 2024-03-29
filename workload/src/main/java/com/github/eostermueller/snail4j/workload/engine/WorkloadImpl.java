package com.github.eostermueller.snail4j.workload.engine;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WorkloadImpl implements Workload {
	List<MethodExecutor> methodExecutors = new CopyOnWriteArrayList<MethodExecutor>();
	
	/**
	 * Actual type used here?
	 * com.github.eostermueller.snail4j.workload.model.UseCases
	 * Why have I left this as object?  Not sure!!!! Need to try it. 
	 * 
	 */
	Object verboseState;
	private boolean encrypted = false;

	@Override
	public void execute() throws WorkloadInvocationException {
		for( MethodExecutor executor : methodExecutors ) {
			executor.execute();
		}
	}
	@Override
	public int size() {
		return this.methodExecutors.size();
	}

	@Override
	public void add(MethodExecutor methodExecutor) {
		this.methodExecutors.add(methodExecutor);
	}
	@Override
	public void setVerboseState(Object pu) {
		this.verboseState = pu;
		
	}
	@Override
	public Object getVerboseState() {
		return this.verboseState;
	}
	
	@Override
	public void setEncrypted(boolean val) {
		encrypted = val;
	}
	@Override
	public boolean isEncrypted() {
		return encrypted;
	}

}
