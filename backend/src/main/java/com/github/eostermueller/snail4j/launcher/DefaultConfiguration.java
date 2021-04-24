package com.github.eostermueller.snail4j.launcher;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.JdkUtils;
import com.github.eostermueller.snail4j.NonStaticOsUtils;
import com.github.eostermueller.snail4j.Snail4jException;

/**
 * @author erikostermueller
 *
 */
public class DefaultConfiguration implements Configuration {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());	

	/**
	 * When this snail4j variable is resolved (by com.github.eostermueller.snail4j.launcher.ConfigLookup), it resolves correctly on MS-Win and *Nix to be the full path of either
	 * mvn.bat or mvn.
	 */
	public static final String MAVEN_EXE_PATH = "#{mavenExePath}";
	/**
	 * When this snail4j variable is resolved (by com.github.eostermueller.snail4j.launcher.ConfigLookup), it resolves correctly on MS-Win and *Nix 
	 * to be the full path snail4j's own copy of jmeter of either
	 * jmeter.bat (MS-Win) or jmeter (*nix)
	 */
	public static final String JMETER_EXE_PATH = "#{jmeterExePath}";
	public static final String JMETER_SHUTDOWN_EXE_PATH = "#{jmeterShutdownExePath}";
	private static final String SPACE = " ";
	private String sutAppZipFileName;
	private int sutAppPort;
	
	private long loadGenerationThreads;
	private long loadGenerationRampupTimeInSeconds;
	private long loadGenerationDurationInSeconds;
	private String sutAppHostname;
	private boolean osWin;
	private String mavenExePath;
	private boolean mavenOnline = false;//the local maven repo distributed with snail4j should be all that is necessary
	private boolean snail4jMavenRepo;

	private String wiremockHostname;
	private int wiremockPort;
	private String h2Hostname;
	private int h2Port;
	private String wiremockStopCmd;
	private long maxJMeterNonGuiPort;
	private String jmeterExePath;
	private String loadGeneratorShutdownCmd;
	private String jmeterShutdownExePath;
	private String useCaseSearchCriteria;
	
	/**
	 * A space-delimited set of JVM parameters that gets passed into 
	 * -Dspring-boot.run.jvmArguments
	 * https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/html/#goals-run-parameters-details-jvmArguments
	 * No quotation marks are allowed in this value.
	 * The following pom.xml file wraps 'this' value in quotes:
	 * https://github.com/eostermueller/snail4j/blob/master/processManager/pom.xml 
	 */
	private String sutJvmArguments;
	
	@JsonIgnore
	@Override
	public void setDefaultHostname(String hostname) throws Snail4jException {
		this.setWiremockHostname	(hostname);
		this.setH2Hostname			(hostname);
		this.setSutAppHostname		(hostname);
		
		Messages m = DefaultFactory.getFactory().getMessages();
		LOGGER.info(m.getHostnameInitMessage(hostname));
	}
	@Override
	public String getSutJvmArguments() {
		return sutJvmArguments;
	}
	@Override
	public void setSutJvmArguments(String val) {
		sutJvmArguments = val;
	}
	
	/**
	 * This is the most important constructor in the project :-)
	 * It sets default values, many of which are required for basic operation.
	 * @throws Snail4jException 
	 */
	public DefaultConfiguration() throws Snail4jException {
		
			String hostname = null;
			
			try {
				hostname = InetAddress.getLocalHost().getHostName();
				this.setDefaultHostname(hostname);
			} catch (UnknownHostException e) {
				throw new Snail4jException(e);
			}
		
    		this.setUserHomeDir(		Paths.get( getUserHomeDirString() )	);

			this.setJavaHome( 			new NonStaticOsUtils().get_JAVA_HOME() );
			this.setSnail4jHome(		Paths.get( this.getUserHomeDirString(), ".snail4j" )			);
			this.setGlowrootHome(			Paths.get( this.getSnail4jHome().toString() , "glowroot") );
			this.setGlowrootZipFileName ("glowroot-0.13.6-dist.zip");

			this.setMavenHome(			Paths.get( this.getSnail4jHome().toString() , this.getMavenZipFileNameWithoutExtension() )		);
			this.setSnail4jMavenRepo(true);
			this.setMavenRepositoryHome(Paths.get( this.getSnail4jHome().toString() , "repository" )		);

			this.setSutAppHome(			Paths.get( this.getSnail4jHome().toString() , "sutApp") );
			this.setSutAppZipFileName ("sutApp.zip");
			this.setSutAppPort		  (9675);
			this.setGlowrootPort(12675);

			this.setSutKillFile(        Paths.get( this.getSnail4jHome().toString() , "deleteMeToStopSnail4jSut.txt") );

			this.setLogDir(             Paths.get( this.getSnail4jHome().toString() , "log" )		);
			this.setSystemUnderTestStdoutLogFileName("systemUnderTest.log");
			this.setLoadGeneratorStdoutLogFileName("loadGenerator.log");
			this.setWiremockStopStdoutLogFileName("wiremockStop.log");

			this.setWiremockFilesHome(		Paths.get( this.getSnail4jHome().toString() , "wiremock") );
			this.setWiremockFilesZipFileName ("wiremockFiles.zip");


			this.setProcessManagerHome(		Paths.get( this.getSnail4jHome().toString() , "processManager") );
			this.setProcessManagerZipFileName ("processManager.zip");

			String baseJMeter = "apache-jmeter-5.2.1";
			this.setJMeterZipFileNameWithoutExtension(baseJMeter);
			this.setJMeterDistHome(		Paths.get( this.getSnail4jHome().toString() , baseJMeter ) );

			this.setStartJMeterNonGuiPort(4445);
			this.setMaxJMeterNonGuiPort(4445+40);

			this.setH2DataFileHome(		Paths.get( this.getSnail4jHome().toString() , "data") );
			this.setH2DataFileName		("perfSandboxDb.mv.db");

			this.setJMeterFilesZipFileName("jmeterFiles.zip");
			this.setJMeterFilesHome(Paths.get( this.getSnail4jHome().toString() , "jmeterFiles") );

			this.setLoadGenerationThreads(3); //3T0TT -- from http://bit.ly/2017tjp
			this.setLoadGenerationRampupTimeInSeconds(3);

			/**
			 * For 99% of the time, don't expect a single test run to last more than 20 minutes....so 60 minutes should be more than enough.
			 */
			this.setLoadGenerationDurationInSeconds(3600);


//			this.setJMeterNonGuiPort(4455);

			/**
			 * snail4j/jmeterFiles/load.jmx
			 */
			StringBuilder sb = new StringBuilder();
			sb.append(JMETER_EXE_PATH);
			sb.append(SPACE);sb.append("-n");
			sb.append(SPACE);sb.append("-l #{jmeterFilesHome}/load.jtl");
			sb.append(SPACE);sb.append("-t #{jmeterFilesHome}/load.jmx");
			
			// -J parameter syntax defined here: 
			// https://jmeter.apache.org/usermanual/get-started.html#override
			sb.append(SPACE);sb.append("-Jsnail4j.host=#{sutAppHostname}");
			sb.append(SPACE);sb.append("-Jsnail4j.port=#{sutAppPort}");
			sb.append(SPACE);sb.append("-Jsnail4j.durationSeconds=#{loadGenerationDurationInSeconds}");
			sb.append(SPACE);sb.append("-Jsnail4j.rampupSeconds=#{loadGenerationRampupTimeInSeconds}");
			sb.append(SPACE);sb.append("-Jsnail4j.threads=#{loadGenerationThreads}");
			this.setLoadGeneratorLaunchCmd(sb.toString());

			sb = new StringBuilder();
			sb.append(JMETER_SHUTDOWN_EXE_PATH);
			this.setLoadGeneratorShutdownCmd(sb.toString());
			
			/*
			 * ToDo:  embed parameters in the following for tcp listen ports for each SUT component:  h2, wiremock, tjp
			 *
			 * Use same approach as this, ab0ve: -Dsnail4j.jmeter.port=#{jmeterNonGuiPort}
			 *
			 */
			StringBuilder sb2 = new StringBuilder();
	 		sb2.append(MAVEN_EXE_PATH);
	 		sb2.append(SPACE);sb2.append("-Dsnail4j.wiremock.port=#{wiremockPort}");
	 		sb2.append(SPACE);sb2.append("-Dsnail4j.wiremock.hostname=#{wiremockHostname}");
	 		sb2.append(SPACE);sb2.append("-Dsnail4j.h2.port=#{h2Port}");
	 		sb2.append(SPACE);sb2.append("-Dsnail4j.h2.hostname=#{h2Hostname}");
	 		sb2.append(SPACE);sb2.append("-Dsnail4j.sut.port=#{sutAppPort}");
	 		sb2.append(SPACE);sb2.append("-Dsnail4j.glowroot.port=#{glowrootPort}");
	 		sb2.append(SPACE);sb2.append("-Dsnail4j.sut.jvmArguments=#{sutJvmArguments}");
			sb2.append(SPACE);sb2.append("verify");
			this.setProcessManagerLaunchCmd( sb2.toString() );
			
			
			StringBuilder sb3 = new StringBuilder();
	 		sb3.append(MAVEN_EXE_PATH);
	 		sb3.append(SPACE);sb3.append("-Dsnail4j.wiremock.port=#{wiremockPort}");
			sb3.append(SPACE);sb3.append("clean");
			sb3.append(SPACE);sb3.append("compile");
			sb3.append(SPACE);sb3.append("wiremock:stop");
			this.setWiremockStopCmd( sb3.toString() );
			
			if (getOsName().contains("windows")) {  
				this.setOsWin(true);
			}
			
			this.setMavenExePath( createMavenExePath() );
			this.setJMeterExePath( createJMeterExePath() );
			this.setJMeterShutdownExePath( createJMeterShutdownExePath() );
			
			this.setWiremockPort(11675);
			
			this.setH2Port(10675);
			// this.setProcessManagerLaunchCmd("#{mavenExePath} verify");

			//Adding workaround for closing threads in Windows OS
			this.setWindowsKillerProcess("#{mavenExePath} antrun:run initialize");

			if (getOsName().contains("windows")) {
				this.setOsWin(true);
			}

			this.setMavenExePath( createMavenExePath() );
			this.setUseCaseSearchCriteria("com.github.eostermueller.tjp2");
			
			//-Xloggc:gc.log -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=128K
			sb3 = new StringBuilder();
			                  sb3.append("-Xmx1024m");
			sb3.append(SPACE);sb3.append("-XX:NewSize=512m");
			sb3.append(SPACE);sb3.append("-XX:MaxNewSize=512m");
//			sb3.append(SPACE);sb3.append("-Xloggc:gc.log");
//			sb3.append(SPACE);sb3.append("-verbose:gc");
//			sb3.append(SPACE);sb3.append("-XX:+PrintGCDetails");
//			sb3.append(SPACE);sb3.append("-XX:+PrintGCDateStamps");
//			sb3.append(SPACE);sb3.append("-XX:+PrintGCTimeStamps");
//			sb3.append(SPACE);sb3.append("-XX:+UseGCLogFileRotation");
//			sb3.append(SPACE);sb3.append("-XX:NumberOfGCLogFiles=5");
//			sb3.append(SPACE);sb3.append("-XX:GCLogFileSize=1m");
			this.setSutJvmArguments( sb3.toString() );

	}
	/*
	 * http://www.java-gaming.org/index.php/topic,14110
os name   os arch   java version
Windows XP   x86   1.5.0_02
Windows XP   x86   1.5.0
Windows XP   x86   1.5.0_03
Windows XP   x86   1.4.2_06
Windows XP   x86   1.5.0_01
Windows XP   x86   1.4.2_05
Windows XP   x86   1.4.2_08
Windows XP   x86   1.6.0-ea
Windows XP   x86   1.4.2_03
Windows XP   x86   1.4.2_07
Windows XP   x86   1.4.2_02
Windows XP   x86   1.4.2
Windows XP   x86   1.4.2_04
Windows XP   x86   1.5.0-beta2
Windows XP   x86   1.4.1_02
Windows XP   x86   1.5.0-rc
Windows XP   x86   1.4.1
Windows XP   x86   1.4.0
Windows XP   x86   1.6.0
Windows XP   x86   1.4.2_01
Windows XP   x86   1.5.0_04
Windows XP   x86   1.5.0-beta
Windows XP   x86   1.4.1_01
Windows XP   x86   1.4.2_09
Windows XP   x86   1.5.0_05
Windows XP   x86   1.4.1_03
Windows XP   x86   1.6.0-beta
Windows XP   x86   1.6.0-rc
Windows XP   x86   1.4.2_10
Windows XP   x86   1.5.0_06
Windows XP   x86   1.4.0_03
Windows XP   x86   1.6.0-beta2
Windows XP   x86   1.4.2_11
Windows XP   x86   1.5.0_07
Windows 2003   x86   1.5.0_02
Windows 2003   x86   1.5.0
Windows 2003   x86   1.5.0_03
Windows 2003   x86   1.4.2_06
Windows 2003   x86   1.5.0_01
Windows 2003   x86   1.4.2_05
Windows 2003   x86   1.6.0-ea
Windows 2003   x86   1.4.2_07
Windows 2003   x86   1.4.2_04
Windows 2003   x86   1.5.0-rc
Windows 2003   x86   1.5.0_04
Windows 2003   x86   1.5.0-beta
Windows 2003   x86   1.5.0_05
Windows 2003   x86   1.6.0-rc
Windows 2003   x86   1.5.0_06
Windows 2003   x86   1.5.0_07
Linux   i386   1.5.0_02
Linux   i386   1.5.0
Linux   i386   1.5.0_03
Linux   i386   1.4.2_06
Linux   i386   1.5.0_01
Linux   i386   1.4.2_05
Linux   i386   1.4.2_08
Linux   i386   1.6.0-ea
Linux   i386   1.4.2_03
Linux   i386   1.4.2_07
Linux   i386   1.4.2_02
Linux   i386   1.4.2-beta
Linux   i386   1.4.2
Linux   i386   1.4.2_04
Linux   i386   1.4.2-01
Linux   i386   1.5.0-rc
Linux   i386   1.4.2-rc1
Linux   i386   1.5.0_04
Linux   i386   1.4.2_09
Linux   i386   1.5.0_05
Linux   i386   1.6.0-beta
Linux   i386   1.6.0-rc
Linux   i386   1.4.2_10
Linux   i386   1.5.0_06
Linux   i386   1.6.0-beta2
Linux   i386   1.4.2_11
Linux   i386   1.5.0_07
Linux   amd64   1.4.2-01
Linux   amd64   1.5.0_05
Windows 2000   x86   1.5.0_02
Windows 2000   x86   1.5.0
Windows 2000   x86   1.5.0_03
Windows 2000   x86   1.4.2_06
Windows 2000   x86   1.5.0_01
Windows 2000   x86   1.4.2_05
Windows 2000   x86   1.4.2_08
Windows 2000   x86   1.6.0-ea
Windows 2000   x86   1.4.2_03
Windows 2000   x86   1.4.2_07
Windows 2000   x86   1.4.2_02
Windows 2000   x86   1.4.2-beta
Windows 2000   x86   1.4.2
Windows 2000   x86   1.4.2_04
Windows 2000   x86   1.5.0-beta2
Windows 2000   x86   1.4.1_02
Windows 2000   x86   1.5.0-rc
Windows 2000   x86   1.4.1
Windows 2000   x86   1.4.2_01
Windows 2000   x86   1.5.0_04
Windows 2000   x86   1.4.2_09
Windows 2000   x86   1.5.0_05
Windows 2000   x86   1.6.0-beta
Windows 2000   x86   1.6.0-rc
Windows 2000   x86   1.4.2_10
Windows 2000   x86   1.5.0_06
Windows 2000   x86   1.6.0-beta2
Windows 2000   x86   1.5.0_07
Windows 2000   x86   1.4.1_07
Mac OS X   i386   1.5.0_05
Mac OS X   i386   1.5.0_06
Mac OS X   ppc   1.5.0_02
Mac OS X   ppc   1.4.2_05
Mac OS X   ppc   1.4.2_03
Mac OS X   ppc   1.4.2_07
Mac OS X   ppc   1.4.2_09
Mac OS X   ppc   1.5.0_05
Mac OS X   ppc   1.5.0_06
Windows 98   x86   1.5.0_03
Windows 98   x86   1.4.2_06
Windows 98   x86   1.5.0_01
Windows 98   x86   1.4.2_02
Windows 98   x86   1.4.0
Windows 98   x86   1.4.2_01
SunOS   x86   1.5.0_04
SunOS   x86   1.5.0_06
SunOS   sparc   1.5.0_02
SunOS   sparc   1.4.2_04
SunOS   sparc   1.5.0-beta2
SunOS   sparc   1.5.0_05
SunOS   sparc   1.5.0_06
FreeBSD   i386   1.4.2-p6
FreeBSD   i386   1.4.2-p7
Windows NT   x86   1.5.0_02
Windows NT   x86   1.5.0
Windows NT   x86   1.4.2_05
Windows NT   x86   1.4.2_08
Windows NT   x86   1.4.2_03
Windows Me   x86   1.5.0_04
Windows Me   x86   1.5.0_06		 *
	 */
	

	@Override
	public void setJMeterShutdownExePath(String val) {
		this.jmeterShutdownExePath = val;
	}
	@Override
	public void setLoadGeneratorShutdownCmd(String val) {
		this.loadGeneratorShutdownCmd = val;
	}


	@Override
	public void setMaxJMeterNonGuiPort(long val) {
		this.maxJMeterNonGuiPort = val;
	}
	@Override
	public long getMaxJMeterNonGuiPort() {
		return this.maxJMeterNonGuiPort;
	}


	@Override
	public void setWiremockStopCmd(String val) {
		wiremockStopCmd = val;
		
		val = stripOfflineVariables(val);
		val = stripSnail4jMavenRepoVariable(val);
		
		this.wiremockStopCmd = val;
		
	}
	@Override
	public String getWiremockStopCmd() {
		String rc = this.wiremockStopCmd;
		
		if (!this.isMavenOnline() ) {
			if (rc.trim().startsWith(MAVEN_EXE_PATH)) {
				rc = MAVEN_EXE_PATH + " --offline" + this.wiremockStopCmd.substring(MAVEN_EXE_PATH.length()); 
			}
		}
		
		if (this.isSnail4jMavenRepo() ) {
			if (rc.trim().startsWith(MAVEN_EXE_PATH)) {
				rc = MAVEN_EXE_PATH + " -Dmaven.repo.local=#{mavenRepositoryHome}" + this.wiremockStopCmd.substring(MAVEN_EXE_PATH.length()); 
			}
		}
		return rc;
		
	}
	@Override	
	public void setSutAppPort(int i) {
		this.sutAppPort = i;
	}
	@Override	
	public int getSutAppPort() {
		return this.sutAppPort;
	}

	/**
	 * Yes, it looks inconsistent/redundant to have both createMavenExecPath() and getMavenExecPath()/setMavenExePath().

snail4j uses Jackson for two things:
1) serialize/deserialize DefaultConfiguration.
2) Using JsonNode to resolve variables like #{mavenHome} and #{javaHome} that are specified in the snail4j.json configuration file.

These things only work on simple getters/setters, like getMavenExecPath()/setMavenExePath().
createMavenExePath() exists so the code can decide on the proper name for the maven executable, based on the
operating system.  mvn.cmd for windows, plain old mvn for unix-like os's
	 * @return
	 */
	public String createMavenExePath() {
		return this.getMavenHome().toString() + File.separator + "bin" + File.separator + this.getMavenExeName();
	}
	public String createJMeterExePath() {
		return this.getJMeterDistHome().toString() + File.separator + "bin" + File.separator + this.getJMeterExeName();
	}
	public String createJMeterShutdownExePath() {
		return this.getJMeterDistHome().toString() + File.separator + "bin" + File.separator + this.getJMeterShutdownExeName();
	}

	private String getOsName() {
		return System.getProperty("os.name").toLowerCase();
	}

	@JsonIgnore
	@Override
	public String getMavenExeName() {
		String rc = "mvn";

		if ( isOsWin() ) {
			rc = "mvn.cmd";
		}
		return rc;
	}
	@JsonIgnore
	@Override
	public String getJMeterExeName() {
		String rc = "jmeter.sh";

		if ( isOsWin() ) {
			rc = "jmeter.bat";
		}
		return rc;
	}
	@JsonIgnore
	@Override
	public String getJMeterShutdownExeName() {
		String rc = "shutdown.sh";

		if ( isOsWin() ) {
			rc = "shutdown.bat";
		}
		return rc;
	}

	@Override
	public void setMavenExePath(String val) {
		this.mavenExePath = val;
	}
	@Override
	public String getMavenExePath() {
		return this.mavenExePath;
	}
	@Override
	public void setJMeterExePath(String val) {
		this.jmeterExePath = val;
	}
	@Override
	public String getJMeterExePath() {
		return this.jmeterExePath;
	}


	@JsonIgnore
	@Override
	public void setOsWin(boolean b) {
		osWin = b;
	}


	@JsonIgnore
	@Override
	public boolean isOsWin() {
		return osWin;
	}

	@Override
	public void setSnail4jMavenRepo(boolean b) {
		snail4jMavenRepo = b;
	}


	/**
	 * This must default to true to support the requirement to install by usb-stick without internet access.
	 */
	@Override
	public boolean isSnail4jMavenRepo() {
		return snail4jMavenRepo;
	}

	/**
	 * Determines whether to maven will download dependencies online, as documented here:
	 * https://maven.apache.org/settings.html
	 * By default this is false, so that snail4j can run without internet, just from an uber jar on a usb stick.
	 * @param b
	 */
	@Override
	public void setMavenOnline(boolean b) {
		mavenOnline = b;
	}


	@Override
	public boolean isMavenOnline() {
		return this.mavenOnline;
	}

	@Override
	public void setUseCaseSearchCriteria(String val) {
		this.useCaseSearchCriteria = val;
	}
	@Override
	public String getUseCaseSearchCriteria() {
		return this.useCaseSearchCriteria;
	}
	@Override
	public void setLoadGenerationDurationInSeconds(long val) {
		this.loadGenerationDurationInSeconds = val;
	}
	@Override
	public long getLoadGenerationDurationInSeconds() {
		return this.loadGenerationDurationInSeconds;
	}
	@Override
	public void setLoadGenerationRampupTimeInSeconds(long val) {
		this.loadGenerationRampupTimeInSeconds = val;
	}
	@Override
	public long getLoadGenerationRampupTimeInSeconds() {
		return this.loadGenerationRampupTimeInSeconds;
	}
	@Override
	public void setLoadGenerationThreads(long val) {
		this.loadGenerationThreads = val;
	}
	@Override
	public long getLoadGenerationThreads() {
		return this.loadGenerationThreads;
	}

	@Override
	public  void setSutAppZipFileName(String val) {
		this.sutAppZipFileName = val;
	}
	@Override
	public String getSutAppZipFileName() {
		return this.sutAppZipFileName;
	}

	private Path javaHome = null;
	private Path sutAppHomePath;
	private Path mavenHome;
	private Path userHomeDir;
	private Path Snail4jHomeDir;
	private int maxExceptionCountPerEvent = 100;
	private String mavenZipFileNameWithoutExtension = "apache-maven-3.6.3";
	private Path mavenRepositoryHome;
	private String wiremockZipFileName;
	private String processManagerZipFileName;
	private Path wiremockHome;
	private String systemUnderTestStdoutLogFileName;
	private String loadGeneratorStdoutLogFileName;

	@Override
	public String getLoadGeneratorStdoutLogFileName() {
		return loadGeneratorStdoutLogFileName;
	}

	@Override
	public void setLoadGeneratorStdoutLogFileName(String loadGeneratorStdoutLogFileName) {
		this.loadGeneratorStdoutLogFileName = loadGeneratorStdoutLogFileName;
	}

	@Override
	public String getSystemUnderTestStdoutLogFileName() {
		return systemUnderTestStdoutLogFileName;
	}

	@Override
	public void setSystemUnderTestStdoutLogFileName(String systemUnderTestStdoutLogFileName) {
		this.systemUnderTestStdoutLogFileName = systemUnderTestStdoutLogFileName;
	}

	@Override
	public Path getWiremockFilesHome() {
		return wiremockHome;
	}

	@Override
	public void setWiremockFilesHome(Path wiremockHome) {
		this.wiremockHome = wiremockHome;
	}

	private String h2DataFileName;
	private Path h2DataFileHome;
	private Path jmeterFilesHomePath;
	private String jmeterFilesZipFileName;
	private String loadGeneratorLaunchCmd;
	private String processManagerLaunchCmd;
	private Path processManagerHome;
	private Path logDir;
	private Path sutKillFile;
	private long jmeterNonGuiPort;
	private Path glowrootHomePath;
	private String glowrootZipFileName;
	private String wiremockStopStdoutLogFileName;
	private String windowsKillerProcess;
	private int glowrootPort;
	private long startJMeterNonGuiPort;
	private String jmeterZipFileNameWithoutExtension;
	private Path jmeterDistHome;


	@Override
	public String getWindowsKillerProcess() {
		return windowsKillerProcess;
	}
	@Override
	public void setWindowsKillerProcess(String windowsKillerProcess) {
		this.windowsKillerProcess = windowsKillerProcess;
	}

	@Override
	public Path getH2DataFileHome() {
		return h2DataFileHome;
	}

	@Override
	public void setH2DataFileHome(Path h2DataFileHome) {
		this.h2DataFileHome = h2DataFileHome;
	}

	@Override
	public String getH2DataFileName() {
		return h2DataFileName;
	}

	@Override
	public void setH2DataFileName(String h2DataFileName) {
		this.h2DataFileName = h2DataFileName;
	}
	public static final String unix_ABS_PATH_TO_TJP = "/Users/erikostermueller/Documents/src/jdist/tjpUnzipped/tjp";
	public static final String unix_JAVA_HOME = "/Library/Java/JavaVirtualMachines/openjdk-11.0.2.jdk/Contents/Home";


	@Override
	public void setSutKillFile(Path val) {
		this.sutKillFile= val;
	}
	@Override
	public Path getSutKillFile() {
		return this.sutKillFile;
	}


	@Override
	public void setLogDir(Path val) {
		logDir = val;

	}
	@Override
	public Path getLogDir() {
		return logDir;
	}


	public DefaultConfiguration(Path pgHome, Path javaHome) {
		this.setSnail4jHome(pgHome);
		this.setJavaHome(javaHome);
	}

	@Override
	public Path getMavenRepositoryHome() {
		return this.mavenRepositoryHome;
	}
	@Override
	public void setMavenRepositoryHome(Path val) {
		this.mavenRepositoryHome = val;
	}
	@Override
	public Path getMavenHome() {
		return this.mavenHome;
	}
	@Override
	public void setMavenHome(Path val) {
		this.mavenHome = val;
	}

	@Override
	public Path getUserHomeDir() {
		return this.userHomeDir;
	}
	@Override
	public void setUserHomeDir(Path val) {
		this.userHomeDir = val;
	}

	@JsonIgnore
	public String getUserHomeDirString() {
		return System.getProperty("user.home");
	}
	@Override
	public String getMavenZipFileNameWithoutExtension() {
		return mavenZipFileNameWithoutExtension;
	}
	@Override
	public void setMavenZipFileNameWithoutExtension(String val) {
		this.mavenZipFileNameWithoutExtension = val;
	}
	@JsonIgnore
	@Override
	public String getMavenZipFileName() {
		return this.getMavenZipFileNameWithoutExtension() + "-bin.zip";
	}
	@Override
	public Path getSnail4jHome() {
		return this.Snail4jHomeDir;
	}
	@Override
	public void setSnail4jHome(Path  val) {
		this.Snail4jHomeDir = val;
	}

	@Override
	public Path getJavaHome() throws Snail4jException {
		return this.javaHome;
	}

	@Override
	public void setJavaHome(Path p) {
		this.javaHome = p;
	}

	@Override
	public int getMaxExceptionCountPerEvent() {
		return this.maxExceptionCountPerEvent;
	}
	@Override
	public void setMaxExceptionCountPerEvent(int val) {
		this.maxExceptionCountPerEvent = val;
	}
	@Override
	public void setSutAppHome(Path val) {
		this.sutAppHomePath = val;
	}
	@Override
	public Path getSutAppHome() {
		return this.sutAppHomePath;
	}
	@Override
	public void setGlowrootHome(Path val) {
		this.glowrootHomePath = val;
	}
	@Override
	public Path getGlowrootHome() {
		return this.glowrootHomePath;
	}
	@Override
	public String getGlowrootZipFileName() {
		return this.glowrootZipFileName;
	}
	@Override
	public void setGlowrootZipFileName(String val) {
		this.glowrootZipFileName = val;
	}

	@Override
	public String getWiremockFilesZipFileName() {
		return this.wiremockZipFileName;
	}
	@Override
	public void setWiremockFilesZipFileName(String val) {
		this.wiremockZipFileName = val;
	}
	@Override
	public void setJMeterFilesHome(Path val) {
		this.jmeterFilesHomePath = val;
	}
	@Override
	public Path getJMeterFilesHome() {
		return this.jmeterFilesHomePath;
	}

	@Override
	public String getJMeterFilesZipFileName() {
		return this.jmeterFilesZipFileName;
	}
	@Override
	public void setJMeterFilesZipFileName(String val) {
		this.jmeterFilesZipFileName = val;
	}

	/**
	 * When a pom.xml file invokes mvn, that's then the 'passthru' parameters are passed.
	 * Note that jmeterFiles/pom-load.xml file doesn't invoke mvn, so this method is freed from the obligation of messing with 'passthru' parameters.
	 */
	@Override
	public String getLoadGeneratorLaunchCmd() {
		String rc = this.loadGeneratorLaunchCmd;

		if (!this.isMavenOnline() ) {
			if (rc.trim().startsWith(MAVEN_EXE_PATH)) {
				rc = MAVEN_EXE_PATH + " --offline" + this.loadGeneratorLaunchCmd.substring(MAVEN_EXE_PATH.length());
			}
		}

		if (this.isSnail4jMavenRepo() ) {
			if (rc.trim().startsWith(MAVEN_EXE_PATH)) {
				rc = MAVEN_EXE_PATH + " -Dmaven.repo.local=#{mavenRepositoryHome}" + this.loadGeneratorLaunchCmd.substring(MAVEN_EXE_PATH.length());
			}
		}
		return rc;

	}

	/**
	 * In the getters for the launch commands (this.getLoadGeneratorLaunchCmd and getProcessManagerLaunchCmd ),
	 * some code decides whether Maven "offline" variables should be used.
	 * Since this.isMavenOnline() must make the final decision on whether offline mode will be used, don't allow maven offline variables
	 * to be set in the corresponding setters.
	 *
	 *  The file processManagager/pom.xml invokes 'other' mvn commands.
	 *  properties in snail4j.json determine when these "passthru" parameters are passed t0 processManagager/pom.xml.
	 *  The values of these "passthru" parameters are optionally used by those 'other' mvn commands.
	 */
	private String stripOfflineVariables(String val) {
		//the order of these two lines is critical.  if reversed, the longer pattern won't be found.
		val = val.replaceAll(" -Dsnail4j.maven.offline.passthru=--offline", "");
		val = val.replaceAll(" --offline", "");
		return val;
	}
	/**
	 * In the getters for the launch commands (this.getLoadGeneratorLaunchCmd and getProcessManagerLaunchCmd ),
	 * some code decides whether the Snail4j Maven repo variable should be used.
	 * Since this.isSnail4jMavenRepo() must make the final decision on which repo will be used, don't allow maven local repo variable
	 * to be set in the corresponding setters.
	 */
	private String stripSnail4jMavenRepoVariable(String val) {

		//the order of these two lines is critical.  if reversed, the longer pattern won't be found.
		val = val.replaceAll(" -Dsnail4j.maven.repo.passthru=-Dmaven.repo.local=\\#\\{mavenRepositoryHome\\}", "");
		val = val.replaceAll(" -Dmaven.repo.local=\\#\\{mavenRepositoryHome\\}", "");
		return val;
	}
	@Override
	public void setLoadGeneratorLaunchCmd(String val) {

		val = stripOfflineVariables(val);
		val = stripSnail4jMavenRepoVariable(val);

		this.loadGeneratorLaunchCmd = val;
	}

	@Override
	public String getProcessManagerLaunchCmd() {
		String rc = this.processManagerLaunchCmd;

		if (!this.isMavenOnline() ) {
			if (rc.trim().startsWith(MAVEN_EXE_PATH)) {
				rc = MAVEN_EXE_PATH + " -Dsnail4j.maven.offline.passthru=--offline --offline" + this.processManagerLaunchCmd.substring(MAVEN_EXE_PATH.length());
			}
		}

		if (this.isSnail4jMavenRepo() ) {
			if (rc.trim().startsWith(MAVEN_EXE_PATH)) {
				rc = MAVEN_EXE_PATH + " -Dsnail4j.maven.repo.passthru=-Dmaven.repo.local=#{mavenRepositoryHome} -Dmaven.repo.local=#{mavenRepositoryHome}" + this.processManagerLaunchCmd.substring(MAVEN_EXE_PATH.length());
			}
		}
		return rc;
	}

	@Override
	public void setProcessManagerLaunchCmd(String val) {
		val = stripOfflineVariables(val);
		val = stripSnail4jMavenRepoVariable(val);

		this.processManagerLaunchCmd = val;
	}
	@Override
	public String getProcessManagerZipFileName() {
		return this.processManagerZipFileName;
	}
	@Override
	public void setProcessManagerZipFileName(String val) {
		this.processManagerZipFileName = val;
	}
	@Override
	public Path getProcessManagerHome() {
		return this.processManagerHome;
	}

	@Override
	public void setProcessManagerHome(Path val) {
		this.processManagerHome = val;
	}
	@Override
	public void setJMeterNonGuiPort(long val) {
		this.jmeterNonGuiPort = val;
	}
	@Override
	public long getJMeterNonGuiPort() {
		return this.jmeterNonGuiPort;
	}
	@Override
	public void setStartJMeterNonGuiPort(long val) {
		this.startJMeterNonGuiPort = val;
	}
	@Override
	public long getStartJMeterNonGuiPort() {
		return this.startJMeterNonGuiPort;
	}

	@Override
	public String getWiremockHostname() {
		return this.wiremockHostname;
	}

	@Override
	public int getWiremockPort() {
		return this.wiremockPort;
	}

	@Override
	public String getH2Hostname() {
		return this.h2Hostname;
	}

	@Override
	public int getH2Port() {
		return h2Port;
	}
	public void setWiremockHostname(String wiremockHostname) {
		this.wiremockHostname = wiremockHostname;
	}

	public void setWiremockPort(int wiremockPort) {
		this.wiremockPort = wiremockPort;
	}

	public void setH2Hostname(String h2Hostname) {
		this.h2Hostname = h2Hostname;
	}

	public void setH2Port(int h2Port) {
		this.h2Port = h2Port;
	}


	@Override
	public String getWiremockStopStdoutLogFileName() {
		return this.wiremockStopStdoutLogFileName;
	}


	@Override
	public void setWiremockStopStdoutLogFileName(String val) {
		this.wiremockStopStdoutLogFileName = val;
		
	}


	@Override
	public void setGlowrootPort(int i) {
		this.glowrootPort = i;
	}
	@Override
	public int getGlowrootPort() {
		return this.glowrootPort;
	}


	@Override
	public String getJMeterZipFileNameWithoutExtension() {
		return this.jmeterZipFileNameWithoutExtension;
	}
	@Override
	public void setJMeterZipFileNameWithoutExtension(String val) {
		this.jmeterZipFileNameWithoutExtension = val;
	}


	@Override
	public Path getJMeterDistHome() {
		return jmeterDistHome;
	}


	@Override
	public void setJMeterDistHome(Path val) {
		this.jmeterDistHome = val;
	}


	@Override
	public void setSutAppHostname(String val) {
		this.sutAppHostname = val;
	}
	@Override
	public String getSutAppHostname() {
		return this.sutAppHostname;
	}

}
