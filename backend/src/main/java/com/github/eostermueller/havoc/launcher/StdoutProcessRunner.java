package com.github.eostermueller.havoc.launcher;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.havoc.PerfGoatException;

/**
 * Stores stdout/err from an OS process, and watches that output to detect certain events life of the process "Startup Complete" is the most obvious one.
 * 
 * Class is named as jdk8 because it d0es n0t reference this jdk 9 feature:
 * https://docs.oracle.com/javase/9/docs/api/java/lang/Process.html#pid--
 * https://docs.oracle.com/javase/9/docs/api/java/lang/ProcessHandle.html#pid--
 * from:
 * https://openjdk.java.net/jeps/102
 * Why?  So we can support java8 and prior!
 * 
 * Need to try these suggestions:
 * https://stackoverflow.com/questions/7260066/output-of-forked-child-process-in-java
 * https://www.javaworld.com/article/2071275/when-runtime-exec---won-t.html
 * @author erikostermueller
 *
 */
public abstract class StdoutProcessRunner extends AbstractProcessRunner implements StateMachine {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private static boolean ynMessageDisplayed_MissingStartupText = false; 

	String processType = null;

	private ProcessBuilder processBuilder;
	
	StateChangeListener parentListener = null;

	private String startupCompleteMessage;
	public StateChangeListener getParentListener() {
		return parentListener;
	}
	public void setParentListener(StateChangeListener parentListener) {
		this.parentListener = parentListener;
	}
	public void setStartupCompleteMessage(String startupCompleteMessage) {
		this.startupCompleteMessage = startupCompleteMessage;
	}
	public String getStartupCompleteMessage() {
		return this.startupCompleteMessage;
	}
	public String getDebugInfo() {
		StringBuilder sb = new StringBuilder();
		
		List<String> myParms = this.getProcessBuilder().command();
		for(String param : myParms) {
			sb.append("Param: " + param + "\n");
		}
		String cwd = "<no cwd defined>";
		sb.append(cwd + "\n");
		
		return sb.toString();
	}
	public StdoutProcessRunner(ProcessKey processKey) throws PerfGoatException {
		super(processKey);
		setState(State.STOPPED);

		StdoutStateChanger stateChanger = new AbstractStdoutStateChanger() {
			/**
			 * Need this also to push into an error state with a bad compile msg:
			 * "Could not find or load main class"
			 */
			@Override
			public void evaluateStdoutLine(String s) throws PerfGoatException {
				if (s!=null) {
					String startupMsg = StdoutProcessRunner.this.getStartupCompleteMessage();
					if (startupMsg!=null) {
						if (s.indexOf( startupMsg ) >=0 ) {
							this.fireStateChange(StdoutProcessRunner.this.getProcessKey(), State.STARTED);
						}
//						} else if (s.toLowerCase().indexOf("error" ) >=0 ) {
//							System.out.println("found exception: " + s);
//							PerfGoatException te = new PerfGoatException(s);
//							DefaultFactory.getFactory().getEventHistory().addException("trying to launch [" + StdoutProcessRunner.this.getDebugInfo() + "]", te);
//							
//							this.fireStateChange(StdoutProcessRunner.this.getProcessKey(), State.ABEND);
//						} 
					} else {
						if (!ynMessageDisplayed_MissingStartupText) {
							LOGGER.info("");
							ynMessageDisplayed_MissingStartupText = true;
						}
					}
				}
			}
		};
		
		StateChangeListener scl = (key, newState) 
				-> this.fireStateChange(key, newState);
				
		stateChanger.registerStateChangeListener(scl);
		this.setStdoutStateChanger(stateChanger);
	}
	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}
	
	@Override
	public void start() throws PerfGoatException {
		
		if (!getState().equals(State.STOPPED)) {
			throw new TjpIllegalStateException( DefaultFactory.getFactory().getMessages().testMustBeStoppedBeforeAttemptingToStart(this.getProcessKey().getKey() ) );
		}
		setState(State.START_IN_PROGRESS);
	    InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getLocalHost();
			
		       ProcessBuilder pb = getProcessBuilder();
		       //pb.directory( getCurrentDirectory() ); //Current working directory of the process.
		       pb.redirectErrorStream(true);
		       
		       /**
		        * Adding this line:
		        * pb.inheritIO();
		        * ...breaks everything, gives me this error:
		        * 
		        * [WARNING] Corrupted STDOUT by directly writing to native stream in forked JVM 1. 
		        * See FAQ web page and the dump file /Users/erikostermueller/Documents/src/jsource/tjpHeadlessAgent/target/surefire-reports/2018-10-21T13-28-41_543-jvmRun1.dumpstream
		        */
		        //debug();
		        Process process = pb.start();
		        getProcessKey().setPid( process.pid() );
		        
		        InputStreamWatcher stdoutWatcher 
		        	= new InputStreamWatcher( 
		        			process.getInputStream(),
		        			getStdoutStateChanger() );
		        			
		        stdoutWatcher.start();
				
		} catch (UnknownHostException e) {
			e.printStackTrace();
			DefaultFactory.getFactory().getEventHistory().addException("trying to launch [" + this.getDebugInfo() + "]", e);
		} catch (IOException e) {
			e.printStackTrace();
			DefaultFactory.getFactory().getEventHistory().addException("trying to launch [" + this.getDebugInfo() + "]", e);
		}
		
	}
	public String toHumanReadableString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("key: " + this.processKey + "\n" );
		sb.append("state: " + this.getState() + "\n");
		sb.append("info: " + this.getDebugInfo() + "\n");
		
		return sb.toString();
	}
	/**
	 * @stolenFrom:  https://kodejava.org/how-do-i-get-process-id-of-a-java-application/ 
	 * 
	 */
	public abstract long getPid(); 
	private void debug() {
		System.out.println("Start of debug");
		List<String> partsOfCommand = this.getProcessBuilder().command();
		for(String part : partsOfCommand) {
			System.out.println(" one part: " + part);
		}

		String currentDir = "<null>";
		System.out.println("cwd: " + currentDir );
		
		System.out.println("cwd (from ProcessBuilder): " + this.getProcessBuilder().directory().toString() );
		System.out.println("end of debug");
		
	}
	private ProcessBuilder getProcessBuilder() {
		return this.processBuilder;
	}
	public void setProcessBuilder(ProcessBuilder processBuilder) {
		this.processBuilder = processBuilder;
	}
	@Override
	public void stop() throws PerfGoatException {
		if (!getState().equals(State.STARTED)) {
			throw new TjpIllegalStateException( DefaultFactory.getFactory().getMessages().testMustBeStartedBeforeAttemptingToStop(this.getProcessKey().getKey(), getState(), State.STOPPED ) );
		}
		setState(State.STOP_IN_PROGRESS);
	}

}
