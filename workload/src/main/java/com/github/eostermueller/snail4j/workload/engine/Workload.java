package com.github.eostermueller.snail4j.workload.engine;

/**
 * List of methods/classes currently configured (by the end user) to execute.
 * @author eoste
 *
 */
public interface Workload {
	
	boolean isEncrypted();

	void execute() throws WorkloadInvocationException;

	void add(MethodExecutor methodExecutor);

	int size();
	
	void setVerboseState(Object json);
	Object getVerboseState();

	void setEncrypted(boolean val);

}
