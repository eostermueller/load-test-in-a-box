package com.github.eostermueller.snail4j.launcher.agent;





/**
 * These tests confirm that we can start a headless OS process and assess success/failures
 * by looking at unstructured messages in the stdout.
 * @author erikostermueller
 *
 */
public class BasicProcessManagementTest {
	boolean ynStateChanged = false;
	
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
