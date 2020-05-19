package com.github.eostermueller.snail4j.launcher.agent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class TestTheTestConfiguration {

	
	public static final String unix_ABS_PATH_TO_TJP = "/Users/janedoe/Documents/src/jdist/tjpUnzipped/tjp";
	public static final String unix_JAVA_HOME = "/Library/Java/JavaVirtualMachines/openjdk-11.0.2.jdk/Contents/Home";
	
	private static final String JAVA_PERF_TROUBLESHOOTING_MASTER = "javaPerformanceTroubleshooting-master";
	
	private static final String win_ABS_PATH_TO_PG = "C:\\UseXrs\\janedoe\\Documents\\src\\jdist\\tjpUnzipped\\tjp";
	private static final String win_JAVA_HOME = "C:/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home";
	
	@Test
	@EnabledOnOs({OS.LINUX, OS.MAC})
	public void canCreatePathsBasedOnInstallationHome_unixStyle() {
		
		Path tjpHome = Paths.get(unix_ABS_PATH_TO_TJP);
		Path javaHome = Paths.get(this.unix_JAVA_HOME);
		char myPlatformSeparater = '/';
				
		TestConfiguration testConfiguration = new TestConfiguration(tjpHome, javaHome);
		
		Path jptHome = testConfiguration.getSutAppHome();
		Assertions.assertEquals(unix_ABS_PATH_TO_TJP + myPlatformSeparater + JAVA_PERF_TROUBLESHOOTING_MASTER, jptHome.toAbsolutePath().toString() );
		

	}
	/**
	 * 
	 */
	@Test
	@EnabledOnOs({OS.WINDOWS})
	public void canCreatePathsBasedOnInstallationHome_winStyle() {
		Path pgHome = Paths.get(win_ABS_PATH_TO_PG);
		Path javaHome = Paths.get(win_JAVA_HOME);
				
		TestConfiguration testConfiguration = new TestConfiguration(pgHome, javaHome);
		

		Path sutHome = testConfiguration.getSutAppHome();
		Assertions.assertEquals(
				win_ABS_PATH_TO_PG + File.separator + JAVA_PERF_TROUBLESHOOTING_MASTER, 
				sutHome.toString() 
			);
		
		

	}
	@EnabledOnOs({OS.WINDOWS})
	@Test
	public void canFindMvnExecutableOnWindows() {
		Path tjpHome = Paths.get(unix_ABS_PATH_TO_TJP);
		Path javaHome = Paths.get(this.unix_JAVA_HOME);
		String myPlatformSeparater = File.separator;
				
		TestConfiguration testConfiguration = new TestConfiguration(tjpHome, javaHome);
		String mavenHome = "C:\\Users\\erikostermueller\\.snail4j\\apache-maven-3.6.2";
		
		testConfiguration.setMavenHome( Paths.get(mavenHome ));
		
		testConfiguration.setOsWin(true);
		
		Assertions.assertEquals( "mvn.cmd", testConfiguration.getMavenExeName() );
		Assertions.assertEquals( mavenHome + "\\bin\\mvn.cmd", testConfiguration.createMavenExePath() );
		
		
	}
	@Test 
	public void canSetMavenOfflineFlagAndGetMavenOfflineParms_lg() {
		Path tjpHome = Paths.get(unix_ABS_PATH_TO_TJP);
		Path javaHome = Paths.get(this.unix_JAVA_HOME);
		char myPlatformSeparater = '/';
				
		TestConfiguration testCfg = new TestConfiguration(tjpHome, javaHome);
		testCfg.setSnail4jMavenRepo(false);
		String launch = testCfg.MAVEN_EXE_PATH + " verify";
		String expectedOfflineLaunch = testCfg.MAVEN_EXE_PATH + " --offline verify";
		
		testCfg.setLoadGeneratorLaunchCmd(launch);
		testCfg.setMavenOnline(true);
		
		assertEquals(
				launch, 
				testCfg.getLoadGeneratorLaunchCmd(),
				"could not assemble mvn command without offline flags"
			);
		
		testCfg.setMavenOnline(false);

		Assertions.assertEquals(
				expectedOfflineLaunch, 
				testCfg.getLoadGeneratorLaunchCmd(),
				"could not correctly assemble mvn command with offline flags"
				);
		
		
	}	
	@Test 
	public void canSetMavenOfflineFlagAndGetMavenOfflineParms_processManager() {
		Path tjpHome = Paths.get(unix_ABS_PATH_TO_TJP);
		Path javaHome = Paths.get(this.unix_JAVA_HOME);
		char myPlatformSeparater = '/';
				
		TestConfiguration testCfg = new TestConfiguration(tjpHome, javaHome);
		testCfg.setSnail4jMavenRepo(false);
		String launch = testCfg.MAVEN_EXE_PATH + " verify";
		String expectedofflineLaunch = testCfg.MAVEN_EXE_PATH + " -Dsnail4j.maven.offline.passthru=--offline --offline verify";
		
		testCfg.setProcessManagerLaunchCmd(launch);
		testCfg.setMavenOnline(true);
		
		Assertions.assertEquals(launch, testCfg.getProcessManagerLaunchCmd());
		
		testCfg.setMavenOnline(false);

		Assertions.assertEquals(expectedofflineLaunch, testCfg.getProcessManagerLaunchCmd());
		
		
	}
	@Test 
	public void canProcessMavenOnlineFlagEvenWithHardCodedOfflineParameters_lg() {
		Path tjpHome = Paths.get(unix_ABS_PATH_TO_TJP);
		Path javaHome = Paths.get(unix_JAVA_HOME);
		
		
		TestConfiguration testCfg = new TestConfiguration(tjpHome, javaHome);
		testCfg.setSnail4jMavenRepo(false);
		String expectedOnlineLaunch = TestConfiguration.MAVEN_EXE_PATH + " verify";
		String expectedOfflineLaunch = TestConfiguration.MAVEN_EXE_PATH + " --offline verify";
		
		testCfg.setLoadGeneratorLaunchCmd(TestConfiguration.MAVEN_EXE_PATH + " --offline verify");
		testCfg.setMavenOnline(true);
		
		Assertions.assertEquals( 
				expectedOnlineLaunch, 
				testCfg.getLoadGeneratorLaunchCmd(),
				"could not assemble mvn command without offline flags"
				);
		
		testCfg.setMavenOnline(false);

		Assertions.assertEquals(
				expectedOfflineLaunch, 
				testCfg.getLoadGeneratorLaunchCmd(),
				"could not correctly assemble mvn command with offline flags");
	}	
	@Test 
	public void canProcessMavenOnlineFlagEvenWithHardCodedOfflineParameters_processManager() {
		Path tjpHome = Paths.get(unix_ABS_PATH_TO_TJP);
		Path javaHome = Paths.get(unix_JAVA_HOME);
		
				
		TestConfiguration testCfg = new TestConfiguration(tjpHome, javaHome);
		testCfg.setSnail4jMavenRepo(false);
		String expectedOnlineLaunch = TestConfiguration.MAVEN_EXE_PATH + " verify";
		String expectedOfflineLaunch = TestConfiguration.MAVEN_EXE_PATH + " -Dsnail4j.maven.offline.passthru=--offline --offline verify";
		
		
		testCfg.setProcessManagerLaunchCmd(TestConfiguration.MAVEN_EXE_PATH + " -Dsnail4j.maven.offline.passthru=--offline --offline verify");
		testCfg.setMavenOnline(true);
		
		Assertions.assertEquals(expectedOnlineLaunch, testCfg.getProcessManagerLaunchCmd());
		
		testCfg.setMavenOnline(false);

		Assertions.assertEquals(expectedOfflineLaunch, testCfg.getProcessManagerLaunchCmd());
		
		
	}
	@Test
	@EnabledOnOs({OS.LINUX, OS.MAC})
	public void canFindMvnExecutableOnNonWindows() {
		Path tjpHome = Paths.get(unix_ABS_PATH_TO_TJP);
		Path javaHome = Paths.get(this.unix_JAVA_HOME);
		char myPlatformSeparater = '/';
				
		TestConfiguration testConfiguration = new TestConfiguration(tjpHome, javaHome);
		String mavenHome = "/Users/erikostermueller/.snail4j/apache-maven-3.6.2";
		
		testConfiguration.setMavenHome( Paths.get(mavenHome ));
		
		testConfiguration.setOsWin(false);
		
		Assertions.assertEquals( "mvn", testConfiguration.getMavenExeName() );
		Assertions.assertEquals( mavenHome + "/bin/mvn", testConfiguration.createMavenExePath() );
		
	}

	@Test
	public void canTurnOffUseOfSnail4jRepo_processManager() {
		Path tjpHome = Paths.get(unix_ABS_PATH_TO_TJP);
		Path javaHome = Paths.get(unix_JAVA_HOME);
		
		String baseParameters = "#{mavenExePath} -Dsnail4j.wiremock.port=#{wiremockPort} -Dsnail4j.wiremock.hostname=#{wiremockHostname} -Dsnail4j.h2.port=#{h2Port} -Dsnail4j.h2.hostname=#{h2Hostname} -Dsnail4j.sut.port=#{sutAppPort} -Dsnail4j.glowroot.port=#{glowrootPort} -Dsnail4j.sut.jvmArguments=#{sutJvmArguments} verify";		
		TestConfiguration testCfg = new TestConfiguration(tjpHome, javaHome);
		testCfg.setProcessManagerLaunchCmd(baseParameters);
		testCfg.setMavenOnline(true);
		testCfg.setSnail4jMavenRepo(false);
				
		String baseExpectedLaunch = " -Dsnail4j.wiremock.port=#{wiremockPort} -Dsnail4j.wiremock.hostname=#{wiremockHostname} -Dsnail4j.h2.port=#{h2Port} -Dsnail4j.h2.hostname=#{h2Hostname} -Dsnail4j.sut.port=#{sutAppPort} -Dsnail4j.glowroot.port=#{glowrootPort} -Dsnail4j.sut.jvmArguments=#{sutJvmArguments} verify";
		String expectedLaunch = TestConfiguration.MAVEN_EXE_PATH + baseExpectedLaunch;
		
		String expectedLaunchWithSnail4jMavenRepo = TestConfiguration.MAVEN_EXE_PATH + " -Dsnail4j.maven.repo.passthru=-Dmaven.repo.local=#{mavenRepositoryHome} -Dmaven.repo.local=#{mavenRepositoryHome}" + baseExpectedLaunch;
		System.out.println("ex: " + expectedLaunchWithSnail4jMavenRepo);
		
		Assertions.assertEquals(expectedLaunch, testCfg.getProcessManagerLaunchCmd());
		
		testCfg.setSnail4jMavenRepo(true);
		
		String actual = testCfg.getProcessManagerLaunchCmd();
		System.out.println("actual:"+ actual);
		Assertions.assertEquals(expectedLaunchWithSnail4jMavenRepo, actual);
		
	}
	

}
