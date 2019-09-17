package com.github.eostermueller.havoc;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.tjp.launcher.PathUtil;
import com.github.eostermueller.tjp.launcher.agent.Configuration;

/**
 * The SpringBootStartupInstaller will handle progress meter
 * for installation activity in this class.
 * Files to be unzipped:
 * <pre>
 * /Users/erikostermueller/Documents/src/jssource/havoc2/havoc2/backend/src/main/resources
drwxr-xr-x  4 erikostermueller  staff        128 Jul 13 13:26 ..
-rw-r--r--  1 erikostermueller  staff        100 Sep  4 00:39 application.properties
-rw-r--r--  1 erikostermueller  staff    9102386 Sep  4 02:13 apache-maven-3.6.0-bin.zip
-rw-r--r--  1 erikostermueller  staff  561333342 Sep  4 02:13 repository.zip
-rw-r--r--  1 erikostermueller  staff     140497 Sep  4 02:13 sut.zip
drwxr-xr-x  7 erikostermueller  staff        224 Sep 15 10:30 .
-rw-r--r--@ 1 erikostermueller  staff    3056049 Sep 15 10:30 wiremock-2.24.1.jar
 
 * </pre>
 * @author erikostermueller
 *
 */
public class PerfGoatInstaller {
	private Configuration cfg;
	public PerfGoatInstaller(Configuration val) {
		this.cfg = val;
	}
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	/**
	 * @optimization:  all install* methods could be invoked in separate threads.
	 */
  public void install() throws PerfGoatException {

	  PathUtil pathUtil = new PathUtil();
	  
    try {
    	pathUtil.createPerfGoatHomeIfNotExist();
    	
    	installMaven(cfg);
    	installMavenRepository(cfg);
    	installSut(cfg);
    	installWiremock(cfg);
    	installH2DbData(cfg);
    	
	} catch (PerfGoatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw e;
	}

  }
  /**
   * Unzip wiremock executable jar file to its own folder on the file system, but don't unzip it!
   * http://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-standalone/2.24.1/wiremock-standalone-2.24.1.jar
   * @param cfg
   * @throws Exception
   */
	private void installWiremock(Configuration cfg) throws PerfGoatException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		
		try {
			File wiremockFolder = cfg.getWiremockHome().toFile();
			if (wiremockFolder.exists()) {
				LOGGER.info("dir for wiremock excutable jar exists.");
			} else {
				LOGGER.info("Creating dir for wiremock excutable jar");
				wiremockFolder.mkdirs();
			}
			
			Path targetWiremockZipFile = Paths.get( wiremockFolder.getAbsolutePath(), cfg.getWiremockZipFileName() );
			if (targetWiremockZipFile.toFile().exists()) {
				LOGGER.info("wiremock excutable jar exists. will not overwrite.");
				
			} else {
				if (path.contains(PathUtil.JAR_SUFFIX)) {
					/**
					 * wiremock zip needs to be extracted from executable jar file to its own folder
					 */
					cleansedPath = pathUtil.cleanPath(path);
		    		LOGGER.info("About to unzip [" + cfg.getWiremockZipFileName() + "] from [" + cleansedPath + "] to [" + targetWiremockZipFile + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, cfg.getMavenZipFileName(), targetWiremockZipFile.toString() );
		    		
		    		LOGGER.info("does [" + targetWiremockZipFile.toFile().getAbsolutePath().toString() + "] exist? [" + targetWiremockZipFile.toFile().exists() + "]" );
					
				} else {
					LOGGER.error("launch as 'java -jar <perfGoat.jar> to get wiremock to install");
				}
			}
		} catch (Exception e) {
			throw new PerfGoatException(e);
		}

	}
  /**
   * When the SUT launches from maven, use the following parameter to specify the 
   * local repository (which will be packaged inside PerfGoat to improve starup perf and enabl offline installations).
   * <pre>
   * -Dmaven.repo.local=/my/rep0
   * </pre>
   * 
   * @param cfg
   * @throws Exception
   */
	private void installMavenRepository(Configuration cfg) throws PerfGoatException {
		  PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		String zipName = "repository.zip";
		
		try {
			Path targetMavenRepositoryZipFile = Paths.get( cfg.getPerfGoatHome().toString(), zipName );
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
		    		
		    		pathUtil.unzip(targetMavenRepositoryZipFile.toFile(), cfg.getPerfGoatHome().toString() );
		    		targetMavenRepositoryZipFile.toFile().delete();
		    	}
				
			} else {
				LOGGER.error("launch as 'java -jar <perfGoat.jar> to get maven to install");
			}
		} catch (Exception e) {
			throw new PerfGoatException(e);
		}

	}  
	private void installH2DbData(Configuration cfg) throws PerfGoatException {
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
				LOGGER.error("launch as 'java -jar <perfGoat.jar> to get maven to install");
			}
			
		} catch (Exception e) {
			throw new PerfGoatException(e);
		}
	}  
  
	private void installMaven(Configuration cfg) throws PerfGoatException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		try {
			Path targetMavenZipFile = Paths.get( cfg.getPerfGoatHome().toString(), cfg.getMavenZipFileName() );
			if (path.contains(PathUtil.JAR_SUFFIX)) {
				
				/**
				 * maven zip needs to be extracted from executable jar file
				 */
				cleansedPath = pathUtil.cleanPath(path);
		    	if ( !cfg.getMavenHome().toFile().exists() ) {
		    		LOGGER.info("About to unzip [" + cfg.getMavenZipFileName() + "] from [" + cleansedPath + "] to [" + targetMavenZipFile + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, cfg.getMavenZipFileName(), targetMavenZipFile.toString() );
		    		
		    		LOGGER.info("does [" + targetMavenZipFile.toFile().getAbsolutePath().toString() + "] exist? [" + targetMavenZipFile.toFile().exists() + "]" );
		    		
		    		pathUtil.unzip(targetMavenZipFile.toFile(), cfg.getPerfGoatHome().toString() );
		    		targetMavenZipFile.toFile().delete();
		    	}
				
			} else {
				LOGGER.error("launch as 'java -jar <perfGoat.jar> to get maven to install");
			}
		} catch (Exception e) {
			throw new PerfGoatException(e);
		}

		
	}  
	private void installSut(Configuration cfg) throws PerfGoatException {
		  PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		String zipName = "sut.zip";
		try {
			Path targetSutZipFile = Paths.get( cfg.getPerfGoatHome().toString(), zipName );
			if (path.contains(PathUtil.JAR_SUFFIX)) {
				
				/**
				 * SUT (system under test) zip needs to be extracted from executable jar file
				 */
				cleansedPath = pathUtil.cleanPath(path);
		    	if ( !cfg.getSutHome().toFile().exists() ) {
		    		LOGGER.info("About to unzip [" + zipName + "] from [" + cleansedPath + "] to [" + targetSutZipFile + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, zipName, targetSutZipFile.toString() );
		    		
		    		LOGGER.info("does [" + targetSutZipFile.toFile().getAbsolutePath().toString() + "] exist? [" + targetSutZipFile.toFile().exists() + "]" );
		    		
		    		pathUtil.unzip(targetSutZipFile.toFile(), cfg.getPerfGoatHome().toString() );
		    		targetSutZipFile.toFile().delete();
		    	}
				
			} else {
				LOGGER.error("launch as 'java -jar <perfGoat.jar> to get maven to install");
			}
		} catch (Exception e) {
			throw new PerfGoatException(e);
		}

		
	}  

}
