package com.github.eostermueller.snail4j;


public class CommandLineArgs {
	public static final String FORCE_LAUNCH = "--forceLaunch";
	public CommandLineArgs(String[] stringArgs) {
		args = stringArgs;
	}

	
	static CommandLineArgs create(String[] stringArgs) {
		CommandLineArgs args = new CommandLineArgs(stringArgs);
		return args;
	}
	String args[] = null;
	
	/**
	 * This is a backup plan, in case snal4j has a bug/issue  with prechecks/validation that unnecessarily aborts install/launch.
	 * @return
	 */
	public boolean isForceLaunch() {
		boolean found = false;
		for (String oneArg : args ) {
			if (FORCE_LAUNCH.toLowerCase().equals(oneArg.toLowerCase())) {
				found = true;
				break;
			}
		}
		
		return found;
	}
	
}
