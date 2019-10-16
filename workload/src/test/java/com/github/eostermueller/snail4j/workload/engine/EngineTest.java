package com.github.eostermueller.snail4j.workload.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.workload.DefaultFactory;
import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.engine.MethodExecutor;
import com.github.eostermueller.snail4j.workload.engine.Workload;
import com.github.eostermueller.snail4j.workload.engine.WorkloadInvocationException;
import com.github.eostermueller.snail4j.workload.model.MethodWrapper;

class EngineTest {

	@Test
	void test() throws Snail4jWorkloadException, WorkloadInvocationException {
		
		Workload workload = DefaultFactory.getFactory().createEmptyWorkload();
		
		MethodWrapper methodWrapper = new MethodWrapper();
		methodWrapper.setDeclaringClassName(TestRunnerImpl.class.getName());
		assertEquals(
				"com.github.eostermueller.snail4j.workload.engine.TestRunnerImpl",
				methodWrapper.getDeclaringClassName()
				);
		methodWrapper.setMethodName("doIt");
		assertEquals("doIt", methodWrapper.getMethodName() );
		
		MethodExecutor methodExecutor = DefaultFactory.getFactory().createMethodExecutor(methodWrapper);
		
		AtomicBoolean yn = new AtomicBoolean(false);
		TestRunner testRunner = () -> {
			yn.set(true);
		};
		
		methodExecutor.setInstance(testRunner);
		
		workload.add(methodExecutor);
		
		workload.execute();
		
		assertTrue(yn.get());
		
		
		
	}

}
