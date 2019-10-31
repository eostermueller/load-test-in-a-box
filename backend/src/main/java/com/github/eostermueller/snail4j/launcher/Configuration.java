package com.github.eostermueller.snail4j.launcher;

import java.nio.file.Path;

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
	public Path getJavaHome();
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
	void setLoadGenerationTargetPort(long val);
	long getLoadGenerationTargetPort();
	long getLoadGenerationRampupTimeInSeconds();
	void setLoadGenerationRampupTimeInSeconds(long val);
	void setLoadGenerationDurationInSeconds(long val);
	long getLoadGenerationDurationInSeconds();
	String getLoadGenerationTargetHost();
	void setLoadGenerationTargetHost(String val);
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
	
}
