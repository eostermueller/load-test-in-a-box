package com.github.eostermueller.snail4j.launcher.agent;

import static org.junit.Assert.*;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Test;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.CommandLine;
import com.github.eostermueller.snail4j.launcher.ConfigLookup;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.DefaultConfigLookup;

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
	public void canDoNoHarmToCmdLineWithoutVariables() throws ConfigVariableNotFoundException, Snail4jException {
		String str = "java -classpath . MyClass";
		CommandLine cmdLine = DefaultFactory.getFactory().createNewCommandLine(str);
		cmdLine.setCommandLine(str);
		
		String[] expected = { "java", "-classpath", ".", "MyClass" };
		assertArrayEquals(expected,cmdLine.getUnprocessedCommandLine() );
		assertArrayEquals(expected,cmdLine.getProcessedCommandLine()   );
	}
	@Test
	public void canFindAndResolveVariable() throws ConfigVariableNotFoundException, Snail4jException {
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
	public void canFindAndResolveVariableWithSpaces() throws ConfigVariableNotFoundException, Snail4jException {
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
