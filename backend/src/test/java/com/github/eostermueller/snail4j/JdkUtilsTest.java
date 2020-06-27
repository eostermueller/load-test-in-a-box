package com.github.eostermueller.snail4j;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.JdkUtils.ProcessDescriptor;
import com.github.eostermueller.snail4j.OsUtils.OsResult;
import com.github.eostermueller.snail4j.launcher.agent.TestConfiguration;

class JdkUtilsTest {
	
	@Test
	/**
	 * 
	 * @throws Snail4jException
	 */
	void canLocateJdkBinExeAndExe() throws Snail4jException {

		OsResult jcmd = JdkUtils.executeJdkBinCmd(JdkUtils.get_JAVA_HOME(),"jcmd");
		System.out.println("jcmd command: " + jcmd.stdout);
		String somePartOfPackageOrClassNameOfTestRunnerWillSurelyIncludeThis = "jcmd";
		assertTrue( jcmd.stdout.toLowerCase().indexOf( somePartOfPackageOrClassNameOfTestRunnerWillSurelyIncludeThis ) >0 );
	}
	
	@Test
	void canParseJCmdOutput() throws Snail4jException {
		ProcessDescriptor[] processes = JdkUtils.getJavaProcesses( JdkUtils.get_JAVA_HOME() );
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
