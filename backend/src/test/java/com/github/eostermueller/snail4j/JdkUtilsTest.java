package com.github.eostermueller.snail4j;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import com.github.eostermueller.snail4j.util.JdkUtils;
import com.github.eostermueller.snail4j.util.JdkUtils.ProcessDescriptor;
import com.github.eostermueller.snail4j.util.OsUtils.OsResult;


class JdkUtilsTest {
	
	
	@Test
	void canDetectActualJavaPath() throws Snail4jException {
		
		Path currentJavaPath = JdkUtils.getCurrentJavaPath();
		assertNotNull(currentJavaPath);
		assertTrue( currentJavaPath.toFile().exists() );

	}
	@Test
	void jdkDetectionDoesNotThrowException() {
		try {
			JdkUtils.isJdk();
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	/**
	 * 
	 * @throws Snail4jException
	 */
	void canLocateJdkBinExeAndExe() throws Snail4jException {

		OsResult jcmd = JdkUtils.executeJdkBinCmd( "jcmd");
		System.out.println("jcmd command: " + jcmd.stdout);
		String somePartOfPackageOrClassNameOfTestRunnerWillSurelyIncludeThis = "jcmd";
		assertTrue( jcmd.stdout.toLowerCase().indexOf( somePartOfPackageOrClassNameOfTestRunnerWillSurelyIncludeThis ) >0 );
	}
	@Test
	void canDetectWhetherJdkSpecificationInThisList() throws Snail4jException {
		
		String[] listOfSpecifications = { "1.1", "1.2" };
		
		assertFalse( JdkUtils.isJavaSpecificationInList( "9", listOfSpecifications ) );
		
		assertFalse( JdkUtils.isJavaSpecificationInList( "1.8", listOfSpecifications ) );
		
		assertTrue( JdkUtils.isJavaSpecificationInList( "1.1", listOfSpecifications ) );
		
		try {
			assertTrue( JdkUtils.isJavaSpecificationInList( "", listOfSpecifications ) );
			fail("empty input should have triggered exception");
		} catch (Snail4jException e) {
			//snail4j returned exception with the bad data, just as we want.
		}
		
		try {
			assertTrue( JdkUtils.isJavaSpecificationInList( null, listOfSpecifications ) );
			fail("empty input should have triggered exception");
		} catch (Snail4jException e) {
			//snail4j returned exception with the bad data, just as we want.
		}
		
		try {
			assertTrue( JdkUtils.isJavaSpecificationInList( "9", null ) );
			fail("empty input should have triggered exception");
		} catch (Snail4jException e) {
			//snail4j returned exception with the bad data, just as we want.
		}
		try {
			assertTrue( JdkUtils.isJavaSpecificationInList( "9", new String[] {} ) );
			fail("empty input should have triggered exception");
		} catch (Snail4jException e) {
			//snail4j returned exception with the bad data, just as we want.
		}
	}
	@Test
	void canParseJCmdOutput() throws Snail4jException {
		ProcessDescriptor[] processes = JdkUtils.getJavaProcesses( );
		boolean ynFoundJCmd = false;
		
		for(ProcessDescriptor desc : processes) {
			assertTrue( desc.pid > 0);
			//Would like to assert for non-null process, but jcmd output for eclipse process has exactly this -- a blank process string/descriptor
			
			if (desc.commandLine.indexOf(JdkUtils.JCMD) > -1)
				ynFoundJCmd = true;
		}
		assertTrue(ynFoundJCmd);
	}
	

	

}
