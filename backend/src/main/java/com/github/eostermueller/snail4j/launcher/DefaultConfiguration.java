package com.github.eostermueller.snail4j.launcher;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author erikostermueller
 *
 */
public class DefaultConfiguration implements Configuration {

	private String sutAppZipFileName;

	/**
	 * This is the most important constructor in the project :-)
	 */
	public DefaultConfiguration() {
    		this.setUserHomeDir(		Paths.get( getUserHomeDirString() )	);
		
			this.setJavaHome( 			Paths.get( System.getProperty("java.home")  ) );
			this.setSnail4jHome(		Paths.get( this.getUserHomeDirString(), ".snail4j" )			);
			
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

			this.setH2DataFileHome(		Paths.get( this.getSnail4jHome().toString() , "data") );
			this.setH2DataFileName		("perfSandboxDb.mv.db");
			
			this.setJMeterFilesZipFileName("jmeterFiles.zip");
			this.setJMeterFilesHome(Paths.get( this.getSnail4jHome().toString() , "jmeterFiles") );
			
			
			this.setJMeterNonGuiPort(4455); 
			this.setLoadGeneratorLaunchCmd("#{mavenHome}/bin/mvn -Dsnail4j.jmeter.port=#{jmeterNonGuiPort} -Dmaven.repo.local=#{mavenRepositoryHome} --offline -f #{jmeterFilesHome}/pom-load.xml -Djmeter.test=#{jmeterFilesHome}/traffic.jmx -Pno-gui clean verify");
			//mvn clean verify -Pno-gui

			/*
			 * ToDo:  embed parameters in the following for tcp listen ports for each SUT component:  h2, wiremock, tjp
			 * 
			 * Use same appr0ach as this, ab0ve: -Dsnail4j.jmeter.port=#{jmeterNonGuiPort}
			 * 
			 */
			this.setProcessManagerLaunchCmd("#{mavenHome}/bin/mvn -Dmaven.repo.local=#{mavenRepositoryHome} --offline verify");
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
