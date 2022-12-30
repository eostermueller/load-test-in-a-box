package com.github.eostermueller.snail4j.launcher;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface Messages {

	String warning();
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
	String unableToFindGlowrootHostAndPort();
	String unableToFindWorkbenchAgentHostAndPort();

	String unableToFindSutHostAndPort();

	String getDefaultShutdownMessage(String url, String appName);

	String getWiremockStopMessage(String humanReadableString);

	String getNoUdpPortsAvailableBetween(int startUdpPort, int udpPortMax);

	String multipleProblems();

	String tcpPortConflict(String name, String hostname, int port, String snail4jProperty);

	String portInitStatus(List<String> errors);

	String getHostnameInitMessage(String hostname);

	String javaHomeEnvVarNotSet();


	String startInstallMessage();

	String successfulInstallation();

	String failedInstallation();

	String unsupportedJavaVersion(String currentJavaSpecificationVersion, Path javaLocation, String[] listOfUnsupportedVersions);

	String jreIsNotEnough(String whereFoundPath, Path currentJavaPath);

	String startupAborted(int countOfFailedPreChecks);

	String JAVA_HOME_mustPointToCurrentJava(Path get_JAVA_HOME, Path currentJavaPath);

	String attemptingToUseJavaHomeToFindJavaCompiler(Path java_home_from_env);

	String unableToCloneSutRepo(String repoUrl, String targetFolder);

	String pleaseManuallyDeleteFolderAndAllContents(String absolutePath);

	String unableToRecursivelyDeleteFolder(String strSutAppFolder);

	String unableToFindPidForCommandLineContaining(String criteria);

	String currentJvm_jreIsNotEnough(Path currentJavaPath);
	String JAVA_HOME_jreIsNotEnough(Path javaHomePath, Path currentJvm);
	String noJAVA_HOMEenvVarFound(Path p);
	String sutProcessesWillUseJAVA_HOME_Jdk(Path firstCandidate);
	String sutProcessesWillUseCurrentJdk(Path firstCandidate);
	String failedToFindJdk();
	String jdkFolderDoesNotExistOrLackingPermissions(File file);
	String javaHomeFolderDoesNotExistOrLackingPermissions_withAlternatives(File javaHomeFolder, Path currentJdkPath);

}
