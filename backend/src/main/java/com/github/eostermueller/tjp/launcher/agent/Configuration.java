package com.github.eostermueller.tjp.launcher.agent;

import java.nio.file.Path;

public interface Configuration {
	/**
	 * Path with 
	 * -- the runnable-jar file for the agent resides.
	 * -- the zip file maven local repo and the SUT
	 * @return
	 */
	public Path getSutHome();
	public void setSutHome(Path val);
	public Path getMavenHome();
	public Path getJavaHome();
	public void setJavaHome(Path p);
	Path getUserHomeDir();
	String getUserHomeDirString();
	
	/*
	 * Examples:  /home/betty/.havoc or C:\Users\betty\.havoc
	 */
	Path getPerfGoatHome();
	
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
	void setPerfGoatHome(Path val);
	void setMavenZipFileNameWithoutExtension(String val);
	Path getMavenRepositoryHome();
	void setMavenRepositoryHome(Path val);
	public String getWiremockZipFileName();
	void setWiremockZipFileName(String val);
	void setH2DataFileName(String h2DataFileName);
	String getH2DataFileName();
	void setH2DataFileHome(Path h2DataFileHome);
	Path getH2DataFileHome();
	void setWiremockHome(Path wiremockHome);
	Path getWiremockHome();
}
