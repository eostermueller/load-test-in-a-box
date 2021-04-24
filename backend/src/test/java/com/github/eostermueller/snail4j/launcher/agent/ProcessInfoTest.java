package com.github.eostermueller.snail4j.launcher.agent;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.Level;
import com.github.eostermueller.snail4j.launcher.ProcessKey;

public class ProcessInfoTest {

	private static final String DELIMITER = "!";

	@Test
	public void canParseProcessKey() throws Snail4jException {
		String myHostName = "myhostname.com";
		long processIdentifier = 2356;
		String processType = "MyLittleTest";
		String suite = "myprocesses";
		long tinyId = 23552; 
		
		ProcessKey processInfo = ProcessKey.create(suite,Level.CHILD,processType,myHostName,tinyId, processIdentifier);
		
		Assertions.assertEquals(
				suite 
				+ DELIMITER + Level.CHILD 
				+ DELIMITER + processType 
				+ DELIMITER + myHostName 
				+ DELIMITER + String.valueOf(tinyId).trim() 
				+ DELIMITER + String.valueOf(processIdentifier).trim(), 
				processInfo.getKey(),
				"Unable to find hostname and pid");
		
	}

}
