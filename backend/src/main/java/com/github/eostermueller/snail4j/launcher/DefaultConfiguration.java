package com.github.eostermueller.snail4j.launcher;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author erikostermueller
 *
 */
public class DefaultConfiguration implements Configuration {

	private static final String SPACE = " ";
	private String sutAppZipFileName;
	private long sutAppPort;
	private long loadGenerationThreads;
	private long loadGenerationTargetPort;
	private long loadGenerationRampupTimeInSeconds;
	private long loadGenerationDurationInSeconds;
	private String loadGenerationTargetHost;

	/**
	 * This is the most important constructor in the project :-)
	 */
	public DefaultConfiguration() {
    		this.setUserHomeDir(		Paths.get( getUserHomeDirString() )	);
		
			this.setJavaHome( 			Paths.get( System.getProperty("java.home")  ) );
			this.setSnail4jHome(		Paths.get( this.getUserHomeDirString(), ".snail4j" )			);
			this.setGlowrootHome(			Paths.get( this.getSnail4jHome().toString() , "glowroot") );
			this.setGlowrootZipFileName ("glowroot-0.13.5-dist.zip");
			
			this.setMavenHome(			Paths.get( this.getSnail4jHome().toString() , this.getMavenZipFileNameWithoutExtension() )		);
			this.setMavenRepositoryHome(Paths.get( this.getSnail4jHome().toString() , "repository" )		);
			
			this.setSutAppHome(			Paths.get( this.getSnail4jHome().toString() , "sutApp") );
			this.setSutAppZipFileName ("sutApp.zip");
			
			this.setSutKillFile(        Paths.get( this.getSnail4jHome().toString() , "deleteMeToStopSnail4jSut.txt") );
			
			
			this.setLogDir(             Paths.get( this.getSnail4jHome().toString() , "log" )		);
			this.setSystemUnderTestStdoutLogFileName("systemUnderTest.log");
			this.setLoadGeneratorStdoutLogFileName("loadGenerator.log");

			this.setWiremockFilesHome(		Paths.get( this.getSnail4jHome().toString() , "wiremock") );
			this.setWiremockFilesZipFileName ("wiremockFiles.zip");
			
			
			this.setProcessManagerHome(		Paths.get( this.getSnail4jHome().toString() , "processManager") );
			this.setProcessManagerZipFileName ("processManager.zip");

			this.setJMeterNonGuiPort(4455);
			
			this.setH2DataFileHome(		Paths.get( this.getSnail4jHome().toString() , "data") );
			this.setH2DataFileName		("perfSandboxDb.mv.db");
			
			this.setJMeterFilesZipFileName("jmeterFiles.zip");
			this.setJMeterFilesHome(Paths.get( this.getSnail4jHome().toString() , "jmeterFiles") );
			
			
			this.setLoadGenerationTargetHost("localhost");
			this.setLoadGenerationThreads(3); //3T0TT -- from http://bit.ly/2017tjp
			this.setLoadGenerationTargetPort(8080);
			this.setLoadGenerationRampupTimeInSeconds(3);
			
			/**
			 * For 99% of the time, don't expect a single test run to last more than 20 minutes....so 60 minutes should be more than enough.
			 */
			this.setLoadGenerationDurationInSeconds(3600);
			
			
			this.setJMeterNonGuiPort(4455);
			
			/**
			 * https://jmeteronthefly.blogspot.com/2018/12/pass-parameters-from-jmeter-maven-plugin.html
			 * 
			 * The variables/names detailed below must stay in sync with variables in these two files:
			 * snail4j/jmeterFiles/pom-load.xml
			 * snail4j/jmeterFiles/load.jmx
			 */
			StringBuilder sb = new StringBuilder();
			
			                 sb.append("#{mavenHome}/bin/mvn");
			sb.append(SPACE);sb.append("--offline");
			sb.append(SPACE);sb.append("-f #{jmeterFilesHome}/pom-load.xml");
			sb.append(SPACE);sb.append("-Pno-gui");
			sb.append(SPACE);sb.append("clean verify");
			sb.append(SPACE);sb.append("-Dsnail4j.jmeter.port=#{jmeterNonGuiPort}");
			sb.append(SPACE);sb.append("-Dmaven.repo.local=#{mavenRepositoryHome}");
			sb.append(SPACE);sb.append("-Djmeter.test=#{jmeterFilesHome}/load.jmx");
			sb.append(SPACE);sb.append("-DmyHost=#{loadGenerationTargetHost}");
			sb.append(SPACE);sb.append("-DmyPort=#{loadGenerationTargetPort}");
			sb.append(SPACE);sb.append("-DmyDurationInSeconds=#{loadGenerationDurationInSeconds}");
			sb.append(SPACE);sb.append("-DmyRampupInSeconds=#{loadGenerationRampupTimeInSeconds}");
			sb.append(SPACE);sb.append("-DmyThreads=#{loadGenerationThreads}");
			this.setLoadGeneratorLaunchCmd(sb.toString());
			//mvn clean verify -Pno-gui

			/*
			 * ToDo:  embed parameters in the following for tcp listen ports for each SUT component:  h2, wiremock, tjp
			 * 
			 * Use same approach as this, ab0ve: -Dsnail4j.jmeter.port=#{jmeterNonGuiPort}
			 * 
			 */
			this.setProcessManagerLaunchCmd("#{mavenHome}/bin/mvn -Dmaven.repo.local=#{mavenRepositoryHome} --offline verify");
	}

	@Override
	public void setLoadGenerationTargetHost(String val) {
		this.loadGenerationTargetHost = val;
	}
	@Override
	public String getLoadGenerationTargetHost() {
		return this.loadGenerationTargetHost;
	}
	@Override
	public void setLoadGenerationDurationInSeconds(long val) {
		this.loadGenerationDurationInSeconds = val;
	}
	@Override
	public long getLoadGenerationDurationInSeconds() {
		return this.loadGenerationRampupTimeInSeconds;
	}
	@Override
	public void setLoadGenerationRampupTimeInSeconds(long val) {
		this.loadGenerationRampupTimeInSeconds = val;
	}
	@Override
	public long getLoadGenerationRampupTimeInSeconds() {
		return this.loadGenerationRampupTimeInSeconds;
	}
	@Override
	public void setLoadGenerationThreads(long val) {
		this.loadGenerationThreads = val;
	}
	@Override
	public long getLoadGenerationThreads() {
		return this.loadGenerationThreads;
	}
	@Override
	public void setLoadGenerationTargetPort(long val) {
		this.loadGenerationTargetPort = val;
	}
	@Override
	public long getLoadGenerationTargetPort() {
		return this.loadGenerationTargetPort;
	}

	@Override
	public  void setSutAppZipFileName(String val) {
		this.sutAppZipFileName = val;
	}
	@Override
	public String getSutAppZipFileName() {
		return this.sutAppZipFileName;
	}

	private Path javaHome = null;
	private Path sutAppHomePath;
	private Path mavenHome;
	private Path userHomeDir;
	private Path Snail4jHomeDir;
	private int maxExceptionCountPerEvent = 100;
	private String mavenZipFileNameWithoutExtension = "apache-maven-3.6.2";
	private Path mavenRepositoryHome;
	private String wiremockZipFileName;
	private String processManagerZipFileName;
	private Path wiremockHome;
	private String systemUnderTestStdoutLogFileName;
	private String loadGeneratorStdoutLogFileName;

	@Override
	public String getLoadGeneratorStdoutLogFileName() {
		return loadGeneratorStdoutLogFileName;
	}

	@Override
	public void setLoadGeneratorStdoutLogFileName(String loadGeneratorStdoutLogFileName) {
		this.loadGeneratorStdoutLogFileName = loadGeneratorStdoutLogFileName;
	}

	@Override
	public String getSystemUnderTestStdoutLogFileName() {
		return systemUnderTestStdoutLogFileName;
	}

	@Override
	public void setSystemUnderTestStdoutLogFileName(String systemUnderTestStdoutLogFileName) {
		this.systemUnderTestStdoutLogFileName = systemUnderTestStdoutLogFileName;
	}

	@Override
	public Path getWiremockFilesHome() {
		return wiremockHome;
	}

	@Override
	public void setWiremockFilesHome(Path wiremockHome) {
		this.wiremockHome = wiremockHome;
	}

	private String h2DataFileName;
	private Path h2DataFileHome;
	private Path jmeterFilesHomePath;
	private String jmeterFilesZipFileName;
	private String loadGeneratorLaunchCmd;
	private String processManagerLaunchCmd;
	private Path processManagerHome;
	private Path logDir;
	private Path sutKillFile;
	private long jmeterNonGuiPort;
	private Path glowrootHomePath;
	private String glowrootZipFileName;

	@Override
	public Path getH2DataFileHome() {
		return h2DataFileHome;
	}

	@Override
	public void setH2DataFileHome(Path h2DataFileHome) {
		this.h2DataFileHome = h2DataFileHome;
	}

	@Override
	public String getH2DataFileName() {
		return h2DataFileName;
	}

	@Override
	public void setH2DataFileName(String h2DataFileName) {
		this.h2DataFileName = h2DataFileName;
	}
	public static final String unix_ABS_PATH_TO_TJP = "/Users/erikostermueller/Documents/src/jdist/tjpUnzipped/tjp";
	public static final String unix_JAVA_HOME = "/Library/Java/JavaVirtualMachines/openjdk-11.0.2.jdk/Contents/Home";
	
	
	@Override
	public void setSutKillFile(Path val) {
		this.sutKillFile= val;
	}
	@Override
	public Path getSutKillFile() {
		return this.sutKillFile;
	}
	

	@Override
	public void setLogDir(Path val) {
		logDir = val;
		
	}
	@Override
	public Path getLogDir() {
		return logDir;
	}
	

	public DefaultConfiguration(Path pgHome, Path javaHome) {
		this.setSnail4jHome(pgHome);
		this.setJavaHome(javaHome);
	}

	@Override
	public Path getMavenRepositoryHome() {
		return this.mavenRepositoryHome;
	}
	@Override
	public void setMavenRepositoryHome(Path val) {
		this.mavenRepositoryHome = val;
	}
	@Override
	public Path getMavenHome() {
		return this.mavenHome;
	}
	@Override
	public void setMavenHome(Path val) {
		this.mavenHome = val;
	}

	@Override
	public Path getUserHomeDir() {
		return this.userHomeDir;
	}
	@Override
	public void setUserHomeDir(Path val) {
		this.userHomeDir = val;
	}
	
	@JsonIgnore
	public String getUserHomeDirString() {
		return System.getProperty("user.home");
	}
	@Override
	public String getMavenZipFileNameWithoutExtension() {
		return mavenZipFileNameWithoutExtension;
	}
	@Override
	public void setMavenZipFileNameWithoutExtension(String val) {
		this.mavenZipFileNameWithoutExtension = val;
	}
	@JsonIgnore
	@Override
	public String getMavenZipFileName() {
		return this.getMavenZipFileNameWithoutExtension() + "-bin.zip";
	}
	@Override
	public Path getSnail4jHome() {
		return this.Snail4jHomeDir;
	}
	@Override
	public void setSnail4jHome(Path  val) {
		this.Snail4jHomeDir = val;
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
		return this.maxExceptionCountPerEvent;
	}
	@Override
	public void setMaxExceptionCountPerEvent(int val) {
		this.maxExceptionCountPerEvent = val;
	}
	@Override
	public void setSutAppHome(Path val) {
		this.sutAppHomePath = val;
	}
	@Override
	public Path getSutAppHome() {
		return this.sutAppHomePath;
	}
	@Override
	public void setGlowrootHome(Path val) {
		this.glowrootHomePath = val;
	}
	@Override
	public Path getGlowrootHome() {
		return this.glowrootHomePath;
	}
	@Override
	public String getGlowrootZipFileName() {
		return this.glowrootZipFileName;
	}
	@Override
	public void setGlowrootZipFileName(String val) {
		this.glowrootZipFileName = val;
	}

	@Override
	public String getWiremockFilesZipFileName() {
		return this.wiremockZipFileName;
	}
	@Override
	public void setWiremockFilesZipFileName(String val) {
		this.wiremockZipFileName = val;
	}
	@Override
	public void setJMeterFilesHome(Path val) {
		this.jmeterFilesHomePath = val;
	}
	@Override
	public Path getJMeterFilesHome() {
		return this.jmeterFilesHomePath;
	}

	@Override
	public String getJMeterFilesZipFileName() {
		return this.jmeterFilesZipFileName;
	}
	@Override
	public void setJMeterFilesZipFileName(String val) {
		this.jmeterFilesZipFileName = val;
	}

	@Override
	public String getLoadGeneratorLaunchCmd() {
		return this.loadGeneratorLaunchCmd;
	}

	@Override
	public void setLoadGeneratorLaunchCmd(String val) {
		this.loadGeneratorLaunchCmd = val;
	}

	@Override
	public String getProcessManagerLaunchCmd() {
		return this.processManagerLaunchCmd;
	}

	@Override
	public void setProcessManagerLaunchCmd(String val) {
		this.processManagerLaunchCmd = val;
	}
	@Override
	public String getProcessManagerZipFileName() {
		return this.processManagerZipFileName;
	}
	@Override
	public void setProcessManagerZipFileName(String val) {
		this.processManagerZipFileName = val;
	}
	@Override
	public Path getProcessManagerHome() {
		return this.processManagerHome;
	}

	@Override
	public void setProcessManagerHome(Path val) {
		this.processManagerHome = val;
	}
	@Override
	public void setJMeterNonGuiPort(long val) {
		this.jmeterNonGuiPort = val;
	}
	@Override
	public long getJMeterNonGuiPort() {
		return this.jmeterNonGuiPort;
	}


}
