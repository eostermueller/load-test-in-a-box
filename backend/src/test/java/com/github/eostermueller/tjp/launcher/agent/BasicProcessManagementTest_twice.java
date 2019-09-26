package com.github.eostermueller.tjp.launcher.agent;


import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.github.eostermueller.havoc.PerfGoatException;
import com.github.eostermueller.havoc.launcher.AbstractStdoutStateChanger;
import com.github.eostermueller.havoc.launcher.Level;
import com.github.eostermueller.havoc.launcher.ProcessKey;
import com.github.eostermueller.havoc.launcher.State;
import com.github.eostermueller.havoc.launcher.StateChangeListener;
import com.github.eostermueller.havoc.launcher.StdoutProcessRunner;
import com.github.eostermueller.havoc.launcher.StdoutProcessRunnerJdk8;
import com.github.eostermueller.havoc.launcher.StdoutStateChanger;


/**
 * These tests confirm that we can start a headless OS process and assess success/failures
 * by looking at unstructured messages in the stdout.
 * @author erikostermueller
 *
 */
public class BasicProcessManagementTest_twice {
	boolean ynStateChanged = false;
	
	 @Rule
	    public TemporaryFolder testFolder = new TemporaryFolder();
	 File tmpFolder = null;
	 @Before
	 public void before() throws IOException {
		tmpFolder = testFolder.getRoot();
	 }
	 
	@Test
	public void canRunJavaProgramAndReadStdout_twice() throws Exception {
	
		this.ynStateChanged = false;
		runJavaProgramAndReadStdout();
		this.ynStateChanged = false;
		runJavaProgramAndReadStdout();
		
	}
	public void runJavaProgramAndReadStdout() throws Exception {
		ProcessKey key = ProcessKey.create(this.getClass().getCanonicalName(), Level.CHILD, "executingJavaClass");
		
		TestConfiguration t = new TestConfiguration();
		
		MockServerProcess testOne = new MockServerProcess(this.tmpFolder,t.getJavaHome(),key.getTinyId());
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
			public void evaluateStdoutLine(String s) throws PerfGoatException {
				if (s.indexOf("Startup Complete") >=0 ) {
					this.fireStateChange(key, State.STARTED);
				}
			}
		};
		ssc.registerStateChangeListener(sscl);

		p.setStdoutStateChanger(ssc);
		p.start();
		Thread.sleep(1000);
		Assert.assertTrue(this.ynStateChanged);
		
	}

}
