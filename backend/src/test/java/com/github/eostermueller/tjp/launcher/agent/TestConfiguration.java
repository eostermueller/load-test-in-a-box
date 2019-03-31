package com.github.eostermueller.tjp.launcher.agent;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *  Defines structure of some dependent folders, as detailed here:
 *   http://erikostermueller.com/index.php/tjp-sandbox/

C:\Users\JaneDoe\Documents\tjp ( on mac /Users/JaneDoe/Documents/tjp )
 ├── bin
 ├── littleMock-master
 ├── javaPerformanceTroubleshooting-master
 └── maven
   ├── settings.xml  <<<<<===== configured to point local repository to sibling folder named 'repo'
   ├── apache-maven-3.5.4
   └── repo 
   
 * @author erikostermueller
 *
 */
public class TestConfiguration extends DefaultConfiguration implements Configuration {
	 
	Path tjpHome = null;
	Path javaHome = null;
	public static final String unix_ABS_PATH_TO_TJP = "/Users/erikostermueller/Documents/src/jdist/tjpUnzipped/tjp";
	public static final String unix_JAVA_HOME = "/Library/Java/JavaVirtualMachines/openjdk-11.0.2.jdk/Contents/Home";
	
	/**
	 * WOW this needs to go away, hard coding paths from my machine.
	 */
	public TestConfiguration() {
			this.setTjpHome(Paths.get(unix_ABS_PATH_TO_TJP) );
			this.setJavaHome( Paths.get(unix_JAVA_HOME) );
		
	}
	public TestConfiguration(Path tjpHome2, Path javaHome) {
		this.setTjpHome(tjpHome2);
		this.setJavaHome(javaHome);
	}

	@Override
	public Path getTjpHome() {
		return this.tjpHome;
	}

	@Override
	public Path getLittleMockHome() {
		return this.getTjpHome().resolve( Paths.get("littleMock-master") );
	}

	@Override
	public Path getJavaPerformanceTroubleshootingHome() {
		return this.getTjpHome().resolve( Paths.get("javaPerformanceTroubleshooting-master") );
	}

	@Override
	public Path getMavenHome() {
		return this.getTjpHome().resolve( Paths.get( "maven/apache-maven-3.5.4" ) );
	}

	@Override
	public Path getJavaHome() {
		return this.javaHome;
	}

	@Override
	public void setTjpHome(Path p) {
		this.tjpHome = p;
	}

	@Override
	public void setJavaHome(Path p) {
		this.javaHome = p;
	}

	@Override
	public int getMaxExceptionCountPerEvent() {
		return 10;
	}

}
