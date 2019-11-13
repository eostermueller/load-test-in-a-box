package com.github.eostermueller.snail4j.launcher.agent.suite;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Event;
import com.github.eostermueller.snail4j.launcher.Level;
import com.github.eostermueller.snail4j.launcher.ProcessKey;
import com.github.eostermueller.snail4j.launcher.SequentialProcessSuite;
import com.github.eostermueller.snail4j.launcher.State;
import com.github.eostermueller.snail4j.launcher.StateChangeListener;
import com.github.eostermueller.snail4j.launcher.StateMachine;
import com.github.eostermueller.snail4j.launcher.StdoutProcessRunnerJdk8;
import com.github.eostermueller.snail4j.launcher.Suite;
import com.github.eostermueller.snail4j.launcher.agent.DoNothingProcessRunner;
import com.github.eostermueller.snail4j.launcher.agent.MockServerProcess;
import com.github.eostermueller.snail4j.launcher.agent.TestConfiguration;

public class SimpleProcessSuiteTest {
	 @Rule
	    public TemporaryFolder testFolder = new TemporaryFolder();
	 File tmpFolder = null;
	 
	 @Before
	 public void before() throws IOException {
		tmpFolder = testFolder.getRoot();
	 }
		
	/**
	 * Records actual state changes so we can verify that the 
	 * right ones took place.
	 * @author erikostermueller
	 *
	 */
	static class StateChange {
		public StateChange(ProcessKey processKey, State state) {
			this.state = state;
			this.processKey = processKey;
		}
		State state = null;
		ProcessKey processKey = null;
	}

	@Test
	public void canFindRunner() throws Snail4jException {

		List<StateChange> stateChanges = new ArrayList<StateChange>();
		
		ProcessKey keyOne = ProcessKey.create("mySuite", Level.CHILD, "one", ProcessKey.getLocalHost().toString() );
		ProcessKey keyTwo = ProcessKey.create("mySuite", Level.CHILD, "two", ProcessKey.getLocalHost().toString() );
		ProcessKey keyThree = ProcessKey.create("mySuite", Level.CHILD, "three", ProcessKey.getLocalHost().toString() );

		StdoutProcessRunnerJdk8 one = new StdoutProcessRunnerJdk8(keyOne);
		StdoutProcessRunnerJdk8 two = new StdoutProcessRunnerJdk8(keyTwo);
		StdoutProcessRunnerJdk8 three = new StdoutProcessRunnerJdk8(keyThree);
		
		TestConfiguration t = new TestConfiguration();
		
		MockServerProcess testOne = new MockServerProcess(this.tmpFolder,t.getJavaHome(),keyOne.getTinyId());
		MockServerProcess testTwo = new MockServerProcess(this.tmpFolder,t.getJavaHome(),keyTwo.getTinyId());
		MockServerProcess testThree = new MockServerProcess(this.tmpFolder,t.getJavaHome(),keyThree.getTinyId());

		one.setProcessBuilder(testOne.getProcessBuilder());
		two.setProcessBuilder(testTwo.getProcessBuilder());
		three.setProcessBuilder(testThree.getProcessBuilder());

		ProcessKey keySuite = ProcessKey.create("mySuite", Level.PARENT, "Aggregator", ProcessKey.getLocalHost().toString() );
		
		Suite suite = new SequentialProcessSuite(keySuite);
		suite.addRunnerInOrder(one);
		suite.addRunnerInOrder(two);
		suite.addRunnerInOrder(three);
		
		StateMachine r = suite.find( two.getProcessKey() );
		
		assertEquals(two.getProcessKey().getKey(),r.getProcessKey().getKey() );
	}
	
	@Test
	public void canFindNextRunner() throws Snail4jException {

		List<StateChange> stateChanges = new ArrayList<StateChange>();
		
		ProcessKey keyOne = ProcessKey.create("mySuite", Level.CHILD, "one", ProcessKey.getLocalHost().toString() );
		ProcessKey keyTwo = ProcessKey.create("mySuite", Level.CHILD, "two", ProcessKey.getLocalHost().toString() );
		ProcessKey keyThree = ProcessKey.create("mySuite", Level.CHILD, "three", ProcessKey.getLocalHost().toString() );
		
		DoNothingProcessRunner one = new DoNothingProcessRunner(keyOne);
		DoNothingProcessRunner two = new DoNothingProcessRunner(keyTwo);
		DoNothingProcessRunner three = new DoNothingProcessRunner(keyThree);

		ProcessKey keySuite = ProcessKey.create("mySuite", Level.PARENT, "Aggregator", ProcessKey.getLocalHost().toString() );
		
		Suite s = new SequentialProcessSuite(keySuite);
		s.addRunnerInOrder(one);
		s.addRunnerInOrder(two);
		s.addRunnerInOrder(three);
		
		StateMachine r = s.findNextRunner( two.getProcessKey() );
		
		assertEquals(three.getProcessKey().getKey(),r.getProcessKey().getKey() );
		
	}
	
	@Test
	public void canDetectWhichStateIsFirstAndFinal() throws Exception {
		int startExceptionCount = DefaultFactory.getFactory().getEventHistory().getEvents().size();
		ProcessKey keyOne = ProcessKey.create("mySuite", Level.CHILD, "one", ProcessKey.getLocalHost().toString() );
		ProcessKey keyTwo = ProcessKey.create("mySuite", Level.CHILD, "two", ProcessKey.getLocalHost().toString() );
		ProcessKey keyThree = ProcessKey.create("mySuite", Level.CHILD, "three", ProcessKey.getLocalHost().toString() );
		
		TestConfiguration t = new TestConfiguration();
		
		MockServerProcess testOne = new MockServerProcess(this.tmpFolder,t.getJavaHome(),keyOne.getTinyId());
		testOne.compile();
		
		MockServerProcess testTwo = new MockServerProcess(this.tmpFolder,t.getJavaHome(),keyTwo.getTinyId());
		testTwo.compile();
		MockServerProcess testThree = new MockServerProcess(this.tmpFolder,t.getJavaHome(),keyThree.getTinyId());
		testThree.compile();
		
		StdoutProcessRunnerJdk8 one = new StdoutProcessRunnerJdk8(keyOne);  
		one.setStartupCompleteMessage(testOne.getStartupCompleteMessage());
		
		StdoutProcessRunnerJdk8 two = new StdoutProcessRunnerJdk8(keyTwo);
		two.setStartupCompleteMessage(testTwo.getStartupCompleteMessage());
		
		StdoutProcessRunnerJdk8 three = new StdoutProcessRunnerJdk8(keyThree);
		three.setStartupCompleteMessage(testThree.getStartupCompleteMessage());
		
		one.setProcessBuilder(testOne.getProcessBuilder());
		two.setProcessBuilder(testTwo.getProcessBuilder());
		three.setProcessBuilder(testThree.getProcessBuilder());
		
		ProcessKey keySuite = ProcessKey.create("mySuite", Level.PARENT, "Aggregator", ProcessKey.getLocalHost().toString() );
		Suite suite = new SequentialProcessSuite(keySuite);
		suite.addRunnerInOrder(one);
		suite.addRunnerInOrder(two);
		suite.addRunnerInOrder(three);
		
		Assert.assertFalse(suite.isFinalRunner(one.getProcessKey()) );
		Assert.assertFalse(suite.isFinalRunner(two.getProcessKey()) );
		Assert.assertTrue(suite.isFinalRunner(three.getProcessKey()) );

		Assert.assertTrue(suite.isFirstRunner(one.getProcessKey()) );
		Assert.assertFalse(suite.isFirstRunner(two.getProcessKey()) );
		Assert.assertFalse(suite.isFirstRunner(three.getProcessKey()) );
	}
	@Test
	public void canConfigureAndRunSimpleSuite() throws Exception {
		
		int startExceptionCount = DefaultFactory.getFactory().getEventHistory().getEvents().size();
		ProcessKey keyOne = ProcessKey.create("mySuite", Level.CHILD, "one", ProcessKey.getLocalHost().toString() );
		ProcessKey keyTwo = ProcessKey.create("mySuite", Level.CHILD, "two", ProcessKey.getLocalHost().toString() );
		ProcessKey keyThree = ProcessKey.create("mySuite", Level.CHILD, "three", ProcessKey.getLocalHost().toString() );
		
		TestConfiguration t = new TestConfiguration();
		
		MockServerProcess testOne = new MockServerProcess(this.tmpFolder,t.getJavaHome(),keyOne.getTinyId());
		testOne.compile();
		
		MockServerProcess testTwo = new MockServerProcess(this.tmpFolder,t.getJavaHome(),keyTwo.getTinyId());
		testTwo.compile();
		MockServerProcess testThree = new MockServerProcess(this.tmpFolder,t.getJavaHome(),keyThree.getTinyId());
		testThree.compile();
		
		StdoutProcessRunnerJdk8 one = new StdoutProcessRunnerJdk8(keyOne);  
		one.setStartupCompleteMessage(testOne.getStartupCompleteMessage());
		
		StdoutProcessRunnerJdk8 two = new StdoutProcessRunnerJdk8(keyTwo);
		two.setStartupCompleteMessage(testTwo.getStartupCompleteMessage());
		
		StdoutProcessRunnerJdk8 three = new StdoutProcessRunnerJdk8(keyThree);
		three.setStartupCompleteMessage(testThree.getStartupCompleteMessage());
		
		one.setProcessBuilder(testOne.getProcessBuilder());
		two.setProcessBuilder(testTwo.getProcessBuilder());
		three.setProcessBuilder(testThree.getProcessBuilder());

		ProcessKey keySuite = ProcessKey.create("mySuite", Level.PARENT, "Aggregator", ProcessKey.getLocalHost().toString() );
		
		Suite suite = new SequentialProcessSuite(keySuite);
		suite.addRunnerInOrder(one);
		suite.addRunnerInOrder(two);
		suite.addRunnerInOrder(three);
		

		List<StateChange> stateChanges = new ArrayList<StateChange>();
		StateChangeListener scl = (processKey, newState) 
				-> stateChanges.add( new StateChange( processKey, newState) );
		
		suite.registerStateChangeListener(scl);
		
		suite.start();
		Thread.sleep(3000);
		
//		String debug = DefaultFactory.getFactory().getEventHistory().debug();
//		System.out.println("Debug: " + debug);
		Assert.assertEquals(startExceptionCount, DefaultFactory.getFactory().getEventHistory().getEvents().size() );
		
		StateChange actualStateChanges[] =  stateChanges.toArray( new StateChange[0] );
		
		assertEquals(keySuite.getKey(),actualStateChanges[0].processKey.getKey());
		assertEquals(State.START_IN_PROGRESS,actualStateChanges[0].state);
		
		assertEquals(keySuite.getKey(),actualStateChanges[1].processKey.getKey());
		assertEquals(State.STARTED,actualStateChanges[1].state);
		
		StateChange[] expectedStateChanges = {
		  new StateChange(keySuite, State.START_IN_PROGRESS),
		  new StateChange(keySuite, State.STARTED)
		};
		
//		StateChange[] expectedStateChanges = {
//				  new StateChange(keyOne, State.START_IN_PROGRESS),
//				  new StateChange(keyOne, State.STARTED),
//				  new StateChange(keyTwo, State.START_IN_PROGRESS),
//				  new StateChange(keyTwo, State.STARTED),
//				  new StateChange(keyThree, State.START_IN_PROGRESS),
//				  new StateChange(keyThree, State.STARTED),
//				};
		
		
		//Assert.assertArrayEquals(expectedStateChanges, actualStateChanges);
		
		
		
		
	}


}
