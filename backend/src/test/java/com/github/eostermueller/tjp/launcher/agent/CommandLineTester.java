package com.github.eostermueller.tjp.launcher.agent;

import static org.junit.Assert.*;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Test;

import com.github.eostermueller.havoc.PerfGoatException;
import com.github.eostermueller.havoc.launcher.CannotFindTjpFactoryClass;
import com.github.eostermueller.havoc.launcher.CommandLine;
import com.github.eostermueller.havoc.launcher.ConfigLookup;
import com.github.eostermueller.havoc.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.havoc.launcher.Configuration;
import com.github.eostermueller.havoc.launcher.DefaultConfigLookup;
import com.github.eostermueller.havoc.launcher.DefaultFactory;

/**
 * 
 * Currently does Not support 
 * <ul>
 * <li>multiple levels of indirection.  For ex:, if the value of #{myVar} is #{yourVar), then #{yourVar) will not get resolved. </li>
 * <li>space inside a single parameter.  As a workaround, reference variables that have spaces</li>
 * </ul>
 *  
 * @author erikostermueller
 *
 */
public class CommandLineTester {

	@Test
	public void canDoNoHarmToCmdLineWithoutVariables() throws ConfigVariableNotFoundException, PerfGoatException {
		String str = "java -classpath . MyClass";
		CommandLine cmdLine = DefaultFactory.getFactory().createNewCommandLine(str);
		cmdLine.setCommandLine(str);
		
		String[] expected = { "java", "-classpath", ".", "MyClass" };
		assertArrayEquals(expected,cmdLine.getUnprocessedCommandLine() );
		assertArrayEquals(expected,cmdLine.getProcessedCommandLine()   );
	}
	@Test
	public void canFindAndResolveVariable() throws ConfigVariableNotFoundException, PerfGoatException {
		ConfigLookup lkup = new DefaultConfigLookup() {
			
			public String getValue(String variableName) {
				return "MyClass";
			}
		};
		
		String str = "java -classpath . #{WhichClass}";
		CommandLine cmdLine = DefaultFactory.getFactory().createNewCommandLine(str);
		cmdLine.setCommandLine(str);
		
		
		cmdLine.setConfigLookup(lkup);
		
		String[] expectedUnProcessed = { "java", "-classpath", ".", "#{WhichClass}" };
		assertArrayEquals(expectedUnProcessed,cmdLine.getUnprocessedCommandLine() );
		String[] expectedProcessed = { "java", "-classpath", ".", "MyClass" };
		
		String[] actual = cmdLine.getProcessedCommandLine();
		String actualDebug = Arrays.asList(actual).toString();
		assertArrayEquals(actualDebug,expectedProcessed, actual );
		

	}
	@Test
	public void canFindAndResolveVariableWithSpaces() throws ConfigVariableNotFoundException, PerfGoatException {
		ConfigLookup lkup = new DefaultConfigLookup() {
			
			public String getValue(String variableName) {
				return "/java 1.8";
			}
		};
		
		String str = "#{javaHome}/java -classpath . YourClass";
		CommandLine cmdLine = DefaultFactory.getFactory().createNewCommandLine(str);
		cmdLine.setCommandLine(str);
		
		
		cmdLine.setConfigLookup(lkup);
		String[] expectedUnProcessed = { "#{javaHome}/java", "-classpath", ".", "YourClass" };
		assertArrayEquals(expectedUnProcessed,cmdLine.getUnprocessedCommandLine() );
		String[] expectedProcessed = { "/java 1.8/java", "-classpath", ".", "YourClass" };
		String[] actual = cmdLine.getProcessedCommandLine();
		String actualDebug = Arrays.asList(actual).toString();
		assertArrayEquals(actualDebug,expectedProcessed, actual   );
		

	}

}
