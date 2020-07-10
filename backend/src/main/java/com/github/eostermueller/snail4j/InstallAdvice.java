package com.github.eostermueller.snail4j;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Messages;

/**
 * Each method here must invoke LOGGER.error(...) with localized/internationalized text.
 * @author eoste
 *
 */
public class InstallAdvice {
	public static final String LOG_PREFIX = "#### ";
	Messages messages = null;
	private static String[] UNSUPPORTED_JAVA_SPECIFICATION_VERSIONS = new String[]{ "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7" };
	public InstallAdvice() throws CannotFindSnail4jFactoryClass {
		messages = DefaultFactory.getFactory().getMessages();
	}

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public boolean isJdk() throws Snail4jException {
		boolean rc = true;

		
		rc = JdkUtils.isJdk();

		Path pathOfJava = JdkUtils.getCurrentJavaPath();
		
		if (pathOfJava==null || pathOfJava.toFile().getAbsolutePath()==null) {
			throw new Snail4jException("Unable to find sun.boot.library.path");
		}
		
		String errorMsg = messages.jreIsNotEnough (pathOfJava );
		if (errorMsg==null || "".equals(errorMsg)) {
			throw new Snail4jException("Bug:  could not create error message showing location of java.");
		}
		if (!rc)
			LOGGER.error( LOG_PREFIX+errorMsg );
			
		return rc;
	}
	public boolean JAVA_HOME_pointsToCurrentJava() throws Snail4jException {
		
		
		boolean rc = false;
		rc = JdkUtils.JAVA_HOME_pointsToCurrentJava();
		
		if (!rc)
			LOGGER.error(LOG_PREFIX+
					messages.JAVA_HOME_mustPointToCurrentJava(
					JdkUtils.get_JAVA_HOME(),
					JdkUtils.getCurrentJavaPath()
					));
		return rc;
	}
	
	public boolean isJavaSpecificationVersionOk() throws Snail4jException {
		boolean rc = true;

		String currentJavaSpecificationVersion = System.getProperty("java.specification.version");
		
		if (currentJavaSpecificationVersion==null || "".equals(currentJavaSpecificationVersion)) {
			throw new Snail4jException("Jdk Bug. System.getProperty is not returning a value for java.specification.version.");
		}
		
		boolean ynOnTheUnsupportedList = JdkUtils.isJavaSpecificationInList(
				currentJavaSpecificationVersion,
				UNSUPPORTED_JAVA_SPECIFICATION_VERSIONS);
		
		Path p = JdkUtils.getCurrentJavaPath();
		if (ynOnTheUnsupportedList)
			LOGGER.error( 
					LOG_PREFIX+
					messages.unsupportedJavaVersion ( 
					currentJavaSpecificationVersion, 
					p, 
					UNSUPPORTED_JAVA_SPECIFICATION_VERSIONS 
					)
				);
			
		return !ynOnTheUnsupportedList;
	}
	/**
	 * 
	 * @return
	 * @throws CannotFindSnail4jFactoryClass 
	 * @throws MalformedURLException 
	 */
	public boolean isJavaHomeEnvVarOk() throws CannotFindSnail4jFactoryClass, MalformedURLException {
		boolean rc = false;
		
		
		String javaHome = System.getenv("JAVA_HOME");
		if (javaHome == null) {
			LOGGER.error( 
					LOG_PREFIX+
					messages.javaHomeEnvVarNotSet() );
		} else {
			File javaHomeFolder = Paths.get(javaHome).toFile();
			if (!javaHomeFolder.exists() || !javaHomeFolder.isDirectory()) {
				LOGGER.error( 
						LOG_PREFIX+
						messages.javaHomeFolderDoesNotExistOrLackingPermissions(javaHomeFolder) );
			} else {
				rc = true;
			}
		}

		if (!rc) {
			LOGGER.error( 
					LOG_PREFIX+
					new DocumentationLinks().getJdkInstallAdvice().toString() );
		}
		
		return rc;
	}

}
