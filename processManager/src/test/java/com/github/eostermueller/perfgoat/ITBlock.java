package com.github.eostermueller.perfgoat;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ITBlock {

	@Test
	public void test() throws IOException {

		String killFileName = System.getProperty("com.github.eostermueller.snail4j.kill.file");
		
    	BlockOnSentinelFile blocker = new BlockOnSentinelFile(killFileName);
    	blocker.block();
		
	}

}
