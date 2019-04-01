package com.github.eostermueller.havoc;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.github.eostermueller.tjp.launcher.PathUtil;
import com.github.eostermueller.tjp.launcher.agent.Configuration;
import com.github.eostermueller.tjp.launcher.agent.DefaultFactory;

@Component
public class MavenInstaller
implements ApplicationListener<ApplicationReadyEvent> {

  /**
   * This event is executed as late as conceivably possible to indicate that 
   * the application is ready to service requests.
   */
  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {

	  PathUtil pathUtil = new PathUtil();
	  
    try {
        Configuration cfg = DefaultFactory.getFactory().getConfiguration();
    	pathUtil.createHavocHomeIfNotExist();
    	
    	String path = pathUtil.getBaseClassspath();
    	String cleansedPath;
    	if (path.contains(PathUtil.JAR_SUFFIX)) {
    		
    		/**
    		 * maven zip needs to be extracted from executable jar file
    		 */
    		cleansedPath = pathUtil.cleanPath(path);
        	if ( !cfg.getMavenHome().toFile().exists() ) {
        		File mvnZip = new File(cfg.getHavocHomeDir().toFile(), cfg.getMavenZipFileName());
        		pathUtil.extractZipFromZip(cleansedPath, cfg.getMavenZipFileName(), cfg.getHavocHomeDir().toString() );
        		pathUtil.unzip(mvnZip, cfg.getHavocHomeDir().toString() );
        		mvnZip.delete();
        	}
    		
    	} else {
    		cleansedPath = path;
        	if ( !cfg.getMavenHome().toFile().exists() ) {
        		File mvnZip = new File(cleansedPath, cfg.getMavenZipFileName());
        		pathUtil.unzip(mvnZip, cfg.getHavocHomeDir().toString() );
        		mvnZip.delete();
        	}
    	}
    	
    	
    	
		
	} catch (UnsupportedEncodingException | URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    return;
  }  
}
