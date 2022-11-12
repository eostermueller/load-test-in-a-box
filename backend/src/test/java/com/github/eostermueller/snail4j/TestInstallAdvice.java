package com.github.eostermueller.snail4j;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.io.TempDir;

import com.github.eostermueller.snail4j.install.InstallAdvice;
import com.github.eostermueller.snail4j.util.JdkUtils;
import com.github.eostermueller.snail4j.util.NonStaticOsUtils;

import org.junit.jupiter.api.condition.OS;


class TestInstallAdvice implements InstallAdvice.StartupLogger {


	@TempDir
	File sharedTempDir;
	 
	TestEnvironment testEnvironment = new TestEnvironment();
	 
	 
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
		testEnvironment.set("JAVA_HOME", File.separator + java.util.UUID.randomUUID().toString() );
		
		InstallAdvice installAdvice = new InstallAdvice( this );
		
		Path p  = JdkUtils.getCurrentJavaPath();
		assertFalse( installAdvice.isJavaHomeDirExists(p) );//detect that it doesn't exist
		 
	 }
	 @Test
	 @EnabledOnOs({OS.WINDOWS})
	 public void canDetectWhen_JAVA_HOME_pointsToNonExistentFolder() throws MalformedURLException, Snail4jException {

		 //set JAVA_HOME to a folder that does not exist
		 String folderThatDoesNotExist = "C:" + File.separator + java.util.UUID.randomUUID().toString();
		testEnvironment.set( NonStaticOsUtils.JAVA_HOME, folderThatDoesNotExist );
		String javaHome = testEnvironment.get(NonStaticOsUtils.JAVA_HOME);

		Path p = Paths.get(javaHome);
		InstallAdvice installAdvice = new InstallAdvice(this);
		assertFalse( installAdvice.isJavaHomeDirExists(p) );//Detect that that folder does not exist.
		 
	 }
	 @Test
	 public void canDetectWhen_JAVA_HOME_pointsToExistingFolder() throws MalformedURLException, Snail4jException {
		 TestEnvironment testEnvironment = new TestEnvironment();
		 
		 assertNotNull(sharedTempDir);
		 
		 testEnvironment.set(NonStaticOsUtils.JAVA_HOME,sharedTempDir.getAbsolutePath());
			
		NonStaticOsUtils utils = new NonStaticOsUtils(testEnvironment);
		Path p = utils.get_JAVA_HOME();
			
		InstallAdvice installAdvice = new InstallAdvice(this);
		assertTrue( installAdvice.isJavaHomeDirExists(p) );
			
			
	 }

	 @Test
	 public void canDetectWhen_JAVA_HOME_isNotSet() throws MalformedURLException, Snail4jException {
		
		InstallAdvice installAdvice = new InstallAdvice(this);
		assertFalse( installAdvice.isJavaHomeDirExists(null) );
	 }
	 
}
