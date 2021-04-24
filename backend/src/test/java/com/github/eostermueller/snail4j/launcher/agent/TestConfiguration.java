package com.github.eostermueller.snail4j.launcher.agent;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.JdkUtils;
import com.github.eostermueller.snail4j.NonStaticOsUtils;
import com.github.eostermueller.snail4j.Snail4jException;
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
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	 
	Path tjpHome = null;
	Path javaHome = null;
	
	
	public TestConfiguration() throws Snail4jException {
		LOGGER.debug("start of TestConfiguration ctor 1");
		this.setJavaHome( new NonStaticOsUtils().get_JAVA_HOME()  );
		
		LOGGER.debug("stop of TestConfiguration ctor 1");
	}
	public TestConfiguration(Path pgHome, Path javaHome) throws Snail4jException {
		LOGGER.debug("start of TestConfiguration ctor 2");
		this.setSnail4jHome(pgHome);
		this.setJavaHome(javaHome);
		LOGGER.debug("stop of TestConfiguration ctor 2");

	}

	@Override
	public Path getSutAppHome() {
		return this.getSnail4jHome().resolve( Paths.get("javaPerformanceTroubleshooting-master") );
	}


	@Override
	public Path getJavaHome() throws Snail4jException {
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
