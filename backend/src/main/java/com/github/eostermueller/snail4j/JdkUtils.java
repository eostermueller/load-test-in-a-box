package com.github.eostermueller.snail4j;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.OsUtils.OsResult;
import com.github.eostermueller.snail4j.launcher.Messages;

public class JdkUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(JdkUtils.class);
	
	static Path getCurrentJavaPath() throws Snail4jException {
		String pathOfRunningJava = System.getProperty("sun.boot.library.path");
		
		Path p = Paths.get(pathOfRunningJava);
		if (p==null || p.toFile().getAbsolutePath()==null) {
			throw new Snail4jException("Bug. No Path returned from JdkUtils.getCurrentJavaPath() for 'sun.boot.library.path' path: " + pathOfRunningJava);
		}
		
		return Paths.get(pathOfRunningJava);		
	}
	static boolean pointsToCurrentJava(Path javaHome) throws Snail4jException {

		javaHome = javaHome.normalize();
		
		Path currentJava = getCurrentJavaPath().normalize();
				
		if (currentJava.endsWith( Paths.get("bin") ))
			currentJava = currentJava.getParent();

		return currentJava.startsWith(javaHome);
	}

	public static boolean isJdk() {
		return !(getDirectoryOfJavaCompiler() == null);
		
	}
	/*
	 * @stolenFrom: https://stackoverflow.com/a/58737549/2377579
	 * 
	 * Helpful for debugging:
	 * java -XshowSettings:properties -version
	 * 
	 * OpenJDK "JRE+JDK" Packaging Scenario:
	 * C:\java\java-1.8.0-openjdk-1.8.0.252-2.b09.ojdkbuild.windows.x86_64\java-1.8.0-openjdk-1.8.0.252-2.b09.ojdkbuild.windows.x86_64\jre\bin
	 * --> ..\..\bin\javac.exe
<pre>
	 
├───bin                <<<<======= javac.exe
├───include
│   └───win32
│       └───bridge
├───jre
│   ├───bin            <<<<======= sun.boot.library.path
│   │   └───server
│   └───lib
├───lib
└───webstart	  
</pre>
	 */
	public static Path getDirectoryOfJavaCompiler() {
		Path pathToJavaCompiler = null;
		String path = getInstallPathOfThisJvm();
	    if(path != null) {
	        String javacPath = "";
	        if(path.endsWith(File.separator + "bin")) {
	            javacPath = path;
	        } else {
	            int libIndex = path.lastIndexOf(File.separator + "lib");
	            if(libIndex > 0) {
	                javacPath = path.substring(0, libIndex) + File.separator + "bin";
	            }
	        }
	        pathToJavaCompiler = getDirectoryOfJavaCompiler( Paths.get(javacPath) );
	    }
	    if (pathToJavaCompiler==null)
		    LOGGER.debug(String.format("Failed to locate java compiler executable. The  sun.boot.library.path is %s", path));
	    else
    	    LOGGER.debug(String.format("Found javac executable in %s", pathToJavaCompiler.toAbsolutePath().toString() ));
	    	
	    return pathToJavaCompiler;
	}
	
	public static String getInstallPathOfThisJvm() {
	    String path = System.getProperty("sun.boot.library.path");
	    LOGGER.debug(String.format("sun.boot.library.path is %s", path));
	    return path;
	}
	public static Path getDirectoryOfJavaCompiler(Path path) {
		Path pathToJavaCompiler = null;
		String possiblePathToCompiler = path.toAbsolutePath().toString();
        if(!possiblePathToCompiler.isEmpty()) {
    	    LOGGER.debug(String.format("looking for javac executable in %s", possiblePathToCompiler));
    	    
            if ( new File(possiblePathToCompiler, "javac").exists() || new File(possiblePathToCompiler, "javac.exe").exists() )
            	pathToJavaCompiler = Paths.get(possiblePathToCompiler);
            else {
	            /**  The above will work most of the time.
	             *  The following addresses the OpenJDK "JRE+JDK" Packaging Scenario, detailed in method comments.
	             */
            	possiblePathToCompiler = possiblePathToCompiler + "/../../bin";
	    	    LOGGER.debug(String.format("looking for javac executable in %s", possiblePathToCompiler));
	            if ( new File(possiblePathToCompiler, "javac").exists() || new File(possiblePathToCompiler, "javac.exe").exists() )
	            	pathToJavaCompiler = Paths.get(possiblePathToCompiler);
            }
        }
        return pathToJavaCompiler;
	}
	public static Path get_JAVA_HOME() throws Snail4jException {
		String javaHomeEnvVar = System.getenv("JAVA_HOME");
		Messages m = DefaultFactory.getFactory().getMessages();
		Path p = null;
		if (javaHomeEnvVar!=null && javaHomeEnvVar.trim().length()>0) {
			p = Paths.get(javaHomeEnvVar);
			
			if (!p.toFile().exists() || !p.toFile().isDirectory() ) {
				throw new Snail4jException( m.javaHomeFolderDoesNotExistOrLackingPermissions(p.toFile()) );
			}
		} else {
			throw new Snail4jException(m.javaHomeEnvVarNotSet());
		}
		return p;
	}
	public static ProcessDescriptor[] getJavaProcesses(Path jdkHome) throws Snail4jException {
		OsResult osResult = executeJdkBinCmd(jdkHome,JCMD);
		List<ProcessDescriptor> processes = new ArrayList<ProcessDescriptor>();
		
		String[] javaProcesses = osResult.stdout.split("\\r?\\n");
		for(String oneLine : javaProcesses) {
			int indexOfSpace = oneLine.indexOf(' ');
			if (indexOfSpace > 1) {
				
				long pid = 0;
				try {
					pid = Long.parseLong(
						oneLine.substring(0,indexOfSpace).trim()
						);
				} catch (NumberFormatException e) {
					throw new Snail4jException(String.format("Unable to locate PID after first space char in this JCMD output line [%s]", oneLine));
				}
				processes.add( ProcessDescriptor.create(
						pid, 
						oneLine.substring(indexOfSpace+1, oneLine.length() ).trim()
						) );
			}
		}
		return processes.toArray(new ProcessDescriptor[] {});
		
	}
	public static OsResult executeJdkBinCmd(Path jdkHome, String jdkCmd) throws Snail4jException {
		OsResult osResult = null;
		Messages m = DefaultFactory.getFactory().getMessages();
		
		if (jdkHome.toFile().exists() && jdkHome.toFile().isDirectory() ) {
			String jdkBinCommand = jdkHome.toFile().getAbsolutePath().toString() + File.separator + "bin" + File.separator + jdkCmd;
			osResult = OsUtils.executeProcess(jdkBinCommand);
		} else {
			throw new Snail4jException( m.javaHomeFolderDoesNotExistOrLackingPermissions(jdkHome.toFile()));
		}
		
		return osResult;
	}
	public static class ProcessDescriptor {
		public static ProcessDescriptor create(long pid, String commandLine) {
			ProcessDescriptor pd = new ProcessDescriptor();
			pd.pid = pid;
			pd.commandLine = commandLine;
			
			return pd;
		}
		public String toString() {
			return String.format("pid=%s commandLine=%s", this.pid,this.commandLine);
		}
		public long pid = 0;
		public String commandLine = null;
	}

	/** Object Name of DiagnosticCommandMBean. */
	   public final static String DIAGNOSTIC_COMMAND_MBEAN_NAME =
	      "com.sun.management:type=DiagnosticCommand";

	public static final String JCMD = "jcmd";
	 
	   /** My MBean Server. */
	   private final MBeanServer server = ManagementFactory.getPlatformMBeanServer();
	 
	   private final ObjectName objectName = null;
	 
	   //  . . .
	 
	   /**
	    * Invoke operation on the DiagnosticCommandMBean that accepts
	    *    String array argument but does not require any String
	    *    argument and returns a String.
	    *
	    * @param operationName Name of operation on DiagnosticCommandMBean.
	    * @param operationDescription Description of operation being invoked
	    *    on the DiagnosticCommandMBean.
	    * @return String returned by DiagnosticCommandMBean operation.
	    */
	   private String invokeNoStringArgumentsCommand(
	      final String operationName, final String operationDescription)
	   {
	      String result;
	      try
	      {
	         result = (String) server.invoke(objectName, operationName, new Object[] {null}, new String[]{String[].class.getName()});
	      }
	      catch (InstanceNotFoundException | ReflectionException | MBeanException exception)
	      {
	         result = "ERROR: Unable to access '" + operationDescription + "' - " + exception;
	      }
	      return result;
	   }
	   /**
	    * @param criteria
	    * @param listOfSpecifications
	    * @return
	    */
	public static boolean isJavaSpecificationInList(String criteria,
			String[] listOfSpecifications) throws Snail4jException {
		
		
		if (criteria==null || criteria.length()==0)
			throw new Snail4jException("Snail4j bug.  checking whether the java.specification.version is valid, and was passed a null/zero length string");
		
		if (listOfSpecifications==null || listOfSpecifications.length==0)
			throw new Snail4jException("Snail4j bug.  checking whether the java.specification.version is valid, and was passed a null/zero array");
		
		boolean rc = false;
		
		for (String javaSpecification : listOfSpecifications) {
			if (javaSpecification.equals(criteria))
				rc = true;
		}
		
		return rc;
	}
}
