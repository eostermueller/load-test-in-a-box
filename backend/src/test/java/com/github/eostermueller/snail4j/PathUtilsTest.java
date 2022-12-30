package com.github.eostermueller.snail4j;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.eostermueller.snail4j.util.JdkUtils;
import com.github.eostermueller.snail4j.util.JdkUtils.ProcessDescriptor;
import com.github.eostermueller.snail4j.util.OsUtils;
import com.github.eostermueller.snail4j.util.OsUtils.OsResult;
import com.github.eostermueller.snail4j.util.PathUtil;


class PathUtilsTest {

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
	
	
	@Test
	void canLocateParentOfSpecificPathElement() {
		
		Path folder = Paths.get("/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib");
		
		Path actualPath = PathUtil.getParentOfSpecificPathElement(folder,"jre");

		Path expectedPath1 = Paths.get("/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/");
		assertEquals( actualPath.compareTo(expectedPath1),0 );
		
		Path expectedPath2 = Paths.get("/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home");
		assertEquals( actualPath.compareTo(expectedPath2),0 );
		
	}

	@Test
	void canLocateParentOfSpecificPathElement_linux() {
		
		Path folder = Paths.get("/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/amd64");
		
		Path actualPath = PathUtil.getParentOfSpecificPathElement(folder,"jre");

		Path expectedPath1 = Paths.get("/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/");
		assertEquals( actualPath.compareTo(expectedPath1),0 );
		
		Path expectedPath2 = Paths.get("/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64");
		assertEquals( actualPath.compareTo(expectedPath2),0 );
		
	}

	
	
	
}
