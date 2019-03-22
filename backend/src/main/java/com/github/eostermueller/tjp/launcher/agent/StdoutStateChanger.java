package com.github.eostermueller.tjp.launcher.agent;

/**
 * A voyer of stdout from a headless process,
 * determines whether the process starts successfully or not.
 * 
 * @author erikostermueller
 *
 */
public interface StdoutStateChanger {

	
	/**
	 * Implement this to have a peek at each line of stdout, and
	 * also decide which line of text ("MyApp is fully started in 5.53 second")
	 * merits a change of state (to State.STARTED).
	 * 
	 * @param s
	 * @return
	 * @throws TjpException 
	 */
	void evaluateStdoutLine(String s) throws TjpException;
	
	/**
	 * Implement this to register/store a list of objects that should be
	 * notified of state changes.
	 * @param scl
	 */
	void registerStateChangeListener(StateChangeListener scl);
	
}
