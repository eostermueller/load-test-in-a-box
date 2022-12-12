package com.github.eostermueller.snail4j;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.processmodel.ProcessIdsSingleton;

public class ProcessIdsTest {

	@Test
	public void canStoreProcessId() {
		
		ProcessIdsSingleton processIds = ProcessIdsSingleton.getInstance();
		
		String myKey = "sut";
		long myPid = 235;
		processIds.put(myKey,myPid);
		
		assertEquals( myPid, processIds.get(myKey));
		
	}
	@Test
	public void canResetProcessId() {
		
		ProcessIdsSingleton processIds = ProcessIdsSingleton.getInstance();
		
		String myKey = "sut";
		long myPid = 235;
		processIds.put(myKey,myPid);
		
		processIds.reset(myKey);
		assertEquals( ProcessIdsSingleton.UNINIT_PID, processIds.get(myKey) );
		
	}
	@Test
	public void canChangeExistingProcessId() {
		
		ProcessIdsSingleton processIds = ProcessIdsSingleton.getInstance();
		
		String myKey = "sut";
		long myPid = 235;
		processIds.put(myKey,myPid);
		long myPid2 = 236;
		processIds.put(myKey,myPid2);
		
		assertEquals( myPid2, processIds.get(myKey));
		
	}
}
