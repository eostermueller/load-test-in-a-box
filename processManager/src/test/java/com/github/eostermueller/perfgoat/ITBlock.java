package com.github.eostermueller.perfgoat;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ITBlock {

	@Test
	public void test() throws IOException {

		String sentinelFileName = System.getProperty("com.github.eostermueller.perfGoat.sentinel.file");
		
    	BlockOnSentinelFile blocker = new BlockOnSentinelFile(sentinelFileName);
    	blocker.block();
		
	}

}
