package com.github.eostermueller.havoc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.havoc.launcher.CannotFindTjpFactoryClass;
import com.github.eostermueller.havoc.launcher.Configuration;
import com.github.eostermueller.havoc.launcher.DefaultFactory;

public class PathUtil {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	  public final static String JAR_SUFFIX = ".jar";
	  private final String FILE_PREFIX = "file:";
	  
	  /**
	   * if running under executable jar -- it returns the path to that file....else the base classpath.
	   * I kept this method separate from cleanPath() because it seems like this might change a bit by platform.
	   * @stolenFrom: https://stackoverflow.com/a/6849255/2377579
	   * @return
	   */
	  public String getBaseClassspath() {
		  return Application.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	  }
	  
	  public void createPerfGoatHomeIfNotExist() throws CannotFindTjpFactoryClass {
		  Configuration cfg = DefaultFactory.getFactory().getConfiguration();
		  File havocHomeDir = cfg.getPerfGoatHome().toFile();
		  if (!havocHomeDir.exists())
			  havocHomeDir.mkdirs();
	  }
	  
	  /**
	   * Given the path to a zip file, locate a different zip file inside and extract it to the targetPath.
	   * @param zipPath
	   * @param critereia
	   * @param targetPath
	   * @stolenFrom https://stackoverflow.com/a/30755984/2377579
	   * @throws IOException
	   */
	public void extractZipFromZip(final String zipPath, final String critereia, final String targetPath) throws IOException {
        LOGGER.info("Does criteria [" + critereia + "] match any of these entries in zip file [" + zipPath + "]?");

	    try (ZipFile zipFile = new ZipFile(zipPath)) {
	        for (final Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();) {
	            ZipEntry zipEntry = e.nextElement();
	            LOGGER.debug("Does criteria match entry [" + zipEntry.getName() + "]");
	            if (zipEntry.getName().endsWith(critereia)) {
	                Files.copy(zipFile.getInputStream(zipEntry), Paths.get(targetPath),
	                           StandardCopyOption.REPLACE_EXISTING);
	            }
	        }
	    }
	}

	/**
	 * Get rid of FILE_PREFIX and unused bits after JAR_SUFFIX
	 * @param basePath
	 * @return
	 * @throws Exception
	 */
	public String cleanPath(String basePath) throws Exception {
		  int beginIndex = basePath.toLowerCase().indexOf(FILE_PREFIX);
		  if (beginIndex <0) {
			  beginIndex = 0;
	      } else {
	    	  beginIndex = this.FILE_PREFIX.length();
	      }
		  
		  int myEndIndex = basePath.toLowerCase().indexOf(JAR_SUFFIX);
		  if (myEndIndex <0) {
			  throw new Exception("could not find [" + JAR_SUFFIX + "] in  [" + basePath + "]");
		  }
		  String newPath = basePath.substring(beginIndex, myEndIndex+JAR_SUFFIX.length() );
		  return newPath;
	}
	/**
	 * 
	 * @param source
	 * @param out
	 * @throws IOException
	 * @stolenFrom https://stackoverflow.com/a/41776933/2377579
	 */
	   public void unzip(File source, String outDir) throws IOException {
		   this.LOGGER.info("Unzipping file [" + source.toString() + "] to [" + outDir + "]");
		    try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source))) {

		        ZipEntry entry = zis.getNextEntry();

		        while (entry != null) {

		            File file = new File(outDir, entry.getName());

		            if (entry.isDirectory()) {
		                file.mkdirs();
		                this.LOGGER.debug("[" + entry.getName() + "][" + entry.getSize()  + "] in zip is a directory.");
		            } else {
		                this.LOGGER.debug("[" + entry.getName() + "][" + entry.getSize() +  "] in zip is a file.");
		                File parent = file.getParentFile();

		                if (!parent.exists()) {
		                    parent.mkdirs();
		                }
		                this.LOGGER.debug("just created parent [" + parent.toString() + "]");
		                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {

		                	long size = entry.getSize();
		                	if (size<0)
		                		size = 0x4000;
		                	
		                    byte[] buffer = new byte[Math.toIntExact(size)];

		                    int location;

		                    while ((location = zis.read(buffer)) != -1) {
		                        bos.write(buffer, 0, location);
		                    }
		                }
		            }
		            entry = zis.getNextEntry();
		        }
		    }
		}	

}
