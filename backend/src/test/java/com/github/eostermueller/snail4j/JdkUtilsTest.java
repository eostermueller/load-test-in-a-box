package com.github.eostermueller.snail4j;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.JdkUtils.ProcessDescriptor;
import com.github.eostermueller.snail4j.OsUtils.OsResult;

class JdkUtilsTest {

	@Test
	void canLocateMyOwnJvmProcess() {
		
		ProcessDescriptor[] processDescriptors = JdkUtils.getJavaProcesses();
		for(ProcessDescriptor p : processDescriptors) {
			System.out.println("processDescriptor:" + p.toString() );
			assertNotNull( p.pid );
			assertNotNull( p.commandLine );
		}
		assertTrue( processDescriptors.length > 0);
	}
	

}
