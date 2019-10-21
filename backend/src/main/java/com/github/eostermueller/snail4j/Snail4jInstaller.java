package com.github.eostermueller.snail4j;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.launcher.CannotFindTjpFactoryClass;
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
	private Configuration cfg;
	public Snail4jInstaller(Configuration val) {
		this.cfg = val;
	}
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	/**
	 * @optimization:  all install* methods could be invoked in separate threads.
	 */
  public void install() throws Snail4jException {

	  PathUtil pathUtil = new PathUtil();
	  
    try {
    	pathUtil.createSnail4jHomeIfNotExist();
    	createLogDir();
    	
    	installMaven(cfg);
    	installMavenRepository(cfg);
    	installSutApp(cfg);
    	installWiremock(cfg);
    	installH2DbData(cfg);
    	installJMeterFiles(cfg);
    	installGlowroot(cfg);
    	installProcessManager(cfg);
    	
    	LOGGER.info("Install finished.  Ready to load test!");
    	
	} catch (Snail4jException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw e;
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
  private void installGlowroot(Configuration cfg2) throws Snail4jException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		try {
			Path targetGlowrootZipFile = Paths.get( cfg.getSnail4jHome().toString(), cfg.getGlowrootZipFileName() );
			if (path.contains(PathUtil.JAR_SUFFIX)) {
				
				/**
				 * glowroot agent jar needs to be extracted from zip
				 */
				cleansedPath = pathUtil.cleanPath(path);
		    	if ( !cfg.getGlowrootHome().toFile().exists() ) {
		    		LOGGER.info("About to unzip [" + cfg.getGlowrootZipFileName() + "] from [" + cleansedPath + "] to [" + targetGlowrootZipFile + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, cfg.getGlowrootZipFileName(), targetGlowrootZipFile.toString() );
		    		
		    		LOGGER.info("does [" + targetGlowrootZipFile.toFile().getAbsolutePath().toString() + "] exist? [" + targetGlowrootZipFile.toFile().exists() + "]" );
		    		
		    		pathUtil.unzip(targetGlowrootZipFile.toFile(), cfg.getGlowrootHome().toString() );
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
	  Configuration cfg = DefaultFactory.getFactory().getConfiguration();
	  File logDir = cfg.getLogDir().toFile();
	  if (!logDir.exists())
		  logDir.mkdirs();
	}
protected void installProcessManager(Configuration cfg2) throws Snail4jException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		
		
		try {
			Path targetProcessManagerZipFile = Paths.get( cfg.getProcessManagerHome().toString(), cfg.getProcessManagerZipFileName() );
			
			if (cfg.getProcessManagerHome().toFile().exists()) {
				LOGGER.info("dir for processManager already exists.");
			} else {
				LOGGER.info("Creating dir for processManager files");
				cfg.getProcessManagerHome().toFile().mkdirs();
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
		    		pathUtil.extractZipFromZip(cleansedPath, cfg.getProcessManagerZipFileName(), targetProcessManagerZipFile.toString() );
		    	}
		    	
		    	String[] fileNames=cfg.getProcessManagerHome().toFile().list();
		    	
	    		LOGGER.info("[" + fileNames.length  + "] file(s) exist(s) in [" + cfg.getProcessManagerHome() + "]");
	    		
	    		if (fileNames.length<1) {
	    			throw new Snail4jException("Install failed.  Tried to uznip [" + cfg.getProcessManagerZipFileName() + "] to [" + cfg.getProcessManagerHome() + "] but [" + targetProcessManagerZipFile.toString() + "] does not exist." );
	    		} else if (fileNames.length==1 && fileNames[0].equals(cfg.getProcessManagerZipFileName()) ) {
	        		pathUtil.unzip(targetProcessManagerZipFile.toFile(), cfg.getProcessManagerHome().toString() );
	        		targetProcessManagerZipFile.toFile().delete(); // don't need anymore because we just unzipped its contents.
	    		} else {
	        		LOGGER.info("Will not unzip [" + cfg.getProcessManagerZipFileName() + "] to avoid overwriting local changes to unzipped files. Delete all files in USER_HOME/.snail4j/processManager and restart snail4j executable jar");
	    		}
		    	
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
			
		} catch (Exception e) {
			throw new Snail4jException(e);
		}		
	}
  
  protected void installJMeterFiles(Configuration cfg2) throws Snail4jException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		
		
		try {
			Path targetJMeterFilesZipFile = Paths.get( cfg.getJMeterFilesHome().toString(), cfg.getJMeterFilesZipFileName() );
			
			if (cfg.getJMeterFilesHome().toFile().exists()) {
				LOGGER.info("dir for jmeter files already exists.");
			} else {
				LOGGER.info("Creating dir for jmter .jmx plan files and the maven file to launch jmeter.");
				cfg.getJMeterFilesHome().toFile().mkdirs();
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
		    		pathUtil.extractZipFromZip(cleansedPath, cfg.getJMeterFilesZipFileName(), targetJMeterFilesZipFile.toString() );
		    	}
		    	
		    	String[] fileNames=cfg.getJMeterFilesHome().toFile().list();
		    	
	    		LOGGER.info("[" + fileNames.length  + "] file(s) exist(s) in [" + cfg.getJMeterFilesHome() + "]");
	    		
	    		if (fileNames.length<1) {
	    			throw new Snail4jException("Install failed.  Tried to uznip [" + cfg.getJMeterFilesZipFileName() + "] to [" + cfg.getJMeterFilesHome() + "] but [" + targetJMeterFilesZipFile.toString() + "] does not exist." );
	    		} else if (fileNames.length==1 && fileNames[0].equals(cfg.getJMeterFilesZipFileName()) ) {
	        		pathUtil.unzip(targetJMeterFilesZipFile.toFile(), cfg.getJMeterFilesHome().toString() );
	        		targetJMeterFilesZipFile.toFile().delete(); // don't need anymore because we just unzipped its contents.
	    		} else {
	        		LOGGER.info("Will not unzip [" + cfg.getJMeterFilesZipFileName() + "] to avoid overwriting local changes to unzipped files. Delete all files in USER_HOME/.snail4j/jmeterFiles and restart snail4j executable jar");
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
	protected void installWiremock(Configuration cfg) throws Snail4jException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		
		
		try {
			Path targetWiremockFilesZipFile = Paths.get( cfg.getWiremockFilesHome().toString(), cfg.getWiremockFilesZipFileName() );
			
			if (cfg.getWiremockFilesHome().toFile().exists()) {
				LOGGER.info("dir for wiremock files already exists.");
			} else {
				LOGGER.info("Creating dir for jmter .jmx plan files and the maven file to launch wiremock.");
				cfg.getWiremockFilesHome().toFile().mkdirs();
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
		    		pathUtil.extractZipFromZip(cleansedPath, cfg.getWiremockFilesZipFileName(), targetWiremockFilesZipFile.toString() );
		    	}
		    	
		    	String[] fileNames=cfg.getWiremockFilesHome().toFile().list();
		    	
	    		LOGGER.info("[" + fileNames.length  + "] file(s) exist(s) in [" + cfg.getWiremockFilesHome() + "]");
	    		
	    		if (fileNames.length<1) {
	    			throw new Snail4jException("Install failed.  Tried to unzip [" + cfg.getWiremockFilesZipFileName() + "] to [" + cfg.getWiremockFilesHome() + "] but [" + targetWiremockFilesZipFile.toString() + "] does not exist." );
	    		} else if (fileNames.length==1 && fileNames[0].equals(cfg.getWiremockFilesZipFileName()) ) {
	        		pathUtil.unzip(targetWiremockFilesZipFile.toFile(), cfg.getWiremockFilesHome().toString() );
	        		targetWiremockFilesZipFile.toFile().delete(); // don't need anymore because we just unzipped its contents.
	    		} else {
	        		LOGGER.info("Will not unzip [" + cfg.getWiremockFilesZipFileName() + "] to avoid overwriting local changes to unzipped files. Delete all files in USER_HOME/.snail4j/wiremockFiles and restart snail4j executable jar");
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
	protected void installMavenRepository(Configuration cfg) throws Snail4jException {
		  PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		String zipName = "repository.zip";
		
		try {
			Path targetMavenRepositoryZipFile = Paths.get( cfg.getSnail4jHome().toString(), zipName );
			if (path.contains(PathUtil.JAR_SUFFIX)) {
				
				/**
				 * maven repository zip needs to be extracted from executable jar file
				 */
				cleansedPath = pathUtil.cleanPath(path);
		    	if ( cfg.getMavenRepositoryHome().toFile().exists() ) {
		    		LOGGER.info("Maven home exists. will not overwrite [" + cfg.getMavenRepositoryHome().toString() + "]");
		    	} else {
		    		LOGGER.info("About to unzip [" + zipName + "] from [" + cleansedPath + "] to [" + targetMavenRepositoryZipFile + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, zipName, targetMavenRepositoryZipFile.toString() );
		    		
		    		LOGGER.info("does [" + targetMavenRepositoryZipFile.toFile().getAbsolutePath().toString() + "] exist? [" + targetMavenRepositoryZipFile.toFile().exists() + "]" );
		    		
		    		pathUtil.unzip(targetMavenRepositoryZipFile.toFile(), cfg.getSnail4jHome().toString() );
		    		targetMavenRepositoryZipFile.toFile().delete();
		    	}
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
		} catch (Exception e) {
			throw new Snail4jException(e);
		}

	}  
	protected void installH2DbData(Configuration cfg) throws Snail4jException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		String zipName = "data.zip";
		
		
		try {
			Path targetH2ZipFile = Paths.get( cfg.getH2DataFileHome().toString(), zipName );
			Path targetH2DataFile = Paths.get( cfg.getH2DataFileHome().toString(), cfg.getH2DataFileName() );
			
			if (cfg.getH2DataFileHome().toFile().exists()) {
				LOGGER.info("dir for h2 db already exists.");
			} else {
				LOGGER.info("Creating dir for h2 db data file");
				cfg.getH2DataFileHome().toFile().mkdirs();
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
	        		pathUtil.unzip(targetH2ZipFile.toFile(), cfg.getH2DataFileHome().toString() );
	        		targetH2ZipFile.toFile().delete(); // don't need anymore because we just unzipped its contents.
	    		}
		    	
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
			
		} catch (Exception e) {
			throw new Snail4jException(e);
		}
	}  
  
	protected void installMaven(Configuration cfg) throws Snail4jException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		try {
			Path targetMavenZipFile = Paths.get( cfg.getSnail4jHome().toString(), cfg.getMavenZipFileName() );
			if (path.contains(PathUtil.JAR_SUFFIX)) {
				
				/**
				 * maven zip needs to be extracted from executable jar file
				 */
				cleansedPath = pathUtil.cleanPath(path);
		    	if ( !cfg.getMavenHome().toFile().exists() ) {
		    		LOGGER.info("About to unzip [" + cfg.getMavenZipFileName() + "] from [" + cleansedPath + "] to [" + targetMavenZipFile + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, cfg.getMavenZipFileName(), targetMavenZipFile.toString() );
		    		
		    		LOGGER.info("does [" + targetMavenZipFile.toFile().getAbsolutePath().toString() + "] exist? [" + targetMavenZipFile.toFile().exists() + "]" );
		    		
		    		pathUtil.unzip(targetMavenZipFile.toFile(), cfg.getSnail4jHome().toString() );
		    		targetMavenZipFile.toFile().delete();
		    	}
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
		} catch (Exception e) {
			throw new Snail4jException(e);
		}

		
	}  
	protected void installSutApp(Configuration cfg) throws Snail4jException {
		  PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		
		try {
			Path targetSutAppZipFile = Paths.get( cfg.getSutAppHome().toString(), cfg.getSutAppZipFileName() );
			
			if (cfg.getSutAppHome().toFile().exists()) {
				LOGGER.info("dir for sutApp files already exists.");
			} else {
				LOGGER.info("Creating dir for sut java app.");
				cfg.getSutAppHome().toFile().mkdirs();
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
		    		pathUtil.extractZipFromZip(cleansedPath, cfg.getSutAppZipFileName(), targetSutAppZipFile.toString() );
		    	}
		    	
		    	String[] fileNames=cfg.getSutAppHome().toFile().list();
		    	
	    		LOGGER.info("[" + fileNames.length  + "] file(s) exist(s) in [" + cfg.getSutAppHome() + "]");
	    		
	    		if (fileNames.length<1) {
	    			throw new Snail4jException("Install failed.  Tried to unzip [" + cfg.getSutAppZipFileName() + "] to [" + cfg.getSutAppHome() + "] but [" + targetSutAppZipFile.toString() + "] does not exist." );
	    		} else if (fileNames.length==1 && fileNames[0].equals(cfg.getSutAppZipFileName()) ) {
	        		pathUtil.unzip(targetSutAppZipFile.toFile(), cfg.getSutAppHome().toString() );
	        		targetSutAppZipFile.toFile().delete(); // don't need anymore because we just unzipped its contents.
	    		} else {
	        		LOGGER.info("Will not unzip [" + cfg.getSutAppZipFileName() + "] to avoid overwriting local changes to unzipped files. Delete all files in " + cfg.getSutAppHome() + " and restart snail4j executable jar");
	    		}
				
			} else {
				LOGGER.error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
			
		} catch (Exception e) {
			throw new Snail4jException(e);
		}		
		
		
//		String zipName = cfg.getSutAppZipFileName();
//		try {
//			Path targetSutZipFile = Paths.get( cfg.getSnail4jHome().toString(), zipName );
//			if (path.contains(PathUtil.JAR_SUFFIX)) {
//				
//				/**
//				 * SUT (system under test) zip needs to be extracted from executable jar file
//				 */
//				cleansedPath = pathUtil.cleanPath(path);
//		    	if ( !cfg.getSutAppHome().toFile().exists() ) {
//		    		LOGGER.info("About to unzip [" + zipName + "] from [" + cleansedPath + "] to [" + targetSutZipFile + "]");
//		    		pathUtil.extractZipFromZip(cleansedPath, zipName, targetSutZipFile.toString() );
//		    		
//		    		LOGGER.info("does [" + targetSutZipFile.toFile().getAbsolutePath().toString() + "] exist? [" + targetSutZipFile.toFile().exists() + "]" );
//		    		
//		    		pathUtil.unzip(targetSutZipFile.toFile(), cfg.getSnail4jHome().toString() );
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
