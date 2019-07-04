package com.github.eostermueller.tjp.launcher.agent.runner;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import com.github.eostermueller.tjp.launcher.agent.AbstractStdoutStateChanger;
import com.github.eostermueller.tjp.launcher.agent.DefaultFactory;
import com.github.eostermueller.tjp.launcher.agent.InputStreamWatcher;
import com.github.eostermueller.tjp.launcher.agent.ProcessKey;
import com.github.eostermueller.tjp.launcher.agent.State;
import com.github.eostermueller.tjp.launcher.agent.StateChangeListener;
import com.github.eostermueller.tjp.launcher.agent.StateMachine;
import com.github.eostermueller.tjp.launcher.agent.StdoutStateChanger;
import com.github.eostermueller.tjp.launcher.agent.TjpException;
import com.github.eostermueller.tjp.launcher.agent.TjpIllegalStateException;

/**
 * @author erikostermueller
 *
 */
public class StdoutProcessRunnerJdk9 extends AbstractProcessRunner implements StateMachine {

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
	public StdoutProcessRunnerJdk9(ProcessKey processKey) throws TjpException {
		super(processKey);
		setState(State.STOPPED);

		StdoutStateChanger stateChanger = new AbstractStdoutStateChanger() {
			/**
			 * Need this also to push into an error state with a bad compile msg:
			 * "Could not find or load main class"
			 */
			@Override
			public void evaluateStdoutLine(String s) throws TjpException {
				if (s!=null) {
					if (s.indexOf(StdoutProcessRunnerJdk9.this.getStartupCompleteMessage() ) >=0 ) {
						this.fireStateChange(StdoutProcessRunnerJdk9.this.getProcessKey(), State.STARTED);
					} else if (s.toLowerCase().indexOf("error" ) >=0 ) {
						System.out.println("found exception: " + s);
						TjpException te = new TjpException(s);
						DefaultFactory.getFactory().getEventHistory().addException("trying to launch [" + StdoutProcessRunnerJdk9.this.getDebugInfo() + "]", te);
						
						this.fireStateChange(StdoutProcessRunnerJdk9.this.getProcessKey(), State.ABEND);
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
	public void start() throws TjpException {
		
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
	/**
	 * @stolenFrom:  https://kodejava.org/how-do-i-get-process-id-of-a-java-application/ 
	 * 
	 */
	private long getPid() {
		 RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();

	        // Get name representing the running Java virtual machine.
	        // It returns something like 6460@AURORA. Where the value
	        // before the @ symbol is the PID.
	        String jvmName = bean.getName();

	        // Extract the PID by splitting the string returned by the
	        // bean.getName() method.
	        long pid = Long.valueOf(jvmName.split("@")[0]);
	        return pid;
	}
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
	public void stop() throws TjpException {
		if (!getState().equals(State.STARTED)) {
			throw new TjpIllegalStateException( DefaultFactory.getFactory().getMessages().testMustBeStartedBeforeAttemptingToStop(this.getProcessKey().getKey(), getState(), State.STOPPED ) );
		}
		setState(State.STOP_IN_PROGRESS);
	}

}
