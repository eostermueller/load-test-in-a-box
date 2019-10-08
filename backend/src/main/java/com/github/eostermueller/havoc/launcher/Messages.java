package com.github.eostermueller.havoc.launcher;

import java.util.List;

public interface Messages {

	String testMustBeStoppedBeforeAttemptingToStart(String name);

	String testMustBeStartedBeforeAttemptingToStop(String name, State state, State stopped);

	String nextRunnerNotFound(ProcessKey thisState, State desiredState);
	
	String unknownProcessStateTransition(ProcessKey key, State desiredState);

	String noValueForVariable(String variableName, List<String> list);

}
