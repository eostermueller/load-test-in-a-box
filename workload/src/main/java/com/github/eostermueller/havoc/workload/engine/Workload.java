package com.github.eostermueller.havoc.workload.engine;

public interface Workload {

	void execute() throws WorkloadInvocationException;

	void add(MethodExecutor methodExecutor);

	int size();
	
	void setVerboseState(Object json);
	Object getVerboseState();

}
