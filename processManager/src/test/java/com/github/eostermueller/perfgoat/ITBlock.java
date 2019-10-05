package com.github.eostermueller.perfgoat;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ITBlock {

	@Test
	public void test() throws IOException {

    	BlockOnSentinelFile blocker = new BlockOnSentinelFile("/tmp/foo");
    	blocker.block();
		
	}

}
