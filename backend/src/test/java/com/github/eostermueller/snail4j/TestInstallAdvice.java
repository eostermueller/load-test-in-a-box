package com.github.eostermueller.snail4j;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.condition.OS;

import com.github.eostermueller.snail4j.InstallAdvice.StartupLogger;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.agent.TestConfiguration;

class TestInstallAdvice implements InstallAdvice.StartupLogger {
	@TempDir
	static File sharedTempDir;
	 
	 @Rule
	  public final EnvironmentVariables environmentVariables
	    = new EnvironmentVariables();
	 
	 
	 @Test
	 @Disabled
	 public void canChangeEnvironmentVariable() {
	 	environmentVariables.clear("JAVA_HOME");
	 	assertNull(System.getenv("JAVA_HOME"));
	 }
	@Override
	public void info(String msg) {
		System.out.println(msg);
	}
	
	@Override
	public void error(String msg) {
		System.out.println(msg);
	}
	@Override
	public void debug(String msg) {
		System.out.println(msg);
	}
	 
	 @Test
	 @EnabledOnOs({OS.LINUX, OS.MAC})
	 public void canDetectWhen_JAVA_HOME_pointsToNonExistentFolder_nix() throws MalformedURLException, Snail4jException {
		 //set JAVA_HOME to a folder that does not exist
		environmentVariables.set("JAVA_HOME", File.separator + java.util.UUID.randomUUID().toString() );
		Configuration cfg = new TestConfiguration();
		
		InstallAdvice installAdvice = new InstallAdvice( this );
		
		Path p  = JdkUtils.getCurrentJavaPath();
		assertFalse( installAdvice.isJavaHomeDirExists(p) );//detect that it doesn't exist
		 
	 }
	 @Test
	 @EnabledOnOs({OS.WINDOWS})
	 public void canDetectWhen_JAVA_HOME_pointsToNonExistentFolder() throws MalformedURLException, Snail4jException {

		 //set JAVA_HOME to a folder that does not exist
		 String folderThatDoesNotExist = "C:" + File.separator + java.util.UUID.randomUUID().toString();
		environmentVariables.set("JAVA_HOME", folderThatDoesNotExist );
		String javaHome = System.getenv("JAVA_HOME");
		assertEquals(folderThatDoesNotExist,javaHome);

		Path p = Paths.get(javaHome);
		InstallAdvice installAdvice = new InstallAdvice(this);
		assertFalse( installAdvice.isJavaHomeDirExists(p) );//Detect that that folder does not exist.
		 
	 }
	 @Test
	 public void canDetectWhen_JAVA_HOME_pointsToExistingFolder() throws MalformedURLException, Snail4jException {
			environmentVariables.set("JAVA_HOME",sharedTempDir.getAbsolutePath());
			
			Path p = JdkUtils.get_JAVA_HOME();
			
			InstallAdvice installAdvice = new InstallAdvice(this);
			assertTrue( installAdvice.isJavaHomeDirExists(p) );
			
			
	 }

	 @Test
	 public void canDetectWhen_JAVA_HOME_isNotSet() throws MalformedURLException, Snail4jException {
			environmentVariables.clear("JAVA_HOME");
			Path p = JdkUtils.get_JAVA_HOME();
			
			InstallAdvice installAdvice = new InstallAdvice(this);
			assertFalse( installAdvice.isJavaHomeDirExists(p) );
			
			
	 }
	 
//	  @Test
//	  public void setEnvironmentVariable() {
//	    environmentVariables.set("name", "value");
//	    assertEquals("value", System.getenv("name"));
//	  }
}
