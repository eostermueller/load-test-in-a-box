package com.github.eostermueller.tjp.launcher;

public interface Messages {

	String testMustBeStoppedBeforeAttemptingToStart(String name);

	String testMustBeStartedBeforeAttemptingToStop(String name, State state, State stopped);

	String nextRunnerNotFound(ProcessKey thisState, State desiredState);
	
	String unknownProcessStateTransition(ProcessKey key, State desiredState);

}
