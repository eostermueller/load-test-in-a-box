package com.github.eostermueller.snail4j.launcher;

import com.github.eostermueller.snail4j.Application;
import com.github.eostermueller.snail4j.systemproperty.SutGitCloneUrl;

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
		return "Verify that 'wiremockHostname' and 'wiremockPort' in the " + Application.INSTALL_ROOT + "/snail4j.json have correct values.";
	}

	@Override
	public String unableToFindH2HostAndPort() {
		return "Verify that 'h2Hostname' and 'h2Port' in the " + Application.INSTALL_ROOT + "/snail4j.json have correct values.";
	}

	@Override
	public String unableToFindSutHostAndPort() {
		return "Verify that 'sutAppHostname' and 'sutAppPort' in the " + Application.INSTALL_ROOT  + "/snail4j.json have correct values.";
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
		return String.format("Port [%d] on [%s] is in use.  This is a port conflict for [%s]; . Configure an availabe/unused port in property [%s] in $HOME or %%USERPROFILE%% /" + Application.INSTALL_ROOT  + "/snail4j.json.",
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
	public String javaHomeFolderDoesNotExistOrLackingPermissions_withAlternatives(File javaHomeFolder, Path currentJdkPath) {
		return String.format("JAVA_HOME env is set to [%s].  The path does not exist or you're lacking permissions to access it. Will attempt to use current JVM path [%s] for SUT JDK.",javaHomeFolder.getAbsolutePath().toString());
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

	@Override
	public String JAVA_HOME_jreIsNotEnough(Path javaHomePath, Path currentJvm) {
		return String.format("the JAVA_HOME path points to a JRE and not a full blown JDK.  SUT processes will attempt to use current JVM for JDK: [%s].",javaHomePath,currentJvm);
	}
	
	@Override
	public String currentJvm_jreIsNotEnough(Path currentJavaPath) {
		return jreIsNotEnough("the currently running JVM",currentJavaPath);
	}
	public String jreIsNotEnough(String whereFound, Path currentJavaPath) {
		String path = currentJavaPath.toFile().getAbsolutePath();
		return String.format("The path found for [%s] is not a JDK:: \n#### \t\t\t%s", whereFound, path );
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
		return "Verify that 'sutAppHostname' and 'glowrootPort' in the " + Application.INSTALL_ROOT + "/snail4j.json have correct values.";
	}

	@Override
	public String unableToFindWorkbenchAgentHostAndPort() {
		return "Verify that 'sutAppHostname' in the " + Application.INSTALL_ROOT + "/snail4j.json have correct values.";
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

	@Override
	public String unableToFindPidForCommandLineContaining(String criteria) {
		return String.format("Unable to find process id using JDK's jcmd command line tool.  Unable to find cmd line that contains [%s]",criteria);
	}

	@Override
	public String warning() {
		return "WARNING";
	}

	@Override
	public String noJAVA_HOMEenvVarFound(Path p) {
		return "No JAVA_HOME environment variable found. Will attempt to use [%s] for the SUT JDK";
	}

	@Override
	public String sutProcessesWillUseJAVA_HOME_Jdk(Path firstCandidate) {
		return String.format("SUT processes will be launched with the JDK at the JAVA_HOME env variable [%s}",firstCandidate);
	}
	@Override
	public String sutProcessesWillUseCurrentJdk(Path firstCandidate) {
		return String.format("SUT processes will be launched with this JDK [%s}",firstCandidate);
	}

	@Override
	public String failedToFindJdk() {
		return "Failed to find JDK. To fix, either launch load-test-in-a-box uber jar with a JDK (a JRE is not sufficient) or point to a JDK using JAVA_HOME environment variable."; 
	}

	@Override
	public String jdkFolderDoesNotExistOrLackingPermissions(File file) {
		return String.format("The JDK path [%] does not exist or you're lacking permissions to access it.",file.getAbsolutePath().toString());	
	}
	@Override
	public String abortingInstall(Exception e) {
		return String.format("Aborting install because of exception: %s", e.getMessage());
	}

	/**
	 * https://stackoverflow.com/a/12682507/2377579
	 */
	@Override
	public String insufficientMemory(long actualMemoryAvailabilInBytes, long minMemoryAvailableRequirementInBytes) {
		return String.format("Insufficient memory.  Consider adding more RAM or stopping programs to free up existing RAM.  \nExpected min available RAM: %,d\nActual available RAM: %,d\n", minMemoryAvailableRequirementInBytes, actualMemoryAvailabilInBytes);
	}

	@Override
	public String wasExpectingTrueOrFalse(String trueOrFalse) {
		return String.format("Was expecting the text 'true' or 'false' but instead found [%s].  leading/trailing spaces or mixed case are ok.", trueOrFalse);
	}

	/**
	 * https://stackoverflow.com/a/12682507/2377579
	 */
	@Override
	public String insufficientDiskSpace(long actualDiskAvailableSizeInBytes,
			long minDiskAvailableSizeRequirementInBytes) {
		return String.format("Insufficient disk space.  Consider adding more disk space or deleting some files to make space.  \nExpected min disk space: %,d\nCurrentActual disk space: %,d\n", actualDiskAvailableSizeInBytes, minDiskAvailableSizeRequirementInBytes);
	}

	@Override
	public String getSutGitCloneUrlDoc() {
		return "URL of a git repository with a load-test-in-a-box SUT.  Formats of URL are documented here:  https://git-scm.com/docs/git-clone that can be passed into a 'git clone' command.";
	}

	@Override
	public String getAvailableDiskSizeValidationDoc() {
		return "load-test-in-a-box will not allow the agent to install if there is too little disk space available.  Setting this system property to 'false' will skip the disk space validation process.";
	}

	@Override
	public String getAvailableMemoryValidationDoc() {
		return String.format("load-test-in-a-box will not allow the agent to install if there is too little memory available.  Setting this system property to '%s' will skip the memory validation process.","false");
	}

	@Override
	public String getDeleteSutDoc() {
		return String.format("Setting this to 'true' will delete the $HOME/.load-test-in-a-box/sutApp folder before starting the uber jar (aka, agent).  The property [%s] is required with this property.", new SutGitCloneUrl().getDashDPropertyName() );  
	}

	@Override
	public String getFluxCapacitorDoc() {
		return "Sample doc to show how to use self-documenting system properties";
	}

	@Override
	public String exceptionWritingConfigFile(Path targetFile, Exception e) {
		return String.format("Exception writing configuration file [%s] to disk.  Exception detail: [%s]",
				targetFile.toString(),
				e.toString());
	}

	@Override
	public String exceptionReadingConfigFile(Path targetFile, Exception e) {
		return String.format("Exception reading configuration file [%s] to disk.  Exception detail: [%s]",
				targetFile.toString(),
				e.toString());
	}

	@Override
	public String javaHomeFolderDoesNotExistOrLackingPermissions(File javaHomeFolder) {
		return String.format("JAVA_HOME env is set to [%s].  This path does not exist or you're lacking permissions to access it. Snail4j uses JAVA_HOME to locate java, jcmd and other tools.",javaHomeFolder.getAbsolutePath().toString());
	}

	@Override
	public String docForHeadlessProperty() {
		return "Remote machines can access glowroot only when set to true.  Like all properties with 'config' in the name, using this property on agent startup will permanently set the property until altered using the same system property. Corresponds to the 'headless' attribute in [" + Application.CONFIG_FILE_NAME + "]  See https://github.com/glowroot/glowroot/wiki/Agent-Installation-%28with-Embedded-Collector%29#optional-post-installation-steps";
	}

	@Override
	public String getDocForDeleteSut() {
		return "CAUTION.  When set to true, deletes $HOME/.load-test-in-a-box/sutApp -- the folder where the SUT resides."
				+ "When [" + new SutGitCloneUrl().getDashDPropertyName() + "] is also set to true, the git URL will be put into place in sutApp AFTER the sutApp folder has been deleted. ";
	}
	
}