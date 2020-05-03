package com.github.eostermueller.snail4j.launcher.agent;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

//import org.junit.jupiter.api.Test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.rules.TemporaryFolder;

import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.AbstractStdoutStateChanger;
import com.github.eostermueller.snail4j.launcher.Level;
import com.github.eostermueller.snail4j.launcher.ProcessKey;
import com.github.eostermueller.snail4j.launcher.State;
import com.github.eostermueller.snail4j.launcher.StateChangeListener;
import com.github.eostermueller.snail4j.launcher.StdoutProcessRunner;
import com.github.eostermueller.snail4j.launcher.StdoutProcessRunnerJdk8;
import com.github.eostermueller.snail4j.launcher.StdoutStateChanger;


/**
 * These tests confirm that we can start a headless OS process and assess success/failures
 * by looking at unstructured messages in the stdout.
 * @author erikostermueller
 *
 */
public class BasicProcessManagementTest {
	boolean ynStateChanged = false;
	
	 @Rule
	    public TemporaryFolder testFolder = new TemporaryFolder();
	 File tmpFolder = null;
	 @Before
	 public void setup() throws IOException {
		 this.tmpFolder = testFolder.newFolder();
	 }
//		/**
//		 * The @Disabled annotation is causing problem: https://stackoverflow.com/a/58421650/2377579
//		 * ...so using the unbecoming "@DisabledOnOs" below.
//		 * 
//		 * @throws Exception
//		 */
//	 
//	@Test
//	@DisabledOnOs({OS.WINDOWS, OS.AIX, OS.SOLARIS, OS.MAC, OS.LINUX})
//	@Disabled("Enable once java8 support is dropped")
//	public void canRunJavaProgramAndReadStdout() throws Exception {
//		ProcessKey key = ProcessKey.create(this.getClass().getCanonicalName(), Level.CHILD, "executingJavaClass");
//		
//		TestConfiguration t = new TestConfiguration();
//		
//		MockServerProcess testOne = new MockServerProcess(this.tmpFolder,t.getJavaHome(),key.getTinyId());
//		testOne.setSleepMsAfterStartup(0);
//		testOne.setSleepMsBeforeStartup(0);
//		testOne.compile();
//		
//		StdoutProcessRunner p = new StdoutProcessRunnerJdk8(key);
//		p.setProcessBuilder( testOne.getProcessBuilder() );
//		
//		StateChangeListener sscl = new StateChangeListener() {
//			@Override
//			public void stateHasChanged(ProcessKey processKey, State newState) {
//				BasicProcessManagementTest.this.ynStateChanged = true;
//			}
//		};
//		
//		StdoutStateChanger ssc = new AbstractStdoutStateChanger() {
//			@Override
//			public void evaluateStdoutLine(String s) throws Snail4jException {
//				if (s.indexOf("Startup Complete") >=0 ) {
//					this.fireStateChange(key, State.STARTED);
//				}
//			}
//		};
//		ssc.registerStateChangeListener(sscl);
//
//		p.setStdoutStateChanger(ssc);
//		p.start();
//		Thread.sleep(1000);
//		Assert.assertTrue(this.ynStateChanged);
//		
//	}

}
