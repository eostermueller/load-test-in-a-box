package com.github.eostermueller.snail4j.launcher;

import java.nio.file.Path;

import com.github.eostermueller.snail4j.Snail4jException;

public interface Configuration {
	/**
	 * Path with
	 * -- the runnable-jar file for the agent resides.
	 * -- the zip file maven local repo and the SUT
	 * @return
	 */
	public Path getSutAppHome();
	public void setSutAppHome(Path val);
	public Path getMavenHome();
	
	/**
	 * Returns JAVA_HOME environment variable -- not the java.home system property.
	 * snail4j startup will fail of JAVA_HOME isn't set.
	 * @return
	 * @throws Snail4jException
	 */
	public Path getJavaHome() throws Snail4jException;
	public void setJavaHome(Path p);
	Path getUserHomeDir();
	String getUserHomeDirString();

	/*
	 * Examples:  /home/betty/.snail4j or C:\Users\betty\.snail4j
	 */
	Path getSnail4jHome();

	/**
	 * Name of the file zipped up with Launcher
	 * @return
	 */
	String getMavenZipFileName();
	String getMavenZipFileNameWithoutExtension();
	void setMavenHome(Path val);
	int getMaxExceptionCountPerEvent();
	void setMaxExceptionCountPerEvent(int val);
	void setUserHomeDir(Path val);
	void setSnail4jHome(Path val);
	void setMavenZipFileNameWithoutExtension(String val);
	Path getMavenRepositoryHome();
	void setMavenRepositoryHome(Path val);
	public String getWiremockFilesZipFileName();
	void setWiremockFilesZipFileName(String val);
	void setWiremockFilesHome(Path wiremockHome);
	Path getWiremockFilesHome();
	void setJMeterFilesHome(Path val);
	Path getJMeterFilesHome();
	String getJMeterFilesZipFileName();
	void setJMeterFilesZipFileName(String val);

	String getLoadGeneratorLaunchCmd();
	void setLoadGeneratorLaunchCmd(String val);
	String getProcessManagerLaunchCmd();
	void setProcessManagerLaunchCmd(String val);
	public String getProcessManagerZipFileName();
	void setProcessManagerZipFileName(String val);
	public Path getProcessManagerHome();
	public void setProcessManagerHome(Path val);
	Path getH2DataFileHome();
	void setH2DataFileHome(Path h2DataFileHome);
	String getH2DataFileName();
	void setH2DataFileName(String h2DataFileName);
	Path getLogDir();
	void setLogDir(Path val);
	void setSystemUnderTestStdoutLogFileName(String systemUnderTestStdoutLogFileName);
	String getSystemUnderTestStdoutLogFileName();
	void setSutKillFile(Path val);
	Path getSutKillFile();
	String getLoadGeneratorStdoutLogFileName();
	void setLoadGeneratorStdoutLogFileName(String loadGeneratorStdoutLogFileName);
	void setJMeterNonGuiPort(long val);
	long getJMeterNonGuiPort();
	String getSutAppZipFileName();
	void setSutAppZipFileName(String val);
	long getLoadGenerationThreads();
	void setLoadGenerationThreads(long val);
	long getLoadGenerationRampupTimeInSeconds();
	void setLoadGenerationRampupTimeInSeconds(long val);
	void setLoadGenerationDurationInSeconds(long val);
	long getLoadGenerationDurationInSeconds();
	String getSutAppHostname();
	void setSutAppHostname(String val);
	Path getGlowrootHome();
	void setGlowrootHome(Path val);
	void setGlowrootZipFileName(String val);
	String getGlowrootZipFileName();
	void setOsWin(boolean b);
	boolean isOsWin();
	String getMavenExeName();
	String getMavenExePath();
	void setMavenExePath(String val);
	boolean isMavenOnline();
	void setMavenOnline(boolean b);
	void setSnail4jMavenRepo(boolean b);
	boolean isSnail4jMavenRepo();
	public String getWiremockHostname();
	public int getWiremockPort();
	public String getH2Hostname();
	public int getH2Port();
	void setSutAppPort(int i);
	int getSutAppPort();
	public void setWiremockStopCmd(String val);
	public String getWiremockStopCmd();
	public String getWiremockStopStdoutLogFileName();
	public void setWiremockStopStdoutLogFileName(String args);
	
	public String getWindowsKillerProcess();
	public void setWindowsKillerProcess(String windowsKillerProcess);
	void setGlowrootPort(int i);
	public int getGlowrootPort();
	void setMaxJMeterNonGuiPort(long val);
	long getMaxJMeterNonGuiPort();
	void setStartJMeterNonGuiPort(long val);
	long getStartJMeterNonGuiPort();
	public Path getJMeterDistHome();
	public void setJMeterDistHome(Path val);
	String getJMeterExeName();
	void setJMeterExePath(String val);
	String getJMeterExePath();
	String getJMeterZipFileNameWithoutExtension();
	void setJMeterZipFileNameWithoutExtension(String val);
	void setLoadGeneratorShutdownCmd(String val);
	void setJMeterShutdownExePath(String val);
	String getJMeterShutdownExeName();
	void setUseCaseSearchCriteria(String val);
	String getUseCaseSearchCriteria();
	void setSutJvmArguments(String val);
	String getSutJvmArguments();
	void setDefaultHostname(String hostname) throws Snail4jException;



}
