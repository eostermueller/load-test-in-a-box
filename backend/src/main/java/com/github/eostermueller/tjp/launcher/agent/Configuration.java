package com.github.eostermueller.tjp.launcher.agent;

import java.nio.file.Path;

public interface Configuration {
	/**
	 * Path with 
	 * -- the runnable-jar file for the agent resides.
	 * -- the zip file with littleMock, jpt and maven and maven local repo.
	 * @return
	 */
	public Path getTjpHome();
	public void setTjpHome(Path p);
	public Path getLittleMockHome();
	public Path getJavaPerformanceTroubleshootingHome();
	public Path getMavenHome();
	public Path getJavaHome();
	public void setJavaHome(Path p);
	public int getMaxExceptionCountPerEvent();
}
