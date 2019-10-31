package com.github.eostermueller.snail4j.launcher;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author erikostermueller
 *
 */
public class DefaultConfiguration implements Configuration {

	public static final String MAVEN_EXE_PATH = "#{mavenExePath}";
	private static final String SPACE = " ";
	private String sutAppZipFileName;
	private long sutAppPort;
	private long loadGenerationThreads;
	private long loadGenerationTargetPort;
	private long loadGenerationRampupTimeInSeconds;
	private long loadGenerationDurationInSeconds;
	private String loadGenerationTargetHost;
	private boolean osWin;
	private String mavenExePath;
	private boolean mavenOnline = false;//the local maven repo distributed with snail4j should be all that is necessary

	/**
	 * This is the most important constructor in the project :-)
	 */
	public DefaultConfiguration() {
    		this.setUserHomeDir(		Paths.get( getUserHomeDirString() )	);
		
			this.setJavaHome( 			Paths.get( System.getProperty("java.home")  ) );
			this.setSnail4jHome(		Paths.get( this.getUserHomeDirString(), ".snail4j" )			);
			this.setGlowrootHome(			Paths.get( this.getSnail4jHome().toString() , "glowroot") );
			this.setGlowrootZipFileName ("glowroot-0.13.5-dist.zip");
			
			this.setMavenHome(			Paths.get( this.getSnail4jHome().toString() , this.getMavenZipFileNameWithoutExtension() )		);
			this.setMavenRepositoryHome(Paths.get( this.getSnail4jHome().toString() , "repository" )		);
			
			this.setSutAppHome(			Paths.get( this.getSnail4jHome().toString() , "sutApp") );
			this.setSutAppZipFileName ("sutApp.zip");
			
			this.setSutKillFile(        Paths.get( this.getSnail4jHome().toString() , "deleteMeToStopSnail4jSut.txt") );
			
			
			this.setLogDir(             Paths.get( this.getSnail4jHome().toString() , "log" )		);
			this.setSystemUnderTestStdoutLogFileName("systemUnderTest.log");
			this.setLoadGeneratorStdoutLogFileName("loadGenerator.log");

			this.setWiremockFilesHome(		Paths.get( this.getSnail4jHome().toString() , "wiremock") );
			this.setWiremockFilesZipFileName ("wiremockFiles.zip");
			
			
			this.setProcessManagerHome(		Paths.get( this.getSnail4jHome().toString() , "processManager") );
			this.setProcessManagerZipFileName ("processManager.zip");

			this.setJMeterNonGuiPort(4455);
			
			this.setH2DataFileHome(		Paths.get( this.getSnail4jHome().toString() , "data") );
			this.setH2DataFileName		("perfSandboxDb.mv.db");
			
			this.setJMeterFilesZipFileName("jmeterFiles.zip");
			this.setJMeterFilesHome(Paths.get( this.getSnail4jHome().toString() , "jmeterFiles") );
			
			
			this.setLoadGenerationTargetHost("localhost");
			this.setLoadGenerationThreads(3); //3T0TT -- from http://bit.ly/2017tjp
			this.setLoadGenerationTargetPort(8080);
			this.setLoadGenerationRampupTimeInSeconds(3);
			
			/**
			 * For 99% of the time, don't expect a single test run to last more than 20 minutes....so 60 minutes should be more than enough.
			 */
			this.setLoadGenerationDurationInSeconds(3600);
			
			
			this.setJMeterNonGuiPort(4455);
			
			/**
			 * https://jmeteronthefly.blogspot.com/2018/12/pass-parameters-from-jmeter-maven-plugin.html
			 * 
			 * The variables/names detailed below must stay in sync with variables in these two files:
			 * snail4j/jmeterFiles/pom-load.xml
			 * snail4j/jmeterFiles/load.jmx
			 */
			StringBuilder sb = new StringBuilder();
			
			                 sb.append(MAVEN_EXE_PATH);
//			sb.append(SPACE);sb.append("--offline");
			sb.append(SPACE);sb.append("-f #{jmeterFilesHome}/pom-load.xml");
			sb.append(SPACE);sb.append("-Pno-gui");
			sb.append(SPACE);sb.append("clean verify");
			sb.append(SPACE);sb.append("-Dsnail4j.jmeter.port=#{jmeterNonGuiPort}");
			sb.append(SPACE);sb.append("-Dmaven.repo.local=#{mavenRepositoryHome}");
			sb.append(SPACE);sb.append("-Djmeter.test=#{jmeterFilesHome}/load.jmx");
			sb.append(SPACE);sb.append("-DmyHost=#{loadGenerationTargetHost}");
			sb.append(SPACE);sb.append("-DmyPort=#{loadGenerationTargetPort}");
			sb.append(SPACE);sb.append("-DmyDurationInSeconds=#{loadGenerationDurationInSeconds}");
			sb.append(SPACE);sb.append("-DmyRampupInSeconds=#{loadGenerationRampupTimeInSeconds}");
			sb.append(SPACE);sb.append("-DmyThreads=#{loadGenerationThreads}");
			this.setLoadGeneratorLaunchCmd(sb.toString());
			//mvn clean verify -Pno-gui

			/*
			 * ToDo:  embed parameters in the following for tcp listen ports for each SUT component:  h2, wiremock, tjp
			 * 
			 * Use same approach as this, ab0ve: -Dsnail4j.jmeter.port=#{jmeterNonGuiPort}
			 * 
			 */
//			this.setProcessManagerLaunchCmd("#{mavenExePath} -Dmaven.repo.local=#{mavenRepositoryHome} --offline verify");
			this.setProcessManagerLaunchCmd("#{mavenExePath} -Dmaven.repo.local=#{mavenRepositoryHome} verify");
			
			if (getOsName().contains("windows")) {  
				this.setOsWin(true);
			}
			
			this.setMavenExePath( createMavenExePath() ); 
			
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
		return this.getMavenHome().toString() + "/bin/" + this.getMavenExeName();
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
	
	@Override	
	public void setMavenExePath(String val) {
		this.mavenExePath = val;
	}
	@Override	
	public String getMavenExePath() {
		return this.mavenExePath;
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
	public void setLoadGenerationTargetHost(String val) {
		this.loadGenerationTargetHost = val;
	}
	@Override
	public String getLoadGenerationTargetHost() {
		return this.loadGenerationTargetHost;
	}
	@Override
	public void setLoadGenerationDurationInSeconds(long val) {
		this.loadGenerationDurationInSeconds = val;
	}
	@Override
	public long getLoadGenerationDurationInSeconds() {
		return this.loadGenerationRampupTimeInSeconds;
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
	public void setLoadGenerationTargetPort(long val) {
		this.loadGenerationTargetPort = val;
	}
	@Override
	public long getLoadGenerationTargetPort() {
		return this.loadGenerationTargetPort;
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
	private String mavenZipFileNameWithoutExtension = "apache-maven-3.6.2";
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
	public Path getJavaHome() {
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

	@Override
	public String getLoadGeneratorLaunchCmd() {
		String rc = this.loadGeneratorLaunchCmd;
		
		if (!this.isMavenOnline() ) {
			if (rc.trim().startsWith(this.MAVEN_EXE_PATH)) {
				rc = this.MAVEN_EXE_PATH + " --offline" + this.loadGeneratorLaunchCmd.substring(this.MAVEN_EXE_PATH.length()); 
			}
		}
		
		return rc;
		
	}

	@Override
	public void setLoadGeneratorLaunchCmd(String val) {
		this.loadGeneratorLaunchCmd = val;
	}

	@Override
	public String getProcessManagerLaunchCmd() {
		String rc = this.processManagerLaunchCmd;
		
		if (!this.isMavenOnline() ) {
			if (rc.trim().startsWith(this.MAVEN_EXE_PATH)) {
				rc = this.MAVEN_EXE_PATH + " -Dsnail4j.maven.offline.passthru=--offline --offline" + this.processManagerLaunchCmd.substring(this.MAVEN_EXE_PATH.length()); 
			}
		}
		
		return rc;
	}

	@Override
	public void setProcessManagerLaunchCmd(String val) {
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


}
