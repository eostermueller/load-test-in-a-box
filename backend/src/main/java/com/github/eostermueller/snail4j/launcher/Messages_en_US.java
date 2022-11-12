package com.github.eostermueller.snail4j.launcher;

import java.io.File;
import java.nio.file.Path;
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

	@Override
	public String getNoUdpPortsAvailableBetween(int startUdpPort, int udpPortMax) {
		return String.format("No UDP ports available between %d and %d",startUdpPort,udpPortMax);
	}

	@Override
	public String multipleProblems() {
		return "Multiple problems were encountered";
	}

	@Override
	public String tcpPortConflict(String name, String hostname, int port, String snail4jProperty) {
		return String.format("Port [%d] on [%s] is in use.  This is a port conflict for [%s]; . Configure an availabe/unused port in property [%s] in $HOME or %%USERPROFILE%% /.snail4j/snail4j.json.",
				port,
				hostname,
				name,
				snail4jProperty);
	}

	@Override
	public String portInitStatus(List<String> errors) {
		
		String status = (errors.size()==0) ? "All required network ports are available for testing!" : "Unable to launch Snail4j.  One or more network port conflicts";

		StringBuilder sb = new StringBuilder();
		sb.append(status + "\n");
		for(String e : errors)
			sb.append(e + "\n");
		
		return sb.toString();
	}

	@Override
	public String getHostnameInitMessage(String hostname) {
		return String.format("Detected hostname [%s].  This will be written to snail4j.json configuration file.", hostname);
	}

	@Override
	public String javaHomeEnvVarNotSet() {
		return "The JAVA_HOME envirnoment variable must be set so Snail4j can locate java, jcmd and other tools.";
	}

	@Override
	public String javaHomeFolderDoesNotExistOrLackingPermissions(File javaHomeFolder) {
		return String.format("JAVA_HOME env is set to [%s].  This path does not exist or you're lacking permissions to access it. Snail4j uses JAVA_HOME to locate java, jcmd and other tools.",javaHomeFolder.getAbsolutePath().toString());
	}

	@Override
	public String startInstallMessage() {
		return "Start of Snail4j Install";
	}

	@Override
	public String successfulInstallation() {
		return "Snail4j Install Succeeded";
	}

	@Override
	public String failedInstallation() {
		return "Snail4j Install Failed";
	}

	@Override
	public String unsupportedJavaVersion(String currentJavaSpecificationVersion, Path javaLocation, String[] listOfUnsupportedVersions) {
		
		return String.format("Snail4j uber jar was launched with java.specification.version=%s in this path: %s. List of all unsuported versions: %s",
				currentJavaSpecificationVersion,
				javaLocation.toString(),
				Arrays.toString(listOfUnsupportedVersions) 
				);
		
	}

	public String jreIsNotEnough(Path currentJavaPath) {
		String path = currentJavaPath.toFile().getAbsolutePath();
		return String.format("Sure looks like you launched a JRE.  Snail4j requires a JDK.  Path to JRE that was used:: \n#### \t\t\t%s", path );
	}

	@Override
	public String startupAborted(int countOfFailedPreChecks) {
		return String.format("[%d] install prechecks failed.", countOfFailedPreChecks);
	}

	@Override
	public String JAVA_HOME_mustPointToCurrentJava(Path JAVA_HOME, Path currentJavaPath) {
		return String.format( "Mismatched JAVA_HOME error.  The java executable and JAVA_HOME environment variable are from different java installations.  \nJAVA_HOME=%s\nCurrent Java=%s",
				JAVA_HOME.toFile().getAbsolutePath(),
				currentJavaPath.toFile().getAbsolutePath() 
				);
	}

	@Override
	public String attemptingToUseJavaHomeToFindJavaCompiler(Path java_home_from_env) {
		return String.format("Attempting to use JAVA_HOME value to locate Java Compiler executable. \n####\t\t\t JAVA_HOME=%s", java_home_from_env.toAbsolutePath().toString() );
	}

	@Override
	public String unableToFindGlowrootHostAndPort() {
		return "Verify that 'sutAppHostname' and 'glowrootPort' in the .snail4j/snail4j.json have correct values.";
	}

	@Override
	public String unableToFindWorkbenchAgentHostAndPort() {
		return "Verify that 'sutAppHostname' in the .snail4j/snail4j.json have correct values.";
	}
	@Override
	public String unableToCloneSutRepo(String repoUrl, String targetFolder) {
		return String.format("Unable to clone repo [%s] to target directory [%s]", repoUrl, targetFolder);
	}
	@Override
	public String pleaseManuallyDeleteFolderAndAllContents(String absolutePath) {
		return String.format("Please manually delete this folder and all its child file/folder contents: [%s] ", absolutePath);
	}

	@Override
	public String unableToRecursivelyDeleteFolder(String strSutAppFolder) {
		return String.format("Unable to recursively delete Snail4j sutApp folder: [%s] ", strSutAppFolder);
	}
}
