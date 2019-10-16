package com.github.eostermueller.snail4j.launcher;

import java.util.List;

public interface Messages {

	String testMustBeStoppedBeforeAttemptingToStart(String name);

	String testMustBeStartedBeforeAttemptingToStop(String name, State state, State stopped);

	String nextRunnerNotFound(ProcessKey thisState, State desiredState);
	
	String unknownProcessStateTransition(ProcessKey key, State desiredState);

	String noValueForVariable(String variableName, List<String> list);

	String getSutStopMessage(String humanReadableString, String absolutePath, boolean ynKillFileExistsBefore,
			boolean ynKillFileExistsAfter);

	String getSutStartMessage(String humanReadableString);

	String getLoadGeneratorStartMessage(String humanReadableString);

	String getLoadGeneratorCommandMessage(String humanReadableString, String command, String hostOrIp, int port);

}
