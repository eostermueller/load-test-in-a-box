package com.github.eostermueller.snail4j.launcher;

import com.github.eostermueller.snail4j.Snail4jException;

/**
 * Currently Broken, but kept around for ideas to support nested references.
 * @author erikostermueller
 *
 */
public class CommandLineWrapper implements CommandLine {
	private static final int DEREFERENCE_ITERATIONS = 3;
	private String cmdLine;
	private ConfigLookup lkup;
	public CommandLineWrapper(String val) {
		this.cmdLine = val;
	}

	@Override
	public void setCommandLine(String str) {
		this.cmdLine = str;
	}

	@Override
	public String[] getUnprocessedCommandLine() {
		return cmdLine.split(DefaultCommandLine.SINGLE_SPACE);
	}

	@Override
	public String[] getProcessedCommandLine() throws ConfigVariableNotFoundException, Snail4jException {
		
		String currentCmdLine = this.cmdLine;
		for(int i = 0; i < DEREFERENCE_ITERATIONS; i++) {
			DefaultCommandLine cl = new DefaultCommandLine(currentCmdLine);
			cl.setConfigLookup(this.lkup);
			
			String[] cmdLine = cl.getProcessedCommandLine();
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < cmdLine.length; j++) {
				if (j > 0) 
					sb.append(DefaultCommandLine.SINGLE_SPACE);
				sb.append(cmdLine[j]);
			}
			currentCmdLine = sb.toString();
			
			//A small optimization: skip additional dereferencing if no variable references are found.
			int index = currentCmdLine.indexOf(VariableTokenizer.VAR_PREFIX + "{");
			if (index<0)
				break;
		}
		return currentCmdLine.split(DefaultCommandLine.SINGLE_SPACE);
	}

	@Override
	public void setConfigLookup(ConfigLookup val) {
		lkup = val;
	}

}
