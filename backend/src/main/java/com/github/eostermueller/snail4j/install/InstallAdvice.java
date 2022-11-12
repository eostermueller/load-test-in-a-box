package com.github.eostermueller.snail4j;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
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
	private StartupLogger startupLogger;
	public InstallAdvice(StartupLogger val) throws Snail4jException {
		messages = DefaultFactory.getFactory().getMessages();
		this.startupLogger = val;
		if (this.startupLogger==null)
			throw new Snail4jException("Bug.  expectied StartupErrorLogger object");
	}
	interface StartupLogger {
		public void error(String msg);
		public void info(String msg);
		public void debug(String msg);
	}
	private static final String WIREMOCK_PORT_PROPERTY = "wiremockPort";

	private static final String H2_PORT_PROPERTY = "h2Port";

	private static final String SUT_PORT_PROPERTY = "sutAppPort";

	private static final String GLOWROOT_PORT_PROPERTY = "glowrootPort";
	
	Messages messages = null;
	private static String[] UNSUPPORTED_JAVA_SPECIFICATION_VERSIONS = new String[]{ "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7" };
	static class TcpTarget {
		static TcpTarget create(String hostname, int port, String name, String snail4jPropertyName, boolean alsoCheckLocalhost) {
			TcpTarget t = new TcpTarget();
			t.hostname = hostname;
			t.port = port;
			t.name = name;
			t.snail4jPropertyName = snail4jPropertyName;
			t.alsoCheckLocalhost = alsoCheckLocalhost;
			return t;
		}
		static TcpTarget create(String hostname, int port, String name, String snail4jPropertyName) {
			return create(hostname, port, name, snail4jPropertyName, false);
		}
		
		boolean alsoCheckLocalhost = false;
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
	
	public int sutPortsAreAvailable(Configuration cfg) throws CannotFindSnail4jFactoryClass, Snail4jMultiException {
		List<String> errors = new ArrayList<String>();
		int timeoutMs = 20000;
		List<TcpTarget> targets = new ArrayList<TcpTarget>();
		targets.add( TcpTarget.create(cfg.getWiremockHostname(), cfg.getWiremockPort(), "Wiremock", WIREMOCK_PORT_PROPERTY) );
		targets.add( TcpTarget.create(cfg.getH2Hostname(),cfg.getH2Port(),"H2",H2_PORT_PROPERTY) );
		targets.add( TcpTarget.create(cfg.getSutAppHostname(), cfg.getSutAppPort(), "SUT",SUT_PORT_PROPERTY) );
		targets.add( TcpTarget.create(cfg.getSutAppHostname(), cfg.getGlowrootPort(), "Glowroot",GLOWROOT_PORT_PROPERTY,true) );
		
		for( TcpTarget t : targets ) {
			String hostname = t.hostname;
			if ( OsUtils.isTcpPortActive(hostname, t.port, timeoutMs) ) {
				t.active = true;
			} else if (t.alsoCheckLocalhost) {
				hostname = "localhost";
				if ( OsUtils.isTcpPortActive(hostname, t.port, timeoutMs) )
					t.active = true;
			}
			
			if (t.active) {
				String error = DefaultFactory.getFactory().getMessages()
						.tcpPortConflict(t.name,hostname,t.port,t.snail4jPropertyName);
				this.startupLogger.error(error);
				errors.add(error);
			}
			startupLogger.debug( "Snail4j Port status: " + t.toString() );
		}
		String portInitStatus = DefaultFactory.getFactory().getMessages()
				.portInitStatus(errors);
		
		DefaultFactory.getFactory().getEventHistory().getEvents().add(
				Event.create(portInitStatus) );

		if (errors.size() > 0)
			this.startupLogger.error(portInitStatus);
		else
			this.startupLogger.debug(portInitStatus);

		return errors.size();
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
			startupLogger.error( errorMsg );
			
		return rc;
	}
	
	public boolean isJavaSpecificationVersionOk() throws Snail4jException {
		String currentJavaSpecificationVersion = System.getProperty("java.specification.version");
		
		if (currentJavaSpecificationVersion==null || "".equals(currentJavaSpecificationVersion)) {
			throw new Snail4jException("Jdk Bug. System.getProperty is not returning a value for java.specification.version.");
		}
		
		boolean ynOnTheUnsupportedList = JdkUtils.isJavaSpecificationInList(
				currentJavaSpecificationVersion,
				UNSUPPORTED_JAVA_SPECIFICATION_VERSIONS);
		
		Path p = JdkUtils.getCurrentJavaPath();
		if (ynOnTheUnsupportedList)
			startupLogger.error( 
					
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
	public boolean isJavaHomeDirExists(Path java_home) throws CannotFindSnail4jFactoryClass, MalformedURLException {
		boolean rc = false;
		
		if (java_home!=null) {
			File javaHomeFolder = java_home.toFile();
			if (!javaHomeFolder.exists() || !javaHomeFolder.isDirectory()) {
				startupLogger.error( 
						messages.javaHomeFolderDoesNotExistOrLackingPermissions(javaHomeFolder) );
			} else {
				rc = true;
			}

			if (!rc) {
				startupLogger.error( 
						new DocumentationLinks().getJdkInstallAdvice().toString() );
			}
			
		}
		
		return rc;
	}

}
