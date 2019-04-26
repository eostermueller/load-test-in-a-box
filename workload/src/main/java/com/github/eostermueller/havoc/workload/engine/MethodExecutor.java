package com.github.eostermueller.havoc.workload.engine;

import com.github.eostermueller.havoc.workload.model.MethodWrapper;

public interface MethodExecutor {

	void setInstance(Object instance);

	Object getInstance();

	void setMethodWrapper(MethodWrapper methodWrapper);

	MethodWrapper getMethodWrapper();

	void execute() throws WorkloadInvocationException;

}
