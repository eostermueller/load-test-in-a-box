package com.github.eostermueller.snail4j.launcher.agent;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.eostermueller.snail4j.PathUtil;

public class PathUtilsTest {
	
	/**
		//Given this:  file:/Users/erikostermueller/Documents/src/jssource/havoc2/havoc2/backend/target/backend-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/
		//Return this:  /Users/erikostermueller/Documents/src/jssource/havoc2/havoc2/backend/target/backend-0.0.1-SNAPSHOT.jar
	 * 
	 */
	private static final String BASE_PATH_DECORATED = "file:/Users/erikostermueller/target/backend-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/";
	private static final String BASE_PATH_CLEAN = "/Users/erikostermueller/target/backend-0.0.1-SNAPSHOT.jar";
	
	@Test
	public void canCleanseDirtyPathToJar() throws Exception {

		PathUtil pathUtil = new PathUtil();
		
		String cleansedPath = pathUtil.cleanPath(BASE_PATH_DECORATED);
		assertEquals(BASE_PATH_CLEAN, cleansedPath);
		
		
	}
	@Test
	public void canCleanseCleanPathToJar() throws Exception {

		PathUtil pathUtil = new PathUtil();
		
		String cleansedPath = pathUtil.cleanPath(BASE_PATH_CLEAN);
		assertEquals(BASE_PATH_CLEAN, cleansedPath);
		
		
	}

}
