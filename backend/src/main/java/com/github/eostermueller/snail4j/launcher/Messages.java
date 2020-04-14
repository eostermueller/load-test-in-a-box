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

	String unableToFindWiremockHostAndPort();

	String unableToFindH2HostAndPort();

	String unableToFindSutHostAndPort();

	String getDefaultShutdownMessage(String url, String appName);

	String getWiremockStopMessage(String humanReadableString);

	String getNoUdpPortsAvailableBetween(int startUdpPort, int udpPortMax);

	String multipleProblems();

	String tcpPortConflict(String name, String hostname, int port, String snail4jProperty);

	String portInitStatus(List<String> errors);

}
