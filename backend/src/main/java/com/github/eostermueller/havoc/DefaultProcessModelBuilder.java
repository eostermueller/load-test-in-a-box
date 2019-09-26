package com.github.eostermueller.havoc;

import java.nio.file.Path;

import com.github.eostermueller.havoc.launcher.CannotFindTjpFactoryClass;
import com.github.eostermueller.havoc.launcher.Configuration;
import com.github.eostermueller.havoc.launcher.Level;
import com.github.eostermueller.havoc.launcher.ProcessKey;
import com.github.eostermueller.havoc.launcher.SequentialProcessSuite;
import com.github.eostermueller.havoc.launcher.StdoutProcessRunner;
import com.github.eostermueller.havoc.launcher.Suite;

public class DefaultProcessModelBuilder implements ProcessModelBuilder {
	private static final String SUT_UNDER_LOAD = "SutUnderLoad";
	private static final String ORCHESTRATOR = "Orchestrator";
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
	 */
	protected StdoutProcessRunner getJMeterProcess() {
		
//		ProcessBuilder pb = new ProcessBuilder(
//				this.getJavaHome().toAbsolutePath().toString(),
//				"-classpath",".",this.getClassName() 
//				);
		
//		pb.directory( this.getCurrentWorkingDir() );
		return null;
	}
	protected StdoutProcessRunner getDbProcess() {
		return null;
	}
	protected StdoutProcessRunner getWiremockProcess() {
		return null;
	}
	protected StdoutProcessRunner getSut() {
		return null;
	}

}
