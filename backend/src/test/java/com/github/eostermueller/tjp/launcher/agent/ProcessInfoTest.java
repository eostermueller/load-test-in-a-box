package com.github.eostermueller.tjp.launcher.agent;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import com.github.eostermueller.havoc.PerfGoatException;

public class ProcessInfoTest {

	private static final String DELIMITER = "!";

	@Test
	public void canParseProcessKey() throws PerfGoatException {
		String myHostName = "myhostname.com";
		long processIdentifier = 2356;
		String processType = "MyLittleTest";
		String suite = "myprocesses";
		long tinyId = 23552; 
		
		ProcessKey processInfo = ProcessKey.create(suite,Level.CHILD,processType,myHostName,tinyId, processIdentifier);
		
		Assert.assertEquals("Unable to find hostname and pid",
				suite 
				+ DELIMITER + Level.CHILD 
				+ DELIMITER + processType 
				+ DELIMITER + myHostName 
				+ DELIMITER + String.valueOf(tinyId).trim() 
				+ DELIMITER + String.valueOf(processIdentifier).trim(), 
				processInfo.getKey());
		
	}

}
