package com.github.eostermueller.snail4j.launcher.agent;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.DefaultConfiguration;

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
	//public static final String unix_JAVA_HOME = "/Library/Java/JavaVirtualMachines/openjdk-11.0.2.jdk/Contents/Home";
	
	/**
	 * WOW this needs to go away, hard coding paths from my machine.
	 */
	public TestConfiguration() {
		this.setJavaHome( 			Paths.get( System.getProperty("java.home")  ) );
		
	}
	public TestConfiguration(Path pgHome, Path javaHome) {
		this.setSnail4jHome(pgHome);
		this.setJavaHome(javaHome);
	}

	@Override
	public Path getSutAppHome() {
		return this.getSnail4jHome().resolve( Paths.get("javaPerformanceTroubleshooting-master") );
	}


	@Override
	public Path getJavaHome() {
		return this.javaHome;
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
