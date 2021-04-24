package com.github.eostermueller.snail4j.launcher.agent;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CommandLine;
import com.github.eostermueller.snail4j.launcher.ConfigLookup;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;
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
	/**
	 * Just testing the API -- not testing any snail4j code quite yet :-)
	 * This jackson API instantiates the target class.
	 * That won't work for snail4j, where the instance (DefaultConfiguration) will already exist.
	 * @stolenFrom: https://www.baeldung.com/jackson-json-node-tree-model
	 * @throws JsonProcessingException 
	 */
	@Test
	public void canApplyNameValuePairToConfig() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper(); //Since creation of an ObjectMapper object is expensive, it's recommended that the same one should be reused for multiple operations.
		 JsonNode node = mapper.createObjectNode();
		    ((ObjectNode) node).put("id", 2016);
		    ((ObjectNode) node).put("name", "baeldung.com");
		 
		    NodeBean toValue = mapper.treeToValue(node, NodeBean.class);
		 
		    assertEquals(2016, toValue.getId());
		    assertEquals("baeldung.com", toValue.getName());		
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
		assertArrayEquals(expectedProcessed, actual, actualDebug );
		

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
		assertArrayEquals(expectedProcessed, actual ,actualDebug  );
		

	}

}
class NodeBean {
    private int id;
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
 
    public NodeBean() {
    }
 
    public NodeBean(int id, String name) {
        this.id = id;
        this.name = name;
    }
 
    // standard getters and setters
}