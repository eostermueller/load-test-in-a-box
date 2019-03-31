package com.github.eostermueller.tjp.launcher.agent;

import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class TestTheTestConfiguration {

	
	public static final String unix_ABS_PATH_TO_TJP = "/Users/erikostermueller/Documents/src/jdist/tjpUnzipped/tjp";
	public static final String unix_JAVA_HOME = "/Library/Java/JavaVirtualMachines/openjdk-11.0.2.jdk/Contents/Home";
	private static final String LITTLE_MOCK_MASTER = "littleMock-master";
	
	private static final String JAVA_PERF_TROUBLESHOOTING_MASTER = "javaPerformanceTroubleshooting-master";
	private static final String MAVEN_HOME_SUFFIX = "maven/apache-maven-3.5.4";
	
	private static final String win_ABS_PATH_TO_TJP = "C:/UseXrs/erikXostermueller/Documents/src/jdist/tjpUnzipped/tjp";
	private static final String win_JAVA_HOME = "C:/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home";
	
	@Test
	public void canCreatePathsBasedOnInstallationHome_unixStyle() {
		Path tjpHome = Paths.get(unix_ABS_PATH_TO_TJP);
		Path javaHome = Paths.get(this.unix_JAVA_HOME);
		char myPlatformSeparater = '/';
				
		TestConfiguration testConfiguration = new TestConfiguration(tjpHome, javaHome);
		
		Path littleMockHome = testConfiguration.getLittleMockHome();
		Assert.assertEquals(unix_ABS_PATH_TO_TJP + myPlatformSeparater + LITTLE_MOCK_MASTER, littleMockHome.toAbsolutePath().toString() );

		Path jptHome = testConfiguration.getJavaPerformanceTroubleshootingHome();
		Assert.assertEquals(unix_ABS_PATH_TO_TJP + myPlatformSeparater + JAVA_PERF_TROUBLESHOOTING_MASTER, jptHome.toAbsolutePath().toString() );
		
		Path mavenHome = testConfiguration.getMavenHome();
		Assert.assertEquals(unix_ABS_PATH_TO_TJP + myPlatformSeparater + MAVEN_HOME_SUFFIX, mavenHome.toAbsolutePath().toString() );

	}
	/**
	 * 
	 */
	@Test
	public void canCreatePathsBasedOnInstallationHome_winStyle() {
		Path tjpHome = Paths.get(win_ABS_PATH_TO_TJP);
		Path javaHome = Paths.get(win_JAVA_HOME);
		char myPlatformSeparater = '/';
				
		TestConfiguration testConfiguration = new TestConfiguration(tjpHome, javaHome);
		
		Path littleMockHome = testConfiguration.getLittleMockHome();
		String littleMockHomeExpected = win_ABS_PATH_TO_TJP + myPlatformSeparater + LITTLE_MOCK_MASTER;
		String littleMockHomeActual = littleMockHome.toString();
		//Assert.assertEquals(win_ABS_PATH_TO_TJP + myPlatformSeparater + LITTLE_MOCK_MASTER, littleMockHome.toAbsolutePath().toString() );
		Assert.assertEquals(littleMockHomeExpected, littleMockHomeActual );

		Path jptHome = testConfiguration.getJavaPerformanceTroubleshootingHome();
		Assert.assertEquals(win_ABS_PATH_TO_TJP + myPlatformSeparater + JAVA_PERF_TROUBLESHOOTING_MASTER, jptHome.toString() );
		
		Path mavenHome = testConfiguration.getMavenHome();
		Assert.assertEquals(win_ABS_PATH_TO_TJP + myPlatformSeparater + MAVEN_HOME_SUFFIX, mavenHome.toString() );
		

	}

}
