package com.github.eostermueller.havoc.processmodel;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.havoc.PerfGoatException;
import com.github.eostermueller.havoc.launcher.AbstractStdoutStateChanger;
import com.github.eostermueller.havoc.launcher.CommandLine;
import com.github.eostermueller.havoc.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.havoc.launcher.Configuration;
import com.github.eostermueller.havoc.launcher.DefaultFactory;
import com.github.eostermueller.havoc.launcher.Level;
import com.github.eostermueller.havoc.launcher.ProcessKey;
import com.github.eostermueller.havoc.launcher.SequentialProcessSuite;
import com.github.eostermueller.havoc.launcher.State;
import com.github.eostermueller.havoc.launcher.StateChange;
import com.github.eostermueller.havoc.launcher.StateChangeListener;
import com.github.eostermueller.havoc.launcher.StdoutProcessRunner;
import com.github.eostermueller.havoc.launcher.StdoutProcessRunnerJdk8;
import com.github.eostermueller.havoc.launcher.StdoutStateChanger;
import com.github.eostermueller.havoc.launcher.Suite;

public class DefaultProcessModelBuilder implements ProcessModelBuilder {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private static final String SUT_UNDER_LOAD = "SutUnderLoad";
	private static final String ORCHESTRATOR = "Orchestrator";
	private static final String PROC_TYPE_JMETER = "load-generator";
	private static final String PROC_TYPE_SUT = "SystemUnderTest";
	private static final String PROC_TYPE_WIREMOCK = "Wiremock";
	private static final String PROC_TYPE_DB = "H2Db";
	private Path javaHome;
	public Path getJavaHome() {
		return javaHome;
	}
	public void setJavaHome(Path javaHome) {
		this.javaHome = javaHome;
	}
	
	private Configuration cfg = null;

	public DefaultProcessModelBuilder(Configuration val) {
		this.cfg = val;
		this.setJavaHome( cfg.getJavaHome() );
	}
	public Configuration getConfiguration() {
		return this.cfg;
	}
	/* (non-Javadoc)
	 * @see com.github.eostermueller.havoc.ProcessModelBuilder#build()
	 */
	@Override
	public Suite build() throws ConfigVariableNotFoundException, PerfGoatException {
		ProcessKey keySuite = ProcessKey.create(SUT_UNDER_LOAD, Level.PARENT, ORCHESTRATOR, ProcessKey.getLocalHost().toString() );
		
		Suite suite = new SequentialProcessSuite(keySuite);
		suite.addRunnerInOrder( this.getWiremockProcess() );
		suite.addRunnerInOrder( this.getDbProcess() );
		suite.addRunnerInOrder( this.getSut() );
		suite.addRunnerInOrder( this.getJMeterProcess() );

		StateChangeListener scl = (processKey, newState) 
				-> ProcessModelSingleton.getInstance().getStateChangeHistory().add( new StateChange( processKey, newState) );
				
		suite.registerStateChangeListener(scl);
		
		

//		List<StateChange> stateChanges = new ArrayList<StateChange>();
//		StateChangeListener scl = (processKey, newState) 
//				-> stateChanges.add( new StateChange( processKey, newState) );
		
//		suite.registerStateChangeListener(scl);
		
//		suite.start();
		
		
		return suite;
	}
	/**
	 * Here is the general idea of the process:
	 * <pre>
	 * mvn -f pom-load.xml -Djmeter.test=$1.jmx
	 * </pre>
	 * @return
	 * @throws PerfGoatException 
	 * @throws ConfigVariableNotFoundException 
	 */
	protected StdoutProcessRunner getJMeterProcess() throws ConfigVariableNotFoundException, PerfGoatException {
		
		CommandLine cmdLine = DefaultFactory.getFactory().createNewCommandLine(
				this.getConfiguration().getJMeterLaunchCmd()
				);
		
		ProcessBuilder pb = new ProcessBuilder( cmdLine.getProcessedCommandLine() );
		pb.directory( this.getConfiguration().getJMeterFilesHome().toFile() );
		
		ProcessKey keyJMeter = ProcessKey.create(
				SUT_UNDER_LOAD, 
				Level.CHILD, 
				PROC_TYPE_JMETER, 
				ProcessKey.getLocalHost().toString()
				);
		
		StdoutProcessRunner jmeter = new StdoutProcessRunnerJdk8(keyJMeter);
		jmeter.setProcessBuilder(pb);
		return jmeter;
	}
	/**
	 * <PRE>
[INFO] ------------------------------------------------------------------------
[INFO] Building Maven Stub Project (No POM) 1
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- h2-maven-plugin:1.0:spawn (default-cli) @ standalone-pom ---
[INFO] H2 server spawned at tcp://localhost:9092
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
	 * </PRE>
	 * @return
	 * @throws ConfigVariableNotFoundException
	 * @throws PerfGoatException
	 */
	protected StdoutProcessRunner getDbProcess() throws ConfigVariableNotFoundException, PerfGoatException {
		CommandLine cmdLine = DefaultFactory.getFactory().createNewCommandLine(
				this.getConfiguration().getDbLaunchCmd()
				);
		
		ProcessBuilder pb = new ProcessBuilder( cmdLine.getProcessedCommandLine() );
		pb.directory( this.getConfiguration().getH2DataFileHome().toFile() );
		
		ProcessKey keyDb = ProcessKey.create(
				SUT_UNDER_LOAD, 
				Level.CHILD, 
				PROC_TYPE_DB, 
				ProcessKey.getLocalHost().toString()
				);
		
		StdoutProcessRunner h2 = new StdoutProcessRunnerJdk8(keyDb);
		h2.setStartupCompleteMessage("H2 server spawned at tcp");
		h2.setProcessBuilder(pb);
		return h2;
	}
	/**
	 * Unfortunately, this remote shutdown doesn't work with my maven-wiremock startup.
	 * Perhaps open a bug report?
	 * https://github.com/tomakehurst/wiremock/pull/79
	 * @return
	 * @throws PerfGoatException 
	 * @throws ConfigVariableNotFoundException 
	 */
	protected StdoutProcessRunner getWiremockProcess() throws ConfigVariableNotFoundException, PerfGoatException {
		CommandLine cmdLine = DefaultFactory.getFactory().createNewCommandLine(
				this.getConfiguration().getWiremockLaunchCmd()
				);
		ProcessBuilder pb = new ProcessBuilder( cmdLine.getProcessedCommandLine() );
		pb.directory( this.getConfiguration().getWiremockFilesHome().toFile() );
		
		ProcessKey keyWiremock = ProcessKey.create(
				SUT_UNDER_LOAD, 
				Level.CHILD, 
				PROC_TYPE_WIREMOCK, 
				ProcessKey.getLocalHost().toString()
				);
		
		StdoutProcessRunner wiremock = new StdoutProcessRunnerJdk8(keyWiremock);
		wiremock.setStartupCompleteMessage("port:");
		wiremock.setProcessBuilder(pb);
		
//		StateChangeListener scl = new StateChangeListener() {
//
//			@Override
//			public void stateHasChanged(ProcessKey processKey, State newState) throws PerfGoatException {
//				StateChange sc = new StateChange(processKey, newState);
//				ProcessModelSingleton.getInstance().getStateChangeHistory().add(sc);
//			}
//		};
		
		
////		StdoutStateChanger ssc = (StdoutStateChanger) new AbstractStdoutStateChanger() {
////			/** Startup banner taken from wiremock-standalone-2.20.0.jar:
////			 * <pre>
////						port:                         8080
////						enable-browser-proxying:      false
////						disable-banner:               false
////						no-request-journal:           false
////						verbose:                      true			 
////			 * </pre>
////			 */
////			@Override
////			public void evaluateStdoutLine(String s) throws PerfGoatException {
////				if (s.indexOf("port:") >=0 ) {
////					this.fireStateChange(keyWiremock, State.STARTED);
////				}
////			}
////		};
//		ssc.registerStateChangeListener(scl);
//		wiremock.setStdoutStateChanger(ssc);
		return wiremock;
		
		
	}
	/**
	 * <PRE>
2019-09-29 22:31:30.008  INFO 32183 --- [  restartedMain] o.s.i.channel.PublishSubscribeChannel    : Channel 'application.errorChannel' has 1 subscriber(s).
2019-09-29 22:31:30.008  INFO 32183 --- [  restartedMain] o.s.i.endpoint.EventDrivenConsumer       : started _org.springframework.integration.errorLogger
2019-09-29 22:31:30.067  INFO 32183 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-09-29 22:31:30.073  INFO 32183 --- [  restartedMain] c.g.e.tjp2.rest.WorkloadController       : Started WorkloadController in 4.523 seconds (JVM running for 5.15)	 * </PRE>
	 * @return
	 * @throws ConfigVariableNotFoundException
	 * @throws PerfGoatException
	 */
	protected StdoutProcessRunner getSut() throws ConfigVariableNotFoundException, PerfGoatException {
		LOGGER.info("sut: " + this.getConfiguration().getSutLaunchCmd());
		//this.getConfiguration().getSutLaunchCmd()
		CommandLine cmdLine = DefaultFactory.getFactory().createNewCommandLine(
				this.getConfiguration().getSutLaunchCmd()
				);
		
		ProcessBuilder pb = new ProcessBuilder( cmdLine.getProcessedCommandLine() );
		pb.directory( this.getConfiguration().getSutHome().toFile() );
		
		ProcessKey keySut = ProcessKey.create(
				SUT_UNDER_LOAD, 
				Level.CHILD, 
				PROC_TYPE_SUT, 
				ProcessKey.getLocalHost().toString()
				);
		
		StdoutProcessRunner sut = new StdoutProcessRunnerJdk8(keySut);
		sut.setStartupCompleteMessage("Tomcat started on port(s):");
		sut.setProcessBuilder(pb);
		
		return sut;
	}
}
