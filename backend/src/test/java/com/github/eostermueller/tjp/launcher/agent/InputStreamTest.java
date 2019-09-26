package com.github.eostermueller.tjp.launcher.agent;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.github.eostermueller.havoc.launcher.AbstractStdoutStateChanger;
import com.github.eostermueller.havoc.launcher.InputStreamWatcher;
import com.github.eostermueller.havoc.launcher.StdoutStateChanger;

public class InputStreamTest {

	protected static final String MY_KEY = "myNameIsBetty";
	boolean foundThird = false;
	@Test
	public void canWatchInputStream() throws InterruptedException {
		
		StdoutStateChanger sse = new AbstractStdoutStateChanger() {

			@Override
			public void evaluateStdoutLine(String s) {
				
				if (s.toLowerCase().indexOf("third") >=0 )
					InputStreamTest.this.foundThird = true;
			}
			
		};


		String myString = "first\nsecond\nthird\n";
		InputStream is = new ByteArrayInputStream( myString.getBytes() );
		InputStreamWatcher isw = new InputStreamWatcher(
				is,sse);
		isw.start();
		Thread.sleep(100);
		Assert.assertTrue(this.foundThird);
	}
}