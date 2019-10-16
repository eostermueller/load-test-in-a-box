package com.github.eostermueller.snail4j.launcher.agent;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.Level;
import com.github.eostermueller.snail4j.launcher.ProcessKey;
import com.github.eostermueller.snail4j.launcher.State;
import com.github.eostermueller.snail4j.launcher.StateMachine;
import com.github.eostermueller.snail4j.launcher.TjpIllegalStateException;


/**
 * Can we start and stop a load test?
 * These tests validate that.
 * @author erikostermueller
 *
 */
public class LoadTestLifecycleTest {

	@Test
	public void canStartLoadTest() throws Snail4jException {
		ProcessKey key = ProcessKey.create(this.getClass().getCanonicalName(),Level.CHILD,"testingStateChanges");
		StateMachine loadTest =  new DoNothingProcessRunner(key); //Factory.createLittleMockLoadTest(true,true);
		
		try {
			Assert.assertEquals( State.STOPPED, loadTest.getState());
			loadTest.start();
			
			ProcessKey processKey = loadTest.getProcessKey();
			Assert.assertEquals( State.START_IN_PROGRESS, loadTest.getState() );
			Assert.assertTrue( processKey.getKey().length() > 15 );
			
		} catch (TjpIllegalStateException e) {
			Assert.fail("Received illegal state exception, test should have started when asked to.");
		}
	}
	@Test
	public void canStopLoadTest() throws Snail4jException {
		ProcessKey key = ProcessKey.create(
				this.getClass().getCanonicalName(),
				Level.CHILD,
				"testingStateStop");

		StateMachine loadTest =  new DoNothingProcessRunner(key); //Factory.createLittleMockLoadTest(true,true);
		loadTest.start();//Gotta started, before we can confirm it is stoppable
		
		//Simulate that the start is complete.
		loadTest.setState(State.STARTED);
		
		try {
			loadTest.stop();
			
			Assert.assertEquals( State.STOP_IN_PROGRESS, loadTest.getState() );
			
		} catch (TjpIllegalStateException e) {
			Assert.fail("Received illegal state exception, test should have started when asked to.");
		}
	}
	
	@Test
	public void canDisplayErrorWhenReStartingLoadTest() throws Snail4jException {
		ProcessKey key = ProcessKey.create(
				this.getClass().getCanonicalName(),
				Level.CHILD,
				"testingStateNegativeTest");
		StateMachine loadTest =  new DoNothingProcessRunner(key); //Factory.createLittleMockLoadTest(true,true);
		loadTest.start();//Gotta start test first, so we can slap the hand of the user that tries to restart.
		
		try {
			loadTest.start();
			Assert.fail("Should have received an exception -- must not start a test that is already started.");
		} catch (TjpIllegalStateException e) {
			//success -- got the exception we were expecting
		}
	}
	/**
	 * To "re-stop" means to attempt to stop a test that is already in State.STOPPED
	 * @throws Snail4jException 
	 */
	@Test
	public void canDisplayErrorWhenReStoppingLoadTest() throws Snail4jException {
		ProcessKey key = ProcessKey.create(
				this.getClass().getCanonicalName(),
				Level.CHILD,
				"testingStateReStop");
		
		StateMachine loadTest =  new DoNothingProcessRunner(key); //Factory.createLittleMockLoadTest(true,true);
		Assert.assertEquals( State.STOPPED, loadTest.getState() );
		
		try {
			loadTest.stop();
			Assert.fail("Should have received an exception -- must not start a test that is already started.");
		} catch (TjpIllegalStateException e) {
			//success -- got the exception we were expecting
		}
	}
}