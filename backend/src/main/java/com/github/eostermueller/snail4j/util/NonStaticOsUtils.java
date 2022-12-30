package com.github.eostermueller.snail4j.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.Messages;

/**
 * This non-static version of OsUtils exists so state can be prepared for JUnit tests, as in TestEnvironment.java
 * @author eoste
 *
 */
public class NonStaticOsUtils {

	public static final String JAVA_HOME = "JAVA_HOME";
	private OsEnvWrapper env;

	public NonStaticOsUtils(OsEnvWrapper val) {
		this.env = val;
	}
	public NonStaticOsUtils() {
		this.env = new RealEnvWrapper();
	}
	
	public OsEnvWrapper getEnv() {
		return this.env;
	}
	
	public Path get_JAVA_HOME() throws Snail4jException {
		String javaHomeEnvVar = getEnv().get(JAVA_HOME);
		Path p = null;
		if (javaHomeEnvVar!=null && javaHomeEnvVar.trim().length()>0) {
			p = Paths.get(javaHomeEnvVar);
		}
		return p;
	}
	
	
}

