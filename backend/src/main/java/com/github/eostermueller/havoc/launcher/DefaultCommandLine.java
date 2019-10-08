package com.github.eostermueller.havoc.launcher;

import java.util.ArrayList;
import java.util.List;

import com.github.eostermueller.havoc.PerfGoatException;

public class DefaultCommandLine implements CommandLine {

	public static final String SINGLE_SPACE = " ";
	private String cmdLine;
	private VariableTokenizer tokenizer = null;
	ConfigLookup lkup = null;
	public DefaultCommandLine(String val) {
		this.tokenizer = new VariableTokenizer(val);
		this.setCommandLine(val);
	}

	@Override
	public void setCommandLine(String str) {
		this.cmdLine = str;

	}
	private String getCommandLine() {
		return this.cmdLine;
	}

	@Override
	public String[] getUnprocessedCommandLine() {
		return getCommandLine().split(SINGLE_SPACE);
	}

	public VariableTokenizer getVariableTokenizer() {
		return this.tokenizer;
	}
	
	/**
	 * overview of algorithm:
	 * Start with array of strings:  x V V x V x
	 * ..where the V's are variables (that this method will resolve) and the x's are all stuff inbetween variables, often ridden with spaces.
	 * The output is an array of arguments, each representing a space-delimited argument.
	 * All the variables are resolved (with getConfigLookup() ), but spaces within values are NOT removed, so as to support paths with spaces in them.
	 * 
	 * Adding support for nested references __should__ be easy, but testing will take a bit.
	 * What is a nester reference?
	 * Non-nested reference:  #{myJavaHome}=/lib/jvm/java1.8
	 * Nested reference:      #{myJavaHome}=#{userHome}/java
	 * <pre>
	 * 
	 * 
	 * </pre>
	 * @throws PerfGoatException 
	 * 
	 *  
	 */
	public String[] getProcessedCommandLine() throws ConfigVariableNotFoundException, PerfGoatException {
		List<String> parts = this.getVariableTokenizer().split(); // input to this method
		List<String> rc = new ArrayList<String>();  //output of this method. 
		
		for(int i = 0; i < parts.size(); i++) {
			
			
			if (this.getVariableTokenizer().isVariableNameWithPunctuation(parts.get(i))) {
				String varName = this.getVariableTokenizer().getVariableNameWithoutPunctuation(parts.get(i));
				
				String value = this.getConfigLookup().getValue(varName);
				
				/**
				 * 
				 *              ______
				 * Use Case A:  #{var} arg.          Resolved #{var} is added new rc element.
				 * 
				 *                      ______
				 * Use Case B:  arg arg #{var} arg.  Resolved #{var} is added new rc element.
				 * 
				 *                     ______
				 * Use Case C:  arg arg#(var} args   Resolved #{var} is appended to most recently added rc element.
				 */
				if (i==0) {                                                 // Use Case A, add new
					rc.add(value);
				} else if (i>0) {
					String prevPart = parts.get(parts.size()-2);
					if (prevPart.endsWith(SINGLE_SPACE)) {                  //Use Case B, add new
						rc.add(value);
					} else {                                                //Use Case C, append to prev
						int mostRecentIndex = rc.size()-1;
						String mostRecentAdd = rc.get(mostRecentIndex);
						rc.set(mostRecentIndex, mostRecentAdd + value);     //append to previous
					}
				}
			} else {
				String[] spaceDelimitedList = parts.get(i).split(SINGLE_SPACE);
				for(int j = 0; j < spaceDelimitedList.length; j++) {
					/**
					 * 
	    			 *              ___
    				 * Use Case D:  arg.                    arg is added new rc element.
					 *                     ___
					 * Use Case E:  #{var} arg              arg is added new rc element.
					 * 
					 *                            ___
					 * Use Case F:  arg arg #{var}arg       arg is appended to most recently added rc element.
					 *                                ___
					 * Use Case G:  arg arg #{var}?arg arg   arg is added new rc element, because split(SPACE)
					 */
					if (j > 0)
						rc.add(spaceDelimitedList[j]);                              //Use Case G, add new.
					else {
						if (i==0) {
							rc.add(spaceDelimitedList[0]);                          //Use Case D, add new.  Analagous to Use Case A
						} else {
							String prevPart = parts.get(parts.size()-2);
							if (prevPart.endsWith(SINGLE_SPACE)) {                  //Use Case E, add new.  Analagous to Use Case B
								rc.add(spaceDelimitedList[0]);
							} else {                                                //Use Case F, append to prev.  Analagous to Use Case C
								int mostRecentIndex = rc.size()-1;
								String mostRecentAdd = rc.get(mostRecentIndex);
								rc.set(mostRecentIndex, mostRecentAdd + spaceDelimitedList[0]);     //append to previous
							}
						}
					}
				}
			}
		}
		return rc.toArray( new String[] {});
	}

	private ConfigLookup getConfigLookup() {
		return this.lkup;
	}

	@Override
	public void setConfigLookup(ConfigLookup val) {
		this.lkup = val;
	}

}
