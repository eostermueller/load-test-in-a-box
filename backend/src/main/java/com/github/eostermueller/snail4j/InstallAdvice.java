package com.github.eostermueller.snail4j;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Messages;

/**
 * Each method here must invoke LOGGER.error(...) with internationalized text.
 * @author eoste
 *
 */
public class InstallAdvice {
	Messages m = null;
	private static String[] UNSUPPORTED_JAVA_SPECIFICATION_VERSIONS = new String[]{ "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7" };
	public InstallAdvice() throws CannotFindSnail4jFactoryClass {
		Messages m = DefaultFactory.getFactory().getMessages();
	}

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	
	public boolean isJavaSpecificationVersionOk() throws Snail4jException {
		boolean rc = true;

		String currentJavaSpecificationVersion = System.getProperty("java.specification.version");
		
		rc = JdkUtils.isJavaSpecificationInList(
				currentJavaSpecificationVersion,
				UNSUPPORTED_JAVA_SPECIFICATION_VERSIONS);

		if (!rc)
			LOGGER.error( m.unsupportedJavaVersion ( currentJavaSpecificationVersion, UNSUPPORTED_JAVA_SPECIFICATION_VERSIONS ) );
			
		return !rc;
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
			LOGGER.error( m.javaHomeEnvVarNotSet() );
		} else {
			File javaHomeFolder = Paths.get(javaHome).toFile();
			if (!javaHomeFolder.exists() || !javaHomeFolder.isDirectory()) {
				LOGGER.error( m.javaHomeFolderDoesNotExistOrLackingPermissions(javaHomeFolder) );
			} else {
				rc = true;
			}
		}

		if (!rc) {
			LOGGER.error( new DocumentationLinks().getJdkInstallAdvice().toString() );
		}
		
		return rc;
	}

}
