package com.github.eostermueller.snail4j;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.launcher.CannotFindTjpFactoryClass;
import com.github.eostermueller.snail4j.launcher.ConfigReaderWriter;
import com.github.eostermueller.snail4j.launcher.Configuration;

/**
 * The SpringBootStartupInstaller will handle progress meter
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
public class Snail4jInstaller {
	Configuration getConfiguration() throws CannotFindTjpFactoryClass {
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
    	installSutApp();
    	installWiremock();
    	installH2DbData();
    	installJMeterFiles();
    	installGlowroot();
    	installProcessManager();
    	
    	
	} catch (Snail4jException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw e;
	}

  }
  /**
   * If snail4j.json exists, read it.
   * Else write (aka install) it.
   * @throws Snail4jException
   */
  void initSnail4jCfgFile() throws Snail4jException {
		
		Configuration c = this.getConfiguration();
		ConfigReaderWriter configWriter = DefaultFactory.getFactory().getConfigReaderWriter(c,c.getSnail4jHome().toFile() );
		File snail4jConfig = new File( this.getConfiguration().getSnail4jHome().toFile(),configWriter.getFileName());
		if (snail4jConfig.exists()) {
			c = configWriter.read();
			DefaultFactory.getFactory().setConfiguration(c);
		} else {
			configWriter.write();
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
			if (path.contains(PathUtil.JAR_SUFFIX)) {
				
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
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
		} catch (Exception e) {
			throw new Snail4jException(e);
		}
		
		
	}
private void createLogDir() throws CannotFindTjpFactoryClass {
	  
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
			
			if (path.contains(PathUtil.JAR_SUFFIX)) {
				
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
	        		LOGGER.info("Will not unzip [" + this.getConfiguration().getProcessManagerZipFileName() + "] to avoid overwriting local changes to unzipped files. Delete all files in USER_HOME/.snail4j/processManager and restart snail4j executable jar");
	    		}
		    	
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
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
			
			if (path.contains(PathUtil.JAR_SUFFIX)) {
				
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
	        		LOGGER.info("Will not unzip [" + this.getConfiguration().getJMeterFilesZipFileName() + "] to avoid overwriting local changes to unzipped files. Delete all files in USER_HOME/.snail4j/jmeterFiles and restart snail4j executable jar");
	    		}
		    	
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
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
			
			if (path.contains(PathUtil.JAR_SUFFIX)) {
				
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
	        		LOGGER.info("Will not unzip [" + this.getConfiguration().getWiremockFilesZipFileName() + "] to avoid overwriting local changes to unzipped files. Delete all files in USER_HOME/.snail4j/wiremockFiles and restart snail4j executable jar");
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
			if (path.contains(PathUtil.JAR_SUFFIX)) {
				
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
			
			if (path.contains(PathUtil.JAR_SUFFIX)) {
				
				/**
				 * maven repository zip needs to be extracted from executable jar file
				 */
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
			if (path.contains(PathUtil.JAR_SUFFIX)) {
				
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
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
		} catch (Exception e) {
			throw new Snail4jException(e);
		}

		
	}  
	protected void installSutApp() throws Snail4jException {
		  PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		
		try {
			Path targetSutAppZipFile = Paths.get( this.getConfiguration().getSutAppHome().toString(), this.getConfiguration().getSutAppZipFileName() );
			
			if (this.getConfiguration().getSutAppHome().toFile().exists()) {
				LOGGER.info("dir for sutApp files already exists.");
			} else {
				LOGGER.info("Creating dir for sut java app.");
				this.getConfiguration().getSutAppHome().toFile().mkdirs();
			}
			
			if (path.contains(PathUtil.JAR_SUFFIX)) {
				
				/**
				 * sutApp files must be extracted from a zip.
				 */
				cleansedPath = pathUtil.cleanPath(path);
				
		    	if ( targetSutAppZipFile.toFile().exists() ) {
		    		LOGGER.info(targetSutAppZipFile.toFile().toString() + " exists. will not overwrite it.");
		    	} else {
		    		LOGGER.info("About to unzip [" + targetSutAppZipFile.toString() + "] from [" + cleansedPath + "] to [" + targetSutAppZipFile.toString() + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, this.getConfiguration().getSutAppZipFileName(), targetSutAppZipFile.toString() );
		    	}
		    	
		    	String[] fileNames=this.getConfiguration().getSutAppHome().toFile().list();
		    	
	    		LOGGER.info("[" + fileNames.length  + "] file(s) exist(s) in [" + this.getConfiguration().getSutAppHome() + "]");
	    		
	    		if (fileNames.length<1) {
	    			throw new Snail4jException("Install failed.  Tried to unzip [" + this.getConfiguration().getSutAppZipFileName() + "] to [" + this.getConfiguration().getSutAppHome() + "] but [" + targetSutAppZipFile.toString() + "] does not exist." );
	    		} else if (fileNames.length==1 && fileNames[0].equals(this.getConfiguration().getSutAppZipFileName()) ) {
	        		pathUtil.unzip(targetSutAppZipFile.toFile(), this.getConfiguration().getSutAppHome().toString() );
	        		targetSutAppZipFile.toFile().delete(); // don't need anymore because we just unzipped its contents.
	    		} else {
	        		LOGGER.info("Will not unzip [" + this.getConfiguration().getSutAppZipFileName() + "] to avoid overwriting local changes to unzipped files. Delete all files in " + this.getConfiguration().getSutAppHome() + " and restart snail4j executable jar");
	    		}
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
			
		} catch (Exception e) {
			throw new Snail4jException(e);
		}		
		
		
//		String zipName = this.getConfiguration().getSutAppZipFileName();
//		try {
//			Path targetSutZipFile = Paths.get( this.getConfiguration().getSnail4jHome().toString(), zipName );
//			if (path.contains(PathUtil.JAR_SUFFIX)) {
//				
//				/**
//				 * SUT (system under test) zip needs to be extracted from executable jar file
//				 */
//				cleansedPath = pathUtil.cleanPath(path);
//		    	if ( !this.getConfiguration().getSutAppHome().toFile().exists() ) {
//		    		LOGGER.info("About to unzip [" + zipName + "] from [" + cleansedPath + "] to [" + targetSutZipFile + "]");
//		    		pathUtil.extractZipFromZip(cleansedPath, zipName, targetSutZipFile.toString() );
//		    		
//		    		LOGGER.info("does [" + targetSutZipFile.toFile().getAbsolutePath().toString() + "] exist? [" + targetSutZipFile.toFile().exists() + "]" );
//		    		
//		    		pathUtil.unzip(targetSutZipFile.toFile(), this.getConfiguration().getSnail4jHome().toString() );
//		    		targetSutZipFile.toFile().delete();
//		    	}
//				
//			} else {
//				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
//			}
//		} catch (Exception e) {
//			throw new Snail4jException(e);
//		}

		
	}  

}
