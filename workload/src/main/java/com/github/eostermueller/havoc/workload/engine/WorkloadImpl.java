package com.github.eostermueller.havoc.workload.engine;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WorkloadImpl implements Workload {
	List<MethodExecutor> methodExecutors = new CopyOnWriteArrayList<MethodExecutor>();  

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

}
