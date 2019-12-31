package com.github.eostermueller.snail4j.launcher;

import java.util.Arrays;
import java.util.List;

public class Messages_en_US implements Messages {

	@Override
	public String testMustBeStoppedBeforeAttemptingToStart(String name) {
		return String.format("The [%s] test must be stopped before attempting to start.", name);
	}

	@Override
	public String testMustBeStartedBeforeAttemptingToStop(String name, State currentState, State desiredState) {
		return String.format("The [%s] test must be started before attempting to stop.  Current state [%s].  Desired State [%s].", name, currentState, desiredState);
	}

	@Override
	public String unknownProcessStateTransition(ProcessKey key, State desiredState) {
		return String.format("Got a request to transition to the state [%s] for process [%s], but can't find this process!", key.getKey(), desiredState);
	}

	@Override
	public String nextRunnerNotFound(ProcessKey thisRunner, State desiredState) {
		return String.format("Current runner is [%s], unable to find next state when transitioning to state[%s].", thisRunner.getKey(), desiredState);
	}
	@Override
	public String noValueForVariable(String variableName, List<String> allVarNames) {
		return String.format("No value for variable [%s]. Please check spelling of [%s] against actual variables:\n %s", 
				variableName, 
				variableName, 
				Arrays.asList(allVarNames).toString() );
	}

	@Override
	public String getSutStopMessage(String humanReadableString, String absolutePath, boolean ynKillFileExistsBefore,
			boolean ynKillFileExistsAfter) {
		return String.format("Attmpted to stop [%s] by deleting file [%s].  Did kill file exist b4/after? [%b/%b]\n",
				humanReadableString, absolutePath, ynKillFileExistsBefore, ynKillFileExistsAfter );
	}

	@Override
	public String getSutStartMessage(String humanReadableString) {
		return String.format("Attmpted to start process [%s]\n", humanReadableString);
	}
	@Override
	public String getWiremockStopMessage(String humanReadableString) {
		return String.format("Attmpted to start process [%s] to stop wiremock.\n", humanReadableString);
	}

	@Override
	public String getLoadGeneratorStartMessage(String humanReadableString) {
		return String.format("Attmpted to start loadGenerator process [%s]\n", humanReadableString);
	}

	@Override
	public String getLoadGeneratorCommandMessage(String humanReadableString, String command, String hostOrIp, int port) {
		return String.format("Attmpted to [%s] the loadGenerator[%s] by sending message [%s] to [%s] [%d]\n",
				command, humanReadableString, command, hostOrIp, port);
	}

	@Override
	public String unableToFindWiremockHostAndPort() {
		return "Verify that 'wiremockHostname' and 'wiremockPort' in the .snail4j/snail4j.json have correct values.";
	}

	@Override
	public String unableToFindH2HostAndPort() {
		return "Verify that 'h2Hostname' and 'h2Port' in the .snail4j/snail4j.json have correct values.";
	}

	@Override
	public String unableToFindSutHostAndPort() {
		return "Verify that 'sutAppHostname' and 'sutAppPort' in the .snail4j/snail4j.json have correct values.";
	}

	@Override
	public String getDefaultShutdownMessage(String url, String appName) {
		return "<missing shutdown response from [" + appName + "] URL: [" + url + "]";
	}

}
