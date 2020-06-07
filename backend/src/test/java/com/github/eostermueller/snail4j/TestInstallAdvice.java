package com.github.eostermueller.snail4j;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.condition.OS;

import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;

class TestInstallAdvice {
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
	 @Test
	 @EnabledOnOs({OS.LINUX, OS.MAC})
	 public void canDetectWhen_JAVA_HOME_pointsToNonExistentFolder_nix() throws CannotFindSnail4jFactoryClass, MalformedURLException {
		 //set JAVA_HOME to a folder that does not exist
		environmentVariables.set("JAVA_HOME", File.separator + java.util.UUID.randomUUID().toString() );
		InstallAdvice installAdvice = new InstallAdvice();
		assertFalse( installAdvice.isJavaHomeEnvVarOk() );//detect that it doesn't exist
		 
	 }
	 @Test
	 @EnabledOnOs({OS.WINDOWS})
	 public void canDetectWhen_JAVA_HOME_pointsToNonExistentFolder() throws CannotFindSnail4jFactoryClass, MalformedURLException {

		 //set JAVA_HOME to a folder that does not exist
		 String folderThatDoesNotExist = "C:" + File.separator + java.util.UUID.randomUUID().toString();
		environmentVariables.set("JAVA_HOME", folderThatDoesNotExist );
		String javaHome = System.getenv("JAVA_HOME");
		assertEquals(folderThatDoesNotExist,javaHome);

		InstallAdvice installAdvice = new InstallAdvice();
		assertFalse( installAdvice.isJavaHomeEnvVarOk() );//Detect that that folder does not exist.
		 
	 }
	 @Test
	 public void canDetectWhen_JAVA_HOME_pointsToExistingFolder() throws CannotFindSnail4jFactoryClass, MalformedURLException {
			environmentVariables.set("JAVA_HOME",sharedTempDir.getAbsolutePath());
			
			InstallAdvice installAdvice = new InstallAdvice();
			assertTrue( installAdvice.isJavaHomeEnvVarOk() );
			
			
	 }

	 @Test
	 public void canDetectWhen_JAVA_HOME_isNotSet() throws CannotFindSnail4jFactoryClass, MalformedURLException {
			environmentVariables.clear("JAVA_HOME");
			
			InstallAdvice installAdvice = new InstallAdvice();
			assertFalse( installAdvice.isJavaHomeEnvVarOk() );
			
			
	 }
	 
//	  @Test
//	  public void setEnvironmentVariable() {
//	    environmentVariables.set("name", "value");
//	    assertEquals("value", System.getenv("name"));
//	  }
}
