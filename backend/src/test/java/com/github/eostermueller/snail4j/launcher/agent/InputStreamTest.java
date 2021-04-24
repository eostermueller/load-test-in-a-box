package com.github.eostermueller.snail4j.launcher.agent;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.launcher.AbstractStdoutStateChanger;
import com.github.eostermueller.snail4j.launcher.InputStreamWatcher;
import com.github.eostermueller.snail4j.launcher.StdoutStateChanger;

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
		Assertions.assertTrue(this.foundThird);
	}
}