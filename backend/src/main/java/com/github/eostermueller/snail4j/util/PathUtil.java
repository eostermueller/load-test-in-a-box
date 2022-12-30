package com.github.eostermueller.snail4j.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.Application;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Configuration;

public class PathUtil {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	  public final static String JAR_SUFFIX = ".jar";
	  public final static String ZIP_SUFFIX = ".zip";
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
	  
	  /**
	   * If this code is currently running inside an uber jar, return true.
	   * 
	   * The Performance Analysis Workbench is currently distributed from (for example): 
	   * https://github.com/eostermueller/snail4j/releases/download/ALPHA.0.0.1/snail4j.ALPHA.0.0.1.zip
	   *  
	   * https://stackoverflow.com/questions/11947037/what-is-an-uber-jar
	   * 
	   * 
	   * @return
	   */
	  public boolean isUberJar() {
		  boolean rc = false;
		  String baseClasspath = this.getBaseClassspath();
		  if (baseClasspath.contains(PathUtil.JAR_SUFFIX)
			  || baseClasspath.contains(PathUtil.ZIP_SUFFIX) ) {
			  rc = true;
		  }
		  return rc;
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
		  
		  if (myEndIndex < 0)
			  myEndIndex = basePath.toLowerCase().indexOf(ZIP_SUFFIX);
		  
		  if (myEndIndex <0) {
			  throw new Exception("could find neither [" + JAR_SUFFIX + "] nor [" + ZIP_SUFFIX + "] in  [" + basePath + "]");
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
		/**
		 * Link [1] says that the method {@link java.nio.file.Path#subpath(int, int)}  "Returns the subsequence of the Path (not including a root element) as specified by the beginning and ending indexes."
		 * I think it's great that it's so specific saying the root is omitted!
		 * 
		 * Unfortunately, the phrasing in the javadoc -- link [2] -- says "Returns a relative Path that is a subsequence of the name elements of this path."
		 * IMHO,  Link [2] should include the "not including a root element" phrase (or similar) found in [2].
		 * [1] https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html
		 * [2] https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/nio/file/Path.html#subpath(int,int)
		 * 
		 * @param searchPath
		 * @param pathElementCriteria
		 * @return
		 */
		public static Path getParentOfSpecificPathElement(Path searchPath, String pathElementCriteria) {
			Path rc = null;
			if (searchPath!=null) {
				for(int i = searchPath.getNameCount() -1; i > 0 ; i--) {
					if (searchPath.getName(i).toString().contentEquals(pathElementCriteria)) {
						rc = searchPath.getRoot().resolve( searchPath.subpath(0, i) );
						break;
					}
				}
			}
			return rc;
		}
	   
	  public void delete(File f) throws IOException {
		   if (f.isDirectory()) {
		     for (File c : f.listFiles())
		       delete(c);
		   }
		   if (!f.delete())
		     throw new FileNotFoundException("Failed to delete file: " + f);
		 }
	  }
