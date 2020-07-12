package com.github.eostermueller.snail4j;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Event;
import com.github.eostermueller.snail4j.launcher.Messages;

/**
 * @author eoste
 *
 */
public class InstallAdvice {
	private StartupErrorLogger startupErrorLogger;
	public InstallAdvice(StartupErrorLogger val) throws Snail4jException {
		this.startupErrorLogger = val;
		if (this.startupErrorLogger==null)
			throw new Snail4jException("Bug.  expectied logger object");
	}
	interface StartupErrorLogger {
		public void error(String msg);
		public void info(String msg);
	}
	private static final String WIREMOCK_PORT_PROPERTY = "wiremockPort";

	private static final String H2_PORT_PROPERTY = "h2Port";

	private static final String SUT_PORT_PROPERTY = "sutAppPort";

	private static final String GLOWROOT_PORT_PROPERTY = "glowrootPort";
	
	Messages messages = null;
	private static String[] UNSUPPORTED_JAVA_SPECIFICATION_VERSIONS = new String[]{ "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7" };
	public InstallAdvice() throws CannotFindSnail4jFactoryClass {
		messages = DefaultFactory.getFactory().getMessages();
	}
	static class TcpTarget {
		static TcpTarget create(String hostname, int port, String name, String snail4jPropertyName) {
			TcpTarget t = new TcpTarget();
			t.hostname = hostname;
			t.port = port;
			t.name = name;
			t.snail4jPropertyName = snail4jPropertyName;
			return t;
		}
		boolean active = false;
		String hostname = null;
		int port = -1;
		String name = null;
		String snail4jPropertyName = null;
		public String toString() {
			return String.format("Name=%s, Hostname=%s,Property=%s,port=%d\n", 
					this.name,
					this.hostname,
					this.snail4jPropertyName,
					this.port);
		}
	}
	
	public void sutPortsAreAvailable(Configuration cfg) throws CannotFindSnail4jFactoryClass, Snail4jMultiException {
		List<String> errors = new ArrayList<String>();
		int timeoutMs = 1000;
		List<TcpTarget> targets = new ArrayList<TcpTarget>();
		targets.add( TcpTarget.create(cfg.getWiremockHostname(), cfg.getWiremockPort(), "Wiremock", WIREMOCK_PORT_PROPERTY) );
		targets.add( TcpTarget.create(cfg.getH2Hostname(),cfg.getH2Port(),"H2",H2_PORT_PROPERTY) );
		targets.add( TcpTarget.create(cfg.getSutAppHostname(), cfg.getSutAppPort(), "SUT",SUT_PORT_PROPERTY) );
		targets.add( TcpTarget.create(cfg.getSutAppHostname(), cfg.getGlowrootPort(), "Glowroot",GLOWROOT_PORT_PROPERTY) );
		
		for( TcpTarget t : targets ) {
			if ( OsUtils.isTcpPortActive(t.hostname, t.port, timeoutMs) ) {
				t.active = true;
				String error = DefaultFactory.getFactory().getMessages()
						.tcpPortConflict(t.name,t.hostname,t.port,t.snail4jPropertyName);
				errors.add( error );
			}
			startupErrorLogger.info( "Snail4j Port status: " + t.toString() );
		}
		String portInitStatus = DefaultFactory.getFactory().getMessages()
				.portInitStatus(errors);
		
		DefaultFactory.getFactory().getEventHistory().getEvents().add(
				Event.create(portInitStatus) );

		if (errors.size() > 0) {
			throw new Snail4jMultiException(errors);
		}
		
	}


	public boolean isJdk() throws Snail4jException {
		boolean rc = true;

		
		rc = JdkUtils.isJdk();

		Path pathOfJava = JdkUtils.getCurrentJavaPath();
		
		if (pathOfJava==null || pathOfJava.toFile().getAbsolutePath()==null) {
			throw new Snail4jException("Unable to find sun.boot.library.path");
		}
		
		String errorMsg = messages.jreIsNotEnough (pathOfJava );
		if (errorMsg==null || "".equals(errorMsg)) {
			throw new Snail4jException("Bug:  could not create error message showing location of java.");
		}
		if (!rc)
			startupErrorLogger.error( LOG_PREFIX+errorMsg );
			
		return rc;
	}
	public boolean JAVA_HOME_pointsToCurrentJava() throws Snail4jException {
		
		
		boolean rc = false;
		rc = JdkUtils.JAVA_HOME_pointsToCurrentJava();
		
		if (!rc)
			startupErrorLogger.error(LOG_PREFIX+
					messages.JAVA_HOME_mustPointToCurrentJava(
					JdkUtils.get_JAVA_HOME(),
					JdkUtils.getCurrentJavaPath()
					));
		return rc;
	}
	
	public boolean isJavaSpecificationVersionOk() throws Snail4jException {
		boolean rc = true;

		String currentJavaSpecificationVersion = System.getProperty("java.specification.version");
		
		if (currentJavaSpecificationVersion==null || "".equals(currentJavaSpecificationVersion)) {
			throw new Snail4jException("Jdk Bug. System.getProperty is not returning a value for java.specification.version.");
		}
		
		boolean ynOnTheUnsupportedList = JdkUtils.isJavaSpecificationInList(
				currentJavaSpecificationVersion,
				UNSUPPORTED_JAVA_SPECIFICATION_VERSIONS);
		
		Path p = JdkUtils.getCurrentJavaPath();
		if (ynOnTheUnsupportedList)
			startupErrorLogger.error( 
					LOG_PREFIX+
					messages.unsupportedJavaVersion ( 
					currentJavaSpecificationVersion, 
					p, 
					UNSUPPORTED_JAVA_SPECIFICATION_VERSIONS 
					)
				);
			
		return !ynOnTheUnsupportedList;
	}
	/**
	 * 
	 * @return
	 * @throws CannotFindSnail4jFactoryClass 
	 * @throws MalformedURLException 
	 */
	public boolean isJavaHomeEnvVarOk() throws CannotFindSnail4jFactoryClass, MalformedURLException {
		boolean rc = false;
		
		
		String javaHome = System.getenv("JAVA_HOME");
		if (javaHome == null) {
			startupErrorLogger.error( 
					LOG_PREFIX+
					messages.javaHomeEnvVarNotSet() );
		} else {
			File javaHomeFolder = Paths.get(javaHome).toFile();
			if (!javaHomeFolder.exists() || !javaHomeFolder.isDirectory()) {
				startupErrorLogger.error( 
						LOG_PREFIX+
						messages.javaHomeFolderDoesNotExistOrLackingPermissions(javaHomeFolder) );
			} else {
				rc = true;
			}
		}

		if (!rc) {
			startupErrorLogger.error( 
					LOG_PREFIX+
					new DocumentationLinks().getJdkInstallAdvice().toString() );
		}
		
		return rc;
	}

}
