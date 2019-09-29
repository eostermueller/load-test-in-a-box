package com.github.eostermueller.havoc.processmodel;

import java.nio.file.Path;

import com.github.eostermueller.havoc.PerfGoatException;
import com.github.eostermueller.havoc.launcher.CannotFindTjpFactoryClass;
import com.github.eostermueller.havoc.launcher.CommandLine;
import com.github.eostermueller.havoc.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.havoc.launcher.Configuration;
import com.github.eostermueller.havoc.launcher.DefaultFactory;
import com.github.eostermueller.havoc.launcher.Level;
import com.github.eostermueller.havoc.launcher.ProcessKey;
import com.github.eostermueller.havoc.launcher.SequentialProcessSuite;
import com.github.eostermueller.havoc.launcher.StdoutProcessRunner;
import com.github.eostermueller.havoc.launcher.StdoutProcessRunnerJdk8;
import com.github.eostermueller.havoc.launcher.Suite;

public class DefaultProcessModelBuilder implements ProcessModelBuilder {
	private static final String SUT_UNDER_LOAD = "SutUnderLoad";
	private static final String ORCHESTRATOR = "Orchestrator";
	private static final String PROC_TYPE_JMETER = "load-generator";
	private static final String PROC_TYPE_SUT = "SystemUnderTest";
	private static final String PROC_TYPE_WIREMOCK = "Wiremock";
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
	public Suite build() throws CannotFindTjpFactoryClass {
		ProcessKey keySuite = ProcessKey.create(SUT_UNDER_LOAD, Level.PARENT, ORCHESTRATOR, ProcessKey.getLocalHost().toString() );
		
		Suite suite = new SequentialProcessSuite(keySuite);
		
		
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
		
		return jmeter;
	}
	protected StdoutProcessRunner getDbProcess() {
		return null;
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
		
		ProcessKey keySut = ProcessKey.create(
				SUT_UNDER_LOAD, 
				Level.CHILD, 
				PROC_TYPE_WIREMOCK, 
				ProcessKey.getLocalHost().toString()
				);
		
		StdoutProcessRunner sut = new StdoutProcessRunnerJdk8(keySut);
		return sut;
		
		
	}
	protected StdoutProcessRunner getSut() throws ConfigVariableNotFoundException, PerfGoatException {
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
		
		StdoutProcessRunner wiremock = new StdoutProcessRunnerJdk8(keySut);
		
		return wiremock;
	}
}
