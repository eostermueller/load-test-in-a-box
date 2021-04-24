package com.github.eostermueller.snail4j.launcher.agent;


import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

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
public class BasicProcessManagementTest_twice {
	boolean ynStateChanged = false;
	
	 
	@Test
	public void canRunJavaProgramAndReadStdout_twice(@TempDir Path tmpDir) throws Exception {
	
		this.ynStateChanged = false;
		runJavaProgramAndReadStdout(tmpDir);
		this.ynStateChanged = false;
		runJavaProgramAndReadStdout(tmpDir);
		
	}
	public void runJavaProgramAndReadStdout(Path tmpDir) throws Exception {
		ProcessKey key = ProcessKey.create(this.getClass().getCanonicalName(), Level.CHILD, "executingJavaClass");
		
		TestConfiguration t = new TestConfiguration();
		
		MockServerProcess testOne = new MockServerProcess(tmpDir.toFile(),t.getJavaHome(),key.getTinyId());
		testOne.setSleepMsAfterStartup(0);
		testOne.setSleepMsBeforeStartup(0);
		testOne.compile();
		
		StdoutProcessRunner p = new StdoutProcessRunnerJdk8(key);
		p.setProcessBuilder( testOne.getProcessBuilder() );
		
		StateChangeListener sscl = new StateChangeListener() {
			@Override
			public void stateHasChanged(ProcessKey processKey, State newState) {
				BasicProcessManagementTest_twice.this.ynStateChanged = true;
			}
		};
		
		StdoutStateChanger ssc = new AbstractStdoutStateChanger() {
			@Override
			public void evaluateStdoutLine(String s) throws Snail4jException {
				if (s.indexOf("Startup Complete") >=0 ) {
					this.fireStateChange(key, State.STARTED);
				}
			}
		};
		ssc.registerStateChangeListener(sscl);

		p.setStdoutStateChanger(ssc);
		p.start();
		Thread.sleep(1000);
		Assertions.assertTrue(this.ynStateChanged);
		
	}

}
