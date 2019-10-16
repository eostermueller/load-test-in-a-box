package com.github.eostermueller.snail4j.workload.engine;

import com.github.eostermueller.snail4j.workload.model.MethodWrapper;

public interface MethodExecutor {

	void setInstance(Object instance);

	Object getInstance();

	void setMethodWrapper(MethodWrapper methodWrapper);

	MethodWrapper getMethodWrapper();

	void execute() throws WorkloadInvocationException;

}
