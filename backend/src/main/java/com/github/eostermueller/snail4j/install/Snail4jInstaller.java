package com.github.eostermueller.snail4j.install;

import java.io.File;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.Application;
import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.config.GenericConfigFileReaderWriter;
import com.github.eostermueller.snail4j.install.InstallAdvice.StartupLogger;
import com.github.eostermueller.snail4j.install.Installer;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Messages;
import com.github.eostermueller.snail4j.util.JdkUtils;
import com.github.eostermueller.snail4j.util.JsonPatchUtils;
import com.github.eostermueller.snail4j.util.NonStaticOsUtils;
import com.github.eostermueller.snail4j.util.OS;
import com.github.eostermueller.snail4j.util.OS.OsFamily;
import com.github.eostermueller.snail4j.util.OsUtils;
import com.github.eostermueller.snail4j.util.PathUtil;
import com.github.eostermueller.snail4j.util.OsUtils.OsResult;

/**
 * The SpringBootStartupInstaller will (ultimately) handle progress meter
 * for installation activity in this class.
 * Files to be unzipped:
 * <pre>
 * backend/src/main/resources
drwxr-xr-x  4 erikostermueller  staff        128 Jul 13 13:26 ..
-rw-r--r--  1 erikostermueller  staff        100 Sep  4 00:39 application.properties
-rw-r--r--  1 erikostermueller  staff    9102386 Sep  4 02:13 apache-maven-3.6.2-bin.zip
-rw-r--r--  1 erikostermueller  staff  561333342 Sep  4 02:13 repository.zip
-rw-r--r--  1 erikostermueller  staff     140497 Sep  4 02:13 sut.zip
drwxr-xr-x  7 erikostermueller  staff        224 Sep 15 10:30 .
-rw-r--r--@ 1 erikostermueller  staff    3056049 Sep 15 10:30 wiremock-2.24.1.jar
 
 * </pre>
 * @author erikostermueller
 *
 */
public class Snail4jInstaller implements InstallAdvice.StartupLogger {
	public static final String LOG_PREFIX = "#### ";
	
	Messages messages = null;
	public Snail4jInstaller() throws CannotFindSnail4jFactoryClass {
		messages = DefaultFactory.getFactory().getMessages();

	}

	/**
	 * Here is some research from the JDKs installed on my machine:
	 * java -XshowSettings:properties -version
	 * 
     * java.specification.version = 14
     * java.specification.version = 1.8
     * java.specification.version = 9

	 * THe JAVA_HOME validation is a must for Mac because of the complicated folder structure of the distribution.
	 * But regardless of the OS, snail4j can't find jcmd and other tools in the path, it uses JAVA_HOME to location them.
	 * @return
	 * @throws Snail4jException 
	 */
	public int preInstallJavaValidation(Configuration cfg) throws MalformedURLException, Snail4jException {
		int errorCount = 0;
		
		InstallAdvice ia = new InstallAdvice((InstallAdvice.StartupLogger)this);
		
		ia.validateThisJvmIsJdk();//this agent regularly uses jcmd, probably other jdk cmds.
		
		ia.validateJavaSpecificationVersion();
		Path sutJdk = ia.getJdkForSUT();
		if (sutJdk!=null) {
			cfg.setSutJDK(sutJdk);
		} 	

		LOGGER.debug(LOG_PREFIX+String.format("Detected [%d] JDK issues",errorCount));
		return errorCount;
	}
	public int preInstallValidation(Configuration cfg) throws MalformedURLException, Snail4jException {
		InstallAdvice ia = new InstallAdvice((InstallAdvice.StartupLogger)this);
		int errorCount = 0;

		try {
			DiskSpaceValidator diskSpaceValidator = DefaultFactory.getFactory().getDiskSpaceValidator();
			diskSpaceValidator.validate();
			ia.startupLogger.debug("No exception from DiskSpaceValidator");
		} catch (Snail4jException s) {
			errorCount++;
			ia.startupLogger.error(s.getMessage());
		}
		try {
			AvailableMemoryValidator availableMemoryValidator = DefaultFactory.getFactory().getAvailableMemoryValidator();
			availableMemoryValidator.validate();
			ia.startupLogger.debug("No exception from AvailableMemoryValidator");
		} catch (Snail4jException s) {
			errorCount++;
			ia.startupLogger.error(s.getMessage());
		}
		
		errorCount += preInstallJavaValidation(cfg);
		int countOfUnavailablePorts = ia.getCountOfUnavailableSUTTcpPorts(cfg);
		switch(countOfUnavailablePorts) {
		case 0:
			//zero problems
			break;
		case 4:
			info("Found processes listening on all 4 SUT ports -- assuming the SUT is up."); //not an error
			break;
		default:
				/**
				 * assuming that either:
				 * a) SUT is only partially up, and end user will have to force it down.
				 * b) the ports in use are actually collisions, likely to be non-Workbench processes are using same ports at Workbench.
				 */
				errorCount += countOfUnavailablePorts;
				break;
		}
		
		info("Number if install issues: " + errorCount );

		return errorCount;
	}

	Configuration getConfiguration() throws Snail4jException {
		return DefaultFactory.getFactory().getConfiguration();
	}
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	/**
	 * @optimization:  all install* methods could be invoked in separate threads.
	 */
  public void install() throws Snail4jException {
	  
    try {
    	
    	createLogDir();
    	
    	installMaven();
    	installMavenRepository();
    	DefaultFactory
    		.getFactory()
    		.createSutInstaller()
    		.install();
    	installWiremock();
    	installH2DbData();
    	installJMeterFiles();
    	installJMeterDistribution();
    	installGlowroot();
    	installProcessManager();
    	
	} catch (Snail4jException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw e;
	}

  }
  
	private void installJMeterDistribution() throws Snail4jException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		String jmeterDistZip = this.getConfiguration().getJMeterZipFileNameWithoutExtension() + ".zip";
		try {
			Path targetJMeterZipFile = Paths.get( this.getConfiguration().getSnail4jHome().toString(), jmeterDistZip );
			if (pathUtil.isUberJar()) {
				
				/**
				 * jmeter zip needs to be extracted from executable jar file
				 */
				cleansedPath = pathUtil.cleanPath(path);
		    	if ( !this.getConfiguration().getJMeterDistHome().toFile().exists() ) {
		    		LOGGER.info("About to unzip [" + jmeterDistZip + "] from [" + cleansedPath + "] to [" + targetJMeterZipFile + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, jmeterDistZip, targetJMeterZipFile.toString() );
		    		
		    		LOGGER.info("does [" + targetJMeterZipFile.toFile().getAbsolutePath().toString() + "] exist? [" + targetJMeterZipFile.toFile().exists() + "]" );
		    		
		    		pathUtil.unzip(targetJMeterZipFile.toFile(), this.getConfiguration().getSnail4jHome().toString() );
		    		targetJMeterZipFile.toFile().delete();
		    	}
		    	if (!this.getConfiguration().isOsWin() ) {
			    	File jmeterBinFolder = new File(this.getConfiguration().getJMeterDistHome().toFile(), "bin");
			    	File jmeterExe = new File( jmeterBinFolder, "jmeter.sh");
			    	if (jmeterExe.exists()) {
			    		String cmd = "chmod +x " + jmeterExe.getAbsolutePath().toString();
			    		OsUtils.executeProcess_bash(cmd, jmeterBinFolder);
			    	} else {
			    		String err= "java.util.File is reporting that the jmeter.sh executable doesn't exist.  [" + jmeterExe.toString() + "].  Cmon, we just installed it.  It should be there!";
			    		LOGGER.error(err);
			    		throw new Snail4jException(err);
			    	}
			    	File jmeterExe2 = new File( jmeterBinFolder, "jmeter");
			    	if (jmeterExe2.exists()) {
			    		String cmd = "chmod +x " + jmeterExe2.getAbsolutePath().toString();
			    		OsUtils.executeProcess_bash(cmd, jmeterBinFolder);
			    	} else {
			    		String err= "java.util.File is reporting that the jmeter executable doesn't exist.  [" + jmeterExe2.toString() + "].  Cmon, we just installed it.  It should be there!";
			    		LOGGER.error(err);
			    		throw new Snail4jException(err);
			    	}
			    	
			    	File shutdownExe = new File( jmeterBinFolder, "shutdown.sh");
			    	if (shutdownExe.exists()) {
			    		String cmd = "chmod +x " + shutdownExe.getAbsolutePath().toString();
			    		OsUtils.executeProcess_bash(cmd, jmeterBinFolder);
			    	} else {
			    		String err= "java.util.File is reporting that the jmeter shutdown.sh executable doesn't exist.  [" + shutdownExe.toString() + "].  Cmon, we just installed it.  It should be there!";
			    		LOGGER.error(err);
			    		throw new Snail4jException(err);
			    	}
		    	}
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
		} catch (Exception e) {
			throw new Snail4jException(e);
		}
	
		
	}  
/**
   * Would be nice to just pull glowroot from maven, so I tried referencing this:
   * <pre>
   * https://search.maven.org/artifact/org.glowroot/glowroot-agent/0.13.5/jar
   * </pre>
   * 
   * but ran into this exact problem:
   * <pre>
   * https://github.com/glowroot/glowroot/issues/582
   * </pre>
   * ....so my fallback plan is this method.
   * @param cfg2
 * @throws Snail4jException 
   */
	 private void installGlowroot() throws Snail4jException {
			PathUtil pathUtil = new PathUtil();
			String path = pathUtil.getBaseClassspath();
			String cleansedPath;
			try {
				Path targetGlowrootZipFile = Paths.get( this.getConfiguration().getSnail4jHome().toString(), this.getConfiguration().getGlowrootZipFileName() );
				if (pathUtil.isUberJar()) {
					
					/**
					 * glowroot agent jar needs to be extracted from zip
					 */
					cleansedPath = pathUtil.cleanPath(path);
					
			    	if ( !this.getConfiguration().getGlowrootHome().toFile().exists() ) {
			    		LOGGER.info("About to unzip [" + this.getConfiguration().getGlowrootZipFileName() + "] from [" + cleansedPath + "] to [" + targetGlowrootZipFile + "]");
			    		pathUtil.extractZipFromZip(cleansedPath, this.getConfiguration().getGlowrootZipFileName(), targetGlowrootZipFile.toString() );
			    		
			    		LOGGER.info("does [" + targetGlowrootZipFile.toFile().getAbsolutePath().toString() + "] exist? [" + targetGlowrootZipFile.toFile().exists() + "]" );
			    		
			    		pathUtil.unzip(targetGlowrootZipFile.toFile(), this.getConfiguration().getGlowrootHome().toString() );
			    		targetGlowrootZipFile.toFile().delete();
			    	}
			    	
			    	optionallyCreateGlowrootAdminJson();
			    	
					
				} else {
					LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
				}
			} catch (Exception e) {
				throw new Snail4jException(e);
			}
			
			
		}
	 /**
	  * Makes Glowroot available from a machine other than the one where the ubar jar was launched from.
	  * https://github.com/glowroot/glowroot/wiki/Agent-Installation-%28with-Embedded-Collector%29
	  * 
	  * @throws Snail4jException
	  */
	 private void optionallyCreateGlowrootAdminJson() throws Snail4jException {
		 String origAdminJsonString = null;
		 String patchedJsonString = null;
		 String adminJsonPath = null;
		  try {
		    	if (this.getConfiguration().isHeadless() ) {
		    		
		    		if (!this.getConfiguration().getGlowrootHome().toFile().exists()) {
		    			throw new Snail4jException("Was expecting the glowroot folder in load-test-in-a-box home folder to exist: " + this.getConfiguration().getGlowrootHome().toString() );
		    		}
		    		
		    		Path adminJsonHome = Paths.get( this.getConfiguration().getGlowrootHome().toString() , "glowroot");
		    		if (!adminJsonHome.toFile().exists()) {
		    			throw new Snail4jException("Was expecting glowroot/glowroot folder to exist inside the load-test-in-a-box home folder: " + adminJsonHome.toString() );
		    		}
		    		
		    		Path adminJson = Paths.get( adminJsonHome.toString() , "admin.json");
		    		adminJsonPath = adminJson.toString();
		    		
		    		GenericConfigFileReaderWriter readerWriter = DefaultFactory.getFactory().getGenericConfigReaderWriter();
		    		
		    		if (adminJson.toFile().exists()) {
		    			adminJsonPath = adminJson.toString();
		    			origAdminJsonString = readerWriter.read(adminJson);
		    			ObjectMapper objectMapper = new ObjectMapper();
		    			JsonNode adminJsonNode = objectMapper.readTree(origAdminJsonString);
		    			JsonPatchUtils utils = new JsonPatchUtils();

		    			JsonNode fullyPatched = utils.setGlowrootBindAddress(adminJsonNode,"0.0.0.0");
		    			patchedJsonString = fullyPatched.toPrettyString();
		    			readerWriter.write(patchedJsonString, adminJson);
		    			
		    		} else {
			    		String bareBonesAdminJson = "{\r\n"
			    				+ "  \"web\": {\r\n"
			    				+ "    \"bindAddress\": \"0.0.0.0\"\r\n"
			    				+ "  }\r\n"
			    				+ "}";
			    		readerWriter.write(bareBonesAdminJson, adminJson);
		    		}
		    	}
		  } catch (Exception e) {
			  throw new Snail4jException(e,"Unsuccessful write to [" + adminJsonPath 
			  	+ " file.  Original data [" + origAdminJsonString + "] patched data["
			  	+ patchedJsonString + "]");
		  }

	
}

	private void createLogDir() throws Snail4jException {
	  
	  File logDir = this.getConfiguration().getLogDir().toFile();
	  if (!logDir.exists())
		  logDir.mkdirs();
	}
protected void installProcessManager() throws Snail4jException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		
		
		try {
			Path targetProcessManagerZipFile = Paths.get( this.getConfiguration().getProcessManagerHome().toString(), this.getConfiguration().getProcessManagerZipFileName() );
			
			if (this.getConfiguration().getProcessManagerHome().toFile().exists()) {
				LOGGER.info("dir for processManager already exists.");
			} else {
				LOGGER.info("Creating dir for processManager files");
				this.getConfiguration().getProcessManagerHome().toFile().mkdirs();
			}
			
			if (pathUtil.isUberJar()) {
				
				/**
				 * processManager files must be extracted from a zip.
				 */
				cleansedPath = pathUtil.cleanPath(path);
				
		    	if ( targetProcessManagerZipFile.toFile().exists() ) {
		    		LOGGER.info("processManager.zip exists. will not overwrite [" + targetProcessManagerZipFile.toString() + "]");
		    	} else {
		    		LOGGER.info("About to unzip [" + targetProcessManagerZipFile.toString() + "] from [" + cleansedPath + "] to [" + targetProcessManagerZipFile.toString() + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, this.getConfiguration().getProcessManagerZipFileName(), targetProcessManagerZipFile.toString() );
		    	}
		    	
		    	String[] fileNames=this.getConfiguration().getProcessManagerHome().toFile().list();
		    	
	    		LOGGER.info("[" + fileNames.length  + "] file(s) exist(s) in [" + this.getConfiguration().getProcessManagerHome() + "]");
	    		
	    		if (fileNames.length<1) {
	    			throw new Snail4jException("Install failed.  Tried to uznip [" + this.getConfiguration().getProcessManagerZipFileName() + "] to [" + this.getConfiguration().getProcessManagerHome() + "] but [" + targetProcessManagerZipFile.toString() + "] does not exist." );
	    		} else if (fileNames.length==1 && fileNames[0].equals(this.getConfiguration().getProcessManagerZipFileName()) ) {
	        		pathUtil.unzip(targetProcessManagerZipFile.toFile(), this.getConfiguration().getProcessManagerHome().toString() );
	        		targetProcessManagerZipFile.toFile().delete(); // don't need anymore because we just unzipped its contents.
	    		} else {
	        		LOGGER.info("Will not unzip [" + this.getConfiguration().getProcessManagerZipFileName() + "] to avoid overwriting local changes to unzipped files. Delete all files in USER_HOME/" + Application.INSTALL_ROOT  + "/processManager and restart snail4j executable jar");
	    		}
		    	
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get processManager to install");
			}
			
		} catch (Exception e) {
			throw new Snail4jException(e);
		}		
	}
  
  protected void installJMeterFiles() throws Snail4jException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		
		
		try {
			Path targetJMeterFilesZipFile = Paths.get( this.getConfiguration().getJMeterFilesHome().toString(), this.getConfiguration().getJMeterFilesZipFileName() );
			
			if (this.getConfiguration().getJMeterFilesHome().toFile().exists()) {
				LOGGER.info("dir for jmeter files already exists.");
			} else {
				LOGGER.info("Creating dir for jmter .jmx plan files and the maven file to launch jmeter.");
				this.getConfiguration().getJMeterFilesHome().toFile().mkdirs();
			}
			
			if (pathUtil.isUberJar()) {
				
				/**
				 * jmeter files must be extracted from a zip.
				 */
				cleansedPath = pathUtil.cleanPath(path);
				
		    	if ( targetJMeterFilesZipFile.toFile().exists() ) {
		    		LOGGER.info("jmeterFiles.zip exists. will not overwrite [" + targetJMeterFilesZipFile.toString() + "]");
		    	} else {
		    		LOGGER.info("About to unzip [" + targetJMeterFilesZipFile.toString() + "] from [" + cleansedPath + "] to [" + targetJMeterFilesZipFile.toString() + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, this.getConfiguration().getJMeterFilesZipFileName(), targetJMeterFilesZipFile.toString() );
		    	}
		    	
		    	String[] fileNames=this.getConfiguration().getJMeterFilesHome().toFile().list();
		    	
	    		LOGGER.info("[" + fileNames.length  + "] file(s) exist(s) in [" + this.getConfiguration().getJMeterFilesHome() + "]");
	    		
	    		if (fileNames.length<1) {
	    			throw new Snail4jException("Install failed.  Tried to uznip [" + this.getConfiguration().getJMeterFilesZipFileName() + "] to [" + this.getConfiguration().getJMeterFilesHome() + "] but [" + targetJMeterFilesZipFile.toString() + "] does not exist." );
	    		} else if (fileNames.length==1 && fileNames[0].equals(this.getConfiguration().getJMeterFilesZipFileName()) ) {
	        		pathUtil.unzip(targetJMeterFilesZipFile.toFile(), this.getConfiguration().getJMeterFilesHome().toString() );
	        		targetJMeterFilesZipFile.toFile().delete(); // don't need anymore because we just unzipped its contents.
	    		} else {
	        		LOGGER.info("Will not unzip [" + this.getConfiguration().getJMeterFilesZipFileName() + "] to avoid overwriting local changes to unzipped files. Delete all files in USER_HOME/" + Application.INSTALL_ROOT  +"/jmeterFiles and restart snail4j executable jar");
	    		}
		    	
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get JMeter test files to install");
			}
			
		} catch (Exception e) {
			throw new Snail4jException(e);
		}		
	}
/**
   * Unzip wiremock executable jar file to its own folder on the file system, but don't unzip it!
   * http://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-standalone/2.24.1/wiremock-standalone-2.24.1.jar
   * @param cfg
   * @throws Exception
   */
	protected void installWiremock() throws Snail4jException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		
		
		try {
			Path targetWiremockFilesZipFile = Paths.get( this.getConfiguration().getWiremockFilesHome().toString(), this.getConfiguration().getWiremockFilesZipFileName() );
			
			if (this.getConfiguration().getWiremockFilesHome().toFile().exists()) {
				LOGGER.info("dir for wiremock files already exists.");
			} else {
				LOGGER.info("Creating dir for jmter .jmx plan files and the maven file to launch wiremock.");
				this.getConfiguration().getWiremockFilesHome().toFile().mkdirs();
			}
			
			if (pathUtil.isUberJar()) {
				
				/**
				 * wiremock files must be extracted from a zip.
				 */
				cleansedPath = pathUtil.cleanPath(path);
				
		    	if ( targetWiremockFilesZipFile.toFile().exists() ) {
		    		LOGGER.info("wiremockFiles.zip exists. will not overwrite [" + targetWiremockFilesZipFile.toString() + "]");
		    	} else {
		    		LOGGER.info("About to unzip [" + targetWiremockFilesZipFile.toString() + "] from [" + cleansedPath + "] to [" + targetWiremockFilesZipFile.toString() + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, this.getConfiguration().getWiremockFilesZipFileName(), targetWiremockFilesZipFile.toString() );
		    	}
		    	
		    	String[] fileNames=this.getConfiguration().getWiremockFilesHome().toFile().list();
		    	
	    		LOGGER.info("[" + fileNames.length  + "] file(s) exist(s) in [" + this.getConfiguration().getWiremockFilesHome() + "]");
	    		
	    		if (fileNames.length<1) {
	    			throw new Snail4jException("Install failed.  Tried to unzip [" + this.getConfiguration().getWiremockFilesZipFileName() + "] to [" + this.getConfiguration().getWiremockFilesHome() + "] but [" + targetWiremockFilesZipFile.toString() + "] does not exist." );
	    		} else if (fileNames.length==1 && fileNames[0].equals(this.getConfiguration().getWiremockFilesZipFileName()) ) {
	        		pathUtil.unzip(targetWiremockFilesZipFile.toFile(), this.getConfiguration().getWiremockFilesHome().toString() );
	        		targetWiremockFilesZipFile.toFile().delete(); // don't need anymore because we just unzipped its contents.
	    		} else {
	        		LOGGER.info("Will not unzip [" + this.getConfiguration().getWiremockFilesZipFileName() + "] to avoid overwriting local changes to unzipped files. Delete all files in USER_HOME/." + Application.INSTALL_ROOT  + "/wiremockFiles and restart snail4j executable jar");
	    		}
		    	
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
			
		} catch (Exception e) {
			throw new Snail4jException(e);
		}		
	}
  /**
   * When the SUT launches from maven, use the following parameter to specify the 
   * local repository (which will be packaged inside snail4j to improve starup perf and enabl offline installations).
   * <pre>
   * -Dmaven.repo.local=/my/rep0
   * </pre>
   * 
   * @param cfg
   * @throws Exception
   */
	protected void installMavenRepository() throws Snail4jException {
		  PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		String zipName = "repository.zip";
		
		try {
			Path targetMavenRepositoryZipFile = Paths.get( this.getConfiguration().getSnail4jHome().toString(), zipName );
			if (pathUtil.isUberJar()) {
				
				/**
				 * maven repository zip needs to be extracted from executable jar file
				 */
				cleansedPath = pathUtil.cleanPath(path);
		    	if ( this.getConfiguration().getMavenRepositoryHome().toFile().exists() ) {
		    		LOGGER.info("Maven home exists. will not overwrite [" + this.getConfiguration().getMavenRepositoryHome().toString() + "]");
		    	} else {
		    		LOGGER.info("About to unzip [" + zipName + "] from [" + cleansedPath + "] to [" + targetMavenRepositoryZipFile + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, zipName, targetMavenRepositoryZipFile.toString() );
		    		
		    		LOGGER.info("does [" + targetMavenRepositoryZipFile.toFile().getAbsolutePath().toString() + "] exist? [" + targetMavenRepositoryZipFile.toFile().exists() + "]" );
		    		
		    		pathUtil.unzip(targetMavenRepositoryZipFile.toFile(), this.getConfiguration().getSnail4jHome().toString() );
		    		targetMavenRepositoryZipFile.toFile().delete();
		    	}
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
		} catch (Exception e) {
			throw new Snail4jException(e);
		}

	}  
	protected void installH2DbData() throws Snail4jException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		String zipName = "data.zip";
		
		
		try {
			Path targetH2ZipFile = Paths.get( this.getConfiguration().getH2DataFileHome().toString(), zipName );
			Path targetH2DataFile = Paths.get( this.getConfiguration().getH2DataFileHome().toString(), this.getConfiguration().getH2DataFileName() );
			
			if (this.getConfiguration().getH2DataFileHome().toFile().exists()) {
				LOGGER.info("dir for h2 db already exists.");
			} else {
				LOGGER.info("Creating dir for h2 db data file");
				this.getConfiguration().getH2DataFileHome().toFile().mkdirs();
			}
			
			if (pathUtil.isUberJar()) {
				
				cleansedPath = pathUtil.cleanPath(path);
				
		    	if ( targetH2ZipFile.toFile().exists() ) {
		    		LOGGER.info("H2Data home exists. will not overwrite [" + targetH2ZipFile.toString() + "]");
		    	} else {
		    		LOGGER.info("About to unzip [" + zipName + "] from [" + cleansedPath + "] to [" + targetH2ZipFile.toString() + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, zipName, targetH2ZipFile.toString() );
		    	}
		    	
	    		LOGGER.info("does [" + targetH2DataFile.toFile().getAbsolutePath().toString() + "] exist? [" + targetH2DataFile.toFile().exists() + "]" );
	    		if (targetH2DataFile.toFile().exists()) {
	        		LOGGER.info("[" + targetH2DataFile.toFile().getAbsolutePath().toString() + "] does exist. will not overwrite.");
	    		} else {
	        		pathUtil.unzip(targetH2ZipFile.toFile(), this.getConfiguration().getH2DataFileHome().toString() );
	        		targetH2ZipFile.toFile().delete(); // don't need anymore because we just unzipped its contents.
	    		}
		    	
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
			
		} catch (Exception e) {
			throw new Snail4jException(e);
		}
	}  
  
	protected void installMaven() throws Snail4jException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		try {
			Path targetMavenZipFile = Paths.get( this.getConfiguration().getSnail4jHome().toString(), this.getConfiguration().getMavenZipFileName() );
			if (pathUtil.isUberJar()) {
				
				/**
				 * maven zip needs to be extracted from executable jar file
				 */
				cleansedPath = pathUtil.cleanPath(path);
		    	if ( !this.getConfiguration().getMavenHome().toFile().exists() ) {
		    		LOGGER.info("About to unzip [" + this.getConfiguration().getMavenZipFileName() + "] from [" + cleansedPath + "] to [" + targetMavenZipFile + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, this.getConfiguration().getMavenZipFileName(), targetMavenZipFile.toString() );
		    		
		    		LOGGER.info("does [" + targetMavenZipFile.toFile().getAbsolutePath().toString() + "] exist? [" + targetMavenZipFile.toFile().exists() + "]" );
		    		
		    		pathUtil.unzip(targetMavenZipFile.toFile(), this.getConfiguration().getSnail4jHome().toString() );
		    		targetMavenZipFile.toFile().delete();
		    	}
		    	if (!this.getConfiguration().isOsWin() ) {
			    	File mavenBinFolder = new File(this.getConfiguration().getMavenHome().toFile(), "bin");
			    	File mavenExe = new File( mavenBinFolder, "mvn");
			    	if (mavenExe.exists()) {
			    		String cmd = "chmod +x " + mavenExe.getAbsolutePath().toString();
			    		OsUtils.executeProcess_bash(cmd, mavenBinFolder);
			    	} else {
			    		String err= "java.util.File is reporting that the maven executable doesn't exist.  [" + mavenExe.toString() + "].  Cmon, we just installed it.  It should be there!";
			    		LOGGER.error(err);
			    		throw new Snail4jException(err);
			    	}
		    	}
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
		} catch (Exception e) {
			throw new Snail4jException(e);
		}

		
	}  
	@Override
	public void error(String msg) {
		System.out.println(LOG_PREFIX + "ERROR: " + msg);		
	}
	@Override
	public void info(String msg) {
		System.out.println(LOG_PREFIX+ "INFO:  " + msg);
	}  
	@Override
	public void debug(String msg) {
		if (LOGGER.isDebugEnabled())
			System.out.println(LOG_PREFIX+"DEBUG: "+msg);
	}

	@Override
	public void warn(String msg) {
		System.out.println(LOG_PREFIX+ "WARN:  " + msg);		
	}  

}
